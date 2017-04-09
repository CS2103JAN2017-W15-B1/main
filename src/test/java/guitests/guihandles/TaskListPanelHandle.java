package guitests.guihandles;


import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import guitests.GuiRobot;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import seedu.todolist.TestApp;
import seedu.todolist.logic.parser.TaskParser;
import seedu.todolist.model.task.Task;
import seedu.todolist.testutil.TestTask;
import seedu.todolist.testutil.TestUtil;

/**
 * Provides a handle for the panel containing the task list.
 */
public class TaskListPanelHandle extends GuiHandle {

    public static final int NOT_FOUND = -1;
    public static final String CARD_PANE_ID = "#cardPane";

    private static final String TASK_LIST_VIEW_ID = "#taskListView";

    public TaskListPanelHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    public List<Task> getSelectedTasks() {
        ListView<Task> taskList = getListView();
        return taskList.getSelectionModel().getSelectedItems();
    }

    public ListView<Task> getListView() {
        return getNode(TASK_LIST_VIEW_ID);
    }

    /**
     * Returns true if the list is showing the task details correctly and in correct order.
     * @param tasks A list of tasks in the correct order.
     */
    public boolean isListMatching(Task... tasks) {
        return this.isListMatching(0, tasks);
    }

    /**
     * Returns true if the list is showing the task details correctly and in correct order.
     * @param startPosition The starting position of the sub list.
     * @param tasks A list of tasks in the correct order.
     */
    public boolean isListMatching(int startPosition, Task... tasks) throws IllegalArgumentException {
        if (tasks.length + startPosition != getListView().getItems().size()) {
            throw new IllegalArgumentException("List size mismatched\n" +
                    "Expected " + (getListView().getItems().size() - 1) + " tasks");
        }
        assertTrue(this.containsInOrder(startPosition, tasks));
        for (int i = 0; i < tasks.length; i++) {
            final int scrollTo = i + startPosition;
            guiRobot.interact(() -> getListView().scrollTo(scrollTo));
            guiRobot.sleep(200);
            if (!TestUtil.compareCardAndTask(getTaskCardHandle(startPosition + i), tasks[i])) {
                System.out.println(getTaskCardHandle(startPosition + i).getName());
                System.out.println(tasks[i].getName());
                return false;
            }
        }
        return true;
    }

    /**
     * Clicks on the ListView.
     */
    public void clickOnListView() {
        Point2D point = TestUtil.getScreenMidPoint(getListView());
        guiRobot.clickOn(point.getX(), point.getY());
    }

    /**
     * Returns true if the {@code tasks} appear as the sub list (in that order) at position {@code startPosition}.
     */
    public boolean containsInOrder(int startPosition, Task... tasks) {
        List<Task> tasksInList = getListView().getItems();

        // Return false if the list in panel is too short to contain the given list
        if (startPosition + tasks.length > tasksInList.size()) {
            return false;
        }

        // Return false if any of the tasks do not match
        for (int i = 0; i < tasks.length; i++) {
            if (!tasksInList.get(startPosition + i).getName().fullName.equals(tasks[i].getName().fullName)) {
                return false;
            }
        }

        return true;
    }

    public TaskCardHandle navigateToTask(String name) {
        guiRobot.sleep(500); //Allow a bit of time for the list to be updated
        final Optional<Task> task = getListView().getItems().stream()
                                                    .filter(p -> p.getName().fullName.equals(name))
                                                    .findAny();
        if (!task.isPresent()) {
            throw new IllegalStateException("Task not found: " + name);
        }

        return navigateToTask(task.get());
    }

    /**
     * Navigates the listview to display and select the task.
     */
    public TaskCardHandle navigateToTask(Task task) {
        int index = getTaskIndex(task);
        System.out.println(index);
        guiRobot.interact(() -> {
            getListView().scrollTo(index);
            guiRobot.sleep(150);
            getListView().getSelectionModel().select(index);
        });
        guiRobot.sleep(100);
        return getTaskCardHandle(task);
    }


    /**
     * Returns the position of the task given, {@code NOT_FOUND} if not found in the list.
     */
    public int getTaskIndex(Task targetTask) {
        TestTask testTargetTask = new TestTask(targetTask);
        List<Task> tasksInList = getListView().getItems();
        for (int i = 0; i < tasksInList.size(); i++) {
            TestTask taskFromList = new TestTask(tasksInList.get(i));
            if (taskFromList.equals(testTargetTask)) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    /**
     * Gets a task from the list by index
     */
    public Task getTask(int index) {
        return getListView().getItems().get(index);
    }

    public TaskCardHandle getTaskCardHandle(int index) {
        return getTaskCardHandle(TaskParser.parseTask(getListView().getItems().get(index)));
    }

    public TaskCardHandle getTaskCardHandle(Task task) {
        Set<Node> nodes = getAllCardNodes();
        Optional<Node> taskCardNode = nodes.stream()
                .filter(n -> new TaskCardHandle(guiRobot, primaryStage, n).isSameTask(task))
                .findFirst();
        if (taskCardNode.isPresent()) {
            return new TaskCardHandle(guiRobot, primaryStage, taskCardNode.get());
        } else {
            return null;
        }
    }

    protected Set<Node> getAllCardNodes() {
        return guiRobot.lookup(CARD_PANE_ID).queryAll();
    }

    public int getNumberOfTasks() {
        return getListView().getItems().size();
    }
}
