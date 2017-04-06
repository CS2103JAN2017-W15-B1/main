package seedu.todolist.logic.commands;

import java.io.IOException;
import java.security.GeneralSecurityException;

import seedu.todolist.integration.GoogleIntegration;
import seedu.todolist.logic.commands.exceptions.CommandException;

public class SyncCommand extends Command {

    public static final String COMMAND_WORD = "sync";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "Sync your current to-do list"
                                             + " to your Google Calendar. This will only sync"
                                             + " Tasks with either StartTime/EndTime";

    public static final String MESSAGE_SUCCESS = "All Tasks have been successfully synced!";

    public SyncCommand() {
        ;
    }

    @Override
    public CommandResult execute() throws CommandException {
        try {
            GoogleIntegration integrator = new GoogleIntegration();
            integrator.sync(model);
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (IOException ioe) {
            throw new CommandException("Syncing failed. Input error: " + ioe.getMessage());
        } catch (GeneralSecurityException gse) {
            throw new CommandException("Syncing failed. Security error: " + gse.getMessage());
        }
    }

    @Override
    public boolean isMutating() {
        return false;
    }

    @Override
    public String getCommandText() {
        return "";
    }
}
