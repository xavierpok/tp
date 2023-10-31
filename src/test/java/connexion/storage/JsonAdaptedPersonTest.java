package connexion.storage;

import static connexion.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static connexion.testutil.Assert.assertThrows;
import static connexion.testutil.TypicalPersons.BENSON;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import connexion.commons.exceptions.IllegalValueException;
import connexion.model.person.Company;
import connexion.model.person.Email;
import connexion.model.person.Job;
import connexion.model.person.LastModifiedDateTime;
import connexion.model.person.Name;
import connexion.model.person.Note;
import connexion.model.person.Person;
import connexion.model.person.Phone;
import connexion.model.person.Schedule;
import connexion.model.person.ScheduleName;

public class JsonAdaptedPersonTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_COMPANY = " ";
    private static final String INVALID_JOB = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_SCHEDULE = "theheheh";
    private static final String INVALID_SCHEDULE_NAME = " ";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_NOTE = "\u2063";
    private static final String INVALID_NOTE_CHARACTER_LIMIT =
            "One morning, when Gregor Samsa woke from troubled dreams, "
            + "he found himself transformed in his bed into a horrible vermin. He lay on his armour-like back, "
            + "and if he lifted his head a little he could see his brown belly, slightly domed and divided by "
            + "arches into stiff sections. The bedding was hardly able to cover it and seemed ready to slide off "
            + "any moment. His many legs, pitifully thin compared with the size of the rest of him, waved about "
            + "helplessly as he looked. \"What's happened to me?\" he thought. It wasn't a dream. His room, "
            + "a proper human room although a little too small, lay peacefully between its four familiar walls. "
            + "A collection of textile samples lay spread out on the table - Samsa was a travelling salesman - and "
            + "above it there hung a picture that he had recently cut out of an illustrated magazine and housed in "
            + "a nice, gilded frame. It showed a lady fitted out with a fur hat and fur boa who sat upright, "
            + "raising a heavy fur muff that covered the whole of her lower arm towards ta"; // 1001 characters

    private static final String INVALID_LAST_MODIFIED_DATE_TIME = "Nov 40, 2000 13:00:10 AM";
    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_COMPANY = BENSON.getCompany().toString();
    private static final String VALID_JOB = BENSON.getJob().toString();
    private static final String VALID_SCHEDULE = BENSON.getSchedule().get().toString();
    private static final String VALID_SCHEDULE_NAME = BENSON.getScheduleName().get().toString();
    private static final String VALID_MARK_STATUS = BENSON.getMarkStatus().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    private static final String VALID_NOTE = BENSON.getNote().toString();

    private static final String VALID_LAST_MODIFIED_DATE_TIME = BENSON.getLastModifiedDateTime().toString();

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_COMPANY, VALID_JOB, VALID_TAGS,
                        VALID_MARK_STATUS, VALID_SCHEDULE, VALID_SCHEDULE_NAME,
                        VALID_LAST_MODIFIED_DATE_TIME, VALID_NOTE);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                null, VALID_PHONE, VALID_EMAIL, VALID_COMPANY, VALID_JOB, VALID_TAGS, VALID_MARK_STATUS,
                VALID_SCHEDULE, VALID_SCHEDULE_NAME, VALID_LAST_MODIFIED_DATE_TIME, VALID_NOTE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(
                        VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_COMPANY, VALID_JOB, VALID_TAGS,
                        VALID_MARK_STATUS, VALID_SCHEDULE, VALID_SCHEDULE_NAME,
                        VALID_LAST_MODIFIED_DATE_TIME, VALID_NOTE);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_NAME, null, VALID_EMAIL, VALID_COMPANY, VALID_JOB, VALID_TAGS, VALID_MARK_STATUS,
                VALID_SCHEDULE, VALID_SCHEDULE_NAME, VALID_LAST_MODIFIED_DATE_TIME, VALID_NOTE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, INVALID_EMAIL,
                        VALID_COMPANY, VALID_JOB, VALID_TAGS, VALID_MARK_STATUS,
                        VALID_SCHEDULE, VALID_SCHEDULE_NAME, VALID_LAST_MODIFIED_DATE_TIME, VALID_NOTE);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_NAME, VALID_PHONE, null,
                VALID_COMPANY, VALID_JOB, VALID_TAGS, VALID_MARK_STATUS,
                VALID_SCHEDULE, VALID_SCHEDULE_NAME, VALID_LAST_MODIFIED_DATE_TIME, VALID_NOTE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidCompany_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE,
                        VALID_EMAIL, INVALID_COMPANY, VALID_JOB, VALID_TAGS, VALID_MARK_STATUS,
                        VALID_SCHEDULE, VALID_SCHEDULE_NAME, VALID_LAST_MODIFIED_DATE_TIME, VALID_NOTE);
        String expectedMessage = Company.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullCompany_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_NAME, VALID_PHONE, VALID_EMAIL, null, VALID_JOB, VALID_TAGS, VALID_MARK_STATUS,
                VALID_SCHEDULE, VALID_SCHEDULE_NAME, VALID_LAST_MODIFIED_DATE_TIME, VALID_NOTE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Company.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidJob_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE,
                        VALID_EMAIL, VALID_COMPANY, INVALID_JOB, VALID_TAGS, VALID_MARK_STATUS,
                        VALID_SCHEDULE, VALID_SCHEDULE_NAME, VALID_LAST_MODIFIED_DATE_TIME, VALID_NOTE);
        String expectedMessage = Job.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullJob_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_COMPANY, null, VALID_TAGS, VALID_MARK_STATUS,
                VALID_SCHEDULE, VALID_SCHEDULE_NAME, VALID_LAST_MODIFIED_DATE_TIME, VALID_NOTE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Job.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullSchedule_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_COMPANY, VALID_JOB, VALID_TAGS, VALID_MARK_STATUS,
                null, VALID_SCHEDULE_NAME, VALID_LAST_MODIFIED_DATE_TIME, VALID_NOTE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Schedule.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidSchedule_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE,
                        VALID_EMAIL, VALID_COMPANY, VALID_JOB, VALID_TAGS, VALID_MARK_STATUS,
                        INVALID_SCHEDULE, VALID_SCHEDULE_NAME, VALID_LAST_MODIFIED_DATE_TIME, VALID_NOTE);
        String expectedMessage = Schedule.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullScheduleName_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_COMPANY, VALID_JOB, VALID_TAGS, VALID_MARK_STATUS,
                VALID_SCHEDULE, null, VALID_LAST_MODIFIED_DATE_TIME, VALID_NOTE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, ScheduleName.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidScheduleName_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE,
                        VALID_EMAIL, VALID_COMPANY, VALID_JOB, VALID_TAGS, VALID_MARK_STATUS,
                        VALID_SCHEDULE, INVALID_SCHEDULE_NAME, VALID_LAST_MODIFIED_DATE_TIME, VALID_NOTE);
        String expectedMessage = ScheduleName.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE,
                        VALID_EMAIL, VALID_COMPANY, VALID_JOB, invalidTags, VALID_MARK_STATUS,
                        VALID_SCHEDULE, VALID_SCHEDULE_NAME, VALID_LAST_MODIFIED_DATE_TIME, VALID_NOTE);
        assertThrows(IllegalValueException.class, person::toModelType);
    }

    @Test
    public void toModelType_nullLastModifiedDateTime_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_COMPANY, VALID_JOB, VALID_TAGS,
                VALID_MARK_STATUS, VALID_SCHEDULE, VALID_SCHEDULE_NAME, null, VALID_NOTE);
        String expectedMessage = String.format(
                MISSING_FIELD_MESSAGE_FORMAT, LastModifiedDateTime.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidLastModifiedDateTime_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE,
                        VALID_EMAIL, VALID_COMPANY, VALID_JOB, VALID_TAGS, VALID_MARK_STATUS,
                        VALID_SCHEDULE, VALID_SCHEDULE_NAME, INVALID_LAST_MODIFIED_DATE_TIME, VALID_NOTE);
        assertThrows(IllegalValueException.class, person::toModelType);
    }

    @Test
    public void toModelType_nullMark_throwsIllegalValueException() throws Exception {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE,
                        VALID_EMAIL, VALID_COMPANY, VALID_JOB, VALID_TAGS, null,
                        VALID_SCHEDULE, VALID_SCHEDULE_NAME, VALID_LAST_MODIFIED_DATE_TIME, VALID_NOTE);
        assertThrows(IllegalValueException.class, person::toModelType);
    }

    @Test
    public void toModelType_markedStatus_returnsMarkedPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_COMPANY, VALID_JOB, VALID_TAGS, "\u2605",
                VALID_SCHEDULE, VALID_SCHEDULE_NAME, VALID_LAST_MODIFIED_DATE_TIME, VALID_NOTE);

        Person modelPerson = person.toModelType();
        assertFalse(modelPerson.getMarkStatus().equals(true));
    }
    @Test
    public void toModelType_unmarkedStatus_returnsUnmarkedPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_COMPANY, VALID_JOB, VALID_TAGS, "\u2606",
                VALID_SCHEDULE, VALID_SCHEDULE_NAME, VALID_LAST_MODIFIED_DATE_TIME, VALID_NOTE);
        Person modelPerson = person.toModelType();
        assertFalse(modelPerson.getMarkStatus().equals(false));
    }

    @Test
    public void toModelType_nullNote_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_COMPANY, VALID_JOB, VALID_TAGS,
                VALID_MARK_STATUS, VALID_SCHEDULE, VALID_SCHEDULE_NAME,
                VALID_LAST_MODIFIED_DATE_TIME, null);
        String expectedMessage = String.format(
                MISSING_FIELD_MESSAGE_FORMAT, Note.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidNote_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE,
                        VALID_EMAIL, VALID_COMPANY, VALID_JOB, VALID_TAGS,
                        VALID_MARK_STATUS, VALID_SCHEDULE, VALID_SCHEDULE_NAME,
                        VALID_LAST_MODIFIED_DATE_TIME, INVALID_NOTE);
        assertThrows(IllegalValueException.class, person::toModelType);
    }

    @Test
    public void toModelType_invalidNoteLength_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE,
                        VALID_EMAIL, VALID_COMPANY, VALID_JOB, VALID_TAGS,
                        VALID_MARK_STATUS, VALID_SCHEDULE, VALID_SCHEDULE_NAME,
                        VALID_LAST_MODIFIED_DATE_TIME, INVALID_NOTE_CHARACTER_LIMIT);
        assertThrows(IllegalValueException.class, person::toModelType);
    }

}

