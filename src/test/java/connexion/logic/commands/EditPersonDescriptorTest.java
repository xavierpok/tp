package connexion.logic.commands;

import static connexion.logic.commands.CommandTestUtil.DESC_AMY;
import static connexion.logic.commands.CommandTestUtil.DESC_BOB;
import static connexion.logic.commands.CommandTestUtil.VALID_COMPANY_BOB;
import static connexion.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static connexion.logic.commands.CommandTestUtil.VALID_JOB_BOB;
import static connexion.logic.commands.CommandTestUtil.VALID_LAST_MODIFIED_BOB;
import static connexion.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static connexion.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static connexion.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import connexion.logic.commands.EditCommand.EditPersonDescriptor;
import connexion.testutil.EditPersonDescriptorBuilder;

public class EditPersonDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditPersonDescriptor descriptorWithSameValues = new EditPersonDescriptor(DESC_AMY);
        assertTrue(DESC_AMY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_AMY.equals(DESC_AMY));

        // null -> returns false
        assertFalse(DESC_AMY.equals(null));

        // different types -> returns false
        assertFalse(DESC_AMY.equals(5));

        // different values -> returns false
        assertFalse(DESC_AMY.equals(DESC_BOB));

        // different name -> returns false
        EditPersonDescriptor editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withName(VALID_NAME_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different phone -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withPhone(VALID_PHONE_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different email -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different company -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withCompany(VALID_COMPANY_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different job -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withJob(VALID_JOB_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different time -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withLastModifiedDateTime(VALID_LAST_MODIFIED_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));
    }

    @Test
    public void toStringMethod() {
        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        String expected = EditPersonDescriptor.class.getCanonicalName() + "{name="
                + editPersonDescriptor.getName().orElse(null) + ", phone="
                + editPersonDescriptor.getPhone().orElse(null) + ", email="
                + editPersonDescriptor.getEmail().orElse(null) + ", company="
                + editPersonDescriptor.getCompany().orElse(null) + ", job="
                + editPersonDescriptor.getCompany().orElse(null) + ", mark="
                + editPersonDescriptor.getJob().orElse(null) + ", tags="
                + editPersonDescriptor.getTags().orElse(null) + ", last_modified="
                + editPersonDescriptor.getLastModifiedDateTime().orElse(null) + "}";
        assertEquals(expected, editPersonDescriptor.toString());
    }
}
