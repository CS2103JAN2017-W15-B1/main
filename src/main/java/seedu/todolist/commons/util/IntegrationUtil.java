package seedu.todolist.commons.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/*
 * A class which helps checking if data from the application
 * has already been integrated to Google Calendar.
 */
public class IntegrationUtil {
    public static boolean isSynced(String integrationDataPath)
            throws FileNotFoundException, IOException {
        FileReader fr = new FileReader(integrationDataPath);
        BufferedReader textReader = new BufferedReader(fr);
        String integrationStatus = textReader.readLine().trim();
        boolean status;
        if (integrationStatus.equals("true")) {
            status = true;
        } else {
            status = false;
        }
        textReader.close();
        return status;
    }

    public static void updateStatus(String integrationDataPath)
            throws FileNotFoundException, IOException {
        FileReader fr = new FileReader(integrationDataPath);
        BufferedReader textReader = new BufferedReader(fr);
        String currentStatus = textReader.readLine();
        System.out.println(currentStatus);
        FileWriter fw = new FileWriter(integrationDataPath);
        BufferedWriter textWriter = new BufferedWriter(fw);
        String updatedStatus = currentStatus.replaceAll("false", "true");
        textWriter.write(updatedStatus);
        textReader.close();
        textWriter.close();
    }
}
