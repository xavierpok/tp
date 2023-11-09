package connexion.logic.parser;


import static connexion.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static connexion.logic.Messages.MESSAGE_INVALID_FIELD_FORMAT;
import static connexion.logic.parser.CliSyntax.PREFIX_SCHEDULE;
import static connexion.logic.parser.CliSyntax.PREFIX_SCHEDULE_NAME;
import static java.util.Objects.requireNonNull;

import java.time.Clock;
import java.time.LocalDateTime;

import connexion.commons.core.index.Index;
import connexion.logic.commands.DeleteCommand;
import connexion.logic.commands.ScheduleCommand;
import connexion.logic.commands.ScheduleCommand.ScheduleDescriptor;
import connexion.logic.parser.exceptions.ParseException;
import connexion.model.person.LastModifiedDateTime;
import connexion.model.person.Schedule;
import connexion.model.person.ScheduleName;

/**
 * Parses input arguments and creates a new ScheduleCommand object
 */
public class ScheduleCommandParser implements ClockDependentParser<ScheduleCommand> {

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
            throw new ParseException(String.format(MESSAGE_INVALID_FIELD_FORMAT,
                    pe.getMessage(), ScheduleCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_SCHEDULE, PREFIX_SCHEDULE_NAME);
        if (argMultimap.getValue(PREFIX_SCHEDULE).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));
        }
        Schedule schedule = ParserUtil.parseSchedule(argMultimap.getValue(PREFIX_SCHEDULE).get());
        ScheduleName scheduleName = ParserUtil
                .parseScheduleName(argMultimap.getValue(PREFIX_SCHEDULE_NAME)
                .orElse("Meeting"));
        LastModifiedDateTime editScheduleTime = new LastModifiedDateTime(LocalDateTime.now(clock));
        ScheduleDescriptor scheduleDescriptor = new ScheduleDescriptor(schedule, scheduleName, editScheduleTime);


        return new ScheduleCommand(index, scheduleDescriptor);
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
