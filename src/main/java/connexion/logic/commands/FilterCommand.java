package connexion.logic.commands;

import static connexion.logic.parser.CliSyntax.PREFIX_COMPANY;
import static connexion.logic.parser.CliSyntax.PREFIX_JOB;
import static connexion.logic.parser.CliSyntax.PREFIX_MARK;
import static connexion.logic.parser.CliSyntax.PREFIX_NAME;
import static connexion.logic.parser.CliSyntax.PREFIX_PHONE;
import static connexion.logic.parser.CliSyntax.PREFIX_TAG;
import static connexion.logic.parser.CliSyntax.PREFIX_UNMARK;
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

    public static final String FIELD_PREFIX_MODE_1 = String.format("[%s, %s, %s, %s, %s, %s]",
            PREFIX_NAME, PREFIX_PHONE, PREFIX_COMPANY, PREFIX_COMPANY, PREFIX_JOB, PREFIX_TAG);

    public static final String FIELD_PREFIX_MODE_2 = String.format("[%s, %s]",
            PREFIX_MARK, PREFIX_UNMARK);

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Filters for all persons whose specified field "
            + "contains any of the specified keywords"
            + "(case-insensitive) and displays them as a list with index numbers. \n"
            + "Parameters (Mode 1): FIELD_PREFIX_1 KEYWORD [MORE_KEYWORDS] "
            + "(FIELD_PREFIX_1 is one of " + FIELD_PREFIX_MODE_1 + ")\n"
            + "Parameters (Mode 2): FIELD_PREFIX_2 "
            + "(FIELD_PREFIX_2 is one of " + FIELD_PREFIX_MODE_2 + ")\n"
            + "Example 1: " + COMMAND_WORD + " " + PREFIX_COMPANY + "Google\n"
            + "Example 2: " + COMMAND_WORD + " " + PREFIX_MARK;

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
