package connexion.logic.parser;


import static connexion.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static connexion.logic.parser.CliSyntax.*;
import static java.util.Objects.requireNonNull;

import java.time.Clock;
import java.util.Optional;

import connexion.commons.core.index.Index;
import connexion.logic.commands.AddCommand;
import connexion.logic.commands.EditCommand;
import connexion.logic.commands.ScheduleCommand;
import connexion.logic.parser.exceptions.ParseException;
import connexion.model.person.Schedule;
import connexion.model.person.ScheduleName;

/**
 * Parses input arguments and creates a new ScheduleCommand object
 */
public class ScheduleCommandParser implements ClockDependantParser<ScheduleCommand> {

    private Clock clock = Clock.systemDefaultZone();

    /**
     * Parses the given {@code String} of arguments in the context of the ScheduleCommand
     * and returns an ScheduleCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ScheduleCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(
                        args, PREFIX_SCHEDULE, PREFIX_SCHEDULE_NAME);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_SCHEDULE, PREFIX_SCHEDULE_NAME);
        if (argMultimap.getValue(PREFIX_SCHEDULE).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));
        }
        Schedule schedule = ParserUtil.parseSchedule(argMultimap.getValue(PREFIX_SCHEDULE).get());
        ScheduleName scheduleName = ParserUtil
                .parseScheduleName(argMultimap.getValue(PREFIX_SCHEDULE_NAME)
                .orElse("Meeting"));

        return new ScheduleCommand(index, schedule, scheduleName);
    }

    /**
     * To specify usage of a specific clock.
     *
     * @param clock The clock to use
     * @return an edited parser using the specified clock
     */
    @Override
    public ScheduleCommandParser withClock(Clock clock) {
        ScheduleCommandParser toReturn = new ScheduleCommandParser();
        toReturn.clock = clock;
        return toReturn;
    }
}
