package guitests;

import static org.junit.Assert.assertTrue;

import seedu.todolist.testutil.TestTask;
import org.junit.Test;

import static seedu.todolist.logic.commands.DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS;
import static seedu.todolist.logic.commands.AddCommand.MESSAGE_ADD_SUCCESS;
import static seedu.todolist.logic.commands.UndoCommand.MESSAGE_UNDO_TASK_SUCCESS;

import seedu.todolist.testutil.TestUtil;



public class UndoRedoCommandTest extends ToDoListGuiTest{
    String commandResultText, undoMessage;

    @Test
    public void add_undo_redo() {
        AddCommandTest add = new AddCommandTest();

        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToAdd = td.changeUi;

        //wrong one that throws NPE
        add.assertAddSuccess(taskToAdd, currentList);




        //correct one
        commandBox.runCommand(taskToAdd.getAddCommand());
        commandResultText = String.format(MESSAGE_ADD_SUCCESS, taskToAdd);
        undoMessage = String.format(MESSAGE_UNDO_TASK_SUCCESS, commandResultText);

        //confirm the list now contains all previous tasks plus the new task
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(taskListPanel.isListMatching(expectedList));

        //confirm the undo works
        commandBox.runCommand("undo");
        assertTrue(taskListPanel.isListMatching(currentList));

        //confirm the redo works
        commandBox.runCommand("redo");
        assertTrue(taskListPanel.isListMatching(expectedList));
    }

    @Test
    public void delete_undo_redo() {
        TestTask[] currentList = td.getTypicalTasks();
        //currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
        int targetIndex = currentList.length / 2;

        TestTask taskToDelete = currentList[targetIndex - 1]; // -1 as array uses zero indexing
        TestTask[] expectedRemainder = TestUtil.removeTaskFromList(currentList, targetIndex);

        commandBox.runCommand("delete " + targetIndex);

        //confirm the list now contains all previous tasks except the deleted task
        assertTrue(taskListPanel.isListMatching(expectedRemainder));

        //confirm the undo works
        commandBox.runCommand("undo");
        assertTrue(taskListPanel.isListMatching(currentList));

        //confirm the message displayed is correct
        String command = String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete.getName());
        String undoMessage = String.format(MESSAGE_UNDO_TASK_SUCCESS, command);
        assertResultMessage(undoMessage);

        //confirm the redo works
        commandBox.runCommand("redo");
        assertTrue(taskListPanel.isListMatching(expectedRemainder));
    }

}
