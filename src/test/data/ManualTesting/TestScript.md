# DoMe! TestScript

## Loading the sample data

1. Ensure that you put `SampleData.xml` inside the `data/` folder (if the `data` directory does not exist inside the directory where the app is located, create it), and rename it as `todolist.xml`
2. Start `[W15-B1][DoMe].jar` by double clicking it.

The app is then ready for manual testing.

## Sample commands and expected outcomes

### Help command
* Input: `help`
* Expected output: GitHub user guide page for DoMe shows up.

### Add command
* Input: add project meeting s/20-8-2017 12.00 PM e/20-8-2017 2.00 PM t/Work
* Expected output: a task with title "project meeting" is added to the end of the `incomplete` panel. Under the title, the following will appear in order: tag `Work`, start time `20-8-2017 12.00 PM`, end time `20-8-2017 2.00 PM`. A confirmation message "New task added: ", following by the task's details is printed in the feedback box above the incomplete panel.

* Input: add Dinner with family s/11-02-2017 6.00 PM
* Expected output: a task with title "Dinner with family" is added to the end of the `incomplete` panel. Under the title, the start time is indicated as `11-02-2017 6.00 PM`. A confirmation message "New task added: ", following by the task's details is printed in the feedback box above the incomplete panel.

* Input: add Read report e/20-04-2017 5.00 PM t/HighPriority t/Urgent
* Expected output: a task with title "Read report" is added to the end of the `incomplete` panel. Under the title, the following will appear in order: tags `HighPriority` and `Urgent`, the end time`20-04-2017 5.00 PM`. A confirmation message "New task added: ", following by the task's details is printed in the feedback box above the incomplete panel.

* Input: add Traveling with Julia s/14-6-2017 e/30-6-2017 t/Travel t/Wife t/Vietnam
* Expected output: a task with title "Traveling with Julia" is added to the end of the `incomplete` panel. Under the title, the following will appear in order: tags `Travel`, `Wife` and `Vietnam`, start time `14-6-2017 12.00 AM`, end time `30-6-2017 12.00 AM`. A confirmation message "New task added: ", following by the task's details is printed in the feedback box above the incomplete panel.

* Input: add Daily jog t/Health t/Daily
* Expected output: a task with title "Daily jog" is added to the end of the `incomplete` panel. Under the title, the following will appear in order: tags `Health`, `Daily`. A confirmation message "New task added: ", following by the task's details is printed in the feedback box above the incomplete panel.

### Edit command

* Input: `edit 43 Project meeting at headquarter s/20-08-2017 10.00 AM e/20-08-2017 11.00 AM t/Urgent`
* Expected outcome: The task with index 43 in the currently displayed list (`Incomplete` list panel), which is the one titled "project meeting", will be replaced with the details specified above. The panel will also automatically scroll to this task and show its updated details (magnified) on the right.

* Input: `edit 1 s/13-04-2017 10.00 AM`
* Expected outcome: A start time `13-04-2017 10.00 AM` will be added to the first task in the current list i.e. task titled "Send email to Maria" will now has a start time as specified. The panel will also automatically scroll to this task and show its updated details (magnified) on the right.

* Input: `edit 5 t/`
* Expected outcome: All the tags of the task "Meet mom and dad for dinner" are removed. The panel will also automatically scroll to this task and show its updated details (magnified) on the right.

### Describe command

* Input: `describe 5 Remember to book Marche@Harbourfront`
* Expected outcome: The specified description will be attached to the task with index 5 on the currently displayed list, which is the task titled "Meet mom and dad for dinner". The description will only be shown on the right but not on the left to avoid cluttering when a long description is given.

### Complete command

* Input: `complete 8`
* Expected outcome: Task with index 8 on the currently displayed list (the task titled "buy soccer ball") is marked as complete. It will disappear from the `Incomplete` list panel at once. It can be seen on the `Complete` list panel instead.

### Delete command

* Input: `list incomplete` and then `delete 3`
* Expected outcome: Task with index 3 on the currently displayed list (the task titled "fetch bobby to school") is deleted from the to-do list. It will disappear from the `Incomplete` list panel at once. A confirmation message "Deleted Task: fetch bobby to school" should appear on the feedback box.

* Input: `list complete` and then `delete 4`
* Expected outcome: Task with index 4 on the currently displayed list (the task titled "send TPS report to Jim") is deleted from the to-do list. It will disappear from the `Complete` list panel at once. A confirmation message "Deleted Task: send TPS report to Jim" should appear on the feedback box.

### List command

* Input: `list all`
* Expected output: a complete list of all the tasks currently in the application will be shown.

* Input: `list incomplete`
* Expected output: a list of all incompleted tasks will be shown. The active tab panel will switch from `All` panel to `Incomplete` panel.

* Input: `list complete`
* Expected output: a list of all completed tasks will be shown. The active tab panel will switch from `Incomplete` panel to `Complete` panel.

* Input: `list overdue`
* Expected output: a list of all overdued tasks, i.e. tasks whose end times are before the current time, will be shown. The active tab panel will switch from `Complete` panel to `Overdue` panel.

* Input: `list upcoming`
* Expected output: a list of all upcoming tasks, i.e. tasks whose end times are after the current time, will be shown. They will be sorted based on urgency, i.e. tasks with the nearest end time will be listed first. The active tab panel will switch from `Overdue` panel to `Upcoming` panel.

### Find command

* Input: `find butterfly`
* Expected outcome: all 7 tasks on the currently displayed list whose titles contains "butterfly" will be listed.

* Input: `complete 3` and then `find butterfly`
* Expected outcome: all 6 tasks on the currently displayed list whose titles contains "butterfly" will be listed, since 1 has just been completed.

* Input: `list incomplete` and then `find t/family`
* Expected outcome: all 4 tasks on the currently displayed list who has the tag "family" will be listed.

* Input: `list incomplete` and then `find t/fam`
* Expected outcome: no tasks are found on the currently displayed list - this requires the tags to be exact.

* Input: `list incomplete` and then `find mom t/family`
* Expected outcome: all 5 tasks on the currently displayed list who either has "mom" in its title or has a tag "family" will be listed.

* Input: `list incomplete` and then `find mom t/family t/work`
* Expected outcome: all 9 tasks on the currently displayed list who either has "mom" in its title or has a tag "family", or has a tag "work" will be listed.

### Undo command

* Input: `list incomplete`, `delete 1`, followed by `undo`
* Expected outcome: the task titled "Send email to Maria" appear again after being deleted from the to-do list. A confirmation message "Undone - Deleted Task: Send email to Maria" appear on the feedback box.

* Input: `list incomplete`, `add fix ipad`, followed by `undo`
* Expected outcome: the task titled "fix ipad" disappear again after being added to to-do list. A confirmation message "Undone - New task added: fix ipad Tags: " appear on the feedback box.

* Input: `list incomplete`, `edit 2 s/11-04-2017 6.00 PM`, followed by `undo`
* Expected outcome: the start time of the task titled "Pick up groceries on the way home" change back to `10-04-2017 6.00 PM` after being changed to `11-04-2017 6.00 PM`. A confirmation message "Undone - Edited Task: Pick up groceries on the way home Start Time: 11-04-2017 6.00 PM Tags: [family]" should appear on the feedback box.

* Input: `list incomplete`, `complete 5`, followed by `undo`
* Expected outcome: the task titled "watch star butterfly" is marked incomplete again after being marked complete. A confirmation message "Undone - Completed Task: watch star butterfly" appear on the feedback box.

* Input: `clear` followed by `undo`
* Expected outcome: the to-do list comes back after it was cleared. A confirmation message "Undone - ToDoList has been cleared!" appear on the feedback box.

### Redo command

* Input: `redo`
* Expected outcome: the to-do list is cleared again. A confirmation message "Redone - ToDoList has been cleared!" should appear on the feedback box.

Remember to key in `undo` to continue with the manual testing.

### Select command

* Input: `select 20`
* Expected outcome: The `Incomplete` panel will automatically scroll to the task index 20 on the currently displayed list (titled "watch soccer match with billy") and show its details (magnified) on the right.

### Sync command

* Input: `sync`
* Expected outcome: The app will open a new browser window asking for permission to access your Google Calendar. Upon allowance, it will delete everything in your Google Calendar and upload all the tasks currently in the app to your Google Calendar. A confirmation message "Tasks have been synced successfully" will appear in the feedback box.

* Input: `delete ...` and then `sync`
* Expected outcome: A confirmation message "Tasks have been synced successfully" will appear in the feedback box. Upon checking your Google Calendar, you'll see the task with title "..." has been deleted from your calendar.

### Clear command

* Input: `clear`
* Expected outcome: All the tasks stored in the app are deleted. A confirmation message "To-do List has been cleared!" will appear in the feedback box.

### Change storage command

* Input: `changestorage newdata`, `add testTask t/testing` and then `exit`.
* Expected outcome: A new folder titled `newdata` will be created in the directory where this app is located. It contains `todolist.xml`, which is the new storage location of DoMe. Upon examination of this file after exiting the app, we can see a task titled "testTask", but we do not see that task in `data/todolist.xml`.

* Input: click to open the app
* Expected outcome: The app loads normally from the new storage location. Path to this location can also be spotted at the lower border of the app window.

### Export save command

* Input: `exportsave anotherplace`, `add testTask2 t/testing` and then `exit`.
* Expected outcome: A new folder titled `anotherplace` will be created in the directory where this app is located. It contains `todolist.xml`, which is a clone of `newdata/todolist.xml`. Upon examination of `newdata/todolist.xml` after exiting the app, we  see a task titled "testTask", but we do not see that task in `anotherplace/todolist.xml`.

### Command suggestion

* Input: `addd fill car fuel`
* Expected outcome: Command box will be coloured red, and the feedback box will tell the user that he/she has keyed in an unknown command. It also proceeds to suggest the most likely command that the user wants to type, which is `add`.

* Input: `dele 21`
* Expected outcome: Command box will be coloured red, and the feedback box will tell the user that he/she has keyed in an unknown command. It also proceeds to suggest the most likely command that the user wants to type, which is `delete`.