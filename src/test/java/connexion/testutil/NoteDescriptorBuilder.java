package connexion.testutil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import connexion.logic.commands.NoteCommand.NoteDescriptor;
import connexion.model.person.LastModifiedDateTime;
import connexion.model.person.Note;

/**
 * A utility class to help with building NoteDescriptor objects.
 */
public class NoteDescriptorBuilder {
    public static final String DEFAULT_NOTE_BUILDER = "CS2103 is pain.";
    public static final LocalDateTime DEFAULT_LOCAL_DATE_TIME = LocalDateTime.parse("2023-12-27-12-14",
            DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm"));

    private NoteDescriptor descriptor;

    /**
     * Initializes the NoteDescriptorBuilder with default note.
     */
    public NoteDescriptorBuilder() {
        descriptor = new NoteDescriptor(new Note(DEFAULT_NOTE_BUILDER),
                new LastModifiedDateTime(DEFAULT_LOCAL_DATE_TIME));
    }

    /**
     * Initializes the NoteDescriptorBuilder with the data of {@code noteDescriptor}.
     */
    public NoteDescriptorBuilder(NoteDescriptor noteDescriptor) {
        this.descriptor = new NoteDescriptor(noteDescriptor);
    }

    /**
     * Sets the {@code Note} of the {@code NoteDescriptor} that we are building.
     */
    public NoteDescriptorBuilder withNote(String note) {
        descriptor.setNote(new Note(note));
        return this;
    }

    /**
     * Sets the {@code LastModifiedDateTime} of the {@code NoteDescriptor} that we are building.
     */
    public NoteDescriptorBuilder withLastModifiedDateTime(LocalDateTime lastModifiedDateTime) {
        descriptor.setLastModifiedDateTime(new LastModifiedDateTime(lastModifiedDateTime));
        return this;
    }

    public NoteDescriptor build() {
        return descriptor;
    }

}
