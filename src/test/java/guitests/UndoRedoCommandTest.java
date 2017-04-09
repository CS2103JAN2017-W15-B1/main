package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.todolist.logic.commands.AddCommand.MESSAGE_ADD_SUCCESS;
import static seedu.todolist.logic.commands.DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS;
import static seedu.todolist.logic.commands.RedoCommand.MESSAGE_NOTHING_TO_REDO;
import static seedu.todolist.logic.commands.UndoCommand.MESSAGE_NOTHING_TO_UNDO;
import static seedu.todolist.logic.commands.UndoCommand.MESSAGE_UNDO_TASK_SUCCESS;


import org.junit.Test;

import seedu.todolist.testutil.TaskBuilder;
import seedu.todolist.testutil.TestTask;
import seedu.todolist.testutil.TestUtil;


//@@author A0144240W

public class UndoRedoCommandTest extends ToDoListGuiTest {
    String commandResultText, undoMessage;

    @Test
    public void add_undo_redo() {

        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToAdd = td.changeUi;
        commandBox.runCommand(taskToAdd.getAddCommand());

        //confirm the list now contains all previous tasks plus the new task
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(taskListPanel.isListMatching(expectedList));

        commandBox.runCommand("undo");
        //confirm the undo works
        assertTrue(taskListPanel.isListMatching(currentList));

        //confirm the message displayed for undo is correct
        commandResultText = String.format(MESSAGE_ADD_SUCCESS, taskToAdd);
        undoMessage = String.format(MESSAGE_UNDO_TASK_SUCCESS, commandResultText);
        assertResultMessage(undoMessage);

        commandBox.runCommand("redo");
        //confirm the redo works
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

        commandBox.runCommand("undo");
        //confirm the undo works
        assertTrue(taskListPanel.isListMatching(currentList));

        //confirm the message displayed for undo is correct
        String command = String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete.getName());
        String undoMessage = String.format(MESSAGE_UNDO_TASK_SUCCESS, command);
        assertResultMessage(undoMessage);

        commandBox.runCommand("redo");
        //confirm the redo works
        assertTrue(taskListPanel.isListMatching(expectedRemainder));
    }

    @Test
    public void edit_undo_redo() throws Exception {
        TestTask[] beforeList = td.getTypicalTasks();
        TestTask[] expectedTasksList = td.getTypicalTasks();
        int filteredTaskListIndex = expectedTasksList.length / 2;
        //TestTask taskToDelete = expectedTasksList[filteredTaskListIndex - 1];

        //still keeps the time when editing the name and tags
        String detailsToEdit = "Visit Grandmother t/family";
        TestTask editedTask = new TaskBuilder().withName("Visit Grandmother")
                .withTags("family").withStartTime("14-02-2017 6.30 PM")
                .build();


        commandBox.runCommand("edit " + filteredTaskListIndex + " " + detailsToEdit);
        expectedTasksList[filteredTaskListIndex - 1] = editedTask;
        assertTrue(taskListPanel.isListMatching(expectedTasksList));

        commandBox.runCommand("undo");
        //confirm the undo works
        assertTrue(taskListPanel.isListMatching(beforeList));

        commandBox.runCommand("redo");
        //confirm the redo works
        assertTrue(taskListPanel.isListMatching(expectedTasksList));
    }

    @Test
    public void clear_undo_redo() {
        TestTask[] expectedList = td.getTypicalTasks();
        assertTrue(taskListPanel.isListMatching(expectedList));
        commandBox.runCommand("clear");
        assertListSize(0);

        commandBox.runCommand("undo");
        assertTrue(taskListPanel.isListMatching(expectedList));

        commandBox.runCommand("redo");
        assertListSize(0);
    }


    @Test
    public void nothing_to_undo_redo() {
        TestTask[] expectedTasksList = td.getTypicalTasks();
        commandBox.runCommand("undo");
        //confirm nothing to undo
        assertTrue(taskListPanel.isListMatching(expectedTasksList));
        assertResultMessage(MESSAGE_NOTHING_TO_UNDO);
        commandBox.runCommand("redo");
        assertTrue(taskListPanel.isListMatching(expectedTasksList));
        assertResultMessage(MESSAGE_NOTHING_TO_REDO);
    }




}
