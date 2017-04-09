package seedu.todolist.testutil;

import seedu.todolist.commons.exceptions.IllegalValueException;
import seedu.todolist.logic.parser.TaskParser;
import seedu.todolist.model.ToDoList;
import seedu.todolist.model.task.UniqueTaskList;

/**
 *
 */
public class TypicalTestTasks {

    public TestTask bossEmail, presentation, dinner, homework, goToGym, helpJohn,
                           helpColleague, dating, changeUi, bookTicket, bookTicketOther,
                           doAlgebraHomework, dentistAppointment, bookAirBnbHousing,
                           updateDigitalSignature, handleCustomerComplaints;

    public TypicalTestTasks() {
        try {
            bossEmail = new TaskBuilder().withName("Reply Boss's Email")
                    .withStartTime("30-06-2017 10.00 AM")
                    .withEndTime("30-06-2017 3.00 PM")
                    .withTags("Work", "Important", "Urgent").build();
            presentation = new TaskBuilder().withName("Presentation")
                    .withEndTime("31-04-2017 11.59 PM")
                    .withTags("School", "Team").build();
            dinner = new TaskBuilder().withName("Dinner")
                    .withStartTime("14-02-2017 6.30 PM")
                    .build();
            homework = new TaskBuilder().withName("Symmetric Bilinear Form")
                    .withTags("Math", "Difficult")
                    .build();
            goToGym = new TaskBuilder().withName("Go To Gym")
                    .withStartTime("14-04-2017 4.00 PM")
                    .withEndTime("14-04-2017 5.00 PM")
                    .withDescription("Do 70 push-ups and 250 kg leg press")
                    .withTags("Health")
                    .build();
            helpColleague = new TaskBuilder().withName("Help Duc with Excel")
                    .withStartTime("02-09-2017 5.17 PM")
                    .withTags("Colleagues", "Excel")
                    .build();
            dating = new TaskBuilder().withName("Date with girlfriend")
                    .withStartTime("10-10-2017 3.00 PM")
                    .withDescription("Remember to book restaurant and buy roses")
                    .build();
            doAlgebraHomework = new TaskBuilder().withName("Field Embedding Theorem")
                    .withTags("Algebra", "Difficult", "NumberTheory")
                    .withCompletion(true)
                    .build();
            dentistAppointment = new TaskBuilder().withName("See dentist")
                    .withStartTime("3-4-2016 9 AM")
                    .withEndTime("3-4-2016 10 AM")
                    .withTags("Important", "Dentist")
                    .build();
            bookAirBnbHousing = new TaskBuilder().withName("book housing on Italy trip")
                    .withEndTime("1-3-2017 10 PM")
                    .withTags("Important", "Housing", "Travel")
                    .withDescription("Book housing on AirBnB to save money")
                    .build();
            updateDigitalSignature = new TaskBuilder().withName("Update expired email digital signature")
                    .withEndTime("1-2-2017")
                    .withTags("Work", "Email", "Signature")
                    .withCompletion(true)
                    .build();
            handleCustomerComplaints = new TaskBuilder().withName("Handle complaints from customer on products")
                    .withStartTime("24-4-2017 10 AM")
                    .withEndTime("24-4-2017 10 PM")
                    .withTags("Work", "Important")
                    .build();

            // Manually added
            changeUi = new TaskBuilder().withName("Change User Interface").build();
            bookTicket = new TaskBuilder().withName("Book Flight Ticket").build();
            bookTicketOther = new TaskBuilder().withName("Book Flight Ticket")
                    .withStartTime("14-04-2017 10.00 AM")
                    .withEndTime("14-04-2017 10.00 PM")
                    .build();
            helpJohn = new TaskBuilder().withName("Help do John's report").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadToDoListWithSampleData(ToDoList ab) {
        for (TestTask task : new TypicalTestTasks().getTypicalTasks()) {
            try {
                ab.addTask(TaskParser.parseTask(task));
            } catch (UniqueTaskList.DuplicateTaskException e) {
                assert false : "not possible";
            }
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[] { bossEmail, presentation, dinner, homework, goToGym, helpColleague, dating,
            doAlgebraHomework, dentistAppointment, bookAirBnbHousing, updateDigitalSignature,
            handleCustomerComplaints };
    }

    public ToDoList getTypicalToDoList() {
        ToDoList ab = new ToDoList();
        loadToDoListWithSampleData(ab);
        return ab;
    }
}
