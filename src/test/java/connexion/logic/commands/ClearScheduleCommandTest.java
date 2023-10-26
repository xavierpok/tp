package connexion.logic.commands;

import static connexion.logic.commands.CommandTestUtil.assertCommandFailure;
import static connexion.logic.commands.CommandTestUtil.assertCommandSuccess;
import static connexion.logic.commands.CommandTestUtil.showPersonAtIndex;
import static connexion.testutil.ClockUtil.DEFAULT_TEST_TIME;
import static connexion.testutil.ClockUtil.OTHER_TEST_TIME;
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
import connexion.model.person.LastModifiedDateTime;
import connexion.model.person.Person;
import connexion.testutil.PersonBuilder;



class ClearScheduleCommandTest {

    private static final ClearScheduleCommand.ClearedScheduleDescriptor DEFAULT_DESCRIPTOR =
            new ClearScheduleCommand.ClearedScheduleDescriptor(new LastModifiedDateTime(DEFAULT_TEST_TIME));
    private static final ClearScheduleCommand.ClearedScheduleDescriptor OTHER_DESCRIPTOR =
            new ClearScheduleCommand.ClearedScheduleDescriptor(new LastModifiedDateTime(OTHER_TEST_TIME));

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    @Test
    public void execute_validIndexUnfilteredList_success() {

        Person personToClear = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person clearedPerson = new PersonBuilder(personToClear)
                .withSchedule("") //empty schedule
                .withScheduleName("")
                .withLastModifiedDateTime(DEFAULT_TEST_TIME)
                .build();
        ClearScheduleCommand clearScheduleCommand = new ClearScheduleCommand(INDEX_FIRST_PERSON,
                DEFAULT_DESCRIPTOR);

        String expectedMessage = String.format(ClearScheduleCommand.MESSAGE_CLEAR_SCHEDULE_SUCCESS,
                Messages.format(clearedPerson));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personToClear, clearedPerson);


        assertCommandSuccess(clearScheduleCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexUnfilteredListOnlyName_success() {


        Person personToClear = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person onlyNameSchedulePerson = new PersonBuilder(personToClear)
                .withSchedule("") //empty schedule
                .withScheduleName("test")
                .withLastModifiedDateTime(DEFAULT_TEST_TIME)
                .build();

        Person clearedPerson = new PersonBuilder(personToClear)
                .withSchedule("") //empty schedule
                .withScheduleName("")
                .withLastModifiedDateTime(DEFAULT_TEST_TIME)
                .build();
        ClearScheduleCommand clearScheduleCommand = new ClearScheduleCommand(INDEX_FIRST_PERSON,
                DEFAULT_DESCRIPTOR);

        String expectedMessage = String.format(ClearScheduleCommand.MESSAGE_CLEAR_NAME_WITHOUT_SCHEDULE_SUCCESS,
                Messages.format(clearedPerson));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personToClear, clearedPerson);

        Model onlyNameModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        onlyNameModel.setPerson(personToClear, onlyNameSchedulePerson);


        assertCommandSuccess(clearScheduleCommand, onlyNameModel, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexUnfilteredListOnlySchedule_success() {


        Person personToClear = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person onlyNameSchedulePerson = new PersonBuilder(personToClear)
                .withScheduleName("") //only empty name
                .withLastModifiedDateTime(DEFAULT_TEST_TIME)
                .build();

        Person clearedPerson = new PersonBuilder(personToClear)
                .withSchedule("") //empty schedule
                .withScheduleName("")
                .withLastModifiedDateTime(DEFAULT_TEST_TIME)
                .build();
        ClearScheduleCommand clearScheduleCommand = new ClearScheduleCommand(INDEX_FIRST_PERSON,
                DEFAULT_DESCRIPTOR);

        String expectedMessage = String.format(ClearScheduleCommand.MESSAGE_CLEAR_SCHEDULE_SUCCESS,
                Messages.format(clearedPerson));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personToClear, clearedPerson);

        Model onlyNameModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        onlyNameModel.setPerson(personToClear, onlyNameSchedulePerson);


        assertCommandSuccess(clearScheduleCommand, onlyNameModel, expectedMessage, expectedModel);
    }

    @Test
    public void execute_emptySchedule_failure() {


        Person personToClear = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person noSchedulePerson = new PersonBuilder(personToClear)
                .withSchedule("") //empty schedule
                .withScheduleName("")
                .withLastModifiedDateTime(DEFAULT_TEST_TIME)
                .build();

        ClearScheduleCommand clearScheduleCommand = new ClearScheduleCommand(INDEX_FIRST_PERSON,
                DEFAULT_DESCRIPTOR);

        String expectedMessage = ClearScheduleCommand.MESSAGE_NO_SCHEDULE;


        Model noScheduleModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        noScheduleModel.setPerson(personToClear, noSchedulePerson);


        assertCommandFailure(clearScheduleCommand, noScheduleModel, expectedMessage);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        ClearScheduleCommand clearScheduleCommand = new ClearScheduleCommand(outOfBoundIndex, DEFAULT_DESCRIPTOR);

        assertCommandFailure(clearScheduleCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);


        Person personToClear = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person clearedPerson = new PersonBuilder(personToClear)
                .withSchedule("") //empty schedule
                .withScheduleName("")
                .withLastModifiedDateTime(DEFAULT_TEST_TIME)
                .build();
        ClearScheduleCommand clearScheduleCommand = new ClearScheduleCommand(INDEX_FIRST_PERSON,
                DEFAULT_DESCRIPTOR);

        String expectedMessage = String.format(ClearScheduleCommand.MESSAGE_CLEAR_SCHEDULE_SUCCESS,
                Messages.format(clearedPerson));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personToClear, clearedPerson);

        assertCommandSuccess(clearScheduleCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        ClearScheduleCommand clearScheduleCommand = new ClearScheduleCommand(outOfBoundIndex, DEFAULT_DESCRIPTOR);

        assertCommandFailure(clearScheduleCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        ClearScheduleCommand clearFirstCommand = new ClearScheduleCommand(INDEX_FIRST_PERSON, DEFAULT_DESCRIPTOR);
        ClearScheduleCommand clearSecondCommand = new ClearScheduleCommand(INDEX_SECOND_PERSON, DEFAULT_DESCRIPTOR);
        ClearScheduleCommand clearThirdCommand = new ClearScheduleCommand(INDEX_FIRST_PERSON, OTHER_DESCRIPTOR);
        // same object -> returns true
        assertTrue(clearFirstCommand.equals(clearFirstCommand));

        // same values -> returns true
        ClearScheduleCommand clearFirstCommandCopy = new ClearScheduleCommand(INDEX_FIRST_PERSON, DEFAULT_DESCRIPTOR);
        assertTrue(clearFirstCommand.equals(clearFirstCommandCopy));

        // different types -> returns false
        assertFalse(clearFirstCommand.equals(1));

        // null -> returns false
        assertFalse(clearFirstCommand.equals(null));

        // different index -> returns false
        assertFalse(clearFirstCommand.equals(clearSecondCommand));

        //different last modified -> returns false
        assertFalse(clearFirstCommand.equals(clearThirdCommand));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        ClearScheduleCommand clearScheduleCommand = new ClearScheduleCommand(index, DEFAULT_DESCRIPTOR);
        String expected = ClearScheduleCommand.class.getCanonicalName() + "{index=" + index + ", "
                + "clearedScheduleDescriptor=" + DEFAULT_DESCRIPTOR + "}";
        assertEquals(expected, clearScheduleCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assertTrue(model.getFilteredPersonList().isEmpty());
    }
}
