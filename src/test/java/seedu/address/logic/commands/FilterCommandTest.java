package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.CompanyContainsKeywordsPredicate;
import seedu.address.model.person.EmailContainsKeywordsPredicate;
import seedu.address.model.person.JobContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.PhoneContainsKeywordsPredicate;
import seedu.address.model.tag.TagContainsKeywordsPredicate;

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
}
