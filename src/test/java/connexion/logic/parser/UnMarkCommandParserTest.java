package connexion.logic.parser;

import static connexion.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static connexion.logic.Messages.MESSAGE_INVALID_FIELD_FORMAT;
import static connexion.logic.parser.CommandParserTestUtil.assertParseFailure;
import static connexion.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static connexion.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import connexion.logic.commands.MarkCommand;
import org.junit.jupiter.api.Test;

import connexion.logic.commands.UnMarkCommand;

public class UnMarkCommandParserTest {
    private UnMarkCommandParser parser = new UnMarkCommandParser();
    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "      " ,
                String.format(MESSAGE_INVALID_FIELD_FORMAT,
                        ParserUtilTest.makeExceptionMessage("index",
                                "      ".trim() , ParserUtil.MESSAGE_INVALID_INDEX),
                        UnMarkCommand.MESSAGE_USAGE));

    }

    @Test
    public void parse_validArgs_returnsMarkCommand() {
        assertParseSuccess(parser, "1", new UnMarkCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_FIELD_FORMAT,
                        ParserUtilTest.makeExceptionMessage("index",
                                "a" , ParserUtil.MESSAGE_INVALID_INDEX),
                        UnMarkCommand.MESSAGE_USAGE));
    }
}
