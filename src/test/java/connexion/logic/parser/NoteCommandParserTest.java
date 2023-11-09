package connexion.logic.parser;

import static connexion.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static connexion.logic.Messages.MESSAGE_INVALID_FIELD_FORMAT;
import static connexion.logic.commands.CommandTestUtil.INVALID_NOTE;
import static connexion.logic.commands.CommandTestUtil.INVALID_NOTE_DESC;
import static connexion.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static connexion.logic.commands.CommandTestUtil.NOTE_DESC_AMY;
import static connexion.logic.commands.CommandTestUtil.NOTE_DESC_BOB;
import static connexion.logic.commands.CommandTestUtil.NOTE_WITH_INVALID_LENGTH_DESC;
import static connexion.logic.commands.CommandTestUtil.VALID_NOTE_AMY;
import static connexion.logic.commands.CommandTestUtil.VALID_NOTE_BOB;
import static connexion.logic.parser.CommandParserTestUtil.assertParseFailure;
import static connexion.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static connexion.testutil.ClockUtil.DEFAULT_TEST_CLOCK;
import static connexion.testutil.ClockUtil.DEFAULT_TEST_TIME;
import static connexion.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import connexion.logic.commands.MarkCommand;
import org.junit.jupiter.api.Test;

import connexion.commons.core.index.Index;
import connexion.logic.commands.NoteCommand;
import connexion.logic.commands.NoteCommand.NoteDescriptor;
import connexion.model.person.Note;
import connexion.testutil.NoteDescriptorBuilder;

public class NoteCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, NoteCommand.MESSAGE_USAGE);

    private NoteCommandParser parser = new NoteCommandParser().withClock(DEFAULT_TEST_CLOCK);

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NOTE_AMY,
                String.format(MESSAGE_INVALID_FIELD_FORMAT,
                        ParserUtilTest.makeExceptionMessage("index",
                                VALID_NOTE_AMY , ParserUtil.MESSAGE_INVALID_INDEX),
                        NoteCommand.MESSAGE_USAGE));

        // no field specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        // no index specified
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_FIELD_FORMAT,
                        ParserUtilTest.makeExceptionMessage("index",
                                "" , ParserUtil.MESSAGE_INVALID_INDEX),
                        NoteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5",
                String.format(MESSAGE_INVALID_FIELD_FORMAT,
                        ParserUtilTest.makeExceptionMessage("index",
                                "-5" , ParserUtil.MESSAGE_INVALID_INDEX),
                        NoteCommand.MESSAGE_USAGE));

        // zero index
        assertParseFailure(parser, "0",
                String.format(MESSAGE_INVALID_FIELD_FORMAT,
                        ParserUtilTest.makeExceptionMessage("index",
                                "0" , ParserUtil.MESSAGE_INVALID_INDEX),
                        NoteCommand.MESSAGE_USAGE));

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string",
                String.format(MESSAGE_INVALID_FIELD_FORMAT,
                        ParserUtilTest.makeExceptionMessage("index",
                                "1 some random string" , ParserUtil.MESSAGE_INVALID_INDEX),
                        NoteCommand.MESSAGE_USAGE));

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 e/ string",
                String.format(MESSAGE_INVALID_FIELD_FORMAT,
                        ParserUtilTest.makeExceptionMessage("index",
                                "1 e/ string" , ParserUtil.MESSAGE_INVALID_INDEX),
                        NoteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidValueOrInvalidLength_failure() {
        assertParseFailure(parser, "1" + INVALID_NOTE_DESC,
                "Invalid note given: " + INVALID_NOTE + "\n" + Note.MESSAGE_CONSTRAINTS); // invalid note
        assertParseFailure(parser, "1" + NOTE_WITH_INVALID_LENGTH_DESC,
                 Note.MESSAGE_CONSTRAINTS_CHARACTER_LIMIT); // note with invalid length
    }

    @Test
    public void parse_validValue_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + NOTE_DESC_BOB;
        NoteDescriptor descriptor = new NoteDescriptorBuilder()
                .withNote(VALID_NOTE_BOB)
                .withLastModifiedDateTime(DEFAULT_TEST_TIME)
                .build();
        NoteCommand expectedCommand = new NoteCommand(targetIndex,
                descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
