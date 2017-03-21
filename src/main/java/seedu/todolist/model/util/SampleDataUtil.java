package seedu.todolist.model.util;

import seedu.todolist.commons.exceptions.IllegalValueException;
import seedu.todolist.model.ReadOnlyToDoList;
import seedu.todolist.model.ToDoList;
import seedu.todolist.model.tag.UniqueTagList;
import seedu.todolist.model.task.EndTime;
import seedu.todolist.model.task.Name;
import seedu.todolist.model.task.StartTime;
import seedu.todolist.model.task.Task;
import seedu.todolist.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.todolist.model.task.parser.TaskParser;

public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        try {
            return new Task[] {
                TaskParser.parseTask(new Name("Concert logistics"), new StartTime("11-11-2017 6.00 PM"), new EndTime("13-11-2017 6.00 PM"), new UniqueTagList("work", "logistics")),
                TaskParser.parseTask(new Name("Reply email"), null, new EndTime("11-10-2017 6.00 PM"), new UniqueTagList("work", "highpriority")),
                TaskParser.parseTask(new Name("clean up working table"), new StartTime("11-11-2016 6.00 PM"), new EndTime("12-11-2016 6.00 PM"), new UniqueTagList("chores", "lowpriority")),
                TaskParser.parseTask(new Name("get present for billy"), new StartTime("24-12-2017 6.00 PM"), new EndTime("25-12-2017 6.00 PM"), new UniqueTagList("chores", "lowpriority"))
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static ReadOnlyToDoList getSampleToDoList() {
        try {
            ToDoList sampleAB = new ToDoList();
            for (Task sampleTask : getSampleTasks()) {
                sampleAB.addTask(sampleTask);
            }
            return sampleAB;
        } catch (DuplicateTaskException e) {
            throw new AssertionError("sample data cannot contain duplicate tasks", e);
        }
    }
}
