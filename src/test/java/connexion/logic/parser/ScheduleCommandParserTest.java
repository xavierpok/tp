package connexion.logic.parser;

import static connexion.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static connexion.logic.commands.CommandTestUtil.AUTO_GIVEN_SCHEDULE_NAME;
import static connexion.logic.commands.CommandTestUtil.INVALID_SCHEDULE_DATE;
import static connexion.logic.commands.CommandTestUtil.INVALID_SCHEDULE_DESC;
import static connexion.logic.commands.CommandTestUtil.INVALID_SCHEDULE_NAME_DESC;
import static connexion.logic.commands.CommandTestUtil.SCHEDULE_DESC_AMY;
import static connexion.logic.commands.CommandTestUtil.SCHEDULE_DESC_BOB;
import static connexion.logic.commands.CommandTestUtil.SCHEDULE_NAME_DESC_AMY;
import static connexion.logic.commands.CommandTestUtil.SCHEDULE_NAME_DESC_BOB;
import static connexion.logic.commands.CommandTestUtil.VALID_SCHEDULE_AMY;
import static connexion.logic.commands.CommandTestUtil.VALID_SCHEDULE_BOB;
import static connexion.logic.commands.CommandTestUtil.VALID_SCHEDULE_NAME_AMY;
import static connexion.logic.parser.CliSyntax.PREFIX_SCHEDULE;
import static connexion.logic.parser.CliSyntax.PREFIX_SCHEDULE_NAME;
import static connexion.logic.parser.CliSyntax.PREFIX_TAG;
import static connexion.logic.parser.CommandParserTestUtil.assertParseFailure;
import static connexion.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static connexion.testutil.ClockUtil.DEFAULT_TEST_CLOCK;
import static connexion.testutil.ClockUtil.DEFAULT_TEST_TIME;
import static connexion.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static connexion.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.jupiter.api.Test;

import connexion.commons.core.index.Index;
import connexion.logic.Messages;
import connexion.logic.commands.ScheduleCommand;
import connexion.logic.commands.ScheduleCommand.ScheduleDescriptor;
import connexion.model.person.Schedule;
import connexion.model.person.ScheduleName;
import connexion.testutil.ScheduleDescriptorBuilder;

public class ScheduleCommandParserTest {
    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE);

    private ScheduleCommandParser parser = new ScheduleCommandParser().withClock(DEFAULT_TEST_CLOCK);

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_SCHEDULE_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + SCHEDULE_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + SCHEDULE_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 e/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_SCHEDULE_DESC, Schedule.MESSAGE_CONSTRAINTS); // invalid schedule
        assertParseFailure(parser, "1" + SCHEDULE_DESC_AMY + INVALID_SCHEDULE_NAME_DESC,
                ScheduleName.MESSAGE_CONSTRAINTS); // invalid schedule name

        // valid schedule name but invalid schedule
        assertParseFailure(parser, "1" + INVALID_SCHEDULE_DESC + SCHEDULE_NAME_DESC_AMY,
                Schedule.MESSAGE_CONSTRAINTS); // invalid schedule
        assertParseFailure(parser, "1" + INVALID_SCHEDULE_DATE + SCHEDULE_NAME_DESC_AMY,
                Schedule.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + SCHEDULE_DESC_BOB + SCHEDULE_NAME_DESC_AMY;
        ScheduleDescriptor descriptor = new ScheduleDescriptorBuilder()
                .withSchedule(VALID_SCHEDULE_BOB).withScheduleName(VALID_SCHEDULE_NAME_AMY)
                .withLastModifiedDateTime(DEFAULT_TEST_TIME)
                .build();

        ScheduleCommand expectedCommand = new ScheduleCommand(targetIndex,
               descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    // Parse without schedule name
    @Test
    public void parse_onlyScheduleSpecified_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + SCHEDULE_DESC_BOB;
        ScheduleDescriptor descriptor = new ScheduleDescriptorBuilder()
                .withSchedule(VALID_SCHEDULE_BOB).withScheduleName(AUTO_GIVEN_SCHEDULE_NAME)
                .withLastModifiedDateTime(DEFAULT_TEST_TIME)
                .build();
        ScheduleCommand expectedCommand = new ScheduleCommand(targetIndex,
                descriptor);


        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // More extensive testing of duplicate parameter detections is done in
        // AddCommandParserTest#parse_repeatedNonTagValue_failure()

        // valid schedule followed by invalid schedule
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + SCHEDULE_DESC_BOB + INVALID_SCHEDULE_DESC;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_SCHEDULE));

        // invalid schedule followed by valid schedule
        userInput = targetIndex.getOneBased() + INVALID_SCHEDULE_DESC + SCHEDULE_DESC_BOB;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_SCHEDULE));

        // multiple valid schedules repeated
        userInput = targetIndex.getOneBased() + SCHEDULE_DESC_BOB + SCHEDULE_DESC_BOB;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_SCHEDULE));

        // multiple invalid schedules
        userInput = targetIndex.getOneBased() + INVALID_SCHEDULE_DESC + INVALID_SCHEDULE_DESC;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_SCHEDULE));

        // valid schedule name followed by invalid schedule name
        userInput = targetIndex.getOneBased() + SCHEDULE_DESC_BOB
                + SCHEDULE_NAME_DESC_AMY + INVALID_SCHEDULE_NAME_DESC;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_SCHEDULE_NAME));

        // invalid schedule name followed by valid schedule name
        userInput = targetIndex.getOneBased() + INVALID_SCHEDULE_DESC + SCHEDULE_DESC_BOB;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_SCHEDULE));

        // multiple valid schedule name repeated
        userInput = targetIndex.getOneBased() + SCHEDULE_DESC_AMY + SCHEDULE_NAME_DESC_BOB + SCHEDULE_NAME_DESC_BOB;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_SCHEDULE_NAME));

        // multiple invalid schedule names
        userInput = targetIndex.getOneBased() + SCHEDULE_DESC_BOB
                + INVALID_SCHEDULE_NAME_DESC + INVALID_SCHEDULE_NAME_DESC;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_SCHEDULE_NAME));

    }
}
