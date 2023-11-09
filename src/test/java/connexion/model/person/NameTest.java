package connexion.model.person;

import static connexion.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class NameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Name(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new Name(invalidName));
    }

    @Test
    public void isValidName() {
        // EP: null value
        assertThrows(NullPointerException.class, () -> Name.isValidName(null));

        // Invalid name
        // EP: Empty strings
        assertFalse(Name.isValidName("")); // empty string
        assertFalse(Name.isValidName(" ")); // spaces only

        // EP: non-alphanumeric characters
        assertFalse(Name.isValidName("*")); // only non-alphanumeric characters
        assertFalse(Name.isValidName("peter*")); // contains non-alphanumeric characters
        assertFalse(Name.isValidName("peterp/")); // contains non-alphanumeric characters

        // Valid name
        // EP: alphanumeric characters
        assertTrue(Name.isValidName("peter jack")); // alphabets only
        assertTrue(Name.isValidName("12345")); // numbers only
        assertTrue(Name.isValidName("peter the 2nd")); // alphanumeric characters
        assertTrue(Name.isValidName("Capital Tan")); // with capital letters
        assertTrue(Name.isValidName("CAPITAL ONLY")); // only capital letters
        assertTrue(Name.isValidName("David Roger Jackson Ray Jr 2nd")); // long names
    }

    @Test
    public void equals() {
        Name name = new Name("Valid Name");

        // same values -> returns true
        assertTrue(name.equals(new Name("Valid Name")));

        // same object -> returns true
        assertTrue(name.equals(name));

        // null -> returns false
        assertFalse(name.equals(null));

        // different types -> returns false
        assertFalse(name.equals(5.0f));

        // different values -> returns false
        assertFalse(name.equals(new Name("Other Valid Name")));
    }

    @Test
    void getDetailString_equals_input() {
        Name name = new Name("Valid Name");
        assertEquals(name.getDetailString(), "Valid Name");
        assertNotEquals(name.getDetailString(), "Nonsense"); //to show it's actually matching the string
    }

    @Test
    void getValue_equals_input() {
        Name name = new Name("Valid Name");
        assertEquals(name.getValue(), "Valid Name");
        assertNotEquals(name.getValue(), "Nonsense"); //to show it's actually matching the string
    }

    @Test
    void getListString_equals_input() {
        Name name = new Name("Valid Name");
        assertEquals(name.getListString(), "Valid Name");
        assertNotEquals(name.getListString(), "Nonsense"); //to show it's actually matching the string
    }
}
