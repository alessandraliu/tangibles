package persistence;

import model.Task;
import model.ToDoList;
import model.UrgencyLevel;
import model.exceptions.EmptyTitleException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads to-do list from JSON data stored in file
// Some code is attributed to the source, JsonSerializationDemo
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads to-do list from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ToDoList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseToDoList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }

    // EFFECTS: parses to-do list from JSON object and returns it
    private ToDoList parseToDoList(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        ToDoList list = new ToDoList(name);
        addsTasks(list, jsonObject);
        return list;
    }

    // MODIFIES: list
    // EFFECTS: parses tasks from JSON object and adds them to to-do list
    private void addsTasks(ToDoList list, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("list");
        for (Object json: jsonArray) {
            JSONObject nextTask = (JSONObject) json;
            addsTask(list, nextTask);
        }
    }

    // MODIFIES: list
    // EFFECTS: parses task from JSON object and adds it to to-do list
    private void addsTask(ToDoList list, JSONObject jsonObject) {
        String title = jsonObject.getString("title");
        String additionalNotes = jsonObject.getString("additional notes");
        UrgencyLevel level = UrgencyLevel.valueOf(jsonObject.getString("urgency level"));

        Task task = null;
        try {
            task = new Task(level, title, additionalNotes);
        } catch (EmptyTitleException e) {
            e.printStackTrace();
        }
        list.addTask(task);
    }
}
