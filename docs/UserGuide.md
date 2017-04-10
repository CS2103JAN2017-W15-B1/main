# *DoMe!* User Guide

By : `Team CS2103-W15-B1`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `Jan 2017`  &nbsp;&nbsp;&nbsp;&nbsp; Licence: `MIT` &nbsp;&nbsp;&nbsp;&nbsp; 

---

1. [Introduction](#1-introduction)
2. [Quick Start](#2-quick-start)
3. [Features](#3-features)
4. [FAQ & Troubleshooting](#4-faq-troubleshooting)
5. [Commands Cheatsheet](#5-commands-cheatsheet)
6. [Appendix](#6-appendix)

## 1. Introduction

Ever felt overwhelmed by the multitude of tasks you have to complete and have no idea where to start? Are you looking for an easy-to-work-with application to help you track all your activities? Well, look no further! Your very own task manager - *DoMe!* is here to assist you!
*DoMe!* is your personal assistant that tracks all your activities and displays them in an easy-to-read display. It saves you the hassle of remembering what needs to be done and helps you prioritise your tasks.
Unlike other software, *DoMe!* is simple and intuitive. All you need is your keyboard to type in a single line of command, removing the inconvenience of clicking and navigating through blundersome menus. Let's get started in being productive and organised with *DoMe!*

## 2. Quick Start

1. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>
> Please note that the app is incompatible with earlier versions of Java 8.
2. Download the latest `DoMe.jar` from the [releases](../../../releases) tab.
3. Copy the file to the folder you want to use as the home folder for your Address Book.
4. Double-click the file to start the app. The GUI should appear in a few seconds.
> <img src="images/UI.png" width="1000">

## 3. Features

> **Command Format**
> 
> * Items in `Square brackets [ ]` denote a required field.
> * Items in `Curved brackets ( )` denote an optional field.
> * `...` denotes that you can have multiple instances.
> * The `[Task-Number]` refers to the index number shown in the task listing that is currently on the screen.<br>
    The `[Task-Number]` **must be a positive integer** 1, 2, 3, ...

### 3.1 Functional features
This is a list of commands you can give to the application

#### 3.1.1 View help: `help`
If you are unsure of the command formats, you can simply type `help` and view all the available commands and how to use them.

_Format:_
`help`

#### 3.1.2 Add task: `add`
It's time to start adding tasks to your todo list! You can add events (tasks with a start time and an end time), kick-start tasks (task with only a start time), deadline tasks (tasks with a date line) and also just tasks with names. You can also organize these tasks using tags.

_Format:_
`add [Name-of-Task] (s/Start-Time) (e/End-Time) (t/Tag-1) (t/Tag-2)`

> Name of task can only contains alphanumeric characters and '.<br>
> Start/End Time can either be in format DD-MM-YYYY H.MM AM/PM  or simply DD-MM-YYYY.<br>
> For example: 12-08-2016 4.00 PM or simply 12-08-2016.<br>
> A task can have any number of tags (including 0).<br>

_Examples:_

* `add date with girlfriend s/07-04-2017 6.00 PM e/07-04-2017 10.00 PM t/mylady`<br>
  If you're on a tight schedule, you can add a task with a specific start time and a specific end time, along with a<br>
  tag to feel organized.

* `add Reply Boss's email s/13-04-2017 10.00 AM e/13-04-2017 10.00 PM t/Work t/Urgent t/HighPriority`<br>
  You can also be more organized by adding more tags - this will make finding the task easier as you will see when <br>
  you reach the `find` command.
  
* `add Start preparing for conference s/15-06-2017 t/Work t/Conference`<br>
  If you are not sure about the end time or want to be flexible with it, you can choose not to specify the end time. <br>

* `add Help John with Excel before his report due e/20-04-2017 t/Work t/Colleague`<br>
  And the same goes for the start time.
  
* `add Daily email check`<br>
  You can also do away with the time and the tags for your daily habits.

> <img src="images/UserGuideDiagrams/edit-1.1.png" width="1000">
> before adding the task

> <img src="images/UserGuideDiagrams/edit-1.2.png" width="1000">
> after adding the task

#### 3.1.3 Edit task: `edit`
You can update the details of your task by editing it. In particular, you can organize your tasks by adding/removing their tags, changing starting and ending time or changing the task's name.
> Add tags to the task at the specified index. The index refers to the index number shown in the last person listing.

_Format:_
`edit [Task-Number] (Name-Of-Task) (s/Start-Time) (e/End-Time)[t/Tag-1] [t/Tag-2] ...`

> * Edits the task at the specified `[Task-Number]`.
> * **At least one** of the optional fields must be provided.
> * Existing values will be updated to the input values.
> * Values not specified will remain the same.
> * When editing tags, the existing tags of the task will be removed i.e adding of tags is not cumulative.
> * You can remove all the task's tags by typing `t/` without specifying any tags after it. 


_Examples:_
* `edit 1 t/urgent t/for mom`<br>
  All tags of the task with index `1` on the current list will be removed and replaced with `[urgent]` and `[for mom]`
  
* `edit 2 meet my mother s/07-04-2017 7.00 PM e/07-04-2017 10.00 PM t/mother`<br>
  Change the name of the task with index `2` on the current list to `meet my mother`, the start time to
  `07-04-2017 7.00 PM`, the end time to `07-04-2017 10.00 PM`, and replace all the tags with `[mother]`.
  
* `edit 2 s/08-04-2017 7.00 PM e/08-04-2017 10.00 PM`<br>
  Given that the task with index `2` on the current list is initially a floating task, by editing the time fields, 
  you can change it to an event with the start time `08-04-2017 7.00 PM` and the end time `07-04-2017 10.00 PM`. 
  You can change a task from a floating task to a task with dates but you cannot change a task with dates into 
  a floating task. 

> <img src="images/UserGuideDiagrams/edit-1.1.png" width="1000">
> task before editing

> <img src="images/UserGuideDiagrams/edit-1.2.png" width="1000">
> task after editing

#### 3.1.4 Describe task: `describe`
You can add in a task description for a specific task and the description will be reflected in the task details.

_Format:_
`describe 1 [desired description]`

_Example:_
* `describe 1 this determines my promotion`<br>
  Reminds yourself that the task with index `1` on the current list will determine your promotion and thus should be attended to immediately.

> <img src="images/UserGuideDiagrams/describe-1.1.png" width="1000">
> task before description

> <img src="images/UserGuideDiagrams/describe-1.2.png" width="1000">
> task after description

#### 3.1.5 Complete task: `complete`
You can mark a task as completed to check it off your list of incomplete tasks.

_Format:_
`complete [Task-Number]`

_Example:_
* `complete 2`<br>
  Check off the task with index number `2` on your incomplete list to avoid mixing up tasks that you've done and tasks
  that you've not done. 

#### 3.1.6 Delete task: `delete`
If you no longer need to do a task, you can simply delete it from your to-do list. However, if you mistakenly delete something, you can retrieve it back using the `undo` command that is explained further down the guide.

_Format:_
`delete [Task-Number]`

_Example:_
* `delete 2`<br>
Remove the task with index number `2`.

#### 3.1.7 List: `list`
You can view a specific type of the tasks you want to view in your to-do list.

_Format:_
`list all`
> Lists all tasks that are currently in your to-do list.

`list complete`
> Lists tasks marked as completed.

`list incomplete`
> Lists tasks marked as incomplete.

`list upcoming`
> Lists all upcoming tasks.

`list overdue`
> Lists incomplete tasks with deadlines that have already passed.

#### 3.1.8 Find: `find`
You can find a task to by simply searching for tasks with matching keywords in their names and/or tags.

> The search is case insensitive.

_Format:_
`find (name) (t/Tag1) (t/Tag2) ...`

_Examples:_
* `find report`<br>
  Returns a list of tasks (if any) with the phrase `report` in its name.
* `find meeting t/work`<br>
  Returns a list of tasks (if any) with the phrase `meeting` in its name and any tags that are
  a full match with `work`. 

#### 3.1.9 Undo previous command: `undo`

You can easily undo your previous command.

> This will undo the most previous command that mutated the data - add, edit, delete, describe & clear. 

_Format:_
`undo`

_Example:_
`undo`<br>
Undo the previous command that changed the data, e.g. Undone: add send TPS report to Bill by Friday 6pm.


#### 3.1.10 Redo previous command: `redo`

You can easily redo your last command undone.

> This will redo the most previous command that was undone.

_Format:_
`redo`

_Example:_
`redo`<br>
Redo the previous command that changed the data, e.g. Redone: add send TPS report to Bill by Friday 6pm.

#### 3.1.11 Select a task: `select`
You can select a task to view more details about it

_Format:_
`select [Task-Number]`

_Example:_
* `select 1`<br>
You can view its details, including its description, in the `Task Details` panel on the right.

#### 3.1.12 Google Integration: `sync`
You can sync your current to-do list to your Google Calendar.

_Format:_
`sync`

_Example:_
* `sync`<br>
If you're syncing for the first time, you would be asked to allow DoMe! to have access to your Google Calendar. Simply 
click `Allow` and wait for the data to sync over, upon which a success message will be displayed in the result display, indicating
that your current data in the to-do list has been synced to your Google Calendar.

#### 3.1.13 Clear the data : `clear`
You can clear your entire to-do list.

_Format:_
`clear`

#### 3.1.14 Customize file storing: `changestorage`
You can change the storage location of the data to transfer your current to-do list to your own storage system with ease
> Store all your data in the app in the file located at PATH_TO_STORAGE_FILE. It is required that this file be a .txt file located in StorageFile/a folder rooted at StorageFile, and that it is created before the command is called.

_Format:_
`changestorage PATH_TO_STORAGE_FILE`

_Examples:_
* `changestorage StorageFile/StoreHereInstead/MyStorage.txt`<br>
  The app will store its data in MyStorage.txt located at StorageFile/StoreHereInstead/MyStorage.txt, provided that this file exists before calling the command.

* `changestorage StorageFile/AnotherStorage.txt`
  The app will store its data in AnotherStorage.txt located under StorageFile instead of the default storage location, provided that this file exists before calling the command.
  
* `changestorage YourThumbDrive`<br>
  Upon the invocation of this command, DoMe! will automatically creates a storage file called `todolist.xml` in `YourThumbDrive` folder and store data there instead! What an easy way to bring home your To-do List with you!

#### 3.1.15 Copy data to new storage location: `exportsave`
You can copy the data in the current to-do list to your own storage system with ease.
> Store all the data of the task manager in the file located at PATH_TO_STORAGE_FILE. It is required that this file be a .txt file located in StorageFile/a folder rooted at StorageFile, and that it is created before the command is called.

_Format:_
`exportsave PATH_TO_STORAGE_FILE`

Examples:
* `exportsave StorageFile/StoreHereInstead/MyStorage.txt`<br>
  The task manager will copy its data in MyStorage.txt located at StorageFile/StoreHereInstead/MyStorage.txt, provided that this file exists before calling the command.

* `exportsave YourThumbDrive`<br>
  Upon invocation of this command, DoMe! will copy your entire To-do List to `todolist.xml` in `YourThumbDrive` folder, allowing you to make a backup of all your data while still storing in the default location.

## 4. FAQ & Troubleshooting

* **Q**: How do I transfer my data to another computer?<br>
* **A**: Copy the app to the other computer and overwrite the empty data file it creates with the file that contains the data of your previous *DoMe!* folder.

* **Q**: I cannot access the Help document.<br>
* **A**: Check your Internet access. Internet connection is required to access the help document.

* **Q**: I cannot sync to Google Calendar.<br>
* **A**: Check your Internet access. Internet connection is required to sync to Google Calendar. It might also take a 
while so do be patient. 

* **Q**: I accidentally cleared my data. What do I do?<br>
* **A**: Try typing in undo to see if the data is restored. We suggest that you can type `exportsave` once in a while
to have a backup of your to-do list.  



## 5. Commands Cheatsheet

* **Add** : `add [Name-of-Task] (s/Start-Time) (e/End-Time) (t/Tag1) ...`

* **Edit** : `edit [Task-Number] [Edited-Details] (t/Edited-Tag)`

* **Describe** : `describe [Task-Description]`
   e.g. `describe 1 check for formatting before sending`

* **Complete** : `complete [Task-Number]`
   e.g. `complete 3`

* **Delete** : `delete [Task-Number]`
   e.g. `delete 3`

* **List** : `list`,`list incomplete`,`list complete`,`list overdue`

* **Find** : `find [Keyword] (t/TagKeyword)...`
  e.g. `find email t/urgent`

* **Undo** : `undo`

* **Redo** : `redo`

* **Select** : `select [Task-Number]` 
  e.g.`select 2`

* **Sync** : `sync`

* **Clear** : `clear`

* **Change Save Location** : `changestorage [PATH_TO_STORAGE]`
 e.g. `changestorage MySavedFiles/StoreHereInstead`

* **Export Save File** : `exportsave [PATH_TO_STORAGE]`
 e.g. `exportsave Thumbdrive/CloneStorageHere`

---
  
## 6. Appendix
  
  | Word | Definition |
|-----|-----|
|[GUI]|Graphic User Interface. The interface presented to users to interact with *DoMe!*.|
|[Storage Path]|This is the directory where your data will be saved.|
