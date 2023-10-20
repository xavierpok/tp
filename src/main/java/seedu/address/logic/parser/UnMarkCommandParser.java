package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.UnMarkCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new UnMarkCommand object
 */
public class UnMarkCommandParser implements Parser<UnMarkCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the UnMarkCommand
     * and returns a UnMarkCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UnMarkCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new UnMarkCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnMarkCommand.MESSAGE_USAGE), pe);
        }
    }
}
