package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.ClockUtil.DEFAULT_TEST_CLOCK;
import static seedu.address.testutil.ClockUtil.DEFAULT_TEST_TIME;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.CompanyContainsKeywordsPredicate;
import seedu.address.model.person.EmailContainsKeywordsPredicate;
import seedu.address.model.person.JobContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.PhoneContainsKeywordsPredicate;
import seedu.address.model.tag.TagContainsKeywordsPredicate;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;

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
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
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
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
