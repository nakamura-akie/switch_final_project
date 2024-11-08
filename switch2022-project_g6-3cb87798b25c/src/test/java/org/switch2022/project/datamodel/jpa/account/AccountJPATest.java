package org.switch2022.project.datamodel.jpa.account;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountJPATest {

    @Test
    void emptyConstructor(){
        AccountJPA accountJPA = new AccountJPA();
        assertNotNull(accountJPA);
    }
    @Test
    void getAccountName() {
        AccountJPA accountJpa = new AccountJPA("Ana", "ana@ana.com", 927466483, "user", true);
        String result = accountJpa.getAccountName();
        assertEquals("Ana", result);
    }

    @Test
    void getEmail() {
        AccountJPA accountJpa = new AccountJPA("Ana", "ana@ana.com", 927466483, "user", true);
        String result = accountJpa.getEmail();
        assertEquals("ana@ana.com", result);
    }

    @Test
    void getPhoneNumber() {
        AccountJPA accountJpa = new AccountJPA("Ana", "ana@ana.com", 927466483, "user", true);
        int result = accountJpa.getPhoneNumber();
        assertEquals(927466483, result);
    }

    @Test
    void getProfileName() {
        AccountJPA accountJpa = new AccountJPA("Ana", "ana@ana.com", 927466483, "user", true);
        String result = accountJpa.getProfileName();
        assertEquals("user", result);
    }

    @Test
    void getAccountIsActive() {
        AccountJPA accountJpa = new AccountJPA("Ana", "ana@ana.com", 927466483, "user", true);
        boolean result = accountJpa.getAccountIsActive();
        assertTrue(result);
    }

    @Test
    void equals_SameObject_Equal() {
        AccountJPA accountJpa = new AccountJPA("Ana", "ana@ana.com", 927466483, "user", true);
        assertEquals(accountJpa,accountJpa);
    }

    @Test
    void equals_EqualObject_Equal() {
        AccountJPA accountJpa = new AccountJPA("Ana", "ana@ana.com", 927466483, "user", true);
        AccountJPA anotherAccountJPA = new AccountJPA("Ana", "ana@ana.com", 927466483, "user", true);
        assertEquals(accountJpa, anotherAccountJPA);
    }

    @Test
    void equals_DifferentType_NotEqual() {
        AccountJPA accountJpa = new AccountJPA("Ana", "ana@ana.com", 927466483, "user", true);
        String anotherAccount = "not an account";
        assertNotEquals(accountJpa, anotherAccount);
    }

    @Test
    void equals_DifferentObject_DifferentName_NotEqual() {
        AccountJPA accountJpa = new AccountJPA("Ana", "ana@ana.com", 927466483, "user", true);
        AccountJPA anotherAccountJPA = new AccountJPA("Rita", "ana@ana.com", 927466483, "user", true);
        assertNotEquals(accountJpa, anotherAccountJPA);
    }

    @Test
    void equals_DifferentObject_DifferentEmail_NotEqual() {
        AccountJPA accountJpa = new AccountJPA("Ana", "john@john.com", 927466483, "user", true);
        AccountJPA anotherAccountJPA = new AccountJPA("Ana", "ana@ana.com", 927466483, "user", true);
        assertNotEquals(accountJpa, anotherAccountJPA);
    }
    @Test
    void equals_DifferentObject_DifferentPhoneNumber_NotEqual() {
        AccountJPA accountJpa = new AccountJPA("Ana", "ana@ana.com", 917844675, "user", true);
        AccountJPA anotherAccountJPA = new AccountJPA("Ana", "ana@ana.com", 927466483, "user", true);
        assertNotEquals(accountJpa, anotherAccountJPA);

    }
    @Test
    void equals_DifferentObject_DifferentProfileName_NotEqual() {
        AccountJPA accountJpa = new AccountJPA("Ana", "ana@ana.com", 927466483, "product owner", true);
        AccountJPA anotherAccountJPA = new AccountJPA("Ana", "ana@ana.com", 927466483, "user", true);
        assertNotEquals(accountJpa, anotherAccountJPA);

    }
    @Test
    void equals_DifferentObject_DifferentAccountStatus_NotEqual() {
        AccountJPA accountJpa = new AccountJPA("Ana", "ana@ana.com", 927466483, "user", false);
        AccountJPA anotherAccountJPA = new AccountJPA("Ana", "ana@ana.com", 927466483, "user", true);
        assertNotEquals(accountJpa, anotherAccountJPA);

    }

    @Test
    void equals_NullObject_False() {
        AccountJPA accountJpa = new AccountJPA("Ana", "ana@ana.com", 927466483, "user", false);
        assertNotNull(accountJpa);

    }

    @Test
    void hashCode_SameObject_True() {
        AccountJPA accountJpa = new AccountJPA("Ana", "ana@ana.com", 927466483, "user", true);
        AccountJPA anotherAccountJPA = new AccountJPA("Ana", "ana@ana.com", 927466483, "user", true);
        int firstHash = accountJpa.hashCode();
        int secondHash = anotherAccountJPA.hashCode();
        assertEquals(firstHash, secondHash);
    }

    @Test
    void hashCode_DifferentObject_False() {
        AccountJPA accountJpa = new AccountJPA("John", "john@john.com", 930477586, "user", true);
        AccountJPA anotherAccountJPA = new AccountJPA("Ana", "ana@ana.com", 927466483, "user", true);
        int firstHash = accountJpa.hashCode();
        int secondHash = anotherAccountJPA.hashCode();

        assertNotEquals(firstHash, secondHash);
    }
}