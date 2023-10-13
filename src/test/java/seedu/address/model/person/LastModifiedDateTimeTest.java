package seedu.address.model.person;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.testutil.Assert.assertThrows;

class LastModifiedDateTimeTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new LastModifiedDateTime(null));
    }




    @Test
    public void equals() {
        LastModifiedDateTime lastModifiedDateTime = new LastModifiedDateTime(LocalDateTime.MAX);

        // same values -> returns true
        assertTrue(lastModifiedDateTime.equals(new LastModifiedDateTime(LocalDateTime.MAX)));

        // same object -> returns true
        assertTrue(lastModifiedDateTime.equals(lastModifiedDateTime));

        // null -> returns false
        assertFalse(lastModifiedDateTime.equals(null));

        // different types -> returns false
        assertFalse(lastModifiedDateTime.equals(5.0f));

        // different values -> returns false
        assertFalse(lastModifiedDateTime.equals(new Job("Other Valid Job")));
    }


}