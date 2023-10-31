package connexion.logic.parser;


import static connexion.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.Clock;
import java.time.LocalDateTime;

import connexion.commons.core.index.Index;
import connexion.logic.commands.ClearScheduleCommand;
import connexion.logic.parser.exceptions.ParseException;
import connexion.model.person.LastModifiedDateTime;


/**
 * Parses input arguments and creates a new EditCommand object
 */
public class ClearScheduleCommandParser implements ClockDependentParser<ClearScheduleCommand> {

    private Clock clock = Clock.systemDefaultZone();

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ClearScheduleCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new ClearScheduleCommand(index, new ClearScheduleCommand.ClearedScheduleDescriptor(
                    new LastModifiedDateTime(LocalDateTime.now(clock))));
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClearScheduleCommand.MESSAGE_USAGE), pe);
        }

    }
    /**
     * To specify usage of a specific clock.
     *
     * @param clock The clock to use
     * @return an edited parser using the specified clock
     */
    @Override
    public ClearScheduleCommandParser withClock(Clock clock) {
        ClearScheduleCommandParser toReturn = new ClearScheduleCommandParser();
        toReturn.clock = clock;
        return toReturn;
    }
}
