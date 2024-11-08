package org.switch2022.project.datamodel.jpa.project;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.switch2022.project.datamodel.jpa.project.ProductBacklogJPA;

import static org.junit.jupiter.api.Assertions.*;

class ProductBacklogJPATest {

    private static String projectCode;
    private static String userStoryCodeList;
    private static ProductBacklogJPA productBacklog;

    @BeforeAll
    static void init() {
        projectCode = "P1";
        userStoryCodeList = "this is a list";
        productBacklog = new ProductBacklogJPA(projectCode, userStoryCodeList);
    }

    @AfterAll
    static void tearDown() {
        projectCode = null;
        userStoryCodeList = null;
        productBacklog = null;
    }

    @Test
    void getProjectCodeValue_ReturnsProjectCode() {

        String result = productBacklog.getProjectCodeValue();

        assertEquals(projectCode, result);
    }

    @Test
    void getUserStoryCodeList_ReturnsUserStoryCodeList() {

        String result = productBacklog.getUserStoryCodeList();

        assertEquals(userStoryCodeList, result);
    }

    @Test
    void equals_SameObject_True() {
        ProductBacklogJPA anotherProductBacklogJPA = productBacklog;

        boolean result = productBacklog.equals(anotherProductBacklogJPA);

        assertTrue(result);
    }

    @Test
    void equals_EqualObject_True() {
        ProductBacklogJPA anotherProductBacklogJPA = new ProductBacklogJPA(projectCode, userStoryCodeList);

        boolean result = productBacklog.equals(anotherProductBacklogJPA);

        assertTrue(result);
    }

    @Test
    void equals_DifferentObject_False() {
        String anotherProjectCode = "P2";
        String anotherList = "this is another list";

        ProductBacklogJPA anotherProductBacklogJPA = new ProductBacklogJPA(anotherProjectCode, anotherList);

        boolean result = productBacklog.equals(anotherProductBacklogJPA);

        assertFalse(result);
    }

    @Test
    void equals_DifferentObjectWithDifferentProjectCode_False() {
        String anotherProjectCode = "P2";

        ProductBacklogJPA anotherProductBacklogJPA = new ProductBacklogJPA(anotherProjectCode, userStoryCodeList);

        boolean result = productBacklog.equals(anotherProductBacklogJPA);

        assertFalse(result);
    }

    @Test
    void equals_DifferentObjectWithDifferentUserStoryList_False() {
        String anotherList = "this is another list";

        ProductBacklogJPA anotherProductBacklogJPA = new ProductBacklogJPA(projectCode, anotherList);

        boolean result = productBacklog.equals(anotherProductBacklogJPA);

        assertFalse(result);
    }

    @Test
    void equals_ObjectOfDifferentType_False() {
        String anotherProductBacklogJPA = "not a product backlog";

        boolean result = productBacklog.equals(anotherProductBacklogJPA);

        assertFalse(result);
    }

    @Test
    void equals_NullObject_False() {

        boolean result = productBacklog.equals(null);

        assertFalse(result);

    }

    @Test
    void hashCode_SameObject_True() {
        ProductBacklogJPA anotherProductBacklogJPA = productBacklog;

        int firstHash = productBacklog.hashCode();
        int secondHash = anotherProductBacklogJPA.hashCode();

        assertEquals(firstHash, secondHash);
    }

    @Test
    void hashCode_DifferentObject_False() {
        String anotherProjectCode = "P3";
        String anotherUserStoryList = "a list to test";
        ProductBacklogJPA productBacklogJPA = new ProductBacklogJPA(anotherProjectCode, anotherUserStoryList);

        int firstHash = productBacklog.hashCode();
        int secondHash = productBacklogJPA.hashCode();

        assertNotEquals(firstHash, secondHash);
    }
}