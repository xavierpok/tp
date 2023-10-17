package seedu.address.logic.commands;


import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * UnMarks a person identified using it's displayed index from the address book.
 */
public class UnMarkCommand extends Command{
    public static final String COMMAND_WORD = "unmark";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Un-marks the person identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_UNMARK_SUCCESS = "Un-marked Person: %1$s";

    private final Index targetIndex;

    public UnMarkCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToUnMark = lastShownList.get(targetIndex.getZeroBased());
        model.unMarkPerson(personToUnMark);
        return new CommandResult(String.format(MESSAGE_UNMARK_SUCCESS, Messages.format(personToUnMark)));
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UnMarkCommand)) {
            return false;
        }

        UnMarkCommand otherUnMarkCommand = (UnMarkCommand) other;
        return targetIndex.equals(otherUnMarkCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
