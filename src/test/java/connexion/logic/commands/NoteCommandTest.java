package connexion.logic.commands;

import static connexion.logic.commands.CommandTestUtil.NOTE_AMY;
import static connexion.logic.commands.CommandTestUtil.NOTE_AMYDIFFTIME;
import static connexion.logic.commands.CommandTestUtil.NOTE_BOB;
import static connexion.logic.commands.CommandTestUtil.assertCommandFailure;
import static connexion.logic.commands.CommandTestUtil.assertCommandSuccess;
import static connexion.logic.commands.CommandTestUtil.showPersonAtIndex;
import static connexion.testutil.PersonBuilder.DEFAULT_LAST_MODIFIED;
import static connexion.testutil.PersonBuilder.DEFAULT_NOTE;
import static connexion.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static connexion.testutil.TypicalIndexes.INDEX_MARKED_PERSON;
import static connexion.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static connexion.testutil.TypicalPersons.getTypicalAddressBook;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import connexion.commons.core.index.Index;
import connexion.logic.Messages;
import connexion.logic.commands.NoteCommand.NoteDescriptor;
import connexion.model.AddressBook;
import connexion.model.Model;
import connexion.model.ModelManager;
import connexion.model.UserPrefs;
import connexion.model.person.Person;
import connexion.testutil.NoteDescriptorBuilder;
import connexion.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for NoteCommand.
 */
public class NoteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredListMarkedPerson_success() {
        Person personToNote = new PersonBuilder(model.getFilteredPersonList().get(INDEX_MARKED_PERSON.getZeroBased()))
                .withNote(DEFAULT_NOTE)
                .withLastModifiedDateTime(DEFAULT_LAST_MODIFIED)
                .withMark(true)
                .build();
        NoteDescriptor descriptor = new NoteDescriptorBuilder()
                .withNote(DEFAULT_NOTE)
                .withLastModifiedDateTime(DEFAULT_LAST_MODIFIED)
                .build();
        NoteCommand noteCommand = new NoteCommand(INDEX_MARKED_PERSON, descriptor);

        String expectedMessage = String.format(NoteCommand.MESSAGE_SUCCESS,
                INDEX_MARKED_PERSON.getOneBased(), DEFAULT_NOTE);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(INDEX_MARKED_PERSON.getZeroBased()), personToNote);

        assertCommandSuccess(noteCommand, model, expectedMessage, expectedModel);
    }
    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Person personToNote = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withNote(DEFAULT_NOTE)
                .withLastModifiedDateTime(DEFAULT_LAST_MODIFIED)
                .build();
        NoteDescriptor descriptor = new NoteDescriptorBuilder()
                .withNote(DEFAULT_NOTE)
                .withLastModifiedDateTime(DEFAULT_LAST_MODIFIED)
                .build();
        NoteCommand noteCommand = new NoteCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(NoteCommand.MESSAGE_SUCCESS,
                INDEX_FIRST_PERSON.getOneBased(), DEFAULT_NOTE);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()), personToNote);

        assertCommandSuccess(noteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person personToNote = new PersonBuilder(personInFilteredList)
                .withNote(DEFAULT_NOTE)
                .withLastModifiedDateTime(DEFAULT_LAST_MODIFIED).build();
        NoteCommand noteCommand = new NoteCommand(INDEX_FIRST_PERSON,
                new NoteDescriptorBuilder()
                        .withNote(DEFAULT_NOTE)
                        .withLastModifiedDateTime(DEFAULT_LAST_MODIFIED)
                        .build());

        String expectedMessage = String.format(NoteCommand.MESSAGE_SUCCESS,
                INDEX_FIRST_PERSON.getOneBased(), DEFAULT_NOTE);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), personToNote);

        assertCommandSuccess(noteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        NoteDescriptor descriptor = new NoteDescriptorBuilder().build();
        NoteCommand noteCommand = new NoteCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(noteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Note filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        NoteCommand noteCommand = new NoteCommand(outOfBoundIndex,
                new NoteDescriptorBuilder().build());

        assertCommandFailure(noteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }


    @Test
    public void equals() {
        final NoteCommand standardCommand = new NoteCommand(INDEX_FIRST_PERSON, NOTE_AMY);

        // same values -> returns true
        NoteDescriptor copyDescriptor = new NoteDescriptor(NOTE_AMY);
        NoteCommand commandWithSameValues = new NoteCommand(INDEX_FIRST_PERSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new NoteCommand(INDEX_SECOND_PERSON, NOTE_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new NoteCommand(INDEX_FIRST_PERSON, NOTE_BOB)));

        //different time -> returns false
        assertFalse(standardCommand.equals(new NoteCommand(INDEX_FIRST_PERSON, NOTE_AMYDIFFTIME)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        NoteDescriptor noteDescriptor = new NoteDescriptorBuilder().build();
        NoteCommand noteCommand = new NoteCommand(index, noteDescriptor);
        String expected = NoteCommand.class.getCanonicalName() + "{index=" + index + ", noteDescriptor="
                + noteDescriptor + "}";
        assertEquals(expected, noteCommand.toString());
    }

}
