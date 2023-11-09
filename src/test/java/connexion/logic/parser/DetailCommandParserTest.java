package connexion.logic.parser;

import static connexion.logic.Messages.MESSAGE_INVALID_FIELD_FORMAT;
import static connexion.logic.parser.CommandParserTestUtil.assertParseFailure;
import static connexion.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static connexion.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import connexion.logic.commands.DetailCommand;

public class DetailCommandParserTest {
    private DetailCommandParser parser = new DetailCommandParser();

    @Test
    public void parse_validArgs_returnsDetailCommand() {
        assertParseSuccess(parser, "1", new DetailCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_FIELD_FORMAT,
                        ParserUtilTest.makeExceptionMessage("index", "a",
                                ParserUtil.MESSAGE_INVALID_INDEX),
                        DetailCommand.MESSAGE_USAGE));
    }
}
