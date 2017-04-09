package seedu.todolist.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.todolist.commons.core.ComponentManager;
import seedu.todolist.commons.core.LogsCenter;
import seedu.todolist.commons.core.UnmodifiableObservableList;
import seedu.todolist.commons.events.model.ToDoListChangedEvent;
import seedu.todolist.commons.events.model.ViewListChangedEvent;
import seedu.todolist.commons.events.storage.SaveLocationChangedEvent;
import seedu.todolist.commons.util.CollectionUtil;
import seedu.todolist.commons.util.FindUtil;
import seedu.todolist.logic.commands.ListCommand;
import seedu.todolist.model.task.Task;
import seedu.todolist.model.task.UniqueTaskList;
import seedu.todolist.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Represents the in-memory model of the to-do list data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final ToDoList toDoList;
    private final FilteredList<Task> filteredTasks;
    private SortedList<Task> sortedTasks;
    private boolean isViewIncomplete, isViewComplete, isViewOverdue, isViewUpcoming;

    private static final String RESET = "reset";
    private static final String DELETE = "delete";
    private static final String ADD = "add";
    private static final String COMPLETE = "complete";
    private static final String UPDATE = "update";
    private static final String DESCRIBE = "describe";
    private static final String CHANGESTORAGE = "changestorage";

    private static final int ERROR_VALUE = 0;
    private static final int NO_SWAP = 1;



    /**
     * Initializes a ModelManager with the given to-do list and userPrefs.
     */
    public ModelManager(ReadOnlyToDoList toDoList, UserPrefs userPrefs) {
        super();
        assert !CollectionUtil.isAnyNull(toDoList, userPrefs);

        logger.fine("Initializing with to-do list: " + toDoList + " and user prefs " + userPrefs);

        this.toDoList = new ToDoList(toDoList);
        filteredTasks = new FilteredList<>(this.toDoList.getTaskList());
        getFilteredIncompleteTaskList();
    }

    public ModelManager() {
        this(new ToDoList(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyToDoList newData) {
        toDoList.resetData(newData);
        indicateToDoListChanged(RESET);
    }

    @Override
    public ReadOnlyToDoList getToDoList() {
        return toDoList;
    }

    @Override
    public boolean isUpcomingView() {
        return isViewUpcoming;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateToDoListChanged(String typeOfCommand) {
        int index = filteredTasks.toArray().length;
        raise(new ToDoListChangedEvent(toDoList, index, typeOfCommand));
    }

    private void indicateToDoListChanged(String typeOfCommand, int index) {
        raise(new ToDoListChangedEvent(toDoList, index, typeOfCommand));
    }

    //@@author A0144240W
    /** Raises an event to indicate that the filteredList has changed */
    private void indicateViewListChanged(String typeOfList) {
        raise(new ViewListChangedEvent(typeOfList));
    }

    @Override
    public synchronized void deleteTask(Task target) throws TaskNotFoundException {
        toDoList.removeTask(target);
        indicateToDoListChanged(DELETE);
    }

    @Override
    //@@author A0141647E
    public synchronized void completeTask(int filteredTaskListIndex, Task target) throws TaskNotFoundException {
        int toDoListIndex = filteredTasks.getSourceIndex(filteredTaskListIndex);
        if (isViewUpcoming) {
            int sortedIndex = sortedTasks.getSourceIndex(filteredTaskListIndex);
            toDoListIndex = filteredTasks.getSourceIndex(sortedIndex);
        }
        toDoList.completeTask(toDoListIndex, target);
        indicateToDoListChanged(COMPLETE);
    }

    @Override
    //TODO add error throwing for unwritable save locations
    //@@author A0139633B
    public synchronized void changeStoragePath(String newFilePath) {
        raise(new SaveLocationChangedEvent(newFilePath));
        indicateToDoListChanged(CHANGESTORAGE);
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        toDoList.addTask(task);
        getFilteredIncompleteTaskList();
        indicateToDoListChanged(ADD);
    }

    @Override
    public void updateTask(int filteredTaskListIndex, Task editedTask)
            throws UniqueTaskList.DuplicateTaskException {
        assert editedTask != null;
        int toDoListIndex = filteredTasks.getSourceIndex(filteredTaskListIndex);

        if (isViewUpcoming) {
            int sortedIndex = sortedTasks.getSourceIndex(filteredTaskListIndex);
            toDoListIndex = filteredTasks.getSourceIndex(sortedIndex);
        }
        toDoList.updateTask(toDoListIndex, editedTask);
        indicateToDoListChanged(UPDATE, toDoListIndex);
    }

    @Override
    //@@author A0141647E
    public void describeTask(int filteredTaskListIndex, Task editedTask) {
        assert editedTask != null;

        int toDoListIndex = filteredTasks.getSourceIndex(filteredTaskListIndex);
        if (isViewUpcoming) {
            int sortedIndex = sortedTasks.getSourceIndex(filteredTaskListIndex);
            toDoListIndex = filteredTasks.getSourceIndex(sortedIndex);
        }
        toDoList.describeTask(toDoListIndex, editedTask);
        indicateToDoListChanged(DESCRIBE);
    }

    //=========== Filtered Task List Accessors =============================================================

    @Override
    public UnmodifiableObservableList<Task> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }

    //@@author A0144240W
    @Override
    public UnmodifiableObservableList<Task> getSortedTaskList() {
        sortedTasks = new SortedList<>(filteredTasks, dateComparator);
        return new UnmodifiableObservableList<>(sortedTasks);
    }


    @Override
    //@@author A0139633B
    public UnmodifiableObservableList<Task> getFilteredIncompleteTaskList() {
        resetViews();
        isViewIncomplete = true;
        filteredTasks.setPredicate((Predicate<? super Task>) task -> {
            return !task.isComplete();
        });
        indicateViewListChanged(ListCommand.TYPE_INCOMPLETE);
        return new UnmodifiableObservableList<>(filteredTasks);
    }

    @Override
    //@@author A0139633B
    public UnmodifiableObservableList<Task> getFilteredCompleteTaskList() {
        resetViews();
        isViewComplete = true;
        filteredTasks.setPredicate((Predicate<? super Task>) task -> {
            return task.isComplete();
        });
        indicateViewListChanged(ListCommand.TYPE_COMPLETE);
        return new UnmodifiableObservableList<>(filteredTasks);
    }

    @Override
    //@@author A0139633B
    public UnmodifiableObservableList<Task> getFilteredOverdueTaskList() {
        resetViews();
        isViewOverdue = true;
        filteredTasks.setPredicate((Predicate<? super Task>) task -> {
            return isOverdue(task);
        });
        indicateViewListChanged(ListCommand.TYPE_OVERDUE);
        return new UnmodifiableObservableList<>(filteredTasks);
    }

    @Override
    //@@author A0139633B
    public UnmodifiableObservableList<Task> getFilteredUpcomingTaskList() {
        resetViews();
        isViewUpcoming = true;
        //get tasks that are not overdue and are incomplete
        //and arrange them in ascending order by start then end time
        filteredTasks.setPredicate((Predicate<? super Task>) task -> {
            return !isOverdue(task);
        });
        indicateViewListChanged(ListCommand.TYPE_UPCOMING);
        return new UnmodifiableObservableList<>(filteredTasks);
    }

    //@@author A0139633B
    /*
     * Checks if the given task's end date exceeds the current datetime
     */
    public boolean isOverdue(Task task) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy h.mm a");
        Date currentDate = new Date();
        if (hasEndTime(task)) {
            String taskDateString = task.getEndTime().toString();
            try {
                Date taskDate = dateFormat.parse(taskDateString);
                return !task.isComplete() && currentDate.compareTo(taskDate) > 0;
            } catch (ParseException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }

    //@@author A0139633B
    /*
     * Checks that the task is not overdue and not completed
     */
    private boolean isUpcoming(Task task) {
        //get current time and compare with the task's end time
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy h.mm a");
        Date currentDate = new Date();
        if (hasEndTime(task)) {
            String taskDateString = task.getEndTime().toString();
            try {
                Date taskDate = dateFormat.parse(taskDateString);
                return currentDate.compareTo(taskDate) <= 0;
            } catch (ParseException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }

    //@@author A0139633B
    //Comparator for Date
    Comparator<? super Task> dateComparator = new Comparator<Task>() {
        @Override
        public int compare(Task firstTask, Task secondTask) {
            if (hasStartTime(firstTask) && hasStartTime(secondTask)) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy h.mm a");
                String firstTaskStartDateString = firstTask.getStartTime().toString();
                String secondTaskStartDateString = secondTask.getStartTime().toString();
                try {
                    Date firstTaskStartDate = dateFormat.parse(firstTaskStartDateString);
                    Date secondTaskStartDate = dateFormat.parse(secondTaskStartDateString);
                    return firstTaskStartDate.compareTo(secondTaskStartDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                    return ERROR_VALUE;
                }
            } else if (hasEndTime(firstTask) && hasEndTime(secondTask)) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy h.mm a");
                String firstTaskEndDateString = firstTask.getEndTime().toString();
                String secondTaskEndDateString = secondTask.getEndTime().toString();
                try {
                    Date firstTaskEndDate = dateFormat.parse(firstTaskEndDateString);
                    Date secondTaskEndDate = dateFormat.parse(secondTaskEndDateString);
                    return firstTaskEndDate.compareTo(secondTaskEndDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                    return ERROR_VALUE;
                }
            } else {
                return NO_SWAP;
            }
        }
    };

    //@@author A0139633B
    private boolean hasStartTime(Task task) {
        return task.getStartTime() != null;
    }

    //@@author A0139633B
    private boolean hasEndTime(Task task) {
        return task.getEndTime() != null;
    }

    @Override
    public void updateFilteredListToShowAll() {
        resetViews();
        indicateViewListChanged(ListCommand.TYPE_ALL);
        filteredTasks.setPredicate(null);
    }

    //@@author A0144240W
    @Override
    public void updateFilteredTaskList(Set<String> keywords) {
        if (isViewIncomplete) {
            filteredTasks.setPredicate(findInIncomplete(keywords));
        } else if (isViewComplete) {
            filteredTasks.setPredicate(findInComplete(keywords));
        } else if (isViewOverdue) {
            filteredTasks.setPredicate(findInOverdue(keywords));
        } else if (isViewUpcoming) {
            filteredTasks.setPredicate(findInUpcoming(keywords));
        } else {
            filteredTasks.setPredicate(findInAll(keywords));
        }
    }


    //=========== Methods to help in filtering task list with given keywords ==============================

    //@@author A0144240W
    /**
     * Returns a predicate to find keywords when in the incomplete view
     * @param keywords
     * @return
     */
    public Predicate<Task> findInIncomplete(Set<String> keywords) {
        return p -> !p.isComplete() && FindUtil.containsIgnoreCase(p, keywords);
    }

    //@@author A0144240W
    /**
     * Returns a predicate to find keywords when in the complete view
     * @param keywords cannot be null
     *
     */
    public Predicate<Task> findInComplete(Set<String> keywords) {
        return p -> p.isComplete() && FindUtil.containsIgnoreCase(p, keywords);
    }

    //@@author A0144240W
    /**
     * Returns a predicate to find keywords when in the overdue view
     * @param keywords cannot be null
     *
     */
    public Predicate<Task> findInOverdue(Set<String> keywords) {
        return p -> isOverdue(p) && FindUtil.containsIgnoreCase(p, keywords);
    }

    //@@author A0144240W
    /**
     * Returns a predicate to find keywords when in the upcoming view
     * @param keywords cannot be null
     *
     */
    public Predicate<Task> findInUpcoming(Set<String> keywords) {
        return p -> isUpcoming(p) && FindUtil.containsIgnoreCase(p, keywords);
    }

    //@@author A0144240W
    /**
     * Returns a predicate to find keywords when in the all view
     * @param keywords cannot be null
     *
     */
    public Predicate<Task> findInAll(Set<String> keywords) {
        return p -> FindUtil.containsIgnoreCase(p, keywords);
    }


    //@@author A0144240W
    /**
     * Resets the boolean values of the views to false.
     */
    private void resetViews() {
        isViewComplete = false;
        isViewIncomplete = false;
        isViewOverdue = false;
        isViewUpcoming = false;
    }
}




