package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.todolist.commons.core.Messages;
import seedu.todolist.testutil.TestTask;

public class FindCommandTest extends ToDoListGuiTest {

    @Test
    public void find_nonEmptyList() {
        assertFindResult("find Mark"); // no results
        commandBox.runCommand("list all");
        assertFindResult("find bilinear", td.homework); // one results

        //find after deleting one result
        commandBox.runCommand("delete 1");
        assertFindResult("find bilinear"); //no result
    }

    @Test
    public void find_emptyList() {
        commandBox.runCommand("clear");
        assertFindResult("find Jean"); // no results
    }

    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand("findgeorge");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND +
                "\nDo you mean: find?");
    }

    @Test
    public void findSubstring() {
        commandBox.runCommand("list all");
        assertFindResult("find se", td.presentation, td.dentistAppointment);
    }

    @Test
    public void findByEitherSubstringOrTags() {
        commandBox.runCommand("list all");
        assertFindResult("find se t/Health", td.presentation, td.goToGym, td.dentistAppointment);
    }

    @Test
    public void findIncorrectTagFail() {
        commandBox.runCommand("list all");
        assertFindResult("find t/Heal");
        commandBox.runCommand("list all");
        assertFindResult("find t/Wok");
    }

    @Test
    public void findMostRecentList() {
        commandBox.runCommand("list all");
        assertFindResult("find t/Dentist", td.dentistAppointment);
        commandBox.runCommand("list upcoming");
        assertFindResult("find t/Dentist");
        commandBox.runCommand("list overdue");
        assertFindResult("find t/Dentist", td.dentistAppointment);

        commandBox.runCommand("list incomplete");
        assertFindResult("find t/Work t/Difficult", td.bossEmail, td.homework, td.handleCustomerComplaints);
        commandBox.runCommand("list complete");
        assertFindResult("find t/Work t/Difficult");
    }

    private void assertFindResult(String command, TestTask... expectedHits) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
