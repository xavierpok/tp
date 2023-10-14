package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;


class LastModifiedDateTimeTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new LastModifiedDateTime(null));
    }

    @Test
    public void factory_invalid_throwsIllegalArgumentException() {
        String invalidLastModifiedDateTime = "";
        assertThrows(IllegalArgumentException.class, () -> LastModifiedDateTime.fromString(""));
    }

    @Test
    public void factory_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> LastModifiedDateTime.fromString(null));
    }



    @Test
    public void isValidLastModifiedDateTime() {
        // null job
        assertThrows(NullPointerException.class, () ->
                LastModifiedDateTime.isValidLastModifiedDateTime(null));

        // invalid LastModifiedDateTimes
        assertFalse(LastModifiedDateTime.isValidLastModifiedDateTime("")); // empty string
        assertFalse(LastModifiedDateTime.isValidLastModifiedDateTime("1 abc 1999, 10:09:00"));
        // nonsensical field
        assertFalse(LastModifiedDateTime.isValidLastModifiedDateTime("40 Jan 1999, 10:09:00"));
        // invalid field
        assertFalse(LastModifiedDateTime.isValidLastModifiedDateTime("1 Jan 1999, 24:09:00"));
        // another invalid field
        assertFalse(LastModifiedDateTime.isValidLastModifiedDateTime(
                LastModifiedDateTime.DEFAULT_LAST_MODIFIED.format(
                        DateTimeFormatter.ISO_LOCAL_DATE_TIME)));
        // Valid input, but in wrong format

        // valid LastModifiedDateTimes
        assertTrue(LastModifiedDateTime.isValidLastModifiedDateTime("1 Jan 1999, 10:09:00"));
        assertTrue(LastModifiedDateTime.isValidLastModifiedDateTime(
                LastModifiedDateTime.DEFAULT_LAST_MODIFIED.format(
                        LastModifiedDateTime.LASTMODIFIED_FORMATTER)));
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

    @Test
    public void toStringMethod() {
        assertEquals(new LastModifiedDateTime(LastModifiedDateTime.DEFAULT_LAST_MODIFIED).toString(),
                LastModifiedDateTime.DEFAULT_LAST_MODIFIED.format(LastModifiedDateTime.LASTMODIFIED_FORMATTER));
        assertEquals(new LastModifiedDateTime(LocalDateTime.MAX).toString(),
                LocalDateTime.MAX.format(LastModifiedDateTime.LASTMODIFIED_FORMATTER));
        assertEquals(new LastModifiedDateTime(LocalDateTime.MIN).toString(),
                LocalDateTime.MIN.format(LastModifiedDateTime.LASTMODIFIED_FORMATTER));
    }


}
