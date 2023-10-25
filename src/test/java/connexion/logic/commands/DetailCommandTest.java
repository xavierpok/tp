package connexion.logic.commands;


import static connexion.logic.commands.CommandTestUtil.assertCommandFailure;
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

public class DetailCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DetailCommand detailCommand = new DetailCommand(outOfBoundIndex);
        assertCommandFailure(detailCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DetailCommand detailFirstCommand = new DetailCommand(INDEX_FIRST_PERSON);
        DetailCommand detailSecondCommand = new DetailCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(detailFirstCommand.equals(detailFirstCommand));

        // same values -> returns true
        DetailCommand detailFirstCommandCopy = new DetailCommand(INDEX_FIRST_PERSON);
        assertTrue(detailFirstCommand.equals(detailFirstCommandCopy));

        // different types -> returns false
        assertFalse(detailFirstCommand.equals(1));

        // null -> returns false
        assertFalse(detailFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(detailFirstCommand.equals(detailSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        DetailCommand detailCommand = new DetailCommand(targetIndex);
        String expected = DetailCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, detailCommand.toString());
    }

}
