package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.scene.control.Labeled;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import seedu.todolist.model.tag.UniqueTagList;
import seedu.todolist.model.task.Task;

/**
 * Provides a handle to a task card in the task list panel.
 */
public class TaskCardHandle extends GuiHandle {
    private static final String NAME_FIELD_ID = "#name";
    private static final String START_FIELD_ID = "#startTime";
    private static final String END_FIELD_ID = "#endTime";
    private static final String DESCRIPTION_FIELD_ID = "#description";
    private static final String TAGS_FIELD_ID = "#tags";

    private Node node;

    public TaskCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node) {
        super(guiRobot, primaryStage, null);
        this.node = node;
    }

    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }

    /*
     * Scan the TaskListCard for {@code name} field and retrieve
     * its value.
     * @return empty String if no {@code name} field is found.
     */
    public String getName() {
        return getTextFromLabel(NAME_FIELD_ID);
    }

    //@@author A0141647E
    /*
     * Scan the TaskListCard for {@code startTime} field and retrieve
     * its value.
     * @return empty String if no {@code startTime} field is found.
     */
    public String getStartTime() {
        return getTextFromLabel(START_FIELD_ID);
    }

    //@@author A0141647E
    /*
     * Scan the TaskListCard for {@code endTime} field and retrieve
     * its value.
     * @return empty String if no {@code endTime} field is found.
     */
    public String getEndTime() {
        return getTextFromLabel(END_FIELD_ID);
    }

    //@@author A0141647E
    /*
     * Scan the TaskListCard for {@code description} field and retrieve
     * its value.
     * @return empty String if no {@code description} field is found.
     */
    public String getDescription() {
        return getTextFromLabel(DESCRIPTION_FIELD_ID);
    }

    public List<String> getTags() {
        return getTags(getTagsContainer());
    }

    private List<String> getTags(Region tagsContainer) {
        return tagsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(node -> ((Labeled) node).getText())
                .collect(Collectors.toList());
    }

    private List<String> getTags(UniqueTagList tags) {
        return tags
                .asObservableList()
                .stream()
                .map(tag -> tag.tagName)
                .collect(Collectors.toList());
    }

    private Region getTagsContainer() {
        return guiRobot.from(node).lookup(TAGS_FIELD_ID).query();
    }

    //@@author A0141647E
    public boolean isSameTask(Task taskToCompare) {
        System.out.println(getName() + " and then " + taskToCompare.getName());
        System.out.println(getStartTime() + " and then " + taskToCompare.getStartTime());
        System.out.println(getEndTime() + " and then " + taskToCompare.getEndTime());
        return getName().equals(taskToCompare.getName().fullName)
                && (taskToCompare.getStartTime() == null ?
                        getStartTime().equals("")
                        : getStartTime().equals("Start: " + taskToCompare.getStartTime().toString()))
                && (taskToCompare.getEndTime() == null ?
                        getEndTime().equals("")
                        : getEndTime().equals("End: " + taskToCompare.getEndTime().toString()))
                && (taskToCompare.getDescription() == null ?
                        getDescription().equals("")
                        : getDescription().equals("Description: " + taskToCompare.getDescription()));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TaskCardHandle) {
            TaskCardHandle handle = (TaskCardHandle) obj;
            return getName().equals(handle.getName())
                    && (getEndTime() == null ?
                            handle.getEndTime() == null
                            : getEndTime().equals(handle.getEndTime()))
                    && (getStartTime() == null ?
                            handle.getStartTime() == null
                            : getStartTime().equals(handle.getStartTime()))
                    && getTags().equals(handle.getTags());
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getName()
                + (getStartTime() == null ? "" : " " + getStartTime())
                + (getEndTime() == null ? "" : " " + getEndTime());
    }
}
