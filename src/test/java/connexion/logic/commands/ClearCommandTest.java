package connexion.logic.commands;

import static connexion.logic.commands.CommandTestUtil.assertCommandSuccess;
import static connexion.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import connexion.model.AddressBook;
import connexion.model.Model;
import connexion.model.ModelManager;
import connexion.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.setAddressBook(new AddressBook());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
