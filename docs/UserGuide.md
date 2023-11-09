---
layout: page
title: User Guide
---

**Connexion** is a desktop application for undergraduate students to manage their networking connections in the Tech industry.

<!-- TOC -->
  * [Quick start](#quick-start)
  * [Features](#features)
    * [Viewing help : `help`](#viewing-help--help)
    * [Adding a new contact : `add`](#adding-a-new-contact--add)
    * [Listing all contacts : `list`](#listing-all-contacts--list)
    * [Editing existing contact details : `edit`](#editing-existing-contact-details--edit)
    * [Marking contacts of interest : `mark`](#marking-contacts-of-interest--mark)
    * [Un-marking contacts of interest : `unmark`](#un-marking-contacts-of-interest--unmark)
    * [Filtering a contact via a specified field : `filter`](#filtering-a-contact-via-a-specified-field--filter)
    * [Scheduling a meeting with a specific contact : `schedule`](#scheduling-a-meeting-with-a-specific-contact--schedule)
    * [Viewing details of a specific contact : `detail`](#viewing-details-of-a-specific-contact--detail)
    * [Clearing a scheduled meeting with a specific contact : `clearschedule`](#clearing-a-scheduled-meeting-with-a-specific-contact--clearschedule)
    * [Adding a note to a specific contact : `note`](#adding-a-note-to-a-specific-contact--note)
    * [Deleting a contact : `delete`](#deleting-a-contact--delete)
    * [Clearing all entries : `clear`](#clearing-all-entries--clear)
    * [Exiting the program : `exit`](#exiting-the-program--exit)
  * [Known issues](#known-issues)
  * [Command Summary](#command-summary)
  * [Command Field Prefix Summary](#command-field-prefix-summary)
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
* Leading & trailing whitespaces are ignored from the input for parameters.
  e.g. `n/               NAME`, `n/NAME                  ` and `n/NAME` are interpreted in the same way. 
* As seen above, the app uses prefixes to indicate the type of parameter, like `n/` for `NAME`.
* Prefixes are defined by having a space before the prefix proper. E.g. `n/ Johnp/123456789` will be interpreted as supplying `Johnp/123456789` to the `NAME` parameter.
* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.
* Commands that modify contacts' detail or create contacts, i.e. `add` and `edit`, time-stamp the contact with a last modified field.
  * `mark` is not considered as modifying contact's detail and hence does not change the last modified date.
* Commands that accept indices as parameters can only accept positive integers less than 2147483647, and a valid index
  i.e. the valid index must be between 1 and the lower of (list size, 2147483647) (inclusive)
* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</div>

<div markdown="span" class="alert alert-primary">:bulb: **Before you begin:**
If there is no data file, either because this is the first time you've started **Connexion** or because you deleted the file, the app will fill with example dataset. You may enter `clear` to clear them. These are present just as an example, and can otherwise be disregarded.
</div>

### Viewing help : `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`

### Adding a new contact : `add`

Creates a new contact and adds it to the app.

Format: ```add n/NAME p/PHONE_NUMBER e/EMAIL c/COMPANY j/JOB [t/TAG]...```

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
A contact can have any number of tags (including 0).
</div>

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
Tags cannot include spaces or non-alphanumeric characters. 
We suggest using **PascalCase** or **camelCase** if you want multiple words in a single tag.
</div>

* Does not allow contacts with duplicate full names to be added. (case-sensitive)
  * E.g. Contacts with name "A" and "a" can co-exist in the contact list, but not "A" and "A".
* Sets the last modified time of the given contact to the current time when executed.

Examples:
* `add n/John Wick p/12345678 e/johnwick@gmail.com c/Google j/Software Engineer t/NUS t/metInHackathon`
* `add n/Aiken Duit p/88888888 e/aikenduit@hotmail.com c/Meta j/Data Engineer`

### Listing all contacts : `list`

Gives the list of all contacts.

Format: `list`

* Allows the user to clear existing filters on the contact list.
  * E.g. `filter m/` followed by `list` clears the filter for marked contacts and displays all contacts instead.

### Editing existing contact details : `edit`

Edits an existing contact details via index.

Format: `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [c/COMPANY] [j/JOB] [t/TAG]...​`

* Edits the contact at the specified `INDEX`. The index refers to the index number shown in the displayed contact list. The index **must be a positive integer** starting from 1.
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the contact will be removed i.e. adding of tags is not cumulative.
* You can remove all the contact’s tags by typing `t/` without
  specifying any tags after it.
* Same as `add`, no duplicate names (case-sensitive) are allowed.
* Updates the last modified time of the given contact to the current time when executed.

Examples:
*  `edit 1 n/John Sick p/87654321 t/` edits the 1st contact’s name, phone number and clears the tags in the current displayed list.
*  `edit 2 n/Betsy Crower t/` edits the name of the 2nd contact to be `Betsy Crower` and clears all existing tags.

### Marking contacts of interest : `mark`

Marks a contact of interest.

Format: `mark INDEX`

* Marks the contact at the specified `INDEX`, which is shown by a yellow filled star.
* The index refers to the index number shown in the displayed contact list.
* The index **must be a positive integer** starting from 1.
* When a new contact is created, the contact is un-marked by default.
* Does NOT update the last modified time of the given contact to the current time when executed.

Examples:
* `list` followed by `mark 2` marks the 2nd contact in the contact list as contact of interest.

### Un-marking contacts of interest : `unmark`

Un-marks a contact of interest.

Format: `unmark INDEX`

* Marks the contact at the specified `INDEX`, which is shown by a yellow hollow star.
* The index refers to the index number shown in the displayed contact list.
* The index **must be a positive integer** starting from 1.
* When a new contact is created, the contact is un-marked by default.
* Does NOT update the last modified time of the given contact to the current time when executed.

Examples:
* `unmark 1` un-marks the 1st contact in the current displayed list.

### Filtering a contact via a specified field : `filter`

Displays all contacts filtered via a specified field.

Supports 2 formats: <br>
Format 1: `filter FIELD_PREFIX_1 KEYWORD [MORE_KEYWORDS]` <br>
Format 2: `filter FIELD_PREFIX_2` <br>

* FIELD_PREFIX: represents the field to filter by.
    * Example: if filter by company, FIELD_PREFIX = “c/”.
    * FIELD_PREFIX_1: one of "n/", "p/", "e/", "c/", "j/", "t/"
    * FIELD_PREFIX_2: one of "m/", "u/"
    * Refer to [Command Field Prefix Summary](#command-field-prefix-summary) for more information about the prefixes.
* Only supports filtering via ONE field.
    * Everything after FIELD_PREFIX_1 will be recognized as keywords, **including field prefixes**!
    * Any keywords behind FIELD_PREFIX_2 will be ignored but the command is still valid, **including field prefixes**!
    * E.g. `filter u/ n/John` will be interpreted the same as `filter u/`.
    * E.g. `filter n/ John u/` will be interpreted as a command to filter in the `NAME` field with argument `John u/`
* The search is case-insensitive. e.g. `google` will match `Google`.
* The order of the keywords does not matter. e.g. `Google Meta` will match `Meta Google`.
* Only returns results with FULL matching keywords to the field.
* Contacts matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`.

Examples:
* `filter c/Google` returns all entries with company fields “Google”, “google” “Google Inc.”.
* `filter t/friends` returns all entries with the tag “friends”.

### Scheduling a meeting with a specific contact : `schedule`

Schedules a meeting with an existing contact via index.

Format: `schedule INDEX i/SCHEDULE_TIME [a/SCHEDULE_NAME]`

* Adds a schedule to the contact at the specified `INDEX`. The index refers to the index number shown in the displayed contact list. The index **must be a positive integer** starting from 1.
* Schedule name is an optional field. If no schedule name is given, the default is `Meeting`.
* Input schedule time must be in the format `YYYY-MM-DD-HH-mm`, and must be valid.
* If there are existing schedules or schedule names, it will be updated to the input schedule and schedule name. If schedule name is not given, it will still be set to `Meeting`.
* Updates the last modified time of the given contact to the current time when executed.
* This command allows the entering of dates before the current date & time, e.g. in the event that you are backdating a meeting that has occurred for your own usage in documentation.
* Examples:
*  `schedule 1 i/2023-12-07-13-45` edits or adds the 1st contact's schedule time and name, where the schedule time is `7 Dec 2023, 13:45:00`, and the schedule name is the default name, `Meeting`.
*  `schedule 3 i/2024-05-06-18-00 a/Evening seminar` edits or adds the 3rd contact's schedule time and name, where the schedule time is `6 May 2024, 18:00:00`, and the schedule name is `Evening seminar`.

### Viewing details of a specific contact : `detail`

Displays all details of a specific contact via index.

Format: `detail INDEX`

* Shows the details of the contact at the specified `INDEX`.
* The index refers to the index number shown in the displayed contact list.
* The index **must be a positive integer** starting from 1.

Examples:
* `detail 1` displays the details for the contact in the first index.
* `filter c/Google` followed by `detail 2` details the 2nd contact in the results of the `filter` command.

### Clearing a scheduled meeting with a specific contact : `clearschedule`

Clears the scheduled meeting with an existing contact via index.

Format : `clearschedule INDEX`

* Clears a schedule to the contact at the specified `INDEX`. The index refers to the index number shown in the displayed contact list. The index **must be a positive integer** starting from 1.
* Clears both the scheduled date & time, and the schedule name.
* Updates the last modified time of the given contact to the current time when executed.

Example : 
* `clearschedule 1` removes both the name and time of schedule associated with the 1st contact.

### Adding a note to a specific contact : `note`

Adds a note with an existing contact via index.

Format: `note INDEX o/[NOTE]`

* Adds a note to the contact at the specified `INDEX`. The index refers to the index number shown in the displayed contact list. The index **must be a positive integer** starting from 1.
* If there are existing notes, it will be updated to the input note.
    * Note is an optional field. If left blank, it will clear any existing note that the contact has.
* By default, note is empty when a contact is added to the contact list.
* Note has a character limit of **1000**.
* Note can contain any alphanumeric character, punctuation marks and whitespaces in between.
  * The only exception to this rule are command prefixes as previously mentioned.
* Updates the last modified time of the given contact to the current time when executed.

Examples:
* `note 1 o/CS2103 is pain!` edits or adds the 1st contact's note to be `CS2103 is pain!`.
* `note 2 o/` clears 2nd contact's note.

### Deleting a contact : `delete`

Deletes a contact via index.

Format: `delete INDEX`

* Deletes the contact at the specified `INDEX`.
* The index refers to the index number shown in the displayed contact list.
* The index **must be a positive integer** starting from 1.

Examples:
* `list` followed by `delete 2` deletes the 2nd contact in the contact list.
* `filter n/Betsy` followed by `delete 1` deletes the 1st contact in the results of the `filter` command.

### Clearing all entries : `clear`

Clears all entries from the contact list.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. When using multiple screens, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the preferences.json file created by the application before running the application again.

--------------------------------------------------------------------------------------------------------------------

## Command Summary

| Action             | Format, Examples                                                                                                                                                               |
|--------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Add**            | `add n/NAME p/PHONE_NUMBER e/EMAIL c/COMPANY j/JOB [t/TAG]...​`<br>e.g., `add n/John Wick p/12345678 e/johnwick@gmail.com c/Google j/Software Engineer t/NUS t/metInHackathon` |
| **Clear**          | `clear`                                                                                                                                                                        |
| **Delete**         | `delete INDEX`<br> e.g., `delete 2`                                                                                                                                            |
| **Edit**           | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [c/COMPANY] [j/JOB] [t/TAG]…​`<br> e.g.,`edit 1 n/John Sick p/87654321 t/`                                                     |
| **Filter**         | `filter FIELD_PREFIX_1 KEYWORD [MORE_KEYWORDS]` <br> e.g., `filter c/Google` <br> OR <br> `filter FIELD_PREFIX_2` <br> e.g., `filter m/`                                       |
| **List**           | `list`                                                                                                                                                                         |
| **Help**           | `help`                                                                                                                                                                         |
| **Mark**           | `mark INDEX` <br> e.g., `mark 2`                                                                                                                                               |
| **Unmark**         | `unmark INDEX` <br> e.g., `unmark 1`                                                                                                                                           |
| **Schedule**       | `schedule INDEX i/SCHEDULE_TIME [a/SCHEDULE_NAME]` <br> e.g., `schedule 3 i/2024-05-06-18-00 a/Evening seminar`                                                                |
| **Detail**         | `detail INDEX` <br> e.g., `detail 1`                                                                                                                                           |
| **Clear Schedule** | `clearschedule INDEX` <br> e.g., `clearschedule 1`                                                                                                                             |
| **Note**           | `note INDEX o/[NOTE]` <br> e.g., `note 1 o/CS2103 is pain`                                                                                                                     |

--------------------------------------------------------------------------------------------------------------------

## Command Field Prefix Summary

<div markdown="span" class="alert alert-primary">:bulb: **Reminder:**
Leading & trailing whitespaces are ignored from the input.
</div>

| Prefix | Description   | Restrictions                                                                                                                                                                                                                                                                                         | Notes                                                                                                                                                                  |
|--------|---------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| c/     | COMPANY       | Non-empty.                                                                                                                                                                                                                                                                                           | -                                                                                                                                                                      |
| e/     | EMAIL         | Non-empty, and of format local-part@domain, local-part is alphanumeric and can contain `+`,`_`,`.`,`-`, but cannot start or end with these characters. Domain must be made up of one or more domain labels seperated by dots, consisting of at least 2 alphanumeric characters separated by hyphens. | "dotless" domains like `example@example` are allowed as they are technically [possible](https://www.icann.org/en/system/files/files/sac-053-en.pdf), just discouraged. |
| j/     | JOB           | Non-empty.                                                                                                                                                                                                                                                                                           | -                                                                                                                                                                      |
| n/     | NAME          | Non-empty, alphanumeric characters and whitespace.                                                                                                                                                                                                                                                   | -                                                                                                                                                                      |
| p/     | PHONE_NUMBER  | Non-empty, 3-15 numeric characters                                                                                                                                                                                                                                                                   | -                                                                                                                                                                      |
| t/     | TAG           | Non-empty, alphanumeric, one word not separated by whitespace (` `)                                                                                                                                                                                                                                  | Multiple instances of this argument can be accepted.                                                                                                                   |
| m/     | MARKED        | None (Does not interpret any parameters)                                                                                                                                                                                                                                                             | Marked contacts, only usable in `filter` command                                                                                                                       |
| u/     | UNMARKED      | None (Does not interpret any parameters)                                                                                                                                                                                                                                                             | Un-marked contacts, only usable in `filter` command                                                                                                                    |
| i/     | SCHEDULE_TIME | Non-empty, must be of format `YYYY-MM-DD-HH-mm` & be a valid date & time                                                                                                                                                                                                                             | Only usable in `schedule` command                                                                                                                                      |
| a/     | SCHEDULE_NAME | Non-empty, alphanumeric characters and whitespace.                                                                                                                                                                                                                                                   | Only usable in `schedule` command                                                                                                                                      |
| o/     | NOTE          | Can be empty, alphanumeric characters, punctuation(any of ``!"#%&'()*+,-./:;<=>?@[\]^`{\| }~``)  and whitespace. Maximum of 1000 characters.                                                                                                                                                         | Only usable in `note` command                                                                                                                                          |                 



