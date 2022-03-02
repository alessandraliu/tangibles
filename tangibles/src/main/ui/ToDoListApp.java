package ui;

import model.Task;
import model.ToDoList;
import model.UrgencyLevel;
import model.exceptions.EmptyTitleException;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;


// Application for to-do list
// Some code is attributed to the source, TellerApp
public class ToDoListApp {
    private static final String JSON_STORE = "./data/todolist.json";
    private Scanner input;
    private ToDoList currentList;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // MODIFIES: this
    // EFFECTS: initializes
    private void init() {
        input = new Scanner(System.in);
    }

    // EFFECTS: runs the to-do list application
    public ToDoListApp() throws FileNotFoundException {
        init();
        currentList = new ToDoList("Ally's to-do list");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runToDoList();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    public void runToDoList() {
        boolean keepGoing = true;
        String command = null;
        init();

        while (keepGoing) {
            displayMenu();
            command = input.nextLine();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }
        System.out.println("\nGo complete those tasks!");
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nOptions:");
        System.out.println("\tc -> Create task");
        System.out.println("\td -> Delete task");
        System.out.println("\ts -> Save to-do list file");
        System.out.println("\tl -> Load to-do list file");
        System.out.println("\tp -> Print to-do list file");
        System.out.println("\tq -> Quit");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("c")) {
            createsTask();
        } else if (command.equals("d")) {
            deletesTask();
        } else if (command.equals("p")) {
            printToDoList();
        } else if (command.equals("s")) {
            saveToDoList();
        } else if (command.equals("l")) {
            loadToDoList();
        } else {
            System.out.println("Selection not valid!");
        }
    }

    // MODIFIES: this
    // EFFECTS: creates a task item
    private void createsTask() {
        System.out.println("Enter title of task:");
        String title = input.nextLine();

        System.out.println("Select urgency level of task:");
        printChoices();
        String choice = input.nextLine();
        UrgencyLevel level = selectUrgencyLevel(choice);

        System.out.println("Add additional notes:");
        String additionalNotes = input.nextLine();

        try {
            Task task = new Task(level, title, additionalNotes);
            currentList.addTask(task);
        } catch (EmptyTitleException e) {
            System.out.println("Cannot make a task item without a title!");
        }
    }

    // MODIFIES: this
    // EFFECTS: deletes a task item by index
    private void deletesTask() {
        System.out.println("Select task to delete (by index; 0 being the first task):");
        int index = input.nextInt();

        if (currentList.isEmpty()) {
            System.out.println("Operation not valid!");
        } else {
            currentList.remove(index);
        }
        input.nextLine();
    }

    // EFFECTS: returns the selected urgency type
    private UrgencyLevel selectUrgencyLevel(String choice) {
        UrgencyLevel ut = UrgencyLevel.EXTREMELY;

        switch (choice) {
            case "1":
                ut = UrgencyLevel.EXTREMELY;
                break;
            case "2":
                ut = UrgencyLevel.MODERATELY;
                break;
            case "3":
                ut = UrgencyLevel.SLIGHTLY;
        }
        return ut;
    }

    // EFFECTS: prints urgency type choices
    private void printChoices() {
        System.out.println("1: EXTREMELY");
        System.out.println("2: MODERATELY");
        System.out.println("3: SLIGHTLY");
    }

    //EFFECTS: prints all tasks in to-do list to the console
    private void printToDoList() {
        List<Task> tasks = currentList.getTasks();

        for (Task t: tasks) {
            System.out.println(t);
        }
    }

    // EFFECTS: saves the to-do list to file
    private void saveToDoList() {
        try {
            jsonWriter.open();
            jsonWriter.write(currentList);
            jsonWriter.close();
            System.out.println("Saved " + currentList.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads to-do list from file
    private void loadToDoList() {
        try {
            currentList = jsonReader.read();
            System.out.println("Loaded " + currentList.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
