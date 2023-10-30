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
    public void constructor_invalidNoteLength_throwsIllegalArgumentException() {
        String noteWithInvalidLength = "One morning, when Gregor Samsa woke from troubled dreams, "
                + "he found himself transformed in his bed into a horrible vermin. He lay on his armour-like back, "
                + "and if he lifted his head a little he could see his brown belly, slightly domed and divided by "
                + "arches into stiff sections. The bedding was hardly able to cover it and seemed ready to slide off "
                + "any moment. His many legs, pitifully thin compared with the size of the rest of him, waved about "
                + "helplessly as he looked. \"What's happened to me?\" he thought. It wasn't a dream. His room, "
                + "a proper human room although a little too small, lay peacefully between its four familiar walls. "
                + "A collection of textile samples lay spread out on the table - Samsa was a travelling salesman - and "
                + "above it there hung a picture that he had recently cut out of an illustrated magazine and housed in "
                + "a nice, gilded frame. It showed a lady fitted out with a fur hat and fur boa who sat upright, "
                + "raising a heavy fur muff that covered the whole of her lower arm towards ta";
        assertThrows(IllegalArgumentException.class, () -> new Note(noteWithInvalidLength));
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
    public void hasValidLength() {
        // Note with 1000 characters, to test boundary
        String noteWithValidLength = "One morning, when Gregor Samsa woke from troubled dreams, "
                + "he found himself transformed in his bed into a horrible vermin. He lay on his armour-like back, "
                + "and if he lifted his head a little he could see his brown belly, slightly domed and divided by "
                + "arches into stiff sections. The bedding was hardly able to cover it and seemed ready to slide off "
                + "any moment. His many legs, pitifully thin compared with the size of the rest of him, waved about "
                + "helplessly as he looked. \"What's happened to me?\" he thought. It wasn't a dream. His room, "
                + "a proper human room although a little too small, lay peacefully between its four familiar walls. "
                + "A collection of textile samples lay spread out on the table - Samsa was a travelling salesman - and "
                + "above it there hung a picture that he had recently cut out of an illustrated magazine and housed in "
                + "a nice, gilded frame. It showed a lady fitted out with a fur hat and fur boa who sat upright, "
                + "raising a heavy fur muff that covered the whole of her lower arm towards t";
        // null note
        assertThrows(NullPointerException.class, () -> Note.hasValidLength(null));

        // note with invalid length
        assertFalse(Note.hasValidLength(noteWithValidLength + "a")); // 1001 characters

        // note with valid length
        assertTrue(Note.hasValidLength(noteWithValidLength)); // 1000 characters
        assertTrue(Note.hasValidLength("")); // empty note
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

