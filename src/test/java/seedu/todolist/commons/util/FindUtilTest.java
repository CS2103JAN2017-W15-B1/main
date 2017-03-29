package seedu.todolist.commons.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.todolist.commons.exceptions.IllegalValueException;
import seedu.todolist.model.tag.Tag;
import seedu.todolist.model.tag.UniqueTagList;
import seedu.todolist.model.task.EndTime;
import seedu.todolist.model.task.Name;
import seedu.todolist.model.task.StartTime;
import seedu.todolist.model.task.Task;
import seedu.todolist.model.task.parser.TaskParser;

public class FindUtilTest {
    Set<Tag> tags;
    Set<String> keywords;
    Task task;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void containsWordIgnoreCase_nullWord_exceptionThrown() throws Exception {
        Tag tag = new Tag("tag1");
        tags = new HashSet<Tag> ();
        tags.add(tag);
        task = generateTaskWithName("typical sentence");
        assertExceptionThrown(task, null, "keywords parameter cannot be null");
    }

    private void assertExceptionThrown(Task task, Set<String> word, String errorMessage) {
        thrown.expect(AssertionError.class);
        thrown.expectMessage(errorMessage);
        FindUtil.containsIgnoreCase(task, word);
    }

    @Test
    public void containsWordIgnoreCase_emptyWord_exceptionThrown()  throws Exception {
        Tag tag = new Tag("tag1");
        tags = new HashSet<Tag> ();
        tags.add(tag);
        Task task = generateTaskWithName("typical sentence");
        keywords = new HashSet<String>();
        keywords.add("   ");
        assertExceptionThrown(task, keywords, "set cannot contain all spaces");
    }

    @Test
    public void containsWordIgnoreCase_nullSentence_exceptionThrown() throws IllegalValueException {
        Tag tag = new Tag("tag1");
        tags = new HashSet<Tag> ();
        tags.add(tag);
        keywords = new HashSet<String>();
        keywords.add("abc");

        assertExceptionThrown(null, keywords, "task parameter cannot be null");
    }

    /*
     * Valid equivalence partitions for word:
     *   - any word
     *   - word containing symbols/numbers
     *   - word with leading/trailing spaces
     *
     * Valid equivalence partitions for sentence:
     *   - empty string
     *   - one word
     *   - multiple words
     *   - sentence with extra spaces
     *
     * Possible scenarios returning true:
     *   - matches first word in sentence
     *   - last word in sentence
     *   - middle word in sentence
     *   - matches multiple words
     *
     * Possible scenarios returning false:
     *   - query word matches part of a sentence word
     *   - sentence word matches part of the query word
     *
     * The test method below tries to verify all above with a reasonably low number of test cases.
     */

    @Test
    public void containsWordIgnoreCase_validInputs_correctResult() throws Exception {
        Tag tag = new Tag("tag1");
        tags = new HashSet<Tag> ();
        tags.add(tag);

        // Matches a partial word only
        task = generateTaskWithName("aaa bbb ccc");
        keywords = new HashSet<String>();
        keywords.add("bb");
        assertTrue(FindUtil.containsIgnoreCase(task, keywords)); // Sentence word bigger than query word

        task = generateTaskWithName("aaa bbb ccc");
        keywords = new HashSet<String>();
        keywords.add("bbbb");
        assertFalse(FindUtil.containsIgnoreCase(task, keywords)); // Query word bigger than sentence word

        // Matches word in the sentence, different upper/lower case letters
        task = generateTaskWithName("aaa bbb ccc");
        keywords = new HashSet<String>();
        keywords.add("Aaa");
        assertTrue(FindUtil.containsIgnoreCase(task, keywords)); // First word (boundary case)


        task = generateTaskWithName("aaa bBb ccc1");
        keywords = new HashSet<String>();
        keywords.add("CCc1");
        assertTrue(FindUtil.containsIgnoreCase(task, keywords)); // Last word (boundary case)

        task = generateTaskWithName("  AAA   bBb   ccc  ");
        keywords = new HashSet<String>();
        keywords.add("aaa");
        assertTrue(FindUtil.containsIgnoreCase(task, keywords)); // Sentence has extra spaces

        task = generateTaskWithName("Aaa");
        keywords = new HashSet<String>();
        keywords.add("aaa");
        assertTrue(FindUtil.containsIgnoreCase(task, keywords)); // Only one word in sentence (boundary case)

        task = generateTaskWithName("AAA   bBb   ccc");
        keywords = new HashSet<String>();
        keywords.add("   ccc   ");
        assertTrue(FindUtil.containsIgnoreCase(task, keywords)); // Leading/trailing spaces


        // Matches multiple words in sentence
        task = generateTaskWithName("  AAA   bBb   ccc bbb ");
        keywords = new HashSet<String>();
        keywords.add("Bbb");
        assertTrue(FindUtil.containsIgnoreCase(task, keywords));
    }

    /**
     * Generates a Task object with given name. Other fields will have some dummy values.
     */
    Task generateTaskWithName(String name) throws Exception {
        return TaskParser.parseTask(
                new Name(name),
                new StartTime("01-01-1995 4.00 PM"),
                new EndTime("01-01-1995 5.00 PM"),
                new UniqueTagList(new Tag("tag"))
                );
    }


}
