---
layout: page
title: Angel's Project Portfolio Page
---
### Project: Connexion

Connexion is a desktop application for undergraduate students to manage their networking connections.
The user interacts with it using a CLI, and it has a GUI created with JavaFX.

Given below are my contributions to the project:

* **New Features**: 
  * Added `mark` command
    * User can mark contacts of interest (indicated by ★ in the UI)
    * Allows users to note which contacts are important to them
    * Created a new field `Mark` for the model
    * Updated UI to reflect a contact's updated mark status by displaying ★ onto the UI
  * Added `unmark` command
    * User can un-mark contacts of interest (indicated by ☆ in the UI)
    * Allows user to undo the mark status of their contact
    * Complements the mark command feature
    * Updated the UI to reflect the contact's updated mark status by displaying ☆ onto the UI
  * Added `detail` command
    * User can view more details about their contact 
    * Important feature as it allows the user to view all details about the contact (e.g. notes and schedule which are not displayed in the list view)
    * Updated the UI to have an extra panel to display contact details
    
* **Code contributed**: [All commits](https://github.com/AY2324S1-CS2103-F13-1/tp/commits/master?author=Angelyxx) | [RepoSense](https://nus-cs2103-ay2324s1.github.io/tp-dashboard/?search=angelyxx&breakdown=true)
* **Project management**: 
  * Reviewed PRs
  
* **Enhancements implemented**: 
  * Made changes to existing UI components for formatting
  
* **Documentation**:
    * User Guide:
      * Added details of the `mark` feature
      * Added details of the `unmark` feature
      * Added details of the `detail` feature
    * Developer Guide:
      * Added details & diagrams of the `mark` feature
      * Added details & diagrams of the `unmark` feature
      * Added details & diagrams of the `detail` feature
      * Added to `planned enhancements` for dealing with text-wrapping and truncation in UI
      * Updated appendix on `instructions for manual testing`

* **Community**:
  * Bugs reported in other teams
    * [Caught a UI Bug](https://github.com/AY2324S1-CS2103T-W12-1/tp/issues/198)
    * [Found a duplicate bug](https://github.com/AY2324S1-CS2103T-W12-1/tp/issues/192)