package seedu.address.storage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Company;
import seedu.address.model.person.Email;
import seedu.address.model.person.Job;
import seedu.address.model.person.LastModifiedDateTime;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String name;
    private final String phone;
    private final String email;
    private final String company;
    private final String job;
    private final String lastModifiedDateTime;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
            @JsonProperty("email") String email, @JsonProperty("company") String company,
            @JsonProperty("job") String job, @JsonProperty("tags") List<JsonAdaptedTag> tags)
            @JsonProperty("lastModifiedDateTime") String lastModifiedDateTime ) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.company = company;
        this.job = job;
        if (tags != null) {
            this.tags.addAll(tags);
        }

        this.lastModifiedDateTime = lastModifiedDateTime;

    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        company = source.getCompany().value;
        job = source.getJob().value;
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        lastModifiedDateTime = source.getLastModifiedDateTime().toString();
        // this has to be toString because it's a non-string
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            personTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (company == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Company.class.getSimpleName()));
        }
        if (!Company.isValidCompany(company)) {
            throw new IllegalValueException(Company.MESSAGE_CONSTRAINTS);
        }
        final Company modelCompany = new Company(company);

        if (job == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Job.class.getSimpleName()));
        }
        if (!Job.isValidJob(job)) {
            throw new IllegalValueException(Job.MESSAGE_CONSTRAINTS);
        }
        final Job modelJob = new Job(job);

        final Set<Tag> modelTags = new HashSet<>(personTags);


        if (lastModifiedDateTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    LastModifiedDateTime.class.getSimpleName()));
        }
        DateTimeFormatter correctFormatter = DateTimeFormatter.
                ofLocalizedDateTime(FormatStyle.MEDIUM);
        //java API does not expose ways to check beyond try-catch

        LocalDateTime lastModified = null;
        // Not a fan of doing this but the try-catch is needed.
        try {
            lastModified = LocalDateTime.parse(
                    lastModifiedDateTime,correctFormatter);
        } catch (DateTimeParseException e) {
            throw new IllegalValueException(LastModifiedDateTime.MESSAGE_CONSTRAINTS);
        }

        final LastModifiedDateTime modelLastModifiedDateTime =
                new LastModifiedDateTime(lastModified);

        return new Person(modelName, modelPhone, modelEmail, modelCompany, modelJob, modelTags, modelLastModifiedDateTime);
    }

}
