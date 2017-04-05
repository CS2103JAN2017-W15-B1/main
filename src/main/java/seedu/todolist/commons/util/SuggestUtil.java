package seedu.todolist.commons.util;

import seedu.todolist.logic.commands.Command;

//@@author A0141647E
/*
 * Contains method that suggests the most likely correct command
 * if the user key in an unknown command.
 */
public class SuggestUtil {

    //@@author A0141647E
    public static String closestCommand(String userCommandType) {
        int distance = 100;
        String mostRelatedCommandType = "";
        for (int i = 0; i < Command.COMMAND_ALL_TYPES.length; i++) {
            int difference = levenshteinDistance(Command.COMMAND_ALL_TYPES[i], userCommandType);
            if (difference < distance ||
                    (difference == distance &&
                    Command.COMMAND_ALL_TYPES[i].charAt(0) == userCommandType.charAt(0))) {
                distance = difference;
                mostRelatedCommandType = Command.COMMAND_ALL_TYPES[i];
            }
        }
        return mostRelatedCommandType;
    }

    //@@author A0141647E
    public static int levenshteinDistance(String string1, String string2) {
        char[] s1 = string1.toCharArray();
        char[] s2 = string2.toCharArray();
        int n1 = s1.length; int n2 = s2.length;
        int[][] d = new int[n1 + 1][n2 + 1];
        for (int i = 1; i < n1 + 1; i++) {
            d[i][0] = i;
        }
        for (int j = 1; j < n2 + 1; j++) {
            d[0][j] = j;
        }
        for (int j = 0; j < n2; j++) {
            for (int i = 0; i < n1; i++) {
                int substitutionCost;
                if (s1[i] == s2[j]) {
                    substitutionCost = 0;
                } else {
                    substitutionCost = 1;
                }
                d[i + 1][j + 1] = Math.min(Math.min(d[i][j + 1] + 1, d[i + 1][j] + 1), d[i][j] + substitutionCost);
            }
        }
        return d[n1][n2];
    }

}
