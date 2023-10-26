package connexion.logic.parser;




import static connexion.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static connexion.logic.parser.CommandParserTestUtil.assertParseFailure;
import static connexion.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static connexion.testutil.ClockUtil.DEFAULT_TEST_CLOCK;
import static connexion.testutil.ClockUtil.DEFAULT_TEST_TIME;
import static connexion.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import connexion.logic.commands.ClearScheduleCommand;
import connexion.model.person.LastModifiedDateTime;


class ClearScheduleCommandParserTest {

    private ClearScheduleCommandParser parser = new ClearScheduleCommandParser().withClock(DEFAULT_TEST_CLOCK);

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "1", new ClearScheduleCommand(INDEX_FIRST_PERSON,
                new ClearScheduleCommand.ClearedScheduleDescriptor(new LastModifiedDateTime(DEFAULT_TEST_TIME))));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ClearScheduleCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1 /t this argument should not be here",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClearScheduleCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "-1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ClearScheduleCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1 /e 2020 /i 2020", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ClearScheduleCommand.MESSAGE_USAGE));

    }


}
