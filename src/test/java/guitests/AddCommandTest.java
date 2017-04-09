package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.todolist.commons.core.Messages;
import seedu.todolist.logic.commands.AddCommand;
import seedu.todolist.logic.parser.TaskParser;
import seedu.todolist.model.task.Task;
import seedu.todolist.testutil.TestTask;
import seedu.todolist.testutil.TestUtil;

public class AddCommandTest extends ToDoListGuiTest {

    @Test
    public void add() {
        //add one task
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToAdd = td.changeUi;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add another task
        taskToAdd = td.bookTicket;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add duplicate task
        commandBox.runCommand(td.changeUi.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(taskListPanel.isListMatching(currentList));

        //@@author A0141647E
        //add task with same name but different timing is allowed
        taskToAdd = td.bookTicketOther;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //@@author A0141647E
        //add task with name that contains " ' " is allowed
        taskToAdd = td.helpJohn;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(td.helpColleague);

        //invalid command
        commandBox.runCommand("adds Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND +
                "\nDo you mean: add?");
    }

    public void assertAddSuccess(TestTask taskToAdd, TestTask... currentList) {
        commandBox.runCommand(taskToAdd.getAddCommand());

        //confirm the new card contains the right data
        Task taskAdding = TaskParser.parseTask(taskToAdd);
        TaskCardHandle addedCard = taskListPanel.navigateToTask(taskAdding);
        assertMatching(taskToAdd, addedCard);

        //confirm the list now contains all previous tasks plus the new task
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }

}
