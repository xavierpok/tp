package connexion.logic.parser;

import static connexion.logic.Messages.MESSAGE_INVALID_FIELD_FORMAT;

import connexion.commons.core.index.Index;
import connexion.logic.commands.MarkCommand;
import connexion.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new MarkCommand object
 */
public class MarkCommandParser implements Parser<MarkCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the MarkCommand
     * and returns a MarkCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MarkCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new MarkCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_FIELD_FORMAT, pe.getMessage(), MarkCommand.MESSAGE_USAGE), pe);
        }
    }
}
