package org.switch2022.project.utils;

import org.switch2022.project.domain.valueobject.UserStoryCode;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The UserStoryCodeListToString class provides utility methods for parsing a string representation of a list
 * of user story codes and converting them into a list of UserStoryCode objects.
 */
public final class UserStoryCodeListToString {

    private UserStoryCodeListToString() {}

    /**
     * Parses a string representation of a list of user story codes and converts them into a list of
     * UserStoryCode objects.
     *
     * @param userStoryCodeValues the string representation of the user story code list
     * @return a list of UserStoryCode objects parsed from the string representation
     */
    public static List<UserStoryCode> parseUserStoryCodeList(String userStoryCodeValues) {
        String userStoryCodeValuesWithoutCurlyBrackets = userStoryCodeValues.substring(1,
         userStoryCodeValues.length() - 1);

        if (userStoryCodeValuesWithoutCurlyBrackets.isEmpty()) {
            return new LinkedList<>();
        }

        List<String> userStoryCodeValueList = List.of(userStoryCodeValuesWithoutCurlyBrackets.split(","));

        return userStoryCodeValueList.stream()
                .map(UserStoryCode::new)
                .collect(Collectors.toCollection(LinkedList::new));
    }
}
