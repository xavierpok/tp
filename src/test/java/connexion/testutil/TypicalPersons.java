package connexion.testutil;

import static connexion.logic.commands.CommandTestUtil.VALID_COMPANY_AMY;
import static connexion.logic.commands.CommandTestUtil.VALID_COMPANY_BOB;
import static connexion.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static connexion.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static connexion.logic.commands.CommandTestUtil.VALID_JOB_AMY;
import static connexion.logic.commands.CommandTestUtil.VALID_JOB_BOB;
import static connexion.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static connexion.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static connexion.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static connexion.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static connexion.logic.commands.CommandTestUtil.VALID_SCHEDULE_AMY;
import static connexion.logic.commands.CommandTestUtil.VALID_SCHEDULE_BOB;
import static connexion.logic.commands.CommandTestUtil.VALID_SCHEDULE_NAME_AMY;
import static connexion.logic.commands.CommandTestUtil.VALID_SCHEDULE_NAME_BOB;
import static connexion.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static connexion.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static connexion.testutil.PersonBuilder.DEFAULT_SCHEDULE;
import static connexion.testutil.PersonBuilder.DEFAULT_SCHEDULE_NAME;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import connexion.model.AddressBook;
import connexion.model.person.Person;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withCompany("Google").withJob("AI Analyst")
            .withEmail("alice@example.com")
            .withPhone("94351253")
            .withTags("friends")
            .withLastModifiedDateTime(PersonBuilder.DEFAULT_LAST_MODIFIED)
            .withSchedule(DEFAULT_SCHEDULE)
            .withScheduleName(DEFAULT_SCHEDULE_NAME)
            .build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withCompany("Mandai Wildlife Group").withJob("Software Engineer")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withTags("owesMoney", "friends")
            .withSchedule("2024-01-27-07-00")
            .withScheduleName("Teams Interview")
            .withLastModifiedDateTime(PersonBuilder.DEFAULT_LAST_MODIFIED)
            .withMark(true)
            .build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com")
            .withCompany("Grab").withJob("AI Engineer")
            .withSchedule("")
            .withScheduleName("")
            .withLastModifiedDateTime(PersonBuilder.DEFAULT_LAST_MODIFIED)
            .withMark(true)
            .build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com")
            .withCompany("Uber").withJob("Data Analyst")
            .withTags("friends")
            .withSchedule("2023-12-08-05-00")
            .withScheduleName("Morning meeting")
            .withLastModifiedDateTime(PersonBuilder.DEFAULT_LAST_MODIFIED)
            .build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com")
            .withCompany("Central Provident Board").withJob("Machine Learning Analyst")
            .withLastModifiedDateTime(PersonBuilder.DEFAULT_LAST_MODIFIED)
            .withSchedule("2023-12-13-08-00")
            .withScheduleName("Morning interview")
            .build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withCompany("Citadel")
            .withJob("AI Engineer")
            .withLastModifiedDateTime(PersonBuilder.DEFAULT_LAST_MODIFIED)
            .withSchedule("2023-12-19-05-00")
            .withScheduleName("Morning meeting")
            .build();

    // marked Person for testing mark functionalities and dependencies
    public static final Person GEORGE = new PersonBuilder().withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com").withCompany("Morgan Stanley")
            .withJob("Risk Analyst")
            .withLastModifiedDateTime(PersonBuilder.DEFAULT_LAST_MODIFIED)
            .withSchedule("2023-11-13-20-00")
            .withScheduleName("Evening seminar")
            .withMark(true)
            .build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withCompany("Google")
            .withJob("Software Developer")
            .withLastModifiedDateTime(PersonBuilder.DEFAULT_LAST_MODIFIED)
            .withSchedule("2023-12-15-05-00")
            .withScheduleName("Morning seminar")
            .build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withCompany("Google")
            .withJob("Software Developer")
            .withLastModifiedDateTime(PersonBuilder.DEFAULT_LAST_MODIFIED)
            .withSchedule("2023-12-14-15-00")
            .withScheduleName("Afternoon tea break")
            .build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withCompany(VALID_COMPANY_AMY)
            .withJob(VALID_JOB_AMY).withTags(VALID_TAG_FRIEND)
            .withLastModifiedDateTime(PersonBuilder.DEFAULT_LAST_MODIFIED)
            .withSchedule(VALID_SCHEDULE_AMY)
            .withScheduleName(VALID_SCHEDULE_NAME_AMY)
            .build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withCompany(VALID_COMPANY_BOB)
            .withJob(VALID_JOB_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .withLastModifiedDateTime(PersonBuilder.DEFAULT_LAST_MODIFIED)
            .withSchedule(VALID_SCHEDULE_BOB)
            .withScheduleName(VALID_SCHEDULE_NAME_BOB)
            .build();
    // Without schedule and schedule name
    public static final Person ANDY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withCompany(VALID_COMPANY_AMY)
            .withJob(VALID_JOB_AMY).withTags(VALID_TAG_FRIEND)
            .withLastModifiedDateTime(PersonBuilder.DEFAULT_LAST_MODIFIED)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }
        return ab;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
