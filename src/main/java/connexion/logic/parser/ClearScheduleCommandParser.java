package connexion.logic.parser;

import connexion.commons.core.index.Index;
import connexion.logic.commands.ClearScheduleCommand;
import connexion.logic.commands.DeleteCommand;
import connexion.logic.commands.EditCommand;
import connexion.logic.commands.EditCommand.EditPersonDescriptor;
import connexion.logic.parser.exceptions.ParseException;
import connexion.model.person.LastModifiedDateTime;
import connexion.model.tag.Tag;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static connexion.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static connexion.logic.parser.CliSyntax.PREFIX_COMPANY;
import static connexion.logic.parser.CliSyntax.PREFIX_EMAIL;
import static connexion.logic.parser.CliSyntax.PREFIX_JOB;
import static connexion.logic.parser.CliSyntax.PREFIX_NAME;
import static connexion.logic.parser.CliSyntax.PREFIX_PHONE;
import static connexion.logic.parser.CliSyntax.PREFIX_TAG;
import static java.util.Objects.requireNonNull;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class ClearScheduleCommandParser implements ClockDependantParser<ClearScheduleCommand> {

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
