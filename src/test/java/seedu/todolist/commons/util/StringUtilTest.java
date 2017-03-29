package seedu.todolist.commons.util;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class StringUtilTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    //---------------- Tests for isUnsignedPositiveInteger --------------------------------------

    @Test
    public void isUnsignedPositiveInteger() {

        // Equivalence partition: null
        assertFalse(StringUtil.isUnsignedInteger(null));

        // EP: empty strings
        assertFalse(StringUtil.isUnsignedInteger("")); // Boundary value
        assertFalse(StringUtil.isUnsignedInteger("  "));

        // EP: not a number
        assertFalse(StringUtil.isUnsignedInteger("a"));
        assertFalse(StringUtil.isUnsignedInteger("aaa"));

        // EP: zero
        assertFalse(StringUtil.isUnsignedInteger("0"));

        // EP: signed numbers
        assertFalse(StringUtil.isUnsignedInteger("-1"));
        assertFalse(StringUtil.isUnsignedInteger("+1"));

        // EP: numbers with white space
        assertFalse(StringUtil.isUnsignedInteger(" 10 ")); // Leading/trailing spaces
        assertFalse(StringUtil.isUnsignedInteger("1 0"));  // Spaces in the middle

        // EP: valid numbers, should return true
        assertTrue(StringUtil.isUnsignedInteger("1")); // Boundary value
        assertTrue(StringUtil.isUnsignedInteger("10"));
    }


    //---------------- Tests for getDetails --------------------------------------

    /*
     * Equivalence Partitions: null, valid throwable object
     */

    @Test
    public void getDetails_exceptionGiven() {
        assertThat(StringUtil.getDetails(new FileNotFoundException("file not found")),
                   containsString("java.io.FileNotFoundException: file not found"));
    }

    @Test
    public void getDetails_nullGiven_assertionError() {
        thrown.expect(AssertionError.class);
        StringUtil.getDetails(null);
    }

}

