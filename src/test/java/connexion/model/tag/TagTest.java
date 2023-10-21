package connexion.model.tag;

import static connexion.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import connexion.model.person.Company;
import connexion.model.person.Name;
import org.junit.jupiter.api.Test;

public class TagTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Tag(null));
    }

    @Test
    public void constructor_invalidTagName_throwsIllegalArgumentException() {
        String invalidTagName = "";
        assertThrows(IllegalArgumentException.class, () -> new Tag(invalidTagName));
    }

    @Test
    public void isValidTagName() {
        // null tag name
        assertThrows(NullPointerException.class, () -> Tag.isValidTagName(null));
    }

    @Test
    void getDetailString_equals_input() {
        Tag tag = new Tag("valid");
        assertEquals(tag.getDetailString(),"valid");
        assertNotEquals(tag.getDetailString(), "Nonsense"); //to show it's actually matching the string
    }

    @Test
    void getValue_equals_input() {
        Tag tag = new Tag("valid");
        assertEquals(tag.getValue(),"valid");
        assertNotEquals(tag.getValue(), "Nonsense"); //to show it's actually matching the string
    }

    @Test
    void getListString_equals_input() {
        Tag tag = new Tag("valid");
        assertEquals(tag.getListString(),"valid");
        assertNotEquals(tag.getListString(), "Nonsense"); //to show it's actually matching the string
    }

    @Test
    void equals() {
        Tag tag = new Tag("valid");

        // same values -> returns true
        assertTrue(tag.equals(new Tag("valid")));

        // same object -> returns true
        assertTrue(tag.equals(tag));

        // null -> returns false
        assertFalse(tag.equals(null));

        // different types -> returns false
        assertFalse(tag.equals(5.0f));

        // different values -> returns false
        assertFalse(tag.equals(new Tag("other")));
    }
}
