# Changes

## 0.15

* Added a check to find out if the running version is the latest available version, with a
corresponding dialog panel that opens automatically. The panel can also be opened from the menu
manually.

## 0.14

* Fixed bug: redetermination of empty rows on clearing form
* Fixed bug: unclear error messages
* Fixed bug: layout disorganized on fullscreen
* Added Bash scripts to: test, package, run
* Added Gnome desktop entry example
* Refactored validation and filtering to decorator pattern for Log and Logs

## 0.13

* Log entry rows are saved to file on each change, and loaded on application start. This replaces
the timed text backup.

## 0.12

* Refactored project into single jar package using mvn shade plugin. This runs all tests during
    building.
* Fixed issue #1: Can't start application. Missing settings file.

## 0.11

* Settings refactored from serialized object to plain text file
* Started with interfaces, fakes and testing with fakes
* Fixed bug textbackup: anonymous object needed to reference outer object

## 0.10

* Persist settings
* About page has link to project site

## 0.9

* Refactored UI elements to separate classes
* Refactored namespaces
* Added menubar
* Added settingspanel
* Rows are added and removed based on number of empty rows

## 0.8

* Fix bug stamp time rounding through refactor
* Is able to round stamps to a user chosen value
* Unit tests for Clock

## 0.7

* Outputs report in a textarea
* Is able to round stamp to fives

## 0.6

* Resizable window
* Is able to combine stories with the same story-code in the report

## 0.5

* Scrollbars and infinite inputs

## 0.4

* Checks for overlap between logs
* Calculates total time spent
* Log has a description field

## 0.3

* Is able to enter the current time with a button press
* Panels refactored in separate private classes. Improved encapsulation.

## 0.2

* Autosaves every 5 minutes to \<date\>.backup.txt

## 0.1

* Generates report
* Is able to calculate time difference
