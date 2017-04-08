package seedu.todolist.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.todolist.commons.util.FxViewUtil;
import seedu.todolist.model.task.Task;

/**
 * The Browser Panel of the App.
 */
public class TaskDetailsPanel extends UiPart<Region> {

    private static final String FXML = "TaskDetailsPanel.fxml";
    private Image completeIcon = new Image("/images/blue-theme/complete-icon.png");

    @FXML
    private AnchorPane display;

    @FXML
    private Label nameLabel;

    @FXML
    private Label tagsHeader;

    @FXML
    private Label startText;

    @FXML
    private Label endText;

    @FXML
    private Label startLabel;

    @FXML
    private Label endLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label descriptionText;

    @FXML
    private FlowPane tags;

    @FXML
    private VBox taskDetails;

    @FXML
    private HBox status;





    //@@author A0144240W

    /**
     * @param placeholder The AnchorPane where the taskDetailsPanel must be inserted
     */
    public TaskDetailsPanel(AnchorPane placeholder) {
        super(FXML);
        FxViewUtil.applyAnchorBoundaryParameters(display, 0.0, 0.0, 0.0, 0.0);
        placeholder.getChildren().add(display);
    }

    //@@author A0144240W
    public void loadPersonPage(Task task) {
        nameLabel.setText(task.getName().toString());
        ImageView image = new ImageView(completeIcon);
        image.setFitWidth(20);
        image.setPreserveRatio(true);
        if (task.isComplete()) {
            status.getChildren().add(image);
        }
        startLabel.setText("From:");
        endLabel.setText("To:");
        startText.setText((task.getStartTime() != null ? task.getStartTime().toString() : "-"));
        endText.setText((task.getEndTime() != null ? task.getEndTime().toString() : "-"));
        descriptionLabel.setText("Description:");
        descriptionText.setText( (task.getDescription() != null ? task.getDescription() : ""));
        tagsHeader.setText("Tags:");
        task.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    //@@author A0144240W
    public void freeResources() {
        tags.getChildren().clear();
        nameLabel.setText("");
        startLabel.setText("");
        endLabel.setText("");
        status.getChildren().clear();
        startText.setText("");
        endText.setText("");
        tagsHeader.setText("");
        descriptionLabel.setText("");
        descriptionText.setText("");

    }

}
