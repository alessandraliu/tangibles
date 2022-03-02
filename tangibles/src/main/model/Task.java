package model;

import model.exceptions.EmptyTitleException;
import org.json.JSONObject;
import persistence.Writable;

// Represents a task having a title and urgency type
public class Task implements Writable {

    private String title;
    private UrgencyLevel level;
    private String additionalNotes;

    // EFFECTS: creates a task with title and urgency type
    //          when a task is created, its status is not yet completed
    //          if title is the empty string, throws EmptyTitleException
    public Task(UrgencyLevel level, String title, String additionalNotes) throws EmptyTitleException {
        if (title.isEmpty()) {
            throw new EmptyTitleException();
        }
        this.level = level;
        this.title = title;
        this.additionalNotes = additionalNotes;
    }

    public String getTitle() {
        return title;
    }

    public UrgencyLevel getUrgencyLevel() {
        return level;
    }

    public String getAddNotes() {
        return additionalNotes;
    }

    // EFFECTS: returns string representation of this task
    public String toString() {
        return "TITLE: " + getTitle() + ", URGENCY LEVEL: " + getUrgencyLevel() + ", ADDITIONAL NOTES: "
                + getAddNotes();
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("title", title);
        json.put("urgency level", level);
        json.put("additional notes", additionalNotes);
        return json;
    }
}
