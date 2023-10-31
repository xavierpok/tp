package connexion.logic.commands;

import static connexion.testutil.ClockUtil.DEFAULT_TEST_TIME;
import static connexion.testutil.ClockUtil.OTHER_TEST_TIME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import connexion.model.person.LastModifiedDateTime;

public class ClearScheduleDescriptorTest {

    private static final ClearScheduleCommand.ClearedScheduleDescriptor TEST_DESCRIPTOR =
            new ClearScheduleCommand.ClearedScheduleDescriptor(new LastModifiedDateTime(DEFAULT_TEST_TIME));
    @Test
    public void equals() {
        // same values -> returns true
        ClearScheduleCommand.ClearedScheduleDescriptor descriptorWithSameValues =
                new ClearScheduleCommand.ClearedScheduleDescriptor(new LastModifiedDateTime(DEFAULT_TEST_TIME));
        ClearScheduleCommand.ClearedScheduleDescriptor otherDescriptor =
                new ClearScheduleCommand.ClearedScheduleDescriptor(new LastModifiedDateTime(OTHER_TEST_TIME));
        assertTrue(TEST_DESCRIPTOR.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(TEST_DESCRIPTOR.equals(TEST_DESCRIPTOR));

        // null -> returns false
        assertFalse(TEST_DESCRIPTOR.equals(null));

        // different types -> returns false
        assertFalse(TEST_DESCRIPTOR.equals(5));

        // different values -> returns false
        assertFalse(TEST_DESCRIPTOR.equals(otherDescriptor));

        // different LastModifiedDateTime -> returns false
        assertFalse(TEST_DESCRIPTOR.equals(
                new ClearScheduleCommand.ClearedScheduleDescriptor(new LastModifiedDateTime(OTHER_TEST_TIME))));

    }

    @Test
    public void toStringMethod() {

        String expected = ClearScheduleCommand.ClearedScheduleDescriptor.class.getCanonicalName()
                + "{lastModified=" + new LastModifiedDateTime(DEFAULT_TEST_TIME) + "}";
        assertEquals(expected, TEST_DESCRIPTOR.toString());
    }
}
