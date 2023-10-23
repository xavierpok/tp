package connexion.logic.commands;

import static connexion.logic.commands.CommandTestUtil.assertCommandFailure;
import static connexion.logic.commands.CommandTestUtil.assertCommandSuccess;
import static connexion.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static connexion.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static connexion.testutil.TypicalPersons.getTypicalAddressBook;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import connexion.commons.core.index.Index;
import connexion.logic.Messages;
import connexion.model.Model;
import connexion.model.ModelManager;
import connexion.model.UserPrefs;
import connexion.model.person.Person;

public class UnMarkCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToUnMark = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        UnMarkCommand unMarkCommand = new UnMarkCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(UnMarkCommand.MESSAGE_UNMARK_SUCCESS,
                Messages.format(personToUnMark));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.markPerson(personToUnMark);

        assertCommandSuccess(unMarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        UnMarkCommand unMarkCommand = new UnMarkCommand(outOfBoundIndex);
        assertCommandFailure(unMarkCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        UnMarkCommand unMarkFirstCommand = new UnMarkCommand(INDEX_FIRST_PERSON);
        UnMarkCommand unMarkSecondCommand = new UnMarkCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(unMarkFirstCommand.equals(unMarkFirstCommand));

        // same values -> returns true
        UnMarkCommand unMarkFirstCommandCopy = new UnMarkCommand(INDEX_FIRST_PERSON);
        assertTrue(unMarkFirstCommand.equals(unMarkFirstCommandCopy));

        // different types -> returns false
        assertFalse(unMarkFirstCommand.equals(1));

        // null -> returns false
        assertFalse(unMarkFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(unMarkFirstCommand.equals(unMarkSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        UnMarkCommand unMarkCommand = new UnMarkCommand(targetIndex);
        String expected = UnMarkCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, unMarkCommand.toString());
    }


}
