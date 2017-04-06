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
    private Label description;
    @FXML
    private FlowPane tags;
    @FXML
    private Label complete;
    @FXML
    private ImageView statusIcon;
    @FXML
    private ImageView taskIcon;

    private Image end_type = new Image("/images/end-task-icon.png");
    private Image start_end_type = new Image("/images/start-end-task-icon.png");
    private Image start_type = new Image("/images/start-task-icon.png");
    private Image floating_type = new Image("/images/floating-task-icon.png");
    private Image completeIcon = new Image("/images/complete-icon.png");


    //@@author A0144240W
    public TaskListCard(Task task, int displayedIndex) {
        super(FXML);
        name.setText(task.getName().fullName);
        id.setText(displayedIndex + ". ");
        setTaskTimes(task);
        if (task.isComplete()) {
            statusIcon.setImage(completeIcon);
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
            startTime.setText("Due by: " + task.getEndTime().toString());
            break;
        case Task.TYPE_START_END:
            startTime.setText("From: " + task.getStartTime().toString());
            endTime.setText("Due by: " + task.getEndTime().toString());
            break;
        case Task.TYPE_START:
            startTime.setText("From: " + task.getStartTime().toString());
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
            taskIcon.setImage(end_type);
            break;
        case Task.TYPE_START_END:
            taskIcon.setImage(start_end_type);
            break;
        case Task.TYPE_START:
            taskIcon.setImage(start_type);
            break;
        case Task.TYPE_FLOATING:
            taskIcon.setImage(floating_type);
            break;
        }
    }



}
