package connexion.model.person;

import static connexion.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

public class ScheduleTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Schedule(null));
    }

    @Test
    public void constructor_invalidSchedule_throwsIllegalArgumentException() {
        String invalidSchedule = "";
        assertThrows(IllegalArgumentException.class, () -> new Schedule(invalidSchedule));
    }

    @Test
    public void isValidScheduleTime() {
        // null schedule
        assertThrows(NullPointerException.class, () -> Schedule.isValidScheduleTime(null));

        // invalid schedule
        assertFalse(Schedule.isValidScheduleTime("")); // empty string
        assertFalse(Schedule.isValidScheduleTime(" ")); // spaces only
        assertFalse(Schedule.isValidScheduleTime("^")); // only non-alphanumeric characters
        assertFalse(Schedule.isValidScheduleTime("peter*")); // contains non-alphanumeric characters

        // valid schedule
        assertTrue(Schedule.isValidScheduleTime("2023-05-06-12-45")); // YYYY-MM-DD-HH-MM format only
    }

    @Test
    public void equals() {
        Schedule schedule = new Schedule("2023-12-12-12-12");

        // same values -> returns true
        assertTrue(schedule.equals(new Schedule("2023-12-12-12-12")));

        // same object -> returns true
        assertTrue(schedule.equals(schedule));

        // null -> returns false
        assertFalse(schedule.equals(null));

        // different types -> returns false
        assertFalse(schedule.equals(5.0f));

        // different values -> returns false
        assertFalse(schedule.equals(new Schedule("2023-11-11-11-12")));
    }

    @Test
    void getDetailString_equals_input() {
        Schedule schedule = new Schedule("2023-12-12-12-12");
        assertEquals(schedule.getDetailString(), "12 Dec 2023, 12:12:00");
        assertNotEquals(schedule.getDetailString(), "2023-12-12-12-12"); //to show it's actually matching the string
    }

    @Test
    void getValue_equals_input() {
        DateTimeFormatter scheduleFormatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm");
        Schedule schedule = new Schedule("2023-12-12-12-12");
        assertEquals(schedule.getValue(), LocalDateTime.parse("2023-12-12-12-12", scheduleFormatter));
        assertNotEquals(schedule.getValue(), LocalDateTime.parse("2023-11-11-11-10",
                scheduleFormatter)); //to show it's actually matching the string
    }

    @Test
    void getListString_equals_input() {
        Schedule schedule = new Schedule("2023-12-12-12-12");
        assertEquals(schedule.getListString(), "12 Dec 2023, 12:12:00");
        assertNotEquals(schedule.getListString(), "2023-12-12-12-12"); //to show it's actually matching the string
    }
}

