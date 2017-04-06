package seedu.todolist.integration;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Iterator;
import java.util.List;

import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

import seedu.todolist.integration.util.GoogleIntegrationUtil;
import seedu.todolist.model.Model;
import seedu.todolist.model.task.Task;

//@@author A0141647E
/*
 * A class that represents a connection with Google Calendar Service.
 * The author made some architectural changes and added in 4 methods:
 * sync, add, taskConverter and addHour.
 */
public class GoogleIntegration {

    //Represent the identification of the calendar that the app requests service from
    public static final String CALENDAR_ID = "primary";

    //Represent an authorized calendar client
    private final com.google.api.services.calendar.Calendar service;

    //Default constructor
    public GoogleIntegration() throws IOException, GeneralSecurityException {
        this.service = GoogleIntegrationUtil.getCalendarService();
    }

    //@@author A0141647E
    /*
     * Import the current Tasks in storage to Google Calendar.
     * This will delete all available Tasks on Google Calendar first before importing.
     */
    public void sync(Model model) throws IOException {
        Iterator<Task> taskIterator = model.getToDoList().getTaskList().iterator();
        clearGoogleCalendar();
        while (taskIterator.hasNext()) {
            Task taskToSync = taskIterator.next();
            if (taskToSync.getStartTime() != null || taskToSync.getEndTime() != null) {
                add(taskToSync);
            }
        }
    }

    //@@author A0141647E
    /*
     * Add a Task object to the user Google Calendar account.
     * It is guaranteed that this Task object is NOT a floating task.
     */
    public void add(Task taskToAdd) throws IOException {
        assert (taskToAdd.getStartTime() != null || taskToAdd.getEndTime() != null);

        System.out.println("Running add to google calendar ...");
        taskToAdd = GoogleIntegrationUtil.taskNormalizer(taskToAdd);
        Event taskEvent = GoogleIntegrationUtil.taskConverter(taskToAdd);

        try {
            Event taskEventAdded = service.events().insert(CALENDAR_ID, taskEvent).execute();
            System.out.printf("Event added successfully: %s\n", taskEventAdded.getSummary());
        } catch (IOException ioe) {
            throw new IOException("Task could not be added to Google Calendar\n"
                    + ioe.getMessage());
        }
    }

    //@@author A0141647E
    /*
     * Clears the google calendar
     */
    public void clearGoogleCalendar() throws IOException {
        Events events = service.events().list("primary").execute();
        List<Event> items = events.getItems();
        if (items.size() != 0) {
            String[] eventIds = new String[items.size()];
            int i = 0;
            for (Event event : items) {
                eventIds[i] = event.getId();
                i++;
            }
            for (int j = 0; j < eventIds.length; j++) {
                service.events().delete("primary", eventIds[j]);
            }
        }
    }

}
