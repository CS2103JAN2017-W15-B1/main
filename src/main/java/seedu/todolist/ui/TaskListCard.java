package seedu.todolist.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.todolist.model.task.Task;

public class TaskListCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";

    private static final Image ICON_END_TYPE = new Image("/images/blue-theme/end-task-icon.png");
    private static final Image ICON_START_END_TYPE = new Image("/images/blue-theme/start-end-task-icon.png");
    private static final Image ICON_START_TYPE = new Image("/images/blue-theme/start-task-icon.png");
    private static final Image ICON_FLOATING_TYPE = new Image("/images/blue-theme/floating-task-icon.png");
    private static final Image ICON_COMPLETE = new Image("/images/complete-icon.png");

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label startTime;
    @FXML
    private Label endTime;
    @FXML
    private FlowPane tags;
    @FXML
    private Label complete;
    @FXML
    private ImageView statusIcon;
    @FXML
    private ImageView taskIcon;


    //@@author A0144240W
    public TaskListCard(Task task, int displayedIndex) {
        super(FXML);
        name.setText(task.getName().fullName);
        id.setText(displayedIndex + ". ");
        setTaskTimes(task);
        if (task.isComplete()) {
            statusIcon.setImage(ICON_COMPLETE);
        }
        setTaskIcon(task);
        initTags(task);
    }

    private void initTags(Task task) {
        task.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    //@@author A0144240W
    /**
     * Sets the text for the timing labels
     * @param task
     */
    private void setTaskTimes(Task task) {
        switch(task.getType()) {
        case Task.TYPE_END:
            endTime.setText("End: " + task.getEndTime().toString());
            break;
        case Task.TYPE_START_END:
            startTime.setText("Start: " + task.getStartTime().toString());
            endTime.setText("End: " + task.getEndTime().toString());
            break;
        case Task.TYPE_START:
            startTime.setText("Start: " + task.getStartTime().toString());
            break;
        default:
            break;
        }
    }


    //@@author A0144240W
    /**
     * Sets the task icon based on the type of task
     */
    private void setTaskIcon(Task task) {
        switch(task.getType()) {
        case Task.TYPE_END:
            taskIcon.setImage(ICON_END_TYPE);
            break;
        case Task.TYPE_START_END:
            taskIcon.setImage(ICON_START_END_TYPE);
            break;
        case Task.TYPE_START:
            taskIcon.setImage(ICON_START_TYPE);
            break;
        case Task.TYPE_FLOATING:
            taskIcon.setImage(ICON_FLOATING_TYPE);
            break;
        }
    }



}
