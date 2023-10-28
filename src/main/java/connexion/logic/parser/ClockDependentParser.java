package connexion.logic.parser;

import java.time.Clock;

import connexion.logic.commands.Command;

/**
 * Parsers that are somehow dependent on a specific clock for date & time.
 * @param <T> the type of command
 */
public interface ClockDependentParser<T extends Command> extends Parser<T> {

    /**
     * To specify usage of a specific clock.
     * @param clock The clock to use
     * @return an edited parser using the specified clock
     */
    public ClockDependentParser<T> withClock(Clock clock);
}
