---
layout: page
title: Xavier's Project Portfolio Page
---

### Project: Connexion

Connexion is a desktop application for undergraduate students to manage their networking connections in the Tech industry.
The user interacts with it using a CLI, and it has a GUI created with JavaFX.

* **New Features**: 
  * `LastModifiedDateTime`
  * `clearschedule`
* **Code contributed**:
  * `ClockDependentParser` interface
  * Implementation of `LastModifiedDateTime` 
    * Addition of `Clock`-keeping within application
    * Implementation of `LastModifiedDateTime` needed to be testable & potentially extensible.
    * Trivial implementation of querying system time directly could not be used due to complete inability to inject timings for testing
      * E.g. if an `add` is supposed to be simulated as happening at a specific time for testing or other purposes, there is no way to implement with trivial implementation.
    * Hence, more complex implementation needed to maintain good OO design & keep coupling low
      * `Logic` and hence `Model` need to keep track of a `Clock` in order to instruct relevant parsers what `Clock` to use
      * Above design structure more closely adheres to existing design structure instead of trivial implementation
  * Implementation of schedule clearing
  * Testing for relevant new features
  * Bugfixes for numerous small issues, most notably :
    * Logic & error message for detection of integer input overflow vs. non-integer input
    * Correction of email validation regex
  * Exhaustive list available [here](https://nus-cs2103-ay2324s1.github.io/tp-dashboard/?search=&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code&since=2023-09-22&tabOpen=true&tabType=authorship&tabAuthor=xavierpok&tabRepo=AY2324S1-CS2103-F13-1%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)
* **Project management**:
  * Set up Github repo for project
  * Ensured PRs properly closed their issues
  * Maintaining & organising issues as needed
  * Troubleshooting of gradle bugs as needed
* **Documentation**:
    * User Guide: 
      * Updated for features above
      * Added section on field restrictions for clarity
      * Clarification of ambiguous sections, such as :
        * Clarification of prefix parsing method
        * Which operations set `LastModifiedDateTime`
    * Developer Guide: to be added soon
      * Add value proposition & target user
      * Expansion of NFRs
      * Addition of diagrams & sections for `clearschedule`, `LastModifiedDateTime`
* **Community**:
    * Contributions to PE dry run
      * Catching of floating point error bug in implementation of `increment` feature as seen [here](https://github.com/AY2324S1-CS2103T-T14-1/tp/issues/220)
      * Catching of documentation bug in same project [here](https://github.com/AY2324S1-CS2103T-T14-1/tp/issues/220)

