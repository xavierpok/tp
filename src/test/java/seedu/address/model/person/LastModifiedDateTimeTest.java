package seedu.address.model.person;

import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.testutil.Assert.assertThrows;

class LastModifiedDateTimeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new LastModifiedDateTime(null));
    }

    @Test
    public void toString_formats_correctly() {
        List<LocalDateTime> testDateTimes = List.of(
                                    LocalDateTime.of(
                                        2023,10,15,12,10,1),
                                    LocalDateTime.of(
                                        2000,5,1,3,5,10),
                                    LocalDateTime.of(
                                        1990,7,8,9,10,11),
                                    LocalDateTime.of(
                                        2103,2,1,0,3,0,0),
                                    LocalDateTime.of(
                                        1989,4,15,8,20,10,5),
                                    LocalDateTime.now(
                                        Clock.system(ZoneId.systemDefault())));

        DateTimeFormatter correctFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);

        for (LocalDateTime testDateTime : testDateTimes) {
            LastModifiedDateTime lastModifiedDateTime = new LastModifiedDateTime(testDateTime);
            assertEquals(correctFormatter.format(testDateTime),lastModifiedDateTime.toString());
        }
    }
    @Test
    public void equals() {
        LastModifiedDateTime lastModifiedDateTime = new LastModifiedDateTime(
                LocalDateTime.of(2103,2,1,0,3,0,0));
        assertTrue(lastModifiedDateTime.equals(
                new LastModifiedDateTime(
                        LocalDateTime.of(
                                2103,2,1,0,3,0,0))));

        assertTrue(lastModifiedDateTime.equals(lastModifiedDateTime));

        assertFalse(lastModifiedDateTime.equals(null));

        assertFalse(lastModifiedDateTime.equals(2103));

        assertFalse(lastModifiedDateTime.equals(new LastModifiedDateTime(
                LocalDateTime.of(
                        2003,2,1,0,3,0,0))));
    }

}