package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

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
