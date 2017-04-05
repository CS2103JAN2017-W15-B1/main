package seedu.todolist.commons.events.storage;

import seedu.todolist.commons.events.BaseEvent;

//@@author A0139633B
/**
 * Indicates that the location of the save file has been changed
 */
public class SaveLocationChangedEvent extends BaseEvent {

    public String newSaveLocation;

    public SaveLocationChangedEvent(String newSaveLocation) {
        this.newSaveLocation = newSaveLocation;
    }

    @Override
    public String toString() {
        return "todolist.xml now saved at " + newSaveLocation;
    }

}
