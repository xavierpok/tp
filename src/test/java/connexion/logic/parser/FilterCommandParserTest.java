package connexion.logic.parser;

import static connexion.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static connexion.logic.parser.CommandParserTestUtil.assertParseFailure;
import static connexion.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import connexion.logic.commands.FilterCommand;
import connexion.model.person.CompanyContainsKeywordsPredicate;
import connexion.model.person.EmailContainsKeywordsPredicate;
import connexion.model.person.IsMarkedPredicate;
import connexion.model.person.JobContainsKeywordsPredicate;
import connexion.model.person.NameContainsKeywordsPredicate;
import connexion.model.person.NotMarkedPredicate;
import connexion.model.person.PhoneContainsKeywordsPredicate;
import connexion.model.tag.TagContainsKeywordsPredicate;

public class FilterCommandParserTest {

    private FilterCommandParser parser = new FilterCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArg_throwsParseException() {
        // No valid field prefix
        assertParseFailure(parser, "c", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));

        // No keywords for mode 1
        assertParseFailure(parser, "c/", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));

        // Invalid field prefix
        assertParseFailure(parser, "a/Amy", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
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

        // no keywords for mark
        expectedFilterCommand =
                new FilterCommand(new IsMarkedPredicate());
        assertParseSuccess(parser, "m/", expectedFilterCommand);

        // multiple keywords, whitespaces for mark, keywords will be discarded
        assertParseSuccess(parser, "m/ AB \n\t CD", expectedFilterCommand);

        // no keywords for unmark
        expectedFilterCommand =
                new FilterCommand(new NotMarkedPredicate());
        assertParseSuccess(parser, "u/", expectedFilterCommand);

        // multiple keywords, whitespaces for unmark, keywords will be discarded
        assertParseSuccess(parser, "u/ AB \n\t CD", expectedFilterCommand);
    }

}
