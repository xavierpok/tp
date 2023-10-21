package connexion.model.person;

import static connexion.testutil.Assert.assertThrows;
import static connexion.testutil.ClockUtil.DEFAULT_TEST_TIME;
import static connexion.testutil.ClockUtil.OTHER_TEST_TIME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

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
                DEFAULT_TEST_TIME.format(
                        DateTimeFormatter.ISO_LOCAL_DATE_TIME)));
        // Valid input, but in wrong format
        assertFalse(LastModifiedDateTime.isValidLastModifiedDateTime("1 Jan, 1999 10:09:00"));

        // valid LastModifiedDateTimes
        assertTrue(LastModifiedDateTime.isValidLastModifiedDateTime("1 Jan 1999, 10:09:00"));
        assertTrue(LastModifiedDateTime.isValidLastModifiedDateTime(
                DEFAULT_TEST_TIME.format(
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
        assertFalse(lastModifiedDateTime.equals(new LastModifiedDateTime(LocalDateTime.MIN)));
    }

    @Test
    public void equals_extraPrecision_true() {
        // Test for equality on extra precision, which we should drop. Precision is to seconds only.
        LastModifiedDateTime lastModifiedDateTime = new LastModifiedDateTime(DEFAULT_TEST_TIME);
        LastModifiedDateTime testDateTime = new LastModifiedDateTime(DEFAULT_TEST_TIME.minusNanos(1));
        assertEquals(lastModifiedDateTime, testDateTime);

        // But the raw LocalDateTimes should be non-equal
        assertNotEquals(DEFAULT_TEST_TIME, DEFAULT_TEST_TIME.minusNanos(1));
    }

    @Test
    public void toStringMethod() {
        assertEquals(new LastModifiedDateTime(DEFAULT_TEST_TIME).toString(),
                DEFAULT_TEST_TIME.format(LastModifiedDateTime.LASTMODIFIED_FORMATTER));
        assertEquals(new LastModifiedDateTime(LocalDateTime.MAX).toString(),
                LocalDateTime.MAX.format(LastModifiedDateTime.LASTMODIFIED_FORMATTER));
        assertEquals(new LastModifiedDateTime(LocalDateTime.MIN).toString(),
                LocalDateTime.MIN.format(LastModifiedDateTime.LASTMODIFIED_FORMATTER));
    }


    @Test
    void getDetailString() {
        assertEquals(new LastModifiedDateTime(DEFAULT_TEST_TIME).getDetailString(),
                DEFAULT_TEST_TIME.format(LastModifiedDateTime.LASTMODIFIED_FORMATTER));
        assertEquals(new LastModifiedDateTime(LocalDateTime.MAX).getDetailString(),
                LocalDateTime.MAX.format(LastModifiedDateTime.LASTMODIFIED_FORMATTER));
        assertEquals(new LastModifiedDateTime(LocalDateTime.MIN).getDetailString(),
                LocalDateTime.MIN.format(LastModifiedDateTime.LASTMODIFIED_FORMATTER));
    }

    @Test
    void getValue() {
        assertEquals(new LastModifiedDateTime(DEFAULT_TEST_TIME).getValue(),
                DEFAULT_TEST_TIME.truncatedTo(ChronoUnit.SECONDS));
        assertNotEquals(new LastModifiedDateTime(DEFAULT_TEST_TIME).getValue(),
                DEFAULT_TEST_TIME);
        // Note that LastModifiedDateTime truncates to seconds, so higher precision should generally not be equal
        // Doesn't apply to cases where is zero already, naturally!
        assertEquals(new LastModifiedDateTime(LocalDateTime.MAX).getValue(),
                LocalDateTime.MAX.truncatedTo(ChronoUnit.SECONDS));
        assertNotEquals(new LastModifiedDateTime(LocalDateTime.MAX).getValue(),
                LocalDateTime.MAX);
        assertEquals(new LastModifiedDateTime(OTHER_TEST_TIME).getValue(),
               OTHER_TEST_TIME.truncatedTo(ChronoUnit.SECONDS));
        assertNotEquals(new LastModifiedDateTime(OTHER_TEST_TIME).getValue(),
                OTHER_TEST_TIME);

        assertEquals(new LastModifiedDateTime(DEFAULT_TEST_TIME).getValue(),
                new LastModifiedDateTime(DEFAULT_TEST_TIME).getValue());
        assertNotEquals(new LastModifiedDateTime(DEFAULT_TEST_TIME).getValue(),
                new LastModifiedDateTime(OTHER_TEST_TIME).getValue());

        // Special case where higher precision past seconds is already zero
        // So truncating doesn't do anything
        assertEquals(new LastModifiedDateTime(LocalDateTime.MIN).getValue(),
                LocalDateTime.MIN.truncatedTo(ChronoUnit.SECONDS));
        assertEquals(new LastModifiedDateTime(LocalDateTime.MIN).getValue(),
                LocalDateTime.MIN);

    }
}
