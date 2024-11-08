package org.switch2022.project.datamodel.assembler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.switch2022.project.datamodel.jpa.userstory.UserStoryIdJPA;
import org.switch2022.project.datamodel.jpa.userstory.UserStoryJPA;
import org.switch2022.project.domain.userstory.UserStory;
import org.switch2022.project.domain.userstory.UserStoryFactory;
import org.switch2022.project.domain.valueobject.*;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserStoryDomainDataAssembler {
    private final UserStoryFactory userStoryFactory;

    /**
     * Instantiates a new UserStoryDomainDataAssembler
     *
     * @param userStoryFactory the user story factory
     * @throws IllegalArgumentException when user story factory is null
     */
    @Autowired
    public UserStoryDomainDataAssembler(UserStoryFactory userStoryFactory) {
        if (userStoryFactory == null) {
            throw new IllegalArgumentException("User Story Factory cannot be null");
        }
        this.userStoryFactory = userStoryFactory;
    }

    /**
     * Converts a domain UserStory to a UserStoryJPA
     *
     * @param userStory the domain user story
     * @return the UserStoryJPA equivalent of the user story provided
     */
    public UserStoryJPA toData(UserStory userStory) {
        UserStoryID userStoryID = userStory.identity();

        UserStoryIdJPA userStoryIdJPA = new UserStoryIdJPA(userStoryID.getProjectCode().getProjectCodeValue(),
                userStoryID.getUserStoryCode().getUserStoryCodeValue());

        String description = userStory.getDescription().getDescriptionValue();
        String status = userStory.getUserStoryStatus().toString();

        return new UserStoryJPA(userStoryIdJPA, description, status);
    }

    /**
     * Converts a UserStoryJPA to a domain UserStory
     *
     * @param userStoryJPA the UserStoryJPA to convert
     * @return the UserStory equivalent of the User Story JPA provided
     */
    public UserStory toDomain(UserStoryJPA userStoryJPA) {
        ProjectCode projectCode = new ProjectCode(userStoryJPA.getuserStoryIdJpa().getProjectCodeValue());
        UserStoryCode userStoryCode = new UserStoryCode(userStoryJPA.getuserStoryIdJpa().getUserStoryCode());

        UserStoryID userStoryID = new UserStoryID(projectCode, userStoryCode);

        Description description = new Description(userStoryJPA.getDescription());

        UserStory userStory = userStoryFactory.createUserStory(userStoryID, description);
        userStory.changeStatus(UserStoryStatus.valueOf(userStoryJPA.getUserStoryStatus()));

        return userStory;
    }

    /**
     * Converts a list of {@link UserStoryID} objects to a list of {@link UserStoryIdJPA}.
     *
     * @param listOfIDs the list of UserStoryID objects to convert
     * @return a list of the corresponding UserStoryIdJPA objects
     */
    public List<UserStoryIdJPA> listOfIdsToData(Iterable<UserStoryID> listOfIDs) {
        List<UserStoryIdJPA> listOfUserStoryIdJPA = new ArrayList<>();

        for (UserStoryID id : listOfIDs) {
            UserStoryIdJPA userStoryIdJPA = userStoryIdToData(id);
            listOfUserStoryIdJPA.add(userStoryIdJPA);
        }

        return listOfUserStoryIdJPA;
    }

    private static UserStoryIdJPA userStoryIdToData(UserStoryID domainUserStoryId) {
        ProjectCode projectCode = domainUserStoryId.getProjectCode();
        UserStoryCode userStoryCode = domainUserStoryId.getUserStoryCode();

        return new UserStoryIdJPA(projectCode.getProjectCodeValue(), userStoryCode.getUserStoryCodeValue());
    }

    /**
     * Converts a list of {@link UserStoryJPA} objects to a list of {@link UserStory}.
     *
     * @param listOfUserStoryJPA the list of UserStoryJPA objects to convert
     * @return the list of UserStory objects
     */
    public Iterable<UserStory> userStoryJPAListToDomain(Iterable<UserStoryJPA> listOfUserStoryJPA) {
        List<UserStory> domainUserStoryList = new ArrayList<>();

        for (UserStoryJPA userStoryJPA : listOfUserStoryJPA) {
            UserStory domainUserStory = toDomain(userStoryJPA);
            domainUserStoryList.add(domainUserStory);
        }

        return domainUserStoryList;
    }

}
