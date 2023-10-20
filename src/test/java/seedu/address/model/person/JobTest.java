package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class JobTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Job(null));
    }

    @Test
    public void constructor_invalidJob_throwsIllegalArgumentException() {
        String invalidJob = "";
        assertThrows(IllegalArgumentException.class, () -> new Job(invalidJob));
    }

    @Test
    public void isValidJob() {
        // null job
        assertThrows(NullPointerException.class, () -> Job.isValidJob(null));

        // invalid jobs
        assertFalse(Job.isValidJob("")); // empty string
        assertFalse(Job.isValidJob(" ")); // spaces only

        // valid jobs
        assertTrue(Job.isValidJob("Software Engineer"));
        assertTrue(Job.isValidJob("-")); // one character
        assertTrue(Job.isValidJob("Optimizing Machine Learning Workflow for Internet Economy Executive")); // long job
    }

    @Test
    public void equals() {
        Job job = new Job("Valid Job");

        // same values -> returns true
        assertTrue(job.equals(new Job("Valid Job")));

        // same object -> returns true
        assertTrue(job.equals(job));

        // null -> returns false
        assertFalse(job.equals(null));

        // different types -> returns false
        assertFalse(job.equals(5.0f));

        // different values -> returns false
        assertFalse(job.equals(new Job("Other Valid Job")));
    }
}
