package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Filters for all persons whose specified field contains any of the specified keywords"
            + "(case-insensitive) and displays them as a list with index numbers. \n"
            + "Parameters: FIELD KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_COMPANY + "Google";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        return new CommandResult("Hi");
    }


}
