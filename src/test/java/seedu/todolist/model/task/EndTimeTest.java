package seedu.todolist.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;

public class EndTimeTest {

    @Test
    public void isValidEndTime() {
        //invalid EndTime
        assertFalse(isValidEndTime("12/12/2016")); //incorrect format
        assertFalse(isValidEndTime("12-12/2016")); //incorrect format
        assertFalse(isValidEndTime("")); //empty string not allowed
        assertFalse(isValidEndTime("      ")); //string of spaces not allowed
        assertFalse(isValidEndTime("12-12 6.00 PM")); //incorrect format - must be dd-mm-yyyy h.mm a
        
        //valid EndTime
        assertTrue(isValidEndTime("12-12-2004 5.00 PM")); //both date and time given
        assertTrue(isValidEndTime("14-06-2014")); //only date given
        assertTrue(isValidEndTime("08-11-2015 8.00 am")); //am can be in lower case
        assertTrue(isValidEndTime("04-12-2017 6 PM")); //time does not need to be h.mm
        assertTrue(isValidEndTime("30-06-2017 6:45 AM")); //. can be replaced with :
        assertTrue(isValidEndTime("4-5-2017 14:00 PM")); //single digit is allowed without the preceding 0
    }
    
    public boolean isValidEndTime(String startTime) {
        Date temp = TimeUtil.parseTime(startTime);
        if (temp == null) {
            return false;
        } else {
            return true;
        }
    }
}
