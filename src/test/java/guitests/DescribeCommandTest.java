package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.todolist.commons.exceptions.IllegalValueException;
import seedu.todolist.logic.commands.DescribeCommand;
import seedu.todolist.testutil.TaskBuilder;
import seedu.todolist.testutil.TestTask;

//@@author A0141647E
public class DescribeCommandTest extends ToDoListGuiTest {

    public TestTask[] expectedTaskList = td.getTypicalTasks();

    //@@author A0141647E
    @Test
    public void describeTaskWithNoDescription() throws IllegalValueException {
        //allow describe a task with no description
        String description = "I must do this quickly";
        int toDoListIndex = 1;
        TestTask describedTask = new TaskBuilder().withName("Reply Boss's Email")
                .withStartTime("30-06-2017 10.00 AM")
                .withEndTime("30-06-2017 3.00 PM")
                .withTags("Work", "Important", "Urgent")
                .withDescription(description)
                .build();
        assertDescribeSuccess(toDoListIndex, toDoListIndex, description, describedTask);
    }

    //@@author A0141647E
    @Test
    public void describeTaskWithDescription() throws IllegalValueException {
        //allow describe a task which has been previously described
        String description = "Not too intensive today";
        int toDoListIndex = 5;
        TestTask describedTask = new TaskBuilder().withName("Go To Gym")
                .withStartTime("14-04-2017 4.00 PM")
                .withEndTime("14-04-2017 5.00 PM")
                .withDescription(description)
                .withTags("Health")
                .build();
        assertDescribeSuccess(toDoListIndex, toDoListIndex, description, describedTask);
    }

    //@@author A0141647E
    /**
     * Checks whether the described task has the correct updated description.
     *
     * @param filteredTaskListIndex index of task to edit in filtered list
     * @param toDoListIndex index of task to edit in the to-do list.
     *      Must refer to the same task as {@code filteredTaskListIndex}
     * @param description details to describe the task with as input to the describe command
     * @param describedTask the expected task after editing the task's details
     */
    private void assertDescribeSuccess(int filteredTaskListIndex, int toDoListIndex,
            String description, TestTask describedTask) {
        commandBox.runCommand("describe " + filteredTaskListIndex + " " + description);


        // confirm the task details panel contains the same description
        assertSameDetails(describedTask, description);

        // confirm the list now contains all previous tasks plus the task with updated details
        expectedTaskList[toDoListIndex - 1] = describedTask;
        System.out.println(describedTask.toString());
        assertTrue(taskListPanel.isListMatching(expectedTaskList));
        assertResultMessage(String.format(DescribeCommand.MESSAGE_DESCRIBE_TASK_SUCCESS,
                describedTask.getDescription(),
                describedTask.getName().fullName));

    }

    public void assertSameDetails(TestTask task, String descriptionAdded) {
        assertTrue(taskDetailsPanel.getDescription().equals(descriptionAdded));
        assertTrue(taskDetailsPanel.getName().equals(task.getName().toString()));
        assertTrue(taskDetailsPanel.getStartTime().equals(task.getStartTime().toString()));
        assertTrue(taskDetailsPanel.getEndTime().equals(task.getEndTime().toString()) );
    }

}
