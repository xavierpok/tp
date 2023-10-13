package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.model.person.CompanyContainsKeywordsPredicate;
import seedu.address.model.person.EmailContainsKeywordsPredicate;
import seedu.address.model.person.JobContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.PhoneContainsKeywordsPredicate;
import seedu.address.model.tag.TagContainsKeywordsPredicate;

public class FilterCommandParserTest {

    private FilterCommandParser parser = new FilterCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFilterCommand() {
        // no leading and trailing whitespaces for name
        FilterCommand expectedFilterCommand =
                new FilterCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "n/Alice Bob", expectedFilterCommand);

        // multiple whitespaces between keywords for name
        assertParseSuccess(parser, "n/ \n Alice \n \t Bob  \t", expectedFilterCommand);

        // no leading and trailing whitespaces for phone
        expectedFilterCommand =
                new FilterCommand(new PhoneContainsKeywordsPredicate(Arrays.asList("1234")));
        assertParseSuccess(parser, "p/1234", expectedFilterCommand);

        // multiple whitespaces between keywords for phone
        assertParseSuccess(parser, "p/ \t\n 1234  \t", expectedFilterCommand);

        // no leading and trailing whitespaces for email
        expectedFilterCommand =
                new FilterCommand(new EmailContainsKeywordsPredicate(Arrays.asList("a@b.com")));
        assertParseSuccess(parser, "e/a@b.com", expectedFilterCommand);

        // multiple whitespaces between keywords for email
        assertParseSuccess(parser, "e/ \t\n a@b.com  \t", expectedFilterCommand);

        // no leading and trailing whitespaces for company
        expectedFilterCommand =
                new FilterCommand(new CompanyContainsKeywordsPredicate(Arrays.asList("Meta", "Google")));
        assertParseSuccess(parser, "c/Meta Google", expectedFilterCommand);

        // multiple whitespaces between keywords for company
        assertParseSuccess(parser, "c/ \n Meta \n \t Google  \t", expectedFilterCommand);

        // no leading and trailing whitespaces for job
        expectedFilterCommand =
                new FilterCommand(new JobContainsKeywordsPredicate(Arrays.asList("Software", "Engineer")));
        assertParseSuccess(parser, "j/Software Engineer", expectedFilterCommand);

        // multiple whitespaces between keywords for job
        assertParseSuccess(parser, "j/ \n Software \n \t Engineer \t", expectedFilterCommand);

        // no leading and trailing whitespaces for tags
        expectedFilterCommand =
                new FilterCommand(new TagContainsKeywordsPredicate(Arrays.asList("friend")));
        assertParseSuccess(parser, "t/friend", expectedFilterCommand);

        // multiple whitespaces between keywords for tags
        assertParseSuccess(parser, "t/ \t\n friend  \t", expectedFilterCommand);
    }

}
