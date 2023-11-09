---
layout: page
title: Kwok Yong's Project Portfolio Page
---

### Project: Connexion

Connexion is a desktop application for undergraduate students to manage their networking connections.
The user interacts with it using a CLI, and it has a GUI created with JavaFX.

Given below are my contributions to the project.

* **New Feature**: 
  * Added `filter` command (PR [\#123](https://github.com/AY2324S1-CS2103-F13-1/tp/pull/123), [\#172](https://github.com/AY2324S1-CS2103-F13-1/tp/pull/172))
    * User can filter contacts based on a specific field, like name, company, tag etc
    * User can also filter contacts based on markStatus
    * An important feature that helps the user to find their contacts of interest
    * Inspired by the prior implementation of `find` command; Multiple `Predicate`s has to be written for the fields to be filtered, then to be passed to `model#updateFilteredPersonList`
    * Considerations have to be made to support two different formats in this command
  * Added `note` command (PR [\#176](https://github.com/AY2324S1-CS2103-F13-1/tp/pull/176))
    * User can add a note to a specific contact
    * A good-to-have feature that helps user to note down certain attributes or characteristics of a contact
    * Considerations have to be made to navigate the immutability of a `Person`, and to show the updates on the GUI
  * Implemented the immediate UI updates in `PersonViewPanel` (PR [\#180](https://github.com/AY2324S1-CS2103-F13-1/tp/pull/180))
    * Decoupled UI from `DetailCommand`
    * Ensured that all commands that modify contact's details will reflect the changes immediately on the UI

* **Enhancement to existing features**:
  * Updated `help` command (PR [\#116](https://github.com/AY2324S1-CS2103-F13-1/tp/pull/116))
    * Updated the link inside `HelpWindow`

* **Code contributed**: [RepoSense Link](https://nus-cs2103-ay2324s1.github.io/tp-dashboard/?search=imkwokyong&breakdown=true)

* **Project management**:
  * Managed releases `v1.2`, `v1.3.trial` and `v1.3` on Github
  * Managed milestones from `v1.1` to `v1.4`, made sure deliverables submitted on time
  * Refactored package name to `connexion` (PR [\#134](https://github.com/AY2324S1-CS2103-F13-1/tp/pull/134))
  * IC for User Guide, ensuring the consistency and coherence of the entire User Guide, catching bugs
    * Example: PR [\#182](https://github.com/AY2324S1-CS2103-F13-1/tp/pull/182) 

* **Documentation**:
  * User Guide:
    * Added the details of features implemented by self (Included in the same PR that implemented said features)
  * Developer Guide:
    * Added the details and diagrams of features implemented by self (PR [\#149](https://github.com/AY2324S1-CS2103-F13-1/tp/pull/149))
    * Added important user stories (PR [\#236](https://github.com/AY2324S1-CS2103-F13-1/tp/pull/236))
    * Added use cases (PR [\#240](https://github.com/AY2324S1-CS2103-F13-1/tp/pull/240))

* **Community**:
  * PRs reviewed (with non-trivial review comments)
    * Examples: [\#181](https://github.com/AY2324S1-CS2103-F13-1/tp/pull/181), [\#259](https://github.com/AY2324S1-CS2103-F13-1/tp/pull/259)
  * Reported bugs and suggestions for other teams in the class during PE-D ([PE-D issues](https://github.com/imkwokyong/ped/issues))

* **Tools**:
  * Integrated CodeCov to the team repo, to check for test coverage