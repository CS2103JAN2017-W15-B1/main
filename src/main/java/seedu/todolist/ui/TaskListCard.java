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


    public TaskListCard(Task task, int displayedIndex) {
        super(FXML);
        name.setText(task.getName().fullName);
        id.setText(displayedIndex + ". ");
        startTime.setText(task.getStartTime() != null ? task.getStartTime().toString() : "");
        endTime.setText(task.getEndTime() != null ? task.getEndTime().toString() : "");
        description.setText(task.getDescription() == null ? "" : task.getDescription());
        Image incompleteIcon = new Image("/images/incomplete-icon.png");
        Image completeIcon = new Image("/images/complete-icon.png");
        if (!task.isComplete()) {
            statusIcon.setImage(incompleteIcon);
        } else {
            statusIcon.setImage(completeIcon);
        }

        Image end_type = new Image("/images/end-task-icon.png");
        Image start_end_type = new Image("/images/start-end-task-icon.png");
        Image start_type = new Image("/images/start-task-icon.png");
        Image floating_type = new Image("/images/floating-task-icon.png");
        if (task.getType().equals(Task.END_TYPE)) {
            taskIcon.setImage(end_type);
        } else if (task.getType().equals(Task.START_END_TYPE)) {
            taskIcon.setImage(start_end_type);
        } else if(task.getType().equals(Task.START_TYPE)) {
            taskIcon.setImage(start_type);
        } else if(task.getType().equals(Task.FLOATING_TYPE)) {
            taskIcon.setImage(floating_type);
        }


        initTags(task);
    }

    private void initTags(Task task) {
        task.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    //private void initStartTime(ReadOnlyTask task) {
    //  startTime.getChildren().add(new Label(task.getStartTime().toString()));
    //}

}
