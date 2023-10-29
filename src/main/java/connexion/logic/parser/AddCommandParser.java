package connexion.logic.parser;

import static connexion.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static connexion.logic.parser.CliSyntax.PREFIX_COMPANY;
import static connexion.logic.parser.CliSyntax.PREFIX_EMAIL;
import static connexion.logic.parser.CliSyntax.PREFIX_JOB;
import static connexion.logic.parser.CliSyntax.PREFIX_NAME;
import static connexion.logic.parser.CliSyntax.PREFIX_PHONE;
import static connexion.logic.parser.CliSyntax.PREFIX_TAG;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Stream;

import connexion.logic.commands.AddCommand;
import connexion.logic.parser.exceptions.ParseException;
import connexion.model.person.Company;
import connexion.model.person.Email;
import connexion.model.person.Job;
import connexion.model.person.LastModifiedDateTime;
import connexion.model.person.Mark;
import connexion.model.person.Name;
import connexion.model.person.Note;
import connexion.model.person.Person;
import connexion.model.person.Phone;
import connexion.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements ClockDependentParser<AddCommand> {

    private Clock clock = Clock.systemDefaultZone();

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(
                        args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_COMPANY, PREFIX_JOB, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_COMPANY, PREFIX_JOB, PREFIX_PHONE, PREFIX_EMAIL)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_COMPANY, PREFIX_JOB);
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Company company = ParserUtil.parseCompany(argMultimap.getValue(PREFIX_COMPANY).get());
        Job job = ParserUtil.parseJob(argMultimap.getValue(PREFIX_JOB).get());
        Mark markStatus = new Mark(false);
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        LastModifiedDateTime lastModifiedDateTime = new LastModifiedDateTime(LocalDateTime.now(clock));
        Note note = new Note("");
        Person person = new Person(name, phone, email, company, job, markStatus, tagList, lastModifiedDateTime, note);

        return new AddCommand(person);
    }



    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * To specify usage of a specific clock.
     * @param clock The clock to use
     * @return an edited parser using the specified clock
     */
    @Override
    public AddCommandParser withClock(Clock clock) {
        AddCommandParser toReturn = new AddCommandParser();
        toReturn.clock = clock;
        return toReturn;
    }
}
