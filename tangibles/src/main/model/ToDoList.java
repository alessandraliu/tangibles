package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Represents a to-do list of tasks
public class ToDoList implements Writable {
    private String name;
    private List<Task> list;

    // EFFECTS: constructs an empty to-do list with a name
    public ToDoList(String name) {
        this.name = name;
        list = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    // MODIFIES: this
    // EFFECTS: task is added to to-do list
    public void addTask(Task task) {
        list.add(task);
    }

    // MODIFIES: this
    // EFFECTS: task is deleted from to-do list
    public void deleteTask(Task task) {
        list.remove(task);
    }

    // EFFECTS: returns true if task is in to-do list
    //          and false otherwise
    public boolean contains(Task task) {
        return list.contains(task);
    }

    // EFFECTS: returns the number of task items in to-do list
    public int size() {
        return list.size();
    }

    // EFFECTS: returns true if to-do list is empty
    public boolean isEmpty() {
        return list.size() == 0;
    }

    // EFFECTS: removes a task item from to-do list
    public void remove(int index) {
        list.remove(index);
    }

    // EFFECTS: returns a task item from to-do list
    public Task getTask(int index) {
        return list.get(index);
    }

    // EFFECTS: returns an unmodifiable list of tasks in this to-do list
    public List<Task> getTasks() {
        return Collections.unmodifiableList(list);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("list", tasksToJson());
        return json;
    }

    // EFFECTS: returns things in this to-do list as a JSON array
    private JSONArray tasksToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Task t: list) {
            jsonArray.put(t.toJson());
        }
        return jsonArray;
    }
}
