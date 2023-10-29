package connexion.model.person;

import static connexion.logic.commands.CommandTestUtil.VALID_COMPANY_BOB;
import static connexion.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static connexion.logic.commands.CommandTestUtil.VALID_JOB_BOB;
import static connexion.logic.commands.CommandTestUtil.VALID_LAST_MODIFIED_BOB;
import static connexion.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static connexion.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static connexion.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static connexion.testutil.Assert.assertThrows;
import static connexion.testutil.TypicalPersons.ALICE;
import static connexion.testutil.TypicalPersons.BOB;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import connexion.testutil.PersonBuilder;


public class PersonTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Person person = new PersonBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> person.getTags().remove(0));
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.isSamePerson(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSamePerson(null));

        // same name, all other attributes different -> returns true
        Person editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withCompany(VALID_COMPANY_BOB).withJob(VALID_JOB_BOB)
                .withTags(VALID_TAG_HUSBAND).withLastModifiedDateTime(VALID_LAST_MODIFIED_BOB).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // different name, all other attributes same -> returns false
        editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSamePerson(editedAlice));

        // name differs in case, all other attributes same -> returns false
        Person editedBob = new PersonBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertFalse(BOB.isSamePerson(editedBob));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new PersonBuilder(BOB).withName(nameWithTrailingSpaces).build();
        assertFalse(BOB.isSamePerson(editedBob));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Person aliceCopy = new PersonBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different person -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Person editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different company -> returns false
        editedAlice = new PersonBuilder(ALICE).withCompany(VALID_COMPANY_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different job -> returns false
        editedAlice = new PersonBuilder(ALICE).withJob(VALID_JOB_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));

        // different last modified -> returns false
        editedAlice = new PersonBuilder(ALICE).withLastModifiedDateTime(LocalDateTime.MIN).build();
        assertFalse(ALICE.equals(editedAlice));


    }

    @Test
    public void toStringMethod() {
        String expected = Person.class.getCanonicalName() + "{name=" + ALICE.getName() + ", phone=" + ALICE.getPhone()
                + ", email=" + ALICE.getEmail() + ", company=" + ALICE.getCompany() + ", job=" + ALICE.getJob()
                + ", tags=" + ALICE.getTags() + ", last-modified=" + ALICE.getLastModifiedDateTime()
                + ", schedule=" + ALICE.getSchedule()
                + ", scheduleName=" + ALICE.getScheduleName()
                + ", note=" + ALICE.getNote() + "}";
        assertEquals(expected, ALICE.toString());
    }
}
