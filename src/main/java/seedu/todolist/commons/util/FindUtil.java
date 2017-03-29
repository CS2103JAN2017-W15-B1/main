package seedu.todolist.commons.util;

import java.util.Set;

import seedu.todolist.model.tag.Tag;
import seedu.todolist.model.task.Task;

public class FindUtil {


  //@@author A0144240W
    /**
     * Returns true if the task has keywords in its tags or name
     * @param keywords cannot be null
     *
     */
    public static boolean containsIgnoreCase(Task task, Set<String> keywords) {
        assert task != null : "task parameter cannot be null";
        assert keywords != null : "keywords parameter cannot be null";


        for (String word : keywords) {
            String preppedWord = word.trim();
            assert !preppedWord.isEmpty() : "set cannot contain all spaces";
            if (preppedWord.startsWith("t/")) {
                String preppedTag = preppedWord.substring(2);
                if (matchTag(task.getTags().toSet(), preppedTag)) {
                    return true;
                }
            } else {
                if (matchName(task.getName().toString(), preppedWord)) {
                    return true;
                }
            }
        }
        return false;
    }


    //@@author A0144240W
    /**
     * Returns true if the set of tags contain the searchTag
     * Ignores case, a full tag match is required.
     * @return
     */
    private static boolean matchTag(Set<Tag> tags, String searchTag) {
        for (Tag tag : tags) {
            if (tag.getTagName().toLowerCase().equals(searchTag.toLowerCase())) {
                return true;
            }
        }
        return false;
    }


    //@@author A0144240W
    /**
     * Returns true if the taskName contains the searchName
     * Ignores case, a full word match is not required.
     * @param taskName must not be null
     * @param searchName must not be null
     *
     */
    private static boolean matchName(String taskName, String searchName) {
        if (taskName.toLowerCase().contains(searchName.toLowerCase())) {
            return true;
        }
        return false;
    }
}

