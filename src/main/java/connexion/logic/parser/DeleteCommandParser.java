package connexion.logic.parser;

import static connexion.logic.Messages.MESSAGE_INVALID_FIELD_FORMAT;

import connexion.commons.core.index.Index;
import connexion.logic.commands.DeleteCommand;
import connexion.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_FIELD_FORMAT, pe.getMessage(), DeleteCommand.MESSAGE_USAGE), pe);
        }
    }

}
