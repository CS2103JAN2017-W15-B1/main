package seedu.todolist.logic.commands;

import seedu.todolist.commons.util.SuggestUtil;
import seedu.todolist.logic.commands.exceptions.CommandException;

/**
 * Represents an incorrect command. Upon execution, throws a CommandException with feedback to the user.
 */
public class IncorrectCommand extends Command {

    public String feedbackToUser;
    private String userCommandWord;
    
    public IncorrectCommand(String feedbackToUser) {
        this.feedbackToUser = feedbackToUser;
    }

    public IncorrectCommand(String feedbackToUser, String userCommandWord) {
        this.feedbackToUser = feedbackToUser;
        this.userCommandWord = userCommandWord;
    }

    @Override
    public CommandResult execute() throws CommandException {
        if (userCommandWord != null) {
            feedbackToUser = feedbackToUser +
                    "\nDo you mean: " +
                    SuggestUtil.closestCommand(userCommandWord) +
                    " ?";
        }
        throw new CommandException(feedbackToUser);
    }

    @Override
    public boolean isMutating() {
        return false;
    }

    @Override
    public String getCommandText() {
        return feedbackToUser;
    }

}

