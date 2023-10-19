package connexion.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import connexion.model.AddressBook;
import connexion.model.ReadOnlyAddressBook;
import connexion.model.person.Company;
import connexion.model.person.Email;
import connexion.model.person.Job;
import connexion.model.person.LastModifiedDateTime;
import connexion.model.person.Name;
import connexion.model.person.Person;
import connexion.model.person.Phone;
import connexion.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Company("Google"),
                new Job("Data Analyst"),
                getTagSet("friends"),
                    new LastModifiedDateTime(LastModifiedDateTime.DEFAULT_LAST_MODIFIED)),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                    new Company("ShopBack"),
                    new Job("Software Engineer"),
                getTagSet("colleagues", "friends"),
                    new LastModifiedDateTime(LastModifiedDateTime.DEFAULT_LAST_MODIFIED)),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                    new Company("Microsoft"),
                    new Job("Data Engineer"),
                getTagSet("neighbours"),
                    new LastModifiedDateTime(LastModifiedDateTime.DEFAULT_LAST_MODIFIED)),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                    new Company("Optiver"),
                    new Job("Quantitative trader"),
                getTagSet("family"),
                    new LastModifiedDateTime(LastModifiedDateTime.DEFAULT_LAST_MODIFIED)),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                    new Company("Proctor & Gamble"),
                    new Job("UI/UX designer"),
                getTagSet("classmates"),
                    new LastModifiedDateTime(LastModifiedDateTime.DEFAULT_LAST_MODIFIED)),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                    new Company("Food Panda"),
                    new Job("Data Analyst"),
                getTagSet("colleagues"),
                    new LastModifiedDateTime(LastModifiedDateTime.DEFAULT_LAST_MODIFIED))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
