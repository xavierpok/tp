package connexion.model.person;

import static connexion.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class NoteTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Note(null));
    }

    @Test
    public void constructor_invalidNote_throwsIllegalArgumentException() {
        String invalidNote = "\u2063";
        assertThrows(IllegalArgumentException.class, () -> new Note(invalidNote));
    }

    @Test
    public void isValidNote() {
        // null note
        assertThrows(NullPointerException.class, () -> Note.isValidNote(null));

        // invalid note
        assertFalse(Note.isValidNote("\u2063")); // invisible characters

        // valid note
        assertTrue(Note.isValidNote("ABC abc 123")); // alphanumeric characters and whitespace
        assertTrue(Note.isValidNote("!@#$%^&*().,';/[]|\\`~")); // punctuations and brackets
        assertTrue(Note.isValidNote("")); // empty note
    }

    @Test
    public void equals() {
        Note note = new Note("Hi");

        // same values -> returns true
        assertTrue(note.equals(new Note("Hi")));

        // same object -> returns true
        assertTrue(note.equals(note));

        // null -> returns false
        assertFalse(note.equals(null));

        // different types -> returns false
        assertFalse(note.equals(5.0f));

        // different values -> returns false
        assertFalse(note.equals(new Note("Bye")));
    }

    @Test
    void getDetailString_equals_input() {
        Note note = new Note("Hi");
        assertEquals(note.getDetailString(), "Hi");
        assertNotEquals(note.getDetailString(), "Bye"); // to show it's actually matching the string
    }

    @Test
    void getValue_equals_input() {
        Note note = new Note("Hi");
        assertEquals(note.getValue(), "Hi");
        assertNotEquals(note.getValue(), "Bye"); // to show it's actually matching the string
    }
}

