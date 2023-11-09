package connexion.logic.parser;

import static connexion.logic.Messages.MESSAGE_INVALID_FIELD_FORMAT;

import connexion.commons.core.index.Index;
import connexion.logic.commands.UnMarkCommand;
import connexion.logic.parser.exceptions.ParseException;

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
                    String.format(MESSAGE_INVALID_FIELD_FORMAT, pe.getMessage(), UnMarkCommand.MESSAGE_USAGE), pe);
        }
    }
}
