package seedu.todolist.commons.core;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.client.util.DateTime;

import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.*;

import seedu.todolist.model.Model;
import seedu.todolist.model.task.EndTime;
import seedu.todolist.model.task.StartTime;
import seedu.todolist.model.task.Task;
import seedu.todolist.model.task.parser.TaskParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Iterator;

public class GoogleIntegration {
    /** Application name. */
    private static final String APPLICATION_NAME = "dome_java";

    /** Directory to store user credentials for this application. */
    private static final java.io.File DATA_STORE_DIR = new java.io.File(
            System.getProperty("user.home"), ".credentials/dome-java");

    /** Global instance of the {@link FileDataStoreFactory}. */
    private static FileDataStoreFactory DATA_STORE_FACTORY;

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    /** Global instance of the HTTP transport. */
    private static HttpTransport HTTP_TRANSPORT;

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved credentials
     * at ~/.credentials/calendar-java-quickstart
     */
    private static final List<String> SCOPES = Arrays.asList("https://www.googleapis.com/auth/calendar");
    
    /**
     * Represent an authorized calendar client
     */
    private final com.google.api.services.calendar.Calendar service;

    /**
     * Default constructor
     */
    public GoogleIntegration() throws IOException { 
        this.service = getCalendarService();
    }

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    /*
     * Creates an authorized Credential object.
     * @return an authorized Credential object.
     * @throws IOException.
     */
    public static Credential authorize() throws IOException {
        // Load client secrets.
        InputStream in = GoogleIntegration.class.getResourceAsStream("/client_secret.json");
        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(DATA_STORE_FACTORY)
                .setAccessType("offline")
                .build();
        Credential credential = new AuthorizationCodeInstalledApp(
                flow, new LocalServerReceiver()).authorize("user");
        System.out.println(
                "Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
        return credential;
    }

    /**
     * Build and return an authorized Calendar client service.    
     * @return an authorized Calendar client service      
     * @throws IOException      
     */
    public static com.google.api.services.calendar.Calendar getCalendarService() throws IOException {
        Credential credential = authorize();
        return new com.google.api.services.calendar.Calendar.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public void run() throws IOException {
        // Build a new authorized API client service.
        // Note: Do not confuse this class with the
        //   com.google.api.services.calendar.model.Calendar class.
        // List the next 10 events from the primary calendar.
        DateTime now = new DateTime(System.currentTimeMillis());
        CalendarListEntry calendarListEntry = service.calendarList().get("primary").execute();
        System.out.println(calendarListEntry.getSummary());
        Events events = service.events().list("primary")
                .setMaxResults(10)
                .setTimeMin(now)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();
        List<Event> items = events.getItems();
        if (items.size() == 0) {
            System.out.println("No upcoming events found.");
        } else {
            System.out.println("Upcoming events");
            for (Event event : items) {
                DateTime start = event.getStart().getDateTime();
                if (start == null) {
                    start = event.getStart().getDate();
                }
                System.out.printf("%s (%s)\n", event.getSummary(), start);
            }
        }
    }

    public void add(Task taskToAdd) {
        //Add a new Event into the user's Google Calendar
        ;
    }

    /*
     * Import the current Tasks in storage to Google Calendar.
     * Meant to be only called once when the application first initiate.
     */
    public void sync(Model model) throws IOException {
        Iterator<Task> taskIterator = model.getToDoList().getTaskList().iterator();
        while (taskIterator.hasNext()) {
            Task taskToSync = taskIterator.next();
            if (taskToSync.getStartTime() != null || taskToSync.getEndTime() != null) {
                taskToSync = taskConverter(taskToSync);
                DateTime startDate = new DateTime(
                        (taskToSync.getStartTime() != null ?
                                taskToSync.getStartTime().getStartTime()
                                : null));
                DateTime endDate = new DateTime(
                        (taskToSync.getEndTime() != null ?
                                taskToSync.getEndTime().getEndTime()
                                : null));
                EventDateTime start = new EventDateTime()
                        .setDateTime(startDate)
                        .setTimeZone("Singapore");
                EventDateTime end = new EventDateTime()
                        .setDateTime(endDate)
                        .setTimeZone("Singapore");
                Event.Reminders reminders = new Event.Reminders()
                        .setUseDefault(true);
                Event taskEvent = new Event()
                        .setSummary(taskToSync.getName().toString())
                        .setDescription(taskToSync.getDescription())
                        .setStart(start)
                        .setEnd(end)
                        .setReminders(reminders);
                
                String calendarId = "primary"; //why ah - oh because my app only has write access here
                taskEvent = service.events().insert(calendarId, taskEvent).execute();
                System.out.printf("Event added: %s\n", taskEvent.getHtmlLink());
            }
        }
    }

    /*
     * A helper method that pre-process a Task to use Google API
     */
    Task taskConverter(Task task) {
        assert (task.getStartTime() != null || task.getEndTime() != null);

        Task processedTask;

        if (task.getStartTime() == null) {
            StartTime defaultStart = new StartTime(addHour(task.getEndTime().getEndTime(),
                    false));
            processedTask = TaskParser.parseTask(
                    task.getName(),
                    defaultStart,
                    task.getEndTime(),
                    task.getTags(),
                    task.isComplete(),
                    task.getDescription());
        }
        else if (task.getEndTime() == null) {
            EndTime defaultEnd = new EndTime(addHour(task.getStartTime().getStartTime(),
                    true));
            processedTask = TaskParser.parseTask(
                    task.getName(),
                    task.getStartTime(),
                    defaultEnd,
                    task.getTags(),
                    task.isComplete(),
                    task.getDescription());
        }
        else {
            processedTask = TaskParser.parseTask(task);
        }
        return processedTask;
    }

    /*
     * A helper method that creates a new Date object
     * one hour before the input Date object
     */
    Date addHour(Date date, boolean up) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if (up) {
            cal.add(Calendar.HOUR_OF_DAY, 1);
        }
        else {
            cal.add(Calendar.HOUR_OF_DAY, -1);
        }
        return cal.getTime();
    }
}
