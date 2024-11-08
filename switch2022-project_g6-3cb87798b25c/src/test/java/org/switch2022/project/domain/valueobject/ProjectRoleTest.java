package org.switch2022.project.domain.valueobject;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProjectRoleTest {

    @Test
    void isProductOwner() {
        assertFalse(ProjectRole.SCRUM_MASTER.isProductOwner());
        assertFalse(ProjectRole.TEAM_MEMBER.isProductOwner());
        assertFalse(ProjectRole.PROJECT_MANAGER.isProductOwner());

        assertTrue(ProjectRole.PRODUCT_OWNER.isProductOwner());
    }

    @Test
    void isScrumMaster() {
        assertFalse(ProjectRole.PRODUCT_OWNER.isScrumMaster());
        assertFalse(ProjectRole.TEAM_MEMBER.isScrumMaster());
        assertFalse(ProjectRole.PROJECT_MANAGER.isScrumMaster());

        assertTrue(ProjectRole.SCRUM_MASTER.isScrumMaster());
    }

    @Test
    void isTeamMember() {
        assertFalse(ProjectRole.PRODUCT_OWNER.isTeamMember());
        assertFalse(ProjectRole.SCRUM_MASTER.isTeamMember());
        assertFalse(ProjectRole.PROJECT_MANAGER.isTeamMember());

        assertTrue(ProjectRole.TEAM_MEMBER.isTeamMember());
    }
}