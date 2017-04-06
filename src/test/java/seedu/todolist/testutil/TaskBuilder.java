package seedu.todolist.testutil;

import seedu.todolist.commons.exceptions.IllegalValueException;
import seedu.todolist.model.tag.Tag;
import seedu.todolist.model.tag.UniqueTagList;
import seedu.todolist.model.task.EndTime;
import seedu.todolist.model.task.Name;
import seedu.todolist.model.task.StartTime;

/**
 *
 */
public class TaskBuilder {

    private TestTask task;

    public TaskBuilder() {
        this.task = new TestTask();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public TaskBuilder(TestTask taskToCopy) {
        this.task = new TestTask(taskToCopy);
    }

    public TaskBuilder withName(String name) throws IllegalValueException {
        this.task.setName(new Name(name));
        return this;
    }

    public TaskBuilder withStartTime(String start) throws IllegalValueException {
        this.task.setStartTime(new StartTime(start));
        return this;
    }

    public TaskBuilder withEndTime(String end) throws IllegalValueException {
        this.task.setEndTime(new EndTime(end));
        return this;
    }

    public TaskBuilder withTags(String ... tags) throws IllegalValueException {
        task.setTags(new UniqueTagList());
        for (String tag: tags) {
            task.getTags().add(new Tag(tag));
        }
        return this;
    }

    public TaskBuilder withDescription(String description) {
        this.task.setDescription(description);
        return this;
    }

    public TaskBuilder withCompletion(boolean isComplete) {
        if (isComplete) {
            this.task.setAsComplete();
        } else {
            this.task.setAsIncomplete();
        }
        return this;
    }

    public TestTask build() {
        return this.task;
    }
}
