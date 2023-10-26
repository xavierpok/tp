package connexion.logic.commands;

import static connexion.logic.commands.CommandTestUtil.SCHEDULE_AMY;
import static connexion.logic.commands.CommandTestUtil.SCHEDULE_BOB;
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

import connexion.logic.commands.ScheduleCommand.ScheduleDescriptor;
import connexion.testutil.ScheduleDescriptorBuilder;

public class ScheduleDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        ScheduleDescriptor descriptorWithSameValues = new ScheduleDescriptor(SCHEDULE_AMY);
        assertTrue(SCHEDULE_AMY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(SCHEDULE_AMY.equals(SCHEDULE_AMY));

        // null -> returns false
        assertFalse(SCHEDULE_AMY.equals(null));

        // different types -> returns false
        assertFalse(SCHEDULE_AMY.equals(5));

        // different values -> returns false
        assertFalse(SCHEDULE_AMY.equals(SCHEDULE_BOB));


        // different time -> returns false
        ScheduleDescriptor editedAmy = new ScheduleDescriptorBuilder(SCHEDULE_AMY)
                .withLastModifiedDateTime(VALID_LAST_MODIFIED_BOB).build();
        assertFalse(SCHEDULE_AMY.equals(editedAmy));
    }

    @Test
    public void toStringMethod() {
        ScheduleDescriptor editPersonDescriptor = new ScheduleDescriptor();
        String expected = ScheduleDescriptor.class.getCanonicalName() + "{name="
                + editPersonDescriptor.getName().orElse(null) + ", phone="
                + editPersonDescriptor.getPhone().orElse(null) + ", email="
                + editPersonDescriptor.getEmail().orElse(null) + ", company="
                + editPersonDescriptor.getCompany().orElse(null) + ", job="
                + editPersonDescriptor.getJob().orElse(null) + ", tags="
                + editPersonDescriptor.getTags().orElse(null) + ", last_modified="
                + editPersonDescriptor.getLastModifiedDateTime().orElse(null) + "}";
        assertEquals(expected, editPersonDescriptor.toString());
    }
}
