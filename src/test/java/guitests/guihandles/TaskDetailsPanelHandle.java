package guitests.guihandles;

import guitests.GuiRobot;
import javafx.stage.Stage;
import seedu.todolist.TestApp;

//@@author A0144240W
/**
 * A handler for the BrowserPanel of the UI
 */
public class TaskDetailsPanelHandle extends GuiHandle {

    private static final String DESCRIPTION_ID = "#descriptionText";
    private static final String END_ID = "#endText";
    private static final String NAME_ID = "#nameLabel";
    private static final String START_ID = "#startText";
    private static final String TASKDETAILS_ID = "#taskDetails";


    public TaskDetailsPanelHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    public String getDescription() {
        return getLabelText(DESCRIPTION_ID);
    }

    public String getName() {
        return getLabelText(NAME_ID);
    }

    public String getStartTime() {
        return getLabelText(START_ID);
    }

    public String getEndTime() {
        return getLabelText(END_ID);
    }


    /**
     * Clicks on the panel.
     */
    public void clickOnWebView() {
        guiRobot.clickOn(TASKDETAILS_ID);
    }

}
