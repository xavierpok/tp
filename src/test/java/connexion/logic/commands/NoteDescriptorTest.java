package connexion.logic.commands;

import static connexion.logic.commands.CommandTestUtil.NOTE_AMY;
import static connexion.logic.commands.CommandTestUtil.NOTE_BOB;
import static connexion.logic.commands.CommandTestUtil.VALID_LAST_MODIFIED_BOB;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import connexion.logic.commands.NoteCommand.NoteDescriptor;
import connexion.testutil.NoteDescriptorBuilder;

public class NoteDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        NoteDescriptor descriptorWithSameValues = new NoteDescriptor(NOTE_AMY);
        assertTrue(NOTE_AMY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(NOTE_AMY.equals(NOTE_AMY));

        // null -> returns false
        assertFalse(NOTE_AMY.equals(null));

        // different types -> returns false
        assertFalse(NOTE_AMY.equals(5));

        // different values -> returns false
        assertFalse(NOTE_AMY.equals(NOTE_BOB));


        // different time -> returns false
        NoteDescriptor editedAmy = new NoteDescriptorBuilder(NOTE_AMY)
                .withLastModifiedDateTime(VALID_LAST_MODIFIED_BOB).build();
        assertFalse(NOTE_AMY.equals(editedAmy));
    }

    @Test
    public void toStringMethod() {
        NoteDescriptor noteDescriptor = new NoteDescriptor(NOTE_BOB);
        String expected = NoteDescriptor.class.getCanonicalName() + "{note="
                + noteDescriptor.getNote() + ", last_modified="
                + noteDescriptor.getLastModifiedDateTime() + "}";
        assertEquals(expected, noteDescriptor.toString());
    }
}
