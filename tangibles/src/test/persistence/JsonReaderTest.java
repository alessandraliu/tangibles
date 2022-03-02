package persistence;

import model.Task;
import model.ToDoList;
import model.UrgencyLevel;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            ToDoList list = reader.read();
            fail("Should have thrown exception.");
        } catch (IOException e) {
        }
    }

    @Test
    void testReaderEmptyToDoList() {
        JsonReader reader = new JsonReader("./data/testWriterEmptyToDoList.json");
        try {
            ToDoList list = reader.read();
            assertEquals("My to-do list", list.getName());
            assertEquals(0, list.size());
        } catch (IOException e) {
            fail("Exception should not have been thrown.");
        }
    }

    @Test
    void testReaderGeneralToDoList() {
        JsonReader reader = new JsonReader("./data/testWriterGeneralToDoList.json");
        try {
            ToDoList list = reader.read();
            assertEquals("My to-do list", list.getName());
            List<Task> tasks = list.getTasks();
            assertEquals(2, tasks.size());
            checkTask(UrgencyLevel.MODERATELY,"walk the dog", "after dinner", tasks.get(0));
            checkTask(UrgencyLevel.EXTREMELY,"submit assignment", "by tonight", tasks.get(1));
        } catch (IOException e) {
            fail("Exception should not have been thrown.");
        }
    }
}