package seedu.address.logic.parser;

import seedu.address.logic.commands.Command;

import java.time.Clock;

/**
 * Parsers that are somehow dependent on a specific clock for date & time.
 * @param <T> the type of command
 */
public interface ClockDependantParser<T extends Command> extends Parser<T> {

    /**
     * To specify usage of a specific clock.
     * @param clock The clock to use
     * @return an edited parser using the specified clock
     */
    public ClockDependantParser<T> withClock(Clock clock);
}
