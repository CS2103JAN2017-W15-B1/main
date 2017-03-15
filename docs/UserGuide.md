TaskManager - User Guide

By : `Team CS2103-W15-B1`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `Jun 2016`  &nbsp;&nbsp;&nbsp;&nbsp; Licence: `MIT`

---

1. [Introduction](#introduction)
2. [Quick Start](#quick-start)
3. [Features](#features)
4. [FAQ](#faq)
5. [Command Summary](#command-summary)
6. [Appendix](#appendix)

## 1. Introduction

Ever felt overwhelmed from the multitude of tasks you have to complete and have no idea where to start? Are you looking for an easy to work with application to help you track all your activities? Well look no further! Your very own TaskManager is here to assist you!
TaskManager is your personal assistant that tracks all your activities and displays them in a simple to read display. It saves you the hassle of remembering what needs to be done and is able to help you prioritise your tasks.
Unlike other complicated softwares, TaskManager is simple and intuitive. It relies completely on the keyboard and only requires a single line of command, removing the inconvenience of clicking and navigating through multiple interfaces. It also has a flexible command interface, accepting many variations of the same command, removing the need to memorise a certain format for every command. Without further ado, let's get started!

## 2. Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>

   > Having any Java 8 version is not enough. <br>
   > This app will not work with earlier versions of Java 8.

1. Download the latest `taskmanager.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your Address Book.
3. Double-click the file to start the app. The GUI should appear in a few seconds.
   > <img src="images/Ui.png" width="600">
4. Refer to the [Features](#features) section below for details of each feature available.<br>


## 3. Features

> **Command Format**
> `Square brackets [ ]` denote a required field
> `Curved brackets ( )` denote an optional field'
> `...` denotes that you can have multiple instances

## 3.01 View help: `help`
View all the available commands and how to use them

_Format:_
`help`

## 3.02 Add task: `add`
Adds a task to your todo list

_Format:_
`add [Name-of-Task] [Deadline] (#Tag-1) (#Tag-2) â€¦`

_Example:_
`add send TPS report to Bill by Friday 6pm #TPS #report`

## 3.03 Delete task: `delete`
Removes a task from your todo list

_Format:_
`remove [Task-Number]`


## 3.04 Edit task: `edit`
Allows you to edit names and details of your task

_Format:_
`edit [Task-Number]`

## 3.05 Complete task: `complete`
Marks task as completed

_Format:_
`complete [Task-Number]`

_Example:_
`complete 2`

## 3.06 Search: `search`

Search for tasks with matching keywords in their names and tags

> The search is case insensitive.

_Format:_
`search [tag/name]`

_Example:_
`search report`
Returns a list of tasks (if any) with the phrase report in its name or tag

## 3.07 View overdue tasks: `overdue`

View a list of tasks which are overdue

_Format:_
`overdue`

_Example:_
`overdue`
Returns a list of overdue tasks (if any)


## 3.08 View tasks by deadline: `due`

View a list of tasks due by a certain date

> You can type in the actual date after the command word due or you can just type in relative dates like today, tomorrow until this month.

_Format:_
`due 23/1` <br>
`due today` <br>
`due this week`

_Example:_
`due tomorrow` <br>
Returns a list of tasks due by tomorrow


## 3.09 Undo previous command: `undo`

Undo a previous command, like how a Ctrl-Z works.

> This will undo the most previous command that mutated the data for example add, edit, delete.

_Format:_
`undo`

_Example:_
`undo` <br>
Returns the undoing of the previous command that mutated the data, e.g. â€œ add send TPS report to Bill by Friday 6pm undone.

## 3.10 Add tags to created task: `tag`

Add a tag to a task
> Add tags to the task at the specified index. The index refers to the index number shown in the last person listing.

_Format:_
`tag [Task-Number] [#Tag-1] (#Tag-2) ...`

_Example:_
`tag 1 urgent, for mom` <br>
Returns the task name with the changed tags

## 3.11 Repeat tasks: `repeat`

Repeat a task at a fixed periodic time for e.g. weekly
> * Add tags to the task at the specified index. The index refers to the index number shown in the last person listing.
> * The periodic time specified must start with the word â€œeveryâ€� and must be followed by one of the seven days of the week.

_Format:_
`repeat [Task-Number] [periodic time]`

_Example:_
`repeat 2 every friday`
Returns the task name that was put on repeat


## 3.12 Attach links: `addlink`
Attach relevant link(s) to the specified task.

> The TASK_NUMBER refers to the index number shown in the most recent listing of the task.
> This command works for all types of tasks: completed, uncompleted, overdue, etc.

_Format:_
`addlink RELEVANT_LINK TASK_NUMBER`

_Examples:_

`addlink https://www.google.com/drive/presentation-to-boss 5`
Attach the link `https://www.google.com/drive/presentation-to-boss` to task number 5 in the list of task.

`addlink https://mail.google.com/my-mail-box/important-email 10`
Attach the link `https://mail.google.com/my-mail-box/important-email` to task number 10 in the list of tasks.

## 3.13 Progress report: `report`
Report to the user his/her completed tasks in the past 1 week, overdue tasks in the past 1 week, and uncompleted tasks in the coming week.

_Format_:
`report`

## 3.14 Calendar view: `calendarview`
Views the current month and upcoming monthâ€™s tasks in the form of a calendar
> The TASK_NUMBER refers to the index number shown in the most recent listing of the task.
> This command works for all types of tasks: completed, uncompleted, overdue, etc.

Format:
`calendarview`


## 3.15 Scroll through previous commands
Format: `up/down arrow key`
>Use the arrow keys to scroll through your previous commands from the most recent the the earliest


## 3.2 Settings
>Enter the command `settings` to access these features

## 3.21 Change font size
Change the font size
> You can specify how much you want to increase or decrease the font size by adding a â€œbyâ€� followed by an integer from 1-10.
> If no integer is specified, the app will change the font size by 1.

_Format:_
`increase font (by 1)`
`decrease font (by 1)`

_Examples:_
* `increase font by 3`
  The app will increase the font size by 3.

* `decrease font`
  The app will decrease the font size by 1.


## 3.22 Message of the Day
Set/Change a message of the day
> Display a motivational message or some ascii art which will be shown when the todo list first starts up

_Format:_
`motd`

_Example:_
```
motd
I can do it!
```


## 3.23 Sync with Google Calendar: `sync`
Sync the list of tasks with the user's Google Calendar. User will be prompted to enter his/her password after entering the command.

_Format:_ `sync USER_EMAIL`

## 3.24 Customize file storing: `store`
Tell the task manager to store its data at the location specified by the user.

_Format:_
`store PATH_TO_STORAGE_FILE`
> Store all the data of the task manager in the file located at PATH_TO_STORAGE_FILE. It is required that this file be a .txt file located in StorageFile/a folder rooted at StorageFile, and that it is created before the command is called.

Examples:
* `store StorageFile/StoreHereInstead/MyStorage.txt`
The task manager will store its data in MyStorage.txt located at StorageFile/StoreHereInstead/MyStorage.txt, provided that this file exists before calling the command.

* `store StorageFile/AnotherStorage.txt`
The task manager will store its data in AnotherStorage.txt located under StorageFile instead of the default storage location, provided that this file exists before calling the command.

## 3.25 Reminder mode: `reminder`
Tell the task manager to/not to remind the user when it is a) 1 hour before the deadline of the most urgent task and b) 30 minutes before the deadline of the most urgent task.

_Format:_
`reminder on TASK_NAME [MORE_TASKS]` to turn **on** the reminder mode for the specified tasks.
`reminder off TASK_NAME [MORE_TASKS]` to turn **off** the reminder mode for the specified tasks.
`reminder on/off all` to turn **on**/**off** the reminder mode for all tasks.
> The task(s) has to be **uncompleted**, **not overdued** task(s).
> By **default**, reminder mode is switched **on** for all **uncompleted**, **not overdued** tasks.

_Examples:_
* `reminder off [do laundry] [buy grocery]`
Tell the task manager not to remind the user for the tasks `do laundry` and `buy grocery`.

* `reminder on [reply boss email]`
Tell the task manager to remind the user 1 hour and 30 minutes before the task `reply boss email` is due.


## 3.26 Collaborate with others: `collaborate`
Collaborate with other colleagues in managing the user's to-do list.
_Format:_
`collaborate EMAIL_1 [MORE EMAILS]`
_Examples:_
* `collaborate jane@gmail.com mary@hotmail.com terence@gmail.com`
Allow users with specified address to view your to-do list.

### Saving the data

Address book data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

## 4. FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous Task Manager folder.

## 5. Command Summary

* **Add**  `add NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]...` <br>
  e.g. `add James Ho p/22224444 e/jamesho@gmail.com a/123, Clementi Rd, 1234665 t/friend t/colleague`

* **Clear** : `clear`

* **Delete** : `delete INDEX` <br>
   e.g. `delete 3`

* **Find** : `find KEYWORD [MORE_KEYWORDS]` <br>
  e.g. `find James Jake`

* **List** : `list` <br>
  e.g.

* **Help** : `help` <br>
  e.g.

* **Select** : `select INDEX` <br>
  e.g.`select 2`
  
---
  
## 6. Appendix
  
  | Word | Definition |
|-----|-----|
|[GUI](#GUI)|Graphic User Interface. The interface presented to users to interact with TaskManager.|
|[Storage Path](#storage-path)|This is the directory where your data will be saved.|
