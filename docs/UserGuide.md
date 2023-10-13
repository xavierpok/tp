---
layout: page
title: User Guide
---

**C**onnexion is a desktop application for undergraduate students to manage their networking connections in the Tech industry.

<!-- TOC -->
  * [Quick start](#quick-start)
  * [Features](#features)
    * [Viewing help : `help`](#viewing-help--help)
    * [Adding a new contact : `add`](#adding-a-new-contact--add)
    * [Listing all contacts : `list`](#listing-all-contacts--list)
    * [Editing existing contact details : `edit`](#editing-existing-contact-details--edit)
    * [Locating persons by name : `find`](#locating-persons-by-name--find)
    * [Marking contacts of interest : `mark`](#marking-contacts-of-interest--mark)
    * [Un-marking contacts of interest : `unmark`](#un-marking-contacts-of-interest--unmark)
    * [Filtering a contact by tags : `filter`](#filtering-a-contact-by-tags--filter)
    * [Deleting a contact : `delete`](#deleting-a-contact--delete)
    * [Clearing all entries : `clear`](#clearing-all-entries--clear)
    * [Exiting the program : `exit`](#exiting-the-program--exit)
  * [Known issues](#known-issues)
  * [Command summary](#command-summary)
  * [Command FIELD Summary](#command-field-summary)
<!-- TOC -->

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `11` or above installed in your Computer.

2. Download the latest `.jar` and run as shown from [here](https://docs.oracle.com/javase/tutorial/deployment/jar/run.html).

3. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g. `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `...​` after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]...​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</div>

### Viewing help : `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`

### Adding a new contact : `add`

Creates a new contact and adds it to the app.

Format: ```add n/NAME p/PHONE_NUMBER e/EMAIL c/COMPANY j/JOB [t/TAG]...```

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
A person can have any number of tags (including 0)
</div>

Examples:
* `add n/John Wick p/12345678 e/johnwick@gmail.com c/Google j/Software Engineer t/NUS Alumni t/Met in Google Hackathon`
* `add n/Aiken Duit p/88888888 e/aikenduit@hotmail.com c/Meta j/Data Engineer`

### Listing all contacts : `list`

Gives the list of all contacts in alphabetical order (by name).

Format: `list`

### Editing existing contact details : `edit`

Edits an existing person's contact details via index.

Format: `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [c/COMPANY] [j/JOB] [t/TAG]...​`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** starting from 1.
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the person will be removed i.e. adding of tags is not cumulative.
* You can remove all the person’s tags by typing `t/` without
  specifying any tags after it.

Examples:
*  `edit 1 n/John Sick p/87654321 t/` edits the 1st person’s name, phone number and clears the tags in the current displayed list.
*  `edit 2 n/Betsy Crower t/` edits the name of the 2nd person to be `Betsy Crower` and clears all existing tags.

Expected output:

`edit 1 n/John Sick p/87654321 t/` would display:
```
Contact edited: John Sick, a Software Engineer from Google 
Changes : 
Name : John Wick >>> John Sick
Phone Number : 12345678 >>>  87654321
Tags : #NUS Alumni #Met in Google Hackathon >>> 
```
Note that as the command cleared the tags, the changes displayed reflect that the tags are now empty. 
I.e., the empty end-result of Tags is expected.

### Locating persons by name : `find`

Finds persons via keywords in name of contact.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g. `hans` will match `Hans`.
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`.
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`.
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`.

Examples:
* `find John` returns John and john tan, but not Joh tan.
* `find alex Young` returns Alex Walker, Alex Young, Young Walker.

Expected output:

It will shorten the list to only include relevant contacts.
For example, if the list contains John Wick and John Tan, when `find John` is entered, the UI will print :
```2 persons listed!```

### Marking contacts of interest : `mark`

Marks a contact of interest.

Format: `mark INDEX`

* Marks the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed contact list.
* The index **must be a positive integer** starting from 1.
* When a new contact is created, the contact is unmarked by default.

Examples:
* `list` followed by `mark 2` marks the 2nd person in the address book as contact of interest.

### Un-marking contacts of interest : `unmark`

Un-marks a contact of interest.

Format: `unmark INDEX`

* Marks the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed contact list.
* The index **must be a positive integer** starting from 1.
* When a new contact is created, the contact is unmarked by default.

Examples:
* `unmark 1` un-marks the 1st person in the current displayed list.

### Filtering a contact by tags : `filter`

Displays all entries filtered by a specified tag.

Format: `filter FIELD/KEYWORD [MORE_KEYWORDS]`

* FIELD: represents the tag to filter by.
    * Example: if filter by company, then FIELD = “c”.
* The search is case-insensitive. e.g. `google` will match `Google`.
* The order of the keywords does not matter. e.g. `Google Meta` will match `Meta Google`.
* Only returns results with FULL matching keywords to the field.
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`.

Examples:
* `filter c/Google` returns all entries with company fields “Google”, “google” “Google Inc.”.
* `filter t/friends` returns all entries with the tag “friends”.


### Deleting a contact : `delete`

Deletes a person's contact via index.

Format: `delete INDEX`

* Deletes the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed contact list.
* The index **must be a positive integer** starting from 1.

Examples:
* `list` followed by `delete 2` deletes the 2nd person in the contact list.
* `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.

### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. When using multiple screens, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the preferences.json file created by the application before running the application again.

--------------------------------------------------------------------------------------------------------------------

## Command summary

| Action     | Format, Examples                                                                                                                                                                             |
|------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Add**    | `add n/NAME p/PHONE_NUMBER e/EMAIL c/COMPANY j/JOB [t/TAG]...​`<br>e.g., `add n/John Wick p/12345678 e/johnwick@gmail.com c/Google j/Software Engineer t/NUS Alumni t/Met in Google Hackathon` |
| **Clear**  | `clear`                                                                                                                                                                                      |
| **Delete** | `delete INDEX`<br> e.g., `delete 2`                                                                                                                                                          |
| **Edit**   | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`<br> e.g.,`edit 1 n/John Sick p/87654321 t/`                                                                           |
| **Filter** | `filter FIELD KEYWORD [MORE_KEYWORDS]` <br> e.g., `filter c/Google`                                                                                                                          |
| **Find**   | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find alex Young`                                                                                                                                   |
| **List**   | `list`                                                                                                                                                                                       |
| **Help**   | `help`                                                                                                                                                                                       |
| **Mark**   | `mark INDEX` <br> e.g., `mark 2`                                                                                                                                                             |
| **Unmark** | `unmark INDEX` <br> e.g., `unmark 1`                                                                                                                                                         |

--------------------------------------------------------------------------------------------------------------------

## Command FIELD Summary

| Tag | Description  | Notes                                                |
|-----|--------------|------------------------------------------------------|
| c/  | COMPANY      | -                                                    |
| e/  | EMAIL        | -                                                    |
| j/  | JOB          | -                                                    |
| n/  | NAME         | -                                                    |
| p/  | PHONE_NUMBER | -                                                    |
| t/  | TAG          | Multiple instances of this argument can be accepted. |

