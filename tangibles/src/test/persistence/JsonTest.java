package persistence;

import model.Task;
import model.UrgencyLevel;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkTask(UrgencyLevel level, String title, String additionalNotes, Task task) {
        assertEquals(level, task.getUrgencyLevel());
        assertEquals(title, task.getTitle());
        assertEquals(additionalNotes, task.getAddNotes());
    }
}
