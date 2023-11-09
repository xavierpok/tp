package connexion.logic.parser;

import static connexion.logic.Messages.MESSAGE_INVALID_FIELD_FORMAT;
import static connexion.logic.parser.CommandParserTestUtil.assertParseFailure;
import static connexion.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static connexion.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import connexion.logic.commands.MarkCommand;


public class MarkCommandParserTest {
    private MarkCommandParser parser = new MarkCommandParser();
    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "      " ,
                String.format(MESSAGE_INVALID_FIELD_FORMAT,
                        ParserUtilTest.makeExceptionMessage("index",
                                "      ".trim() , ParserUtil.MESSAGE_INVALID_INDEX),
                        MarkCommand.MESSAGE_USAGE));

    }

    @Test
    public void parse_validArgs_returnsMarkCommand() {
        assertParseSuccess(parser, "1", new MarkCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_FIELD_FORMAT,
                        ParserUtilTest.makeExceptionMessage("index",
                                "a" , ParserUtil.MESSAGE_INVALID_INDEX),
                        MarkCommand.MESSAGE_USAGE));
    }

}
