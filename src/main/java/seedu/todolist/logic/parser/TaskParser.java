package seedu.todolist.logic.parser;

import static seedu.todolist.logic.parser.CliSyntax.PREFIX_END;
import static seedu.todolist.logic.parser.CliSyntax.PREFIX_START;
import static seedu.todolist.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import seedu.todolist.commons.exceptions.IllegalValueException;
import seedu.todolist.model.tag.Tag;
import seedu.todolist.model.tag.UniqueTagList;
import seedu.todolist.model.task.EndTask;
import seedu.todolist.model.task.EndTime;
import seedu.todolist.model.task.FloatingTask;
import seedu.todolist.model.task.Name;
import seedu.todolist.model.task.StartEndTask;
import seedu.todolist.model.task.StartTask;
import seedu.todolist.model.task.StartTime;
import seedu.todolist.model.task.Task;

//@@author A0141647E
/*
 * Parse input into a suitable type of Task.
 */
public class TaskParser {

    public static final String MESSAGE_INVALID_TASK = "Name must be present";

    //@@author A0141647E
    /**
     * Parses the given {@code String} of input that contains parameters
     * of a Task object and calls the suitable TaskParser based on the parameters.
     */
    public static Task parseTask(String taskInput) throws IllegalValueException {
        ArgumentTokenizer argsTokenizer =
                new ArgumentTokenizer(PREFIX_START, PREFIX_END, PREFIX_TAG);
        argsTokenizer.tokenize(taskInput);

        String name;
        String startTime;
        String endTime;
        Set<String> tags;

        try {
            name = argsTokenizer.getPreamble().get();
            startTime = (argsTokenizer.getValue(PREFIX_START).isPresent() ?
                    argsTokenizer.getValue(PREFIX_START).get()
                    : null);
            endTime = (argsTokenizer.getValue(PREFIX_END).isPresent() ?
                    argsTokenizer.getValue(PREFIX_END).get()
                    : null);
            tags = ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG));
        } catch (NoSuchElementException nsse) {
            throw new NoSuchElementException(MESSAGE_INVALID_TASK);
        }

        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }

        return parseTask(new Name(name),
                (startTime != null ? new StartTime(startTime) : null),
                (endTime != null ? new EndTime(endTime) : null),
                new UniqueTagList(tagSet));
    }

    //@@author A0141647E
    /*
     * An overloaded method that takes in a Name object,
     * a StartTime object, an EndTime object and a set of Tags
     * and create a suitable Task object.
     */
    public static Task parseTask(Name name, StartTime startTime,
            EndTime endTime, UniqueTagList uniqueTagList) {
        assert name != null;

        if (startTime != null && endTime != null) {
            return new StartEndTask(name, startTime, endTime, uniqueTagList);
        } else if (startTime != null && endTime == null) {
            return new StartTask(name, startTime, uniqueTagList);
        } else if (startTime == null && endTime != null) {
            return new EndTask(name, endTime, uniqueTagList);
        } else {
            return new FloatingTask(name, uniqueTagList);
        }
    }

    //@@author A0141647E
    /*
     * An overloaded method that takes in a Name object,
     * a StartTime object, an EndTime object, a set of Tags
     * and a completion status
     * and create a suitable Task object.
     */
    public static Task parseTask(Name name, StartTime startTime,
            EndTime endTime, UniqueTagList uniqueTagList,
            boolean isComplete, String description) {
        assert name != null;

        Task resultTask;

        if (startTime != null && endTime != null) {
            resultTask = new StartEndTask(name, startTime, endTime, uniqueTagList, isComplete);
        } else if (startTime != null && endTime == null) {
            resultTask = new StartTask(name, startTime, uniqueTagList, isComplete);
        } else if (startTime == null && endTime != null) {
            resultTask = new EndTask(name, endTime, uniqueTagList, isComplete);
        } else {
            resultTask = new FloatingTask(name, uniqueTagList, isComplete);
        }
        resultTask.setDescription(description);
        return resultTask;
    }

    //@@author A0141647E
    /*
     * Another overloaded method that takes in a Task object
     * and create a copy of it.
     */
    public static Task parseTask(Task taskToCopy) {
        assert taskToCopy != null;

        Name name = taskToCopy.getName();
        StartTime startTime = taskToCopy.getStartTime();
        EndTime endTime = taskToCopy.getEndTime();
        UniqueTagList uniqueTagList = taskToCopy.getTags();
        boolean isComplete = taskToCopy.isComplete();
        String description = taskToCopy.getDescription();

        return parseTask(name, startTime, endTime, uniqueTagList, isComplete, description);
    }
}
