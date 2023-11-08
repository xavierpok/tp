package connexion.logic.parser;

import static connexion.logic.parser.ParserUtil.MESSAGE_INDEX_EXCEEDS_INT_LIMIT;
import static connexion.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static connexion.testutil.Assert.assertThrows;
import static connexion.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import connexion.commons.core.index.Index;
import org.junit.jupiter.api.Test;

import connexion.logic.parser.exceptions.ParseException;
import connexion.model.person.Company;
import connexion.model.person.Email;
import connexion.model.person.Job;
import connexion.model.person.Name;
import connexion.model.person.Note;
import connexion.model.person.Phone;
import connexion.model.person.Schedule;
import connexion.model.person.ScheduleName;
import connexion.model.tag.Tag;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_COMPANY = " ";
    private static final String INVALID_JOB = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_SCHEDULE = "2023-00-12-12-12";
    private static final String INVALID_SCHEDULE_NAME = "Semin@r";
    private static final String INVALID_NOTE = "\u2063";
    private static final String NOTE_WITH_INVALID_LENGTH = "One morning, when Gregor Samsa woke from troubled dreams, "
            + "he found himself transformed in his bed into a horrible vermin. He lay on his armour-like back, "
            + "and if he lifted his head a little he could see his brown belly, slightly domed and divided by "
            + "arches into stiff sections. The bedding was hardly able to cover it and seemed ready to slide off "
            + "any moment. His many legs, pitifully thin compared with the size of the rest of him, waved about "
            + "helplessly as he looked. \"What's happened to me?\" he thought. It wasn't a dream. His room, "
            + "a proper human room although a little too small, lay peacefully between its four familiar walls. "
            + "A collection of textile samples lay spread out on the table - Samsa was a travelling salesman - and "
            + "above it there hung a picture that he had recently cut out of an illustrated magazine and housed in "
            + "a nice, gilded frame. It showed a lady fitted out with a fur hat and fur boa who sat upright, "
            + "raising a heavy fur muff that covered the whole of her lower arm towards ta";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_COMPANY = "JP Morgan";
    private static final String VALID_JOB = "Data Analyst";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";
    private static final String VALID_NOTE = "CS2103 is pAin!!!!";

    private static final String WHITESPACE = " \t\r\n";
    private static final String VALID_SCHEDULE = "2023-12-27-12-45";
    private static final String VALID_SCHEDULE_NAME = "Seminar";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
                -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INDEX_EXCEEDS_INT_LIMIT, ()
            -> ParserUtil.parseIndex(Long.toString((long) Integer.MAX_VALUE + 1)));
    }
    @Test
    public void parseIndex_negativeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
                -> ParserUtil.parseIndex("-1"));
    }
    @Test
    public void parseIndex_fractionalInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
                -> ParserUtil.parseIndex("1.1"));
    }
    @Test
    public void parseIndex_zeroInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
                -> ParserUtil.parseIndex("0")); //just the one zero
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
                -> ParserUtil.parseIndex("00")); //more zeros
    }



    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("  1  "));

        // all possible digits
        assertEquals(Index.fromOneBased(1234567890),
                ParserUtil.parseIndex("1234567890"));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parsePhone_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePhone((String) null));
    }

    @Test
    public void parsePhone_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePhone(INVALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithoutWhitespace_returnsPhone() throws Exception {
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(VALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithWhitespace_returnsTrimmedPhone() throws Exception {
        String phoneWithWhitespace = WHITESPACE + VALID_PHONE + WHITESPACE;
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(phoneWithWhitespace));
    }

    @Test
    public void parseCompany_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseCompany((String) null));
    }

    @Test
    public void parseCompany_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseCompany(INVALID_COMPANY));
    }

    @Test
    public void parseCompany_validValueWithoutWhitespace_returnsCompany() throws Exception {
        Company expectedCompany = new Company(VALID_COMPANY);
        assertEquals(expectedCompany, ParserUtil.parseCompany(VALID_COMPANY));
    }

    @Test
    public void parseCompany_validValueWithWhitespace_returnsTrimmedCompany() throws Exception {
        String companyWithWhitespace = WHITESPACE + VALID_COMPANY + WHITESPACE;
        Company expectedCompany = new Company(VALID_COMPANY);
        assertEquals(expectedCompany, ParserUtil.parseCompany(companyWithWhitespace));
    }

    @Test
    public void parseJob_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseJob((String) null));
    }

    @Test
    public void parseJob_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseJob(INVALID_JOB));
    }

    @Test
    public void parseJob_validValueWithoutWhitespace_returnsJob() throws Exception {
        Job expectedJob = new Job(VALID_JOB);
        assertEquals(expectedJob, ParserUtil.parseJob(VALID_JOB));
    }

    @Test
    public void parseJob_validValueWithWhitespace_returnsTrimmedJob() throws Exception {
        String jobWithWhitespace = WHITESPACE + VALID_JOB + WHITESPACE;
        Job expectedJob = new Job(VALID_JOB);
        assertEquals(expectedJob, ParserUtil.parseJob(jobWithWhitespace));
    }

    @Test
    public void parseEmail_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseEmail((String) null));
    }

    @Test
    public void parseEmail_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseEmail(INVALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithoutWhitespace_returnsEmail() throws Exception {
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(VALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        String emailWithWhitespace = WHITESPACE + VALID_EMAIL + WHITESPACE;
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(emailWithWhitespace));
    }

    @Test
    public void parseTag_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTag(null));
    }

    @Test
    public void parseTag_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTag(INVALID_TAG));
    }

    @Test
    public void parseTag_validValueWithoutWhitespace_returnsTag() throws Exception {
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(VALID_TAG_1));
    }

    @Test
    public void parseTag_validValueWithWhitespace_returnsTrimmedTag() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_TAG_1 + WHITESPACE;
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(tagWithWhitespace));
    }

    @Test
    public void parseTags_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTags(null));
    }

    @Test
    public void parseTags_collectionWithInvalidTags_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, INVALID_TAG)));
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseTags(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<Tag> expectedTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }

    @Test
    public void parseNote_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseNote((String) null));
    }

    @Test
    public void parseNote_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseNote(INVALID_NOTE));
    }

    @Test
    public void parseNote_invalidLength_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseNote(NOTE_WITH_INVALID_LENGTH));
    }

    @Test
    public void parseNote_validValueWithoutWhitespace_returnsNote() throws Exception {
        Note expectedNote = new Note(VALID_NOTE);
        assertEquals(expectedNote, ParserUtil.parseNote(VALID_NOTE));
    }

    @Test
    public void parseNote_validValueWithWhitespace_returnsTrimmedNote() throws Exception {
        String noteWithWhitespace = WHITESPACE + VALID_NOTE + WHITESPACE;
        Note expectedNote = new Note(VALID_NOTE);
        assertEquals(expectedNote, ParserUtil.parseNote(noteWithWhitespace));
    }

    @Test
    public void parseSchedule_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseSchedule((String) null));
    }

    @Test
    public void parseSchedule_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseSchedule(INVALID_SCHEDULE));
    }

    @Test
    public void parseSchedule_validValue_returnsSchedule() throws Exception {
        Schedule expectedSchedule = new Schedule(VALID_SCHEDULE);
        assertEquals(expectedSchedule, ParserUtil.parseSchedule(VALID_SCHEDULE));
    }

    @Test
    public void parseScheduleName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseScheduleName((String) null));
    }

    @Test
    public void parseScheduleName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseScheduleName(INVALID_SCHEDULE_NAME));
    }

    @Test
    public void parseScheduleName_validValueWithoutWhitespace_returnsScheduleName() throws Exception {
        ScheduleName expectedScheduleName = new ScheduleName(VALID_SCHEDULE_NAME);
        assertEquals(expectedScheduleName, ParserUtil.parseScheduleName(VALID_SCHEDULE_NAME));
    }

    @Test
    public void parseScheduleName_validValueWithWhitespace_returnsTrimmedScheduleName() throws Exception {
        String scheduleNameWithWhitespace = WHITESPACE + VALID_SCHEDULE_NAME + WHITESPACE;
        ScheduleName expectedScheduleName = new ScheduleName(VALID_SCHEDULE_NAME);
        assertEquals(expectedScheduleName, ParserUtil.parseScheduleName(scheduleNameWithWhitespace));
    }
}
