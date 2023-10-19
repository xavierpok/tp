package connexion.logic.commands;

import static connexion.logic.parser.CliSyntax.PREFIX_COMPANY;
import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import connexion.commons.util.ToStringBuilder;
import connexion.logic.Messages;
import connexion.logic.commands.exceptions.CommandException;
import connexion.model.Model;
import connexion.model.person.Person;

/**
 * Filters and lists all persons in contact list whose specified field contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Filters for all persons whose specified field "
            + "contains any of the specified keywords"
            + "(case-insensitive) and displays them as a list with index numbers. \n"
            + "Parameters: FIELD/KEYWORD [MORE_KEYWORDS]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_COMPANY + "Google";

    private final Predicate<Person> predicate;

    public FilterCommand(Predicate<Person> predicate) {
        this.predicate = predicate;
    }
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FilterCommand)) {
            return false;
        }

        FilterCommand otherFilterCommand = (FilterCommand) other;
        return predicate.equals(otherFilterCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
