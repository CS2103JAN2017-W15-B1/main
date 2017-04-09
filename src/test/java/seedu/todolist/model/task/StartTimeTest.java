package seedu.todolist.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;

public class StartTimeTest {

    @Test
    public void isValidStartTime() {
        //invalid StartTime
        assertFalse(isValidStartTime("12/12/2016")); //incorrect format
        assertFalse(isValidStartTime("12-12/2016")); //incorrect format
        assertFalse(isValidStartTime("")); //empty string not allowed
        assertFalse(isValidStartTime("      ")); //string of spaces not allowed
        assertFalse(isValidStartTime("12-12 6.00 PM")); //incorrect format - must be dd-mm-yyyy h.mm a

        //valid StartTime
        assertTrue(isValidStartTime("12-12-2004 5.00 PM")); //both date and time given
        assertTrue(isValidStartTime("14-06-2014")); //only date given
        assertTrue(isValidStartTime("08-11-2015 8.00 am")); //am can be in lower case
        assertTrue(isValidStartTime("04-12-2017 6 PM")); //time does not need to be h.mm
        assertTrue(isValidStartTime("30-06-2017 6:45 AM")); //. can be replaced with :
        assertTrue(isValidStartTime("4-5-2017 14:00 PM")); //single digit is allowed without the preceding 0
    }

    public boolean isValidStartTime(String startTime) {
        Date temp = TimeUtil.parseTime(startTime);
        return (temp == null ? false : true);
    }
}
