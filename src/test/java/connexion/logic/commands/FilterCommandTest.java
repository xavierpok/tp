package connexion.logic.commands;

import static connexion.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static connexion.logic.commands.CommandTestUtil.assertCommandSuccess;
import static connexion.testutil.TypicalPersons.ALICE;
import static connexion.testutil.TypicalPersons.BENSON;
import static connexion.testutil.TypicalPersons.CARL;
import static connexion.testutil.TypicalPersons.DANIEL;
import static connexion.testutil.TypicalPersons.ELLE;
import static connexion.testutil.TypicalPersons.FIONA;
import static connexion.testutil.TypicalPersons.GEORGE;
import static connexion.testutil.TypicalPersons.getTypicalAddressBook;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import connexion.model.Model;
import connexion.model.ModelManager;
import connexion.model.UserPrefs;
import connexion.model.person.CompanyContainsKeywordsPredicate;
import connexion.model.person.EmailContainsKeywordsPredicate;
import connexion.model.person.IsMarkedPredicate;
import connexion.model.person.JobContainsKeywordsPredicate;
import connexion.model.person.NameContainsKeywordsPredicate;
import connexion.model.person.NotMarkedPredicate;
import connexion.model.person.PhoneContainsKeywordsPredicate;
import connexion.model.tag.TagContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FilterCommand}.
 */
public class FilterCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        NameContainsKeywordsPredicate firstPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        NameContainsKeywordsPredicate secondPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("second"));

        FilterCommand filterFirstCommand = new FilterCommand(firstPredicate);
        FilterCommand filterSecondCommand = new FilterCommand(secondPredicate);

        // same object -> returns true
        assertTrue(filterFirstCommand.equals(filterFirstCommand));

        // same values -> returns true
        FilterCommand filterFirstCommandCopy = new FilterCommand(firstPredicate);
        assertTrue(filterFirstCommand.equals(filterFirstCommandCopy));

        // different types -> returns false
        assertFalse(filterFirstCommand.equals(1));

        // null -> returns false
        assertFalse(filterFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(filterFirstCommand.equals(filterSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        NameContainsKeywordsPredicate namePredicate = prepareNamePredicate(" ");
        PhoneContainsKeywordsPredicate phonePredicate = preparePhonePredicate(" ");
        EmailContainsKeywordsPredicate emailPredicate = prepareEmailPredicate(" ");
        CompanyContainsKeywordsPredicate companyPredicate = prepareCompanyPredicate(" ");
        JobContainsKeywordsPredicate jobPredicate = prepareJobPredicate(" ");
        TagContainsKeywordsPredicate tagPredicate = prepareTagPredicate(" ");

        // Check for name
        FilterCommand command = new FilterCommand(namePredicate);
        expectedModel.updateFilteredPersonList(namePredicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());

        // Check for phone
        command = new FilterCommand(phonePredicate);
        expectedModel.updateFilteredPersonList(phonePredicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());

        // Check for email
        command = new FilterCommand(emailPredicate);
        expectedModel.updateFilteredPersonList(emailPredicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());

        // Check for company
        command = new FilterCommand(companyPredicate);
        expectedModel.updateFilteredPersonList(companyPredicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());

        // Check for job
        command = new FilterCommand(jobPredicate);
        expectedModel.updateFilteredPersonList(jobPredicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());

        // Check for tag
        command = new FilterCommand(tagPredicate);
        expectedModel.updateFilteredPersonList(tagPredicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        NameContainsKeywordsPredicate namePredicate =
                prepareNamePredicate("Kurz Elle Kunz");
        CompanyContainsKeywordsPredicate companyPredicate =
                prepareCompanyPredicate("Grab Provident Citadel");
        JobContainsKeywordsPredicate jobPredicate =
                prepareJobPredicate("AI");
        TagContainsKeywordsPredicate tagPredicate =
                prepareTagPredicate("friends");
        PhoneContainsKeywordsPredicate phonePredicate =
                preparePhonePredicate("95352563 9482224 9482427");
        EmailContainsKeywordsPredicate emailPredicate =
                prepareEmailPredicate("heinz@example.com werner@example.com lydia@example.com");

        // Check for name
        FilterCommand command = new FilterCommand(namePredicate);
        expectedModel.updateFilteredPersonList(namePredicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredPersonList());

        // Check for company
        command = new FilterCommand(companyPredicate);
        expectedModel.updateFilteredPersonList(namePredicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredPersonList());

        // Check for job
        command = new FilterCommand(jobPredicate);
        expectedModel.updateFilteredPersonList(jobPredicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, CARL, FIONA), model.getFilteredPersonList());

        // Check for tag
        command = new FilterCommand(tagPredicate);
        expectedModel.updateFilteredPersonList(tagPredicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, DANIEL), model.getFilteredPersonList());

        // Check for phone
        command = new FilterCommand(phonePredicate);
        expectedModel.updateFilteredPersonList(phonePredicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredPersonList());

        // Check for email
        command = new FilterCommand(emailPredicate);
        expectedModel.updateFilteredPersonList(emailPredicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredPersonList());
    }

    @Test
    public void execute_markedUnmarked_personsFound() {
        // Inside expectedModel, 3 persons are marked, 4 persons are not marked
        // BENSON, CARL, GEORGE are marked, others are not marked
        String expectedMessageMarked = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        String expectedMessageUnmarked = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 4);

        IsMarkedPredicate markedPredicate = new IsMarkedPredicate();
        FilterCommand command = new FilterCommand(markedPredicate);
        expectedModel.updateFilteredPersonList(markedPredicate);
        assertCommandSuccess(command, model, expectedMessageMarked, expectedModel);
        assertEquals(Arrays.asList(BENSON, CARL, GEORGE), model.getFilteredPersonList());

        NotMarkedPredicate notMarkedPredicate = new NotMarkedPredicate();
        command = new FilterCommand(notMarkedPredicate);
        expectedModel.updateFilteredPersonList(notMarkedPredicate);
        assertCommandSuccess(command, model, expectedMessageUnmarked, expectedModel);
        assertEquals(Arrays.asList(ALICE, DANIEL, ELLE, FIONA), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        NameContainsKeywordsPredicate namePredicate =
                new NameContainsKeywordsPredicate(Arrays.asList("keyword"));
        PhoneContainsKeywordsPredicate phonePredicate =
                new PhoneContainsKeywordsPredicate(Arrays.asList("keyword"));
        EmailContainsKeywordsPredicate emailPredicate =
                new EmailContainsKeywordsPredicate(Arrays.asList("keyword"));
        CompanyContainsKeywordsPredicate companyPredicate =
                new CompanyContainsKeywordsPredicate(Arrays.asList("keyword"));
        JobContainsKeywordsPredicate jobPredicate =
                new JobContainsKeywordsPredicate(Arrays.asList("keyword"));
        TagContainsKeywordsPredicate tagPredicate =
                new TagContainsKeywordsPredicate(Arrays.asList("keyword"));
        IsMarkedPredicate markedPredicate =
                new IsMarkedPredicate();
        NotMarkedPredicate notMarkedPredicate =
                new NotMarkedPredicate();

        // Check for name
        FilterCommand filterCommand = new FilterCommand(namePredicate);
        String expected = FilterCommand.class.getCanonicalName() + "{predicate=" + namePredicate + "}";
        assertEquals(expected, filterCommand.toString());

        // Check for phone
        filterCommand = new FilterCommand(phonePredicate);
        expected = FilterCommand.class.getCanonicalName() + "{predicate=" + phonePredicate + "}";
        assertEquals(expected, filterCommand.toString());

        // Check for email
        filterCommand = new FilterCommand(emailPredicate);
        expected = FilterCommand.class.getCanonicalName() + "{predicate=" + emailPredicate + "}";
        assertEquals(expected, filterCommand.toString());

        // Check for company
        filterCommand = new FilterCommand(companyPredicate);
        expected = FilterCommand.class.getCanonicalName() + "{predicate=" + companyPredicate + "}";
        assertEquals(expected, filterCommand.toString());

        // Check for job
        filterCommand = new FilterCommand(jobPredicate);
        expected = FilterCommand.class.getCanonicalName() + "{predicate=" + jobPredicate + "}";
        assertEquals(expected, filterCommand.toString());

        // Check for tag
        filterCommand = new FilterCommand(tagPredicate);
        expected = FilterCommand.class.getCanonicalName() + "{predicate=" + tagPredicate + "}";
        assertEquals(expected, filterCommand.toString());

        // Check for mark
        filterCommand = new FilterCommand(markedPredicate);
        expected = FilterCommand.class.getCanonicalName() + "{predicate=" + markedPredicate + "}";
        assertEquals(expected, filterCommand.toString());

        // Check for unmark
        filterCommand = new FilterCommand(notMarkedPredicate);
        expected = FilterCommand.class.getCanonicalName() + "{predicate=" + notMarkedPredicate + "}";
        assertEquals(expected, filterCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameContainsKeywordsPredicate prepareNamePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Parses {@code userInput} into a {@code PhoneContainsKeywordsPredicate}.
     */
    private PhoneContainsKeywordsPredicate preparePhonePredicate(String userInput) {
        return new PhoneContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Parses {@code userInput} into a {@code EmailContainsKeywordsPredicate}.
     */
    private EmailContainsKeywordsPredicate prepareEmailPredicate(String userInput) {
        return new EmailContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Parses {@code userInput} into a {@code CompanyContainsKeywordsPredicate}.
     */
    private CompanyContainsKeywordsPredicate prepareCompanyPredicate(String userInput) {
        return new CompanyContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Parses {@code userInput} into a {@code JobContainsKeywordsPredicate}.
     */
    private JobContainsKeywordsPredicate prepareJobPredicate(String userInput) {
        return new JobContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Parses {@code userInput} into a {@code TagContainsKeywordsPredicate}.
     */
    private TagContainsKeywordsPredicate prepareTagPredicate(String userInput) {
        return new TagContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Creates a {@code IsMarkedPredicate}.
     */
    private IsMarkedPredicate prepareMarkPredicate() {
        return new IsMarkedPredicate();
    }

    /**
     * Creates a {@code NotMarkedPredicate}.
     */
    private NotMarkedPredicate prepareUnMarkPredicate() {
        return new NotMarkedPredicate();
    }
}
