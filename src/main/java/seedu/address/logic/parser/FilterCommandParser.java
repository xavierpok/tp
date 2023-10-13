package seedu.address.logic.parser;


import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_FILTER_TAG_NOT_IMPLEMENTED;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.CompanyContainsKeywordsPredicate;
import seedu.address.model.person.EmailContainsKeywordsPredicate;
import seedu.address.model.person.JobContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.PhoneContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FilterCommand object.
 */
public class FilterCommandParser implements Parser<FilterCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FilterCommand
     * and returns a FilterCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();
        String prefix;
        String[] keywords;

        if (trimmedArgs.length() <= 2) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        prefix = trimmedArgs.substring(0, 2);
        keywords = trimmedArgs.substring(2).trim().split("\\s+");

        if (prefix.equals(PREFIX_NAME.getPrefix())) {
            return new FilterCommand(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        }
        if (prefix.equals(PREFIX_PHONE.getPrefix())) {
            return new FilterCommand(new PhoneContainsKeywordsPredicate(Arrays.asList(keywords)));
        }
        if (prefix.equals(PREFIX_EMAIL.getPrefix())) {
            return new FilterCommand(new EmailContainsKeywordsPredicate(Arrays.asList(keywords)));
        }
        if (prefix.equals(PREFIX_COMPANY.getPrefix())) {
            return new FilterCommand(new CompanyContainsKeywordsPredicate(Arrays.asList(keywords)));
        }
        if (prefix.equals(PREFIX_JOB.getPrefix())) {
            return new FilterCommand(new JobContainsKeywordsPredicate(Arrays.asList(keywords)));
        }
        // Filtering for tags not implemented yet
        if (prefix.equals(PREFIX_TAG.getPrefix())) {
            throw new ParseException(MESSAGE_FILTER_TAG_NOT_IMPLEMENTED);
        }
        // No valid prefix exists, invalid command format
        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
    }
}
