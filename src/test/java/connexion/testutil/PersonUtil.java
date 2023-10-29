package connexion.testutil;

import static connexion.logic.parser.CliSyntax.PREFIX_COMPANY;
import static connexion.logic.parser.CliSyntax.PREFIX_EMAIL;
import static connexion.logic.parser.CliSyntax.PREFIX_JOB;
import static connexion.logic.parser.CliSyntax.PREFIX_NAME;
import static connexion.logic.parser.CliSyntax.PREFIX_PHONE;
import static connexion.logic.parser.CliSyntax.PREFIX_SCHEDULE;
import static connexion.logic.parser.CliSyntax.PREFIX_SCHEDULE_NAME;
import static connexion.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import connexion.logic.commands.AddCommand;
import connexion.logic.commands.EditCommand.EditPersonDescriptor;
import connexion.model.person.Person;
import connexion.model.tag.Tag;

/**
 * A utility class for Person.
 */
public class PersonUtil {

    /**
     * Returns an add command string for adding the {@code person}.
     */
    public static String getAddCommand(Person person) {
        return AddCommand.COMMAND_WORD + " " + getPersonDetails(person);
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getPersonDetails(Person person) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + person.getName().getValue() + " ");
        sb.append(PREFIX_PHONE + person.getPhone().getValue() + " ");
        sb.append(PREFIX_EMAIL + person.getEmail().getValue() + " ");
        sb.append(PREFIX_COMPANY + person.getCompany().getValue() + " ");
        sb.append(PREFIX_JOB + person.getJob().getValue() + " ");
        if (person.getSchedule().isPresent()) {
            sb.append(PREFIX_SCHEDULE + person.getSchedule().get().toString() + " ");
        }
        if (person.getScheduleName().isPresent()) {
            sb.append(PREFIX_SCHEDULE_NAME + person.getScheduleName().get().toString() + " ");
        }
        person.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.getValue() + " ")
        // here, s is the tag. This code appends each tag w/ prefixes in to toString
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditPersonDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.getValue()).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.getValue()).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.getValue()).append(" "));
        descriptor.getCompany().ifPresent(company -> sb.append(PREFIX_COMPANY).append(company.getValue()).append(" "));
        descriptor.getJob().ifPresent(job -> sb.append(PREFIX_JOB).append(job.getValue()).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.getValue()).append(" "));
            }
        }
        return sb.toString();
    }
}
