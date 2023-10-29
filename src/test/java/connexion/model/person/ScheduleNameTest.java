package connexion.model.person;

import static connexion.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ScheduleNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ScheduleName(null));
    }

    @Test
    public void constructor_invalidScheduleName_throwsIllegalArgumentException() {
        String invalidScheduleName = "";
        assertThrows(IllegalArgumentException.class, () -> new ScheduleName(invalidScheduleName));
    }

    @Test
    public void isValidScheduleName() {
        // null name
        assertThrows(NullPointerException.class, () -> ScheduleName.isValidScheduleName(null));

        // invalid name
        assertFalse(ScheduleName.isValidScheduleName("")); // empty string
        assertFalse(ScheduleName.isValidScheduleName(" ")); // spaces only
        assertFalse(ScheduleName.isValidScheduleName("^")); // only non-alphanumeric characters
        assertFalse(ScheduleName.isValidScheduleName("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(ScheduleName.isValidScheduleName("peter jack")); // alphabets only
        assertTrue(ScheduleName.isValidScheduleName("12345")); // numbers only
        assertTrue(ScheduleName.isValidScheduleName("peter the 2nd")); // alphanumeric characters
        assertTrue(ScheduleName.isValidScheduleName("Capital Tan")); // with capital letters
        assertTrue(ScheduleName.isValidScheduleName("David Roger Jackson Ray Jr 2nd")); // long names
    }

    @Test
    public void equals() {
        ScheduleName name = new ScheduleName("Valid ScheduleName");

        // same values -> returns true
        assertTrue(name.equals(new ScheduleName("Valid ScheduleName")));

        // same object -> returns true
        assertTrue(name.equals(name));

        // null -> returns false
        assertFalse(name.equals(null));

        // different types -> returns false
        assertFalse(name.equals(5.0f));

        // different values -> returns false
        assertFalse(name.equals(new ScheduleName("Other Valid ScheduleName")));
    }

    @Test
    void getDetailString_equals_input() {
        ScheduleName name = new ScheduleName("Valid ScheduleName");
        assertEquals(name.getDetailString(), "Valid ScheduleName");
        assertNotEquals(name.getDetailString(), "Nonsense"); //to show it's actually matching the string
    }

    @Test
    void getValue_equals_input() {
        ScheduleName name = new ScheduleName("Valid ScheduleName");
        assertEquals(name.getValue(), "Valid ScheduleName");
        assertNotEquals(name.getValue(), "Nonsense"); //to show it's actually matching the string
    }

    @Test
    void getListString_equals_input() {
        ScheduleName name = new ScheduleName("Valid ScheduleName");
        assertEquals(name.getListString(), "Valid ScheduleName");
        assertNotEquals(name.getListString(), "Nonsense"); //to show it's actually matching the string
    }
}

