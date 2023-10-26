package connexion.logic.commands;

import static connexion.logic.commands.CommandTestUtil.SCHEDULE_AMY;
import static connexion.logic.commands.CommandTestUtil.SCHEDULE_AMYDIFFTIME;
import static connexion.logic.commands.CommandTestUtil.SCHEDULE_BOB;
import static connexion.logic.commands.CommandTestUtil.assertCommandFailure;
import static connexion.logic.commands.CommandTestUtil.assertCommandSuccess;
import static connexion.logic.commands.CommandTestUtil.showPersonAtIndex;
import static connexion.testutil.PersonBuilder.DEFAULT_LAST_MODIFIED;
import static connexion.testutil.PersonBuilder.DEFAULT_SCHEDULE;
import static connexion.testutil.PersonBuilder.DEFAULT_SCHEDULE_NAME;
import static connexion.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static connexion.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static connexion.testutil.TypicalPersons.getTypicalAddressBook;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import connexion.commons.core.index.Index;
import connexion.logic.Messages;
import connexion.logic.commands.ScheduleCommand.ScheduleDescriptor;
import connexion.model.AddressBook;
import connexion.model.Model;
import connexion.model.ModelManager;
import connexion.model.UserPrefs;
import connexion.model.person.Person;
import connexion.testutil.PersonBuilder;
import connexion.testutil.ScheduleDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ScheduleCommand.
 */
public class ScheduleCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Person scheduledPerson = new PersonBuilder(model.getFilteredPersonList().get(0))
                .withSchedule(DEFAULT_SCHEDULE)
                .withScheduleName(DEFAULT_SCHEDULE_NAME)
                .withLastModifiedDateTime(DEFAULT_LAST_MODIFIED)
                .build();
        ScheduleDescriptor descriptor = new ScheduleDescriptorBuilder()
                .withSchedule(DEFAULT_SCHEDULE)
                .withScheduleName(DEFAULT_SCHEDULE_NAME)
                .withLastModifiedDateTime(DEFAULT_LAST_MODIFIED)
                .build();
        ScheduleCommand scheduleCommand = new ScheduleCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(ScheduleCommand.SCHEDULE_ADD_SUCCESS, Messages.format(scheduledPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        //expectedModel.setClock(model.getClock());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), scheduledPerson);

        assertCommandSuccess(scheduleCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person scheduledPerson = new PersonBuilder(personInFilteredList)
                .withSchedule(DEFAULT_SCHEDULE)
                .withScheduleName(DEFAULT_SCHEDULE_NAME)
                .withLastModifiedDateTime(DEFAULT_LAST_MODIFIED).build();
        ScheduleCommand scheduleCommand = new ScheduleCommand(INDEX_FIRST_PERSON,
                new ScheduleDescriptorBuilder()
                        .withSchedule(DEFAULT_SCHEDULE)
                        .withScheduleName(DEFAULT_SCHEDULE_NAME)
                        .withLastModifiedDateTime(DEFAULT_LAST_MODIFIED)
                        .build());

        String expectedMessage = String.format(ScheduleCommand.SCHEDULE_ADD_SUCCESS, Messages.format(scheduledPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), scheduledPerson);

        assertCommandSuccess(scheduleCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        ScheduleDescriptor descriptor = new ScheduleDescriptorBuilder().build();
        ScheduleCommand scheduleCommand = new ScheduleCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(scheduleCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Schedule filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        ScheduleCommand scheduleCommand = new ScheduleCommand(outOfBoundIndex,
                new ScheduleDescriptorBuilder().build());

        assertCommandFailure(scheduleCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }


    @Test
    public void equals() {
        final ScheduleCommand standardCommand = new ScheduleCommand(INDEX_FIRST_PERSON, SCHEDULE_AMY);

        // same values -> returns true
        ScheduleDescriptor copyDescriptor = new ScheduleDescriptor(SCHEDULE_AMY);
        ScheduleCommand commandWithSameValues = new ScheduleCommand(INDEX_FIRST_PERSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new ScheduleCommand(INDEX_SECOND_PERSON, SCHEDULE_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new ScheduleCommand(INDEX_FIRST_PERSON, SCHEDULE_BOB)));

        //different time -> returns false
        assertFalse(standardCommand.equals(new ScheduleCommand(INDEX_FIRST_PERSON, SCHEDULE_AMYDIFFTIME)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        ScheduleDescriptor scheduleDescriptor = new ScheduleDescriptorBuilder().build();
        ScheduleCommand scheduleCommand = new ScheduleCommand(index, scheduleDescriptor);
        String expected = ScheduleCommand.class.getCanonicalName() + "{index=" + index + ", scheduleDescriptor="
                + scheduleDescriptor + "}";
        assertEquals(expected, scheduleCommand.toString());
    }

}

