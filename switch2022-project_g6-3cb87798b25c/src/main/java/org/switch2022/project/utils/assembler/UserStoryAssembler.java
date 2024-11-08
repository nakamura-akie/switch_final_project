package org.switch2022.project.utils.assembler;

import org.switch2022.project.domain.userstory.UserStory;
import org.switch2022.project.utils.dto.UserStoryDTO;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The UserStoryAssembler class is responsible for converting User Story objects
 * into UserStoryDTOs.
 */
public final class UserStoryAssembler {

    /**
     * Private constructor to prevent instantiation of the UserStoryAssembler class.
     */
    private UserStoryAssembler() {}

    /**
     * Creates a user story DTO list
     *
     * @param userStoryIterator the user story iterator with the
     *                          user stories to convert to
     *                          UserStoryDTO and to
     *                          add to the user story list
     * @return list of OutputUserStory DTOs
     */
    public static List<UserStoryDTO> createUserStoryDTOList(Iterator<UserStory> userStoryIterator) {
        List<UserStoryDTO> userStoryList = new ArrayList<>();

        while (userStoryIterator.hasNext()) {
            UserStory currentUserStory = userStoryIterator.next();

            UserStoryDTO userStoryDTO = createUserStoryDTO(currentUserStory);

            userStoryList.add(userStoryDTO);
        }

        return userStoryList;
    }

    /**
     * Converts a User Story to an OutputUserStoryDTO
     *
     * @param userStory the user story
     * @return the Output UserStory DTO of the provided user story
     */
    public static UserStoryDTO createUserStoryDTO(UserStory userStory) {
        UserStoryDTO userStoryDTO = new UserStoryDTO();
        userStoryDTO.projectCode = userStory.getProjectCode();
        userStoryDTO.userStoryCode = userStory.getUserStoryCode();
        userStoryDTO.description = userStory.getDescription();
        userStoryDTO.status = userStory.getUserStoryStatus();
        return userStoryDTO;
    }
}