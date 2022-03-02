package persistence;

import model.Task;
import model.ToDoList;
import model.UrgencyLevel;
import model.exceptions.EmptyTitleException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            ToDoList list = new ToDoList("My to-do list");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("Should have thrown exception.");
        } catch (IOException e) {
        }
    }

    @Test
    void testWriterEmptyToDoList() {
        try {
            ToDoList list = new ToDoList("My to-do list");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyToDoList.json");
            writer.open();
            writer.write(list);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyToDoList.json");
            list = reader.read();
            assertEquals("My to-do list", list.getName());
            assertEquals(0, list.size());
        } catch (IOException e) {
            fail("Exception should not have been thrown.");
        }
    }

    @Test
    void testWriterGeneralToDoList() {
        try {
            ToDoList list = new ToDoList("My to-do list");
            list.addTask(new Task(UrgencyLevel.MODERATELY,"walk the dog", "after dinner"));
            list.addTask(new Task(UrgencyLevel.EXTREMELY,"submit assignment", "by tonight"));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralToDoList.json");
            writer.open();
            writer.write(list);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralToDoList.json");
            list = reader.read();
            assertEquals("My to-do list", list.getName());
            List<Task> tasks = list.getTasks();
            assertEquals(2, tasks.size());
            checkTask(UrgencyLevel.MODERATELY,"walk the dog", "after dinner", tasks.get(0));
            checkTask(UrgencyLevel.EXTREMELY,"submit assignment", "by tonight", tasks.get(1));
        } catch (EmptyTitleException e) {
            fail("Exception should not have been thrown.");
        } catch (IOException e) {
            fail("Exception should not have been thrown.");
        }
    }
}
