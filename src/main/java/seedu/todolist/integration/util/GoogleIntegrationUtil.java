package seedu.todolist.integration.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;

import seedu.todolist.integration.GoogleIntegration;
import seedu.todolist.model.task.EndTime;
import seedu.todolist.model.task.StartTime;
import seedu.todolist.model.task.Task;
import seedu.todolist.model.task.parser.TaskParser;

public class GoogleIntegrationUtil {

    /** Application name. */
    private static final String APPLICATION_NAME = "dome_java";

    /** Directory to store user credentials for this application. */
    private static final java.io.File DATA_STORE_DIR = new java.io.File(
            System.getProperty("user.home"), ".credentials/dome-java");

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved credentials
     * at ~/.credentials/calendar-java-quickstart
     */
    private static final List<String> SCOPES = Arrays.asList("https://www.googleapis.com/auth/calendar");

    //==================== Methods that help to establish a secure connection with Google Calendar Service =======

    /*
     * Creates an authorized Credential object.
     * @return an authorized Credential object.
     * @throws IOException.
     */
    public static Credential authorize() throws IOException, GeneralSecurityException {
        // Load client secrets.
        InputStream in = GoogleIntegration.class.getResourceAsStream("/client_secret.json");
        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        FileDataStoreFactory DATA_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        HttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(DATA_FACTORY)
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
     * @return an authorized Calendar client service.
     * @throws IOException
     */
    public static com.google.api.services.calendar.Calendar getCalendarService()
            throws IOException, GeneralSecurityException {
        Credential credential = authorize();
        HttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        return new com.google.api.services.calendar.Calendar.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    //=========== Methods that normalize Task objects into the format supported by Google Calendar Service =====

    //@@author A0141647E
    /*
     * A helper method that pre-process a Task to use Google API
     */
    public static Task taskNormalizer(Task task) {
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
        } else if (task.getEndTime() == null) {
            EndTime defaultEnd = new EndTime(addHour(task.getStartTime().getStartTime(),
                    true));
            processedTask = TaskParser.parseTask(
                    task.getName(),
                    task.getStartTime(),
                    defaultEnd,
                    task.getTags(),
                    task.isComplete(),
                    task.getDescription());
        } else {
            processedTask = TaskParser.parseTask(task);
        }

        return processedTask;
    }

    //@@author A0141647E
    /*
     * A helper method that converts a Task object into a corresponding Event object
     * supported by Google Calendar Service.
     */
    public static Event taskConverter(Task taskToConvert) {
        DateTime startDate = new DateTime(
                (taskToConvert.getStartTime() != null ?
                        taskToConvert.getStartTime().getStartTime()
                        : null));
        DateTime endDate = new DateTime(
                (taskToConvert.getEndTime() != null ?
                        taskToConvert.getEndTime().getEndTime()
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
                .setSummary(taskToConvert.getName().toString())
                .setDescription(taskToConvert.getDescription())
                .setStart(start)
                .setEnd(end)
                .setReminders(reminders);
        return taskEvent;
    }

    //@@author A0141647E
    /*
     * A helper method that creates a new Date object
     * one hour before the input Date object
     */
    public static Date addHour(Date date, boolean up) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if (up) {
            cal.add(Calendar.HOUR_OF_DAY, 1);
        } else {
            cal.add(Calendar.HOUR_OF_DAY, -1);
        }

        return cal.getTime();
    }
}
