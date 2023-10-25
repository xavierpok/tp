package connexion.logic.parser;

import static connexion.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static connexion.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static connexion.testutil.Assert.assertThrows;
import static connexion.testutil.ClockUtil.DEFAULT_TEST_CLOCK;
import static connexion.testutil.ClockUtil.DEFAULT_TEST_TIME;
import static connexion.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import connexion.logic.commands.AddCommand;
import connexion.logic.commands.ClearCommand;
import connexion.logic.commands.DeleteCommand;
import connexion.logic.commands.EditCommand;
import connexion.logic.commands.EditCommand.EditPersonDescriptor;
import connexion.logic.commands.ExitCommand;
import connexion.logic.commands.FilterCommand;
import connexion.logic.commands.HelpCommand;
import connexion.logic.commands.ListCommand;
import connexion.logic.commands.MarkCommand;
import connexion.logic.commands.UnMarkCommand;
import connexion.logic.parser.exceptions.ParseException;
import connexion.model.person.CompanyContainsKeywordsPredicate;
import connexion.model.person.EmailContainsKeywordsPredicate;
import connexion.model.person.JobContainsKeywordsPredicate;
import connexion.model.person.NameContainsKeywordsPredicate;
import connexion.model.person.Person;
import connexion.model.person.PhoneContainsKeywordsPredicate;
import connexion.model.tag.TagContainsKeywordsPredicate;
import connexion.testutil.EditPersonDescriptorBuilder;
import connexion.testutil.PersonBuilder;
import connexion.testutil.PersonUtil;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser().withClock(DEFAULT_TEST_CLOCK);

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().withLastModifiedDateTime(DEFAULT_TEST_TIME).build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().withLastModifiedDateTime(DEFAULT_TEST_TIME).build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_filter() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");

        List<String> nameArgs = Arrays.asList("n/", "foo", "bar", "baz");
        FilterCommand nameCommand = (FilterCommand) parser.parseCommand(
                FilterCommand.COMMAND_WORD + " "
                        + nameArgs.stream().collect(Collectors.joining(" ")));
        assertEquals(new FilterCommand(new NameContainsKeywordsPredicate(keywords)), nameCommand);

        List<String> phoneArgs = Arrays.asList("p/", "foo", "bar", "baz");
        FilterCommand phoneCommand = (FilterCommand) parser.parseCommand(
                FilterCommand.COMMAND_WORD + " "
                        + phoneArgs.stream().collect(Collectors.joining(" ")));
        assertEquals(new FilterCommand(new PhoneContainsKeywordsPredicate(keywords)), phoneCommand);

        List<String> emailArgs = Arrays.asList("e/", "foo", "bar", "baz");
        FilterCommand emailCommand = (FilterCommand) parser.parseCommand(
                FilterCommand.COMMAND_WORD + " "
                        + emailArgs.stream().collect(Collectors.joining(" ")));
        assertEquals(new FilterCommand(new EmailContainsKeywordsPredicate(keywords)), emailCommand);

        List<String> companyArgs = Arrays.asList("c/", "foo", "bar", "baz");
        FilterCommand companyCommand = (FilterCommand) parser.parseCommand(
                FilterCommand.COMMAND_WORD + " "
                        + companyArgs.stream().collect(Collectors.joining(" ")));
        assertEquals(new FilterCommand(new CompanyContainsKeywordsPredicate(keywords)), companyCommand);

        List<String> jobArgs = Arrays.asList("j/", "foo", "bar", "baz");
        FilterCommand jobCommand = (FilterCommand) parser.parseCommand(
                FilterCommand.COMMAND_WORD + " "
                        + jobArgs.stream().collect(Collectors.joining(" ")));
        assertEquals(new FilterCommand(new JobContainsKeywordsPredicate(keywords)), jobCommand);

        List<String> tagArgs = Arrays.asList("t/", "foo", "bar", "baz");
        FilterCommand tagCommand = (FilterCommand) parser.parseCommand(
                FilterCommand.COMMAND_WORD + " "
                        + tagArgs.stream().collect(Collectors.joining(" ")));
        assertEquals(new FilterCommand(new TagContainsKeywordsPredicate(keywords)), tagCommand);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_mark() throws Exception {
        MarkCommand command = (MarkCommand) parser.parseCommand(
                MarkCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new MarkCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_unMark() throws Exception {
        UnMarkCommand command = (UnMarkCommand) parser.parseCommand(
                UnMarkCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new UnMarkCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
