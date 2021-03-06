package seedu.todolist.model.task;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;

import java.util.Date;

//@@author A0141647E
/*
 * Contains methods which help parse string input into Date objects
 * used mostly in StartTime and EndTime constructor.
 */
public class TimeUtil {

    //@@author A0141647E
    /*
     * Parse a string input and return a Date object that corresponds to
     * the date and time conveyed in the string input.
     * @return null if the given string does not follow allowed format
     */
    public static Date parseTime(String time) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy h.mm a");
        Date temp = dateFormatter.parse(time, new ParsePosition(0));
        if (temp == null) {
            dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
            temp = dateFormatter.parse(time, new ParsePosition(0));
            return temp;
        }
        return temp;
    }
}
