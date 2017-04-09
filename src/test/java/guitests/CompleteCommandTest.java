package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.todolist.logic.commands.CompleteCommand.MESSAGE_COMPLETE_TASK_SUCCESS;

import org.junit.Test;

import seedu.todolist.commons.core.Messages;
import seedu.todolist.testutil.TestTask;
import seedu.todolist.testutil.TestUtil;

//@@author A0139329X
//modified by A0141647E
public class CompleteCommandTest extends ToDoListGuiTest {

    public TestTask[] incompleteList = td.getTypicalTasks();
    public TestTask[] completeList = new TestTask[0];

    @Test
    public void complete() {
        int targetIndex;

        //complete task at the beginning of the incomplete list
        targetIndex = 1;
        assertCompleteSuccess(targetIndex);

        //complete task at the end of the incomplete list
        commandBox.runCommand("list incomplete");
        targetIndex = incompleteList.length;
        assertCompleteSuccess(targetIndex);

        //supply an invalid index
        commandBox.runCommand("list incomplete");
        targetIndex = incompleteList.length + 1;
        commandBox.runCommand("complete " + targetIndex);
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    //@@author A0141647E
    /**
     * Runs the complete command to complete the task at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. index 1 to complete the first task in the list,
     */
    private void assertCompleteSuccess(int targetIndexOneIndexed) {
        TestTask taskToComplete = incompleteList[targetIndexOneIndexed - 1]; // -1 as array uses zero indexing
        incompleteList = TestUtil.removeTaskFromList(incompleteList, targetIndexOneIndexed);
        completeList = TestUtil.addTasksToList(completeList, taskToComplete);

        commandBox.runCommand("complete " + targetIndexOneIndexed);

        //confirm the incomplete list now contains all previous tasks except the deleted task
        assertTrue(taskListPanel.isListMatching(incompleteList));
        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_COMPLETE_TASK_SUCCESS, taskToComplete.getName().fullName));
        //confirm the task can be found in the list of complete tasks
        commandBox.runCommand("list complete");
        assertTrue(taskListPanel.isListMatching(completeList));
    }

}
