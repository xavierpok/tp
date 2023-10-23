package connexion.logic.parser;

import static connexion.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static connexion.logic.commands.CommandTestUtil.COMPANY_DESC_AMY;
import static connexion.logic.commands.CommandTestUtil.COMPANY_DESC_BOB;
import static connexion.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static connexion.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static connexion.logic.commands.CommandTestUtil.INVALID_COMPANY_DESC;
import static connexion.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static connexion.logic.commands.CommandTestUtil.INVALID_JOB_DESC;
import static connexion.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static connexion.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static connexion.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static connexion.logic.commands.CommandTestUtil.JOB_DESC_AMY;
import static connexion.logic.commands.CommandTestUtil.JOB_DESC_BOB;
import static connexion.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static connexion.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static connexion.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static connexion.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static connexion.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static connexion.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static connexion.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static connexion.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static connexion.logic.commands.CommandTestUtil.VALID_COMPANY_BOB;
import static connexion.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static connexion.logic.commands.CommandTestUtil.VALID_JOB_BOB;
import static connexion.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static connexion.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static connexion.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static connexion.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static connexion.logic.parser.CliSyntax.PREFIX_COMPANY;
import static connexion.logic.parser.CliSyntax.PREFIX_EMAIL;
import static connexion.logic.parser.CliSyntax.PREFIX_JOB;
import static connexion.logic.parser.CliSyntax.PREFIX_NAME;
import static connexion.logic.parser.CliSyntax.PREFIX_PHONE;
import static connexion.logic.parser.CommandParserTestUtil.assertParseFailure;
import static connexion.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static connexion.testutil.ClockUtil.DEFAULT_TEST_CLOCK;
import static connexion.testutil.ClockUtil.DEFAULT_TEST_TIME;
import static connexion.testutil.TypicalPersons.AMY;
import static connexion.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import connexion.logic.Messages;
import connexion.logic.commands.AddCommand;
import connexion.model.person.Company;
import connexion.model.person.Email;
import connexion.model.person.Job;
import connexion.model.person.Name;
import connexion.model.person.Person;
import connexion.model.person.Phone;
import connexion.model.tag.Tag;
import connexion.testutil.PersonBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser().withClock(DEFAULT_TEST_CLOCK);

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder(BOB).withTags(VALID_TAG_FRIEND)
                .withLastModifiedDateTime(DEFAULT_TEST_TIME).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + COMPANY_DESC_BOB + JOB_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));


        // multiple tags - all accepted
        Person expectedPersonMultipleTags = new PersonBuilder(BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .withLastModifiedDateTime(DEFAULT_TEST_TIME)
                .build();
        assertParseSuccess(parser,
                NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + COMPANY_DESC_BOB
                        + JOB_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                new AddCommand(expectedPersonMultipleTags));
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        String validExpectedPersonString = NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + COMPANY_DESC_BOB + JOB_DESC_BOB + TAG_DESC_FRIEND;

        // multiple names
        assertParseFailure(parser, NAME_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple phones
        assertParseFailure(parser, PHONE_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple emails
        assertParseFailure(parser, EMAIL_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // multiple companies
        assertParseFailure(parser, COMPANY_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_COMPANY));

        // multiple jobs
        assertParseFailure(parser, JOB_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_JOB));

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedPersonString + PHONE_DESC_AMY + EMAIL_DESC_AMY
                        + NAME_DESC_AMY + COMPANY_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(
                        PREFIX_NAME, PREFIX_JOB, PREFIX_EMAIL, PREFIX_COMPANY, PREFIX_PHONE));

        // invalid value followed by valid value

        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, INVALID_EMAIL_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, INVALID_PHONE_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid address
        assertParseFailure(parser, INVALID_COMPANY_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_COMPANY));

        // valid value followed by invalid value

        // invalid name
        assertParseFailure(parser, validExpectedPersonString + INVALID_NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, validExpectedPersonString + INVALID_EMAIL_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, validExpectedPersonString + INVALID_PHONE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid company
        assertParseFailure(parser, validExpectedPersonString + INVALID_COMPANY_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_COMPANY));

        // invalid job
        assertParseFailure(parser, validExpectedPersonString + INVALID_JOB_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_JOB));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Person expectedPerson = new PersonBuilder(AMY).withTags().withLastModifiedDateTime(DEFAULT_TEST_TIME).build();
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                        + COMPANY_DESC_AMY + JOB_DESC_AMY,
                        new AddCommand(expectedPerson));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + COMPANY_DESC_BOB + JOB_DESC_BOB, expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_PHONE_BOB + EMAIL_DESC_BOB
                        + COMPANY_DESC_BOB + JOB_DESC_BOB, expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + VALID_EMAIL_BOB
                        + COMPANY_DESC_BOB + JOB_DESC_BOB, expectedMessage);

        // missing address prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + VALID_COMPANY_BOB + VALID_JOB_BOB, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB
                        + VALID_COMPANY_BOB + VALID_JOB_BOB, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + COMPANY_DESC_BOB + JOB_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC
                + EMAIL_DESC_BOB + COMPANY_DESC_BOB + JOB_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC
                + COMPANY_DESC_BOB + JOB_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Email.MESSAGE_CONSTRAINTS);

        // invalid company
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_COMPANY_DESC
                + JOB_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Company.MESSAGE_CONSTRAINTS);

        // invalid job
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + COMPANY_DESC_BOB + INVALID_JOB_DESC + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Job.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + COMPANY_DESC_BOB + JOB_DESC_BOB + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + INVALID_COMPANY_DESC + JOB_DESC_BOB, Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + COMPANY_DESC_BOB + JOB_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
