package ui;

import model.Task;
import model.ToDoList;
import model.UrgencyLevel;
import model.exceptions.EmptyTitleException;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

// Graphical User Interface for to-do list
public class ToDoListGUI extends JFrame implements ActionListener {

    public static final int WIDTH = 500;
    public static final int HEIGHT = 200;

    private static final String JSON_STORE = "./data/todolist.json";

    private JPanel menu;
    private JButton b1;
    private JButton b2;
    private JButton b3;
    private JButton b4;
    private JButton b5;

    private JPanel addTaskPanel;
    private JButton addTask;
    private JLabel title;
    private JLabel urgencyLevel;
    private JLabel addNotes;
    private JComboBox<UrgencyLevel> ul;
    private JTextField t1;
    private JTextField t2;

    private ToDoList toDoList;
    private JList<Task> currentList;
    private DefaultListModel<Task> listModel;

    private JPanel toDoListPanel;
    private JButton deleteButton;
    private JButton returnToMenuButton;

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // Constructs a new JFrame
    public ToDoListGUI() {
        super("Tangibles");
        toDoList = new ToDoList("To-Do List");
        setPreferredSize(new Dimension(WIDTH,HEIGHT));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        initializeMenu();
        initializeMenuButtons();
        addButtonsToMenu();
        addActionToButton();

        createAddTaskPanel();
        viewAndDeleteTaskPanel();

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        menu.setVisible(true);
    }

    // EFFECTS: initializes menu panel
    public void initializeMenu() {
        menu = new JPanel();
        menu.setLayout(new BoxLayout(menu, BoxLayout.PAGE_AXIS));
        menu.setBackground(Color.LIGHT_GRAY);
        add(menu);
        menu.add(Box.createVerticalStrut(15));
    }

    // EFFECTS: initializes menu buttons with labels
    public void initializeMenuButtons() {
        b1 = new JButton("Add Task");
        b2 = new JButton("Save To-Do List");
        b3 = new JButton("Load To-Do List");
        b4 = new JButton("View/Delete Tasks");
        b5 = new JButton("Quit");
    }

    // MODIFIES: this
    // EFFECTS: adds button to menu
    public void addButton(JButton b, JPanel panel) {
        b.setBackground(Color.WHITE);
        b.setFont(new Font("Calibri", Font.BOLD, 12));
        b.setAlignmentX(CENTER_ALIGNMENT);
        b.setAlignmentY(CENTER_ALIGNMENT);
        panel.add(b);
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // EFFECTS: calls addButton for each button
    public void addButtonsToMenu() {
        addButton(b1, menu);
        addButton(b2, menu);
        addButton(b3, menu);
        addButton(b4, menu);
        addButton(b5, menu);
    }

    // MODIFIES: this
    // EFFECTS: sets the action of each button
    public void addActionToButton() {
        b1.addActionListener(this);
        b1.setActionCommand("Add Task");
        b2.addActionListener(this);
        b2.setActionCommand("Save To-Do List");
        b3.addActionListener(this);
        b3.setActionCommand("Load To-Do List");
        b4.addActionListener(this);
        b4.setActionCommand("View/Delete Tasks");
        b5.addActionListener(this);
        b5.setActionCommand("Quit");
    }

    // EFFECTS: invokes method(s) when button is clicked
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Add Task":
                initializeAddTaskPanel();
                break;
            case "Save To-Do List":
                playSound("./data/chime.wav");
                saveToDoList();
                break;
            case "Load To-Do List":
                loadToDoList();
                break;
            case "View/Delete Tasks":
                initializeViewAndDeleteTaskPanel();
                break;
            case "RETURN TO MENU":
                returnToMenu();
                break;
            case "ADD TASK":
                addsTask();
                break;
            case "Quit":
                System.exit(0);
        }
    }

    // MODIFIES: this
    // EFFECTS: creates add task panel
    public void createAddTaskPanel() {
        addTaskPanel = new JPanel(new GridLayout(4,0));
        addTaskPanel.setBorder(new EmptyBorder(10,10,10,10));

        returnToMenuButton = new JButton("RETURN TO MENU");
        returnToMenuButton.setActionCommand("RETURN TO MENU");
        returnToMenuButton.addActionListener(this);
        addButton(returnToMenuButton, addTaskPanel);

        createFieldsForAddTaskPanel();
        addLabelsToAddTaskPanel();
    }

    // EFFECTS: adds the add task panel to screen
    public void initializeAddTaskPanel() {
        add(addTaskPanel);
        addTaskPanel.setBackground(Color.LIGHT_GRAY);
        addTaskPanel.setVisible(true);
        menu.setVisible(false);
        toDoListPanel.setVisible(false);
    }

    // MODIFIES: this
    // EFFECTS: creates fields for user to type into
    public void createFieldsForAddTaskPanel() {
        urgencyLevel = new JLabel(" Select urgency level of task: ");
        ul = new JComboBox<>(UrgencyLevel.values());

        title = new JLabel(" Enter title of task: ");
        t1 = new JTextField(20);

        addNotes = new JLabel(" Add additional notes: ");
        t2 = new JTextField(20);

        addTask = new JButton("ADD TASK");
        addTask.setFont(new Font("Calibri", Font.BOLD, 12));
        addTask.setActionCommand("ADD TASK");
        addTask.addActionListener(this);
    }

    // EFFECTS: adds user input labels onto panel
    public void addLabelsToAddTaskPanel() {
        addTaskPanel.add(addTask);
        addTaskPanel.add(urgencyLevel);
        addTaskPanel.add(ul);
        addTaskPanel.add(title);
        addTaskPanel.add(t1);
        addTaskPanel.add(addNotes);
        addTaskPanel.add(t2);
    }

    // MODIFIES: this
    // EFFECTS: adds task to to-do list
    public void addsTask() {
        try {
            Task task = new Task((UrgencyLevel) ul.getSelectedItem(), t1.getText(), t2.getText());
            toDoList.addTask(task);
            listModel.addElement(task);
        } catch (EmptyTitleException e) {
            System.out.println("Cannot make a task item without a title!");
        }
    }

    // MODIFIES: this
    // EFFECTS: creates panel that displays list of tasks
    public void viewAndDeleteTaskPanel() {
        toDoListPanel = new JPanel();

        returnToMenuButton = new JButton("RETURN TO MENU");
        returnToMenuButton.setActionCommand("RETURN TO MENU");
        returnToMenuButton.addActionListener(this);

        deleteButton = new JButton("DELETE TASK");
        deleteButton.setActionCommand("DELETE TASK");
        deleteButton.addActionListener(new DeleteListener());

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout());
        buttonPane.setBackground(Color.LIGHT_GRAY);
        buttonPane.add(returnToMenuButton);
        buttonPane.add(deleteButton);
        buttonPane.setVisible(true);

        displayListOfTasks();
        toDoListPanel.add(buttonPane);
        toDoListPanel.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: displays list of tasks on panel,
    //          gives user the option to select and delete a task
    // Some code is attributed to the source, ListDemo from Oracle
    public void displayListOfTasks() {
        listModel = new DefaultListModel<>();

        for (Task toDoListTask : toDoList.getTasks()) {
            listModel.addElement(toDoListTask);
        }

        currentList = new JList<>(listModel);
        currentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        currentList.setSelectedIndex(0);
        currentList.addListSelectionListener(e -> { });
        currentList.setVisibleRowCount(5);
        currentList.setMinimumSize(new Dimension(50,50));
        toDoListPanel.add(currentList);
    }

    class DeleteListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int index = currentList.getSelectedIndex();
            toDoList.deleteTask(listModel.remove(index));

            int size = listModel.getSize();
            if (size == 0) {
                deleteButton.setEnabled(false);
            }
        }
    }

    // EFFECTS: adds view/delete task panel
    public void initializeViewAndDeleteTaskPanel() {
        add(toDoListPanel);
        toDoListPanel.setBackground(Color.LIGHT_GRAY);
        toDoListPanel.setVisible(true);
        menu.setVisible(false);
        addTaskPanel.setVisible(false);
    }

    // EFFECTS: saves current to-do list
    private void saveToDoList() {
        try {
            jsonWriter.open();
            jsonWriter.write(toDoList);
            jsonWriter.close();
            System.out.println("Saved " + toDoList.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads current to-do list
    private void loadToDoList() {
        try {
            toDoList = jsonReader.read();
            listModel.clear();
            for (Task toDoListTask : toDoList.getTasks()) {
                listModel.addElement(toDoListTask);
            }
            System.out.println("Loaded " + toDoList.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // EFFECTS: returns to main menu
    public void returnToMenu() {
        menu.setVisible(true);
        addTaskPanel.setVisible(false);
        toDoListPanel.setVisible(false);
    }

    // EFFECTS: plays given sound
    // Some code is attributed to https://stackoverflow.com/questions/26305/how-can-i-play-sound-in-java
    public void playSound(String soundName) {
        try {
            Clip clip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(soundName));
            clip.open(inputStream);
            clip.start();
        } catch (Exception e) {
            System.out.println("Error playing sound.");
        }
    }
}