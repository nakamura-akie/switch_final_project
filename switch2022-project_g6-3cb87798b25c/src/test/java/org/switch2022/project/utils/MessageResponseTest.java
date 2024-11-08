package org.switch2022.project.utils;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageResponseTest {
    private static MessageResponse messageResponse;

    @BeforeAll
    static void setup() {
        messageResponse = new MessageResponse("Message");
           }

    @AfterAll
    static void tearDown() {
        messageResponse = null;
    }


    @Test
    void testEquals_sameObject_True() {
        assertEquals(messageResponse, messageResponse);
    }

    @Test
    void testEquals_differentObjectsButSameContent_True() {
        MessageResponse response2 = new MessageResponse("Message");
        assertEquals(messageResponse, response2);
    }

    @Test
    void testEquals_differentObjectsAndDifferentContent_False() {
        MessageResponse response2 = new MessageResponse("Message2");
        assertNotEquals(messageResponse, response2);
    }

    @Test
    void testEquals_compareWithNull_False() {
        assertNotEquals(null, messageResponse);
    }

    @Test
    void testEquals_compareWithDifferentClass_False() {
        assertNotEquals("Message1", messageResponse);
    }

    @Test
    void testHashCode_sameContent_True() {
        MessageResponse response2 = new MessageResponse("Message");
        assertEquals(messageResponse.hashCode(), response2.hashCode());
    }

    @Test
    void testHashCode_differentContent_False() {
        MessageResponse response2 = new MessageResponse("Message2");
        assertNotEquals(messageResponse.hashCode(), response2.hashCode());
    }

    @Test
    void testGetMessage() {
        String message = "Message";
        MessageResponse response = new MessageResponse(message);
        assertEquals(message, response.getMessage());
    }
}

