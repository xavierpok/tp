package connexion.logic.parser;

import static connexion.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static connexion.logic.Messages.MESSAGE_INVALID_FIELD_FORMAT;
import static connexion.logic.commands.CommandTestUtil.COMPANY_DESC_AMY;
import static connexion.logic.commands.CommandTestUtil.COMPANY_DESC_BOB;
import static connexion.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static connexion.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static connexion.logic.commands.CommandTestUtil.INVALID_COMPANY;
import static connexion.logic.commands.CommandTestUtil.INVALID_COMPANY_DESC;
import static connexion.logic.commands.CommandTestUtil.INVALID_EMAIL;
import static connexion.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static connexion.logic.commands.CommandTestUtil.INVALID_JOB;
import static connexion.logic.commands.CommandTestUtil.INVALID_JOB_DESC;
import static connexion.logic.commands.CommandTestUtil.INVALID_NAME;
import static connexion.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static connexion.logic.commands.CommandTestUtil.INVALID_PHONE;
import static connexion.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static connexion.logic.commands.CommandTestUtil.INVALID_TAG;
import static connexion.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static connexion.logic.commands.CommandTestUtil.JOB_DESC_AMY;
import static connexion.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static connexion.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static connexion.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static connexion.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static connexion.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static connexion.logic.commands.CommandTestUtil.VALID_COMPANY_AMY;
import static connexion.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static connexion.logic.commands.CommandTestUtil.VALID_JOB_AMY;
import static connexion.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static connexion.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static connexion.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static connexion.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static connexion.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static connexion.logic.parser.CliSyntax.PREFIX_COMPANY;
import static connexion.logic.parser.CliSyntax.PREFIX_EMAIL;
import static connexion.logic.parser.CliSyntax.PREFIX_JOB;
import static connexion.logic.parser.CliSyntax.PREFIX_PHONE;
import static connexion.logic.parser.CliSyntax.PREFIX_TAG;
import static connexion.logic.parser.CommandParserTestUtil.assertParseFailure;
import static connexion.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static connexion.logic.parser.ParserUtilTest.makeExceptionMessage;
import static connexion.testutil.ClockUtil.DEFAULT_TEST_CLOCK;
import static connexion.testutil.ClockUtil.DEFAULT_TEST_TIME;
import static connexion.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static connexion.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static connexion.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import org.junit.jupiter.api.Test;

import connexion.commons.core.index.Index;
import connexion.logic.Messages;
import connexion.logic.commands.EditCommand;
import connexion.logic.commands.EditCommand.EditPersonDescriptor;
import connexion.model.person.Company;
import connexion.model.person.Email;
import connexion.model.person.Job;
import connexion.model.person.Name;
import connexion.model.person.Phone;
import connexion.model.tag.Tag;
import connexion.testutil.EditPersonDescriptorBuilder;

public class EditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser().withClock(DEFAULT_TEST_CLOCK);

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, String.format(MESSAGE_INVALID_FIELD_FORMAT,
                makeExceptionMessage("index", VALID_NAME_AMY, ParserUtil.MESSAGE_INVALID_INDEX),
                EditCommand.MESSAGE_USAGE));

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_FIELD_FORMAT,
                makeExceptionMessage("index", "", ParserUtil.MESSAGE_INVALID_INDEX),
                EditCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_AMY,
                String.format(MESSAGE_INVALID_FIELD_FORMAT,
                        makeExceptionMessage("index",
                                "-5" , ParserUtil.MESSAGE_INVALID_INDEX),
                        EditCommand.MESSAGE_USAGE));

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AMY,
                String.format(MESSAGE_INVALID_FIELD_FORMAT,
                        makeExceptionMessage("index",
                                "0" , ParserUtil.MESSAGE_INVALID_INDEX),
                        EditCommand.MESSAGE_USAGE));

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string" + NAME_DESC_AMY,
                String.format(MESSAGE_INVALID_FIELD_FORMAT,
                        makeExceptionMessage("index",
                                "1 some random string" , ParserUtil.MESSAGE_INVALID_INDEX),
                        EditCommand.MESSAGE_USAGE));

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string" + NAME_DESC_AMY,
                String.format(MESSAGE_INVALID_FIELD_FORMAT,
                        makeExceptionMessage("index",
                                "1 i/ string" , ParserUtil.MESSAGE_INVALID_INDEX),
                        EditCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC,
                makeExceptionMessage("name", INVALID_NAME, Name.MESSAGE_CONSTRAINTS));
        // invalid name
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC,
                makeExceptionMessage("phone number", INVALID_PHONE, Phone.MESSAGE_CONSTRAINTS));
        // invalid phone
        assertParseFailure(parser, "1" + INVALID_EMAIL_DESC,
                makeExceptionMessage("email", INVALID_EMAIL, Email.MESSAGE_CONSTRAINTS));
        // invalid email
        assertParseFailure(parser, "1" + INVALID_COMPANY_DESC,
                makeExceptionMessage("company name", INVALID_COMPANY, Company.MESSAGE_CONSTRAINTS));
        // invalid company
        assertParseFailure(parser, "1" + INVALID_JOB_DESC,
                makeExceptionMessage("job", INVALID_JOB, Job.MESSAGE_CONSTRAINTS));
        // invalid job
        assertParseFailure(parser, "1" + INVALID_TAG_DESC,
                makeExceptionMessage("tag", INVALID_TAG, Tag.MESSAGE_CONSTRAINTS));
        // invalid tag

        // invalid phone followed by valid email
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC + EMAIL_DESC_AMY,
                makeExceptionMessage("phone number", INVALID_PHONE, Phone.MESSAGE_CONSTRAINTS));

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Person} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_DESC_HUSBAND + TAG_EMPTY,
                makeExceptionMessage("tag", "", Tag.MESSAGE_CONSTRAINTS));
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_EMPTY + TAG_DESC_HUSBAND,
                makeExceptionMessage("tag", "", Tag.MESSAGE_CONSTRAINTS));
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND,
                makeExceptionMessage("tag", "", Tag.MESSAGE_CONSTRAINTS));

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_EMAIL_DESC
                        + VALID_COMPANY_AMY + VALID_JOB_AMY + VALID_PHONE_AMY,
                        makeExceptionMessage("name", INVALID_NAME, Name.MESSAGE_CONSTRAINTS));
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + TAG_DESC_HUSBAND
                + EMAIL_DESC_AMY + COMPANY_DESC_AMY + JOB_DESC_AMY + NAME_DESC_AMY + TAG_DESC_FRIEND;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_AMY)
                .withCompany(VALID_COMPANY_AMY).withJob(VALID_JOB_AMY)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).withLastModifiedDateTime(DEFAULT_TEST_TIME).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + EMAIL_DESC_AMY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_AMY).withLastModifiedDateTime(DEFAULT_TEST_TIME).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + NAME_DESC_AMY;
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withLastModifiedDateTime(DEFAULT_TEST_TIME).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = targetIndex.getOneBased() + PHONE_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_AMY)
                .withLastModifiedDateTime(DEFAULT_TEST_TIME).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = targetIndex.getOneBased() + EMAIL_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withEmail(VALID_EMAIL_AMY)
                .withLastModifiedDateTime(DEFAULT_TEST_TIME).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // company
        userInput = targetIndex.getOneBased() + COMPANY_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withCompany(VALID_COMPANY_AMY)
                .withLastModifiedDateTime(DEFAULT_TEST_TIME).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // job
        userInput = targetIndex.getOneBased() + JOB_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withJob(VALID_JOB_AMY)
                .withLastModifiedDateTime(DEFAULT_TEST_TIME).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_FRIEND;
        descriptor = new EditPersonDescriptorBuilder().withTags(VALID_TAG_FRIEND)
                .withLastModifiedDateTime(DEFAULT_TEST_TIME).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // More extensive testing of duplicate parameter detections is done in
        // AddCommandParserTest#parse_repeatedNonTagValue_failure()

        // valid followed by invalid
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + INVALID_PHONE_DESC + PHONE_DESC_BOB;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid followed by valid
        userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + INVALID_PHONE_DESC;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple valid fields repeated
        userInput = targetIndex.getOneBased() + PHONE_DESC_AMY + COMPANY_DESC_AMY + EMAIL_DESC_AMY
                + TAG_DESC_FRIEND + PHONE_DESC_AMY
                + COMPANY_DESC_AMY + EMAIL_DESC_AMY + TAG_DESC_FRIEND + JOB_DESC_AMY
                + PHONE_DESC_BOB + COMPANY_DESC_BOB + EMAIL_DESC_BOB + TAG_DESC_HUSBAND;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE, PREFIX_EMAIL, PREFIX_COMPANY));

        // multiple invalid values
        userInput = targetIndex.getOneBased() + INVALID_PHONE_DESC + INVALID_JOB_DESC + INVALID_EMAIL_DESC
                + INVALID_PHONE_DESC + INVALID_JOB_DESC + INVALID_EMAIL_DESC + INVALID_COMPANY_DESC;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE, PREFIX_EMAIL, PREFIX_JOB));
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder()
                .withTags().withLastModifiedDateTime(DEFAULT_TEST_TIME).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
