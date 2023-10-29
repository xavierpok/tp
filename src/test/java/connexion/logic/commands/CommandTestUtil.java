package connexion.logic.commands;

import static connexion.logic.parser.CliSyntax.PREFIX_COMPANY;
import static connexion.logic.parser.CliSyntax.PREFIX_EMAIL;
import static connexion.logic.parser.CliSyntax.PREFIX_JOB;
import static connexion.logic.parser.CliSyntax.PREFIX_NAME;
import static connexion.logic.parser.CliSyntax.PREFIX_NOTE;
import static connexion.logic.parser.CliSyntax.PREFIX_PHONE;
import static connexion.logic.parser.CliSyntax.PREFIX_SCHEDULE;
import static connexion.logic.parser.CliSyntax.PREFIX_SCHEDULE_NAME;
import static connexion.logic.parser.CliSyntax.PREFIX_TAG;
import static connexion.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import connexion.commons.core.index.Index;
import connexion.logic.commands.exceptions.CommandException;
import connexion.model.AddressBook;
import connexion.model.Model;
import connexion.model.person.NameContainsKeywordsPredicate;
import connexion.model.person.Person;
import connexion.testutil.EditPersonDescriptorBuilder;
import connexion.testutil.NoteDescriptorBuilder;
import connexion.testutil.ScheduleDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_COMPANY_AMY = "Jane's Street";
    public static final String VALID_COMPANY_BOB = "Goldman Sach";
    public static final String VALID_JOB_AMY = "Software Engineer";
    public static final String VALID_JOB_BOB = "Mobile App developer";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";

    public static final LocalDateTime VALID_LAST_MODIFIED_AMY = LocalDateTime.MIN;
    public static final LocalDateTime VALID_LAST_MODIFIED_BOB = LocalDateTime.MAX;
    public static final String VALID_SCHEDULE_AMY = "2023-12-13-09-00";
    public static final String VALID_SCHEDULE_BOB = "2023-12-25-13-00";
    public static final String VALID_SCHEDULE_NAME_AMY = "Morning Meeting";
    public static final String VALID_SCHEDULE_NAME_BOB = "Interview";
    // When schedule is given but not the schedule name
    public static final String AUTO_GIVEN_SCHEDULE_NAME = "Meeting";

    public static final String VALID_NOTE_AMY = "Promoted to Manager!";

    // Empty String is valid too
    public static final String VALID_NOTE_BOB = "";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String COMPANY_DESC_AMY = " " + PREFIX_COMPANY + VALID_COMPANY_AMY;
    public static final String COMPANY_DESC_BOB = " " + PREFIX_COMPANY + VALID_COMPANY_BOB;
    public static final String JOB_DESC_AMY = " " + PREFIX_JOB + VALID_JOB_AMY;
    public static final String JOB_DESC_BOB = " " + PREFIX_JOB + VALID_JOB_BOB;

    public static final String SCHEDULE_DESC_AMY = " " + PREFIX_SCHEDULE + VALID_SCHEDULE_AMY;
    public static final String SCHEDULE_DESC_BOB = " " + PREFIX_SCHEDULE + VALID_SCHEDULE_BOB;
    public static final String SCHEDULE_NAME_DESC_AMY = " " + PREFIX_SCHEDULE_NAME + VALID_SCHEDULE_NAME_AMY;
    public static final String SCHEDULE_NAME_DESC_BOB = " " + PREFIX_SCHEDULE_NAME + VALID_SCHEDULE_NAME_BOB;
    public static final String NOTE_DESC_AMY = " " + PREFIX_NOTE + VALID_NOTE_AMY;
    public static final String NOTE_DESC_BOB = " " + PREFIX_NOTE + VALID_NOTE_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_COMPANY_DESC = " " + PREFIX_COMPANY; // empty string not allowed for companies
    public static final String INVALID_JOB_DESC = " " + PREFIX_JOB; // empty string not allowed for jobs
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags
    public static final String INVALID_SCHEDULE_DESC = " " + PREFIX_SCHEDULE
            + "2023/07/10/06/59"; // '/' not allowed for schedule
    public static final String INVALID_SCHEDULE_DATE = " " + PREFIX_SCHEDULE
            + "2023-40-10-06-59"; // correct format but wrong date (month field is wrong)
    public static final String INVALID_SCHEDULE_NAME_DESC = " "
            + PREFIX_SCHEDULE_NAME + "Meeting*"; // '*' not allowed in schedule names

    public static final String INVALID_NOTE = " " + PREFIX_NOTE
            + "\u2063"; // invisible characters not allowed for note

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;
    public static final EditCommand.EditPersonDescriptor DESC_AMYDIFFTIME;

    public static final ScheduleCommand.ScheduleDescriptor SCHEDULE_AMY;
    public static final ScheduleCommand.ScheduleDescriptor SCHEDULE_BOB;
    public static final ScheduleCommand.ScheduleDescriptor SCHEDULE_AMYDIFFTIME;

    public static final NoteCommand.NoteDescriptor NOTE_AMY;
    public static final NoteCommand.NoteDescriptor NOTE_BOB;
    public static final NoteCommand.NoteDescriptor NOTE_AMYDIFFTIME;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withCompany(VALID_COMPANY_AMY)
                .withJob(VALID_JOB_AMY)
                .withTags(VALID_TAG_FRIEND).withLastModifiedDateTime(VALID_LAST_MODIFIED_AMY).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withCompany(VALID_COMPANY_BOB)
                .withJob(VALID_JOB_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
                .withLastModifiedDateTime(VALID_LAST_MODIFIED_BOB).build();
        DESC_AMYDIFFTIME = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withCompany(VALID_COMPANY_AMY)
                .withJob(VALID_JOB_AMY)
                .withTags(VALID_TAG_FRIEND).withLastModifiedDateTime(VALID_LAST_MODIFIED_BOB).build();
        SCHEDULE_AMY = new ScheduleDescriptorBuilder().withSchedule(VALID_SCHEDULE_AMY)
                .withScheduleName(VALID_SCHEDULE_NAME_AMY)
                .withLastModifiedDateTime(VALID_LAST_MODIFIED_AMY)
                .build();
        SCHEDULE_BOB = new ScheduleDescriptorBuilder().withSchedule(VALID_SCHEDULE_BOB)
                .withScheduleName(VALID_SCHEDULE_NAME_BOB)
                .withLastModifiedDateTime(VALID_LAST_MODIFIED_BOB)
                .build();
        SCHEDULE_AMYDIFFTIME = new ScheduleDescriptorBuilder().withSchedule(VALID_SCHEDULE_AMY)
                .withScheduleName(VALID_SCHEDULE_NAME_AMY)
                .withLastModifiedDateTime(VALID_LAST_MODIFIED_BOB)
                .build();
        NOTE_AMY = new NoteDescriptorBuilder().withNote(VALID_NOTE_AMY)
                .withLastModifiedDateTime(VALID_LAST_MODIFIED_AMY)
                .build();
        NOTE_BOB = new NoteDescriptorBuilder().withNote(VALID_NOTE_BOB)
                .withLastModifiedDateTime(VALID_LAST_MODIFIED_BOB)
                .build();
        NOTE_AMYDIFFTIME = new NoteDescriptorBuilder().withNote(VALID_NOTE_AMY)
                .withLastModifiedDateTime(VALID_LAST_MODIFIED_BOB)
                .build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
                                            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered person list and selected person in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Person> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPersonList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPersonList().size());

        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        final String[] splitName = person.getName().getValue().split("\\s+");
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPersonList().size());
    }

}
