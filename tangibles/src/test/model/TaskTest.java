package model;

import model.exceptions.EmptyTitleException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static model.UrgencyLevel.*;
import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {

    private Task task1;
    private Task task2;
    private Task task3;

    @BeforeEach
    public void runBefore() throws EmptyTitleException {
        task1 = new Task(EXTREMELY,"prepare work presentation", "due tomorrow");
        task2 = new Task(MODERATELY,"clean apartment", "remember to vacuum");
        task3 = new Task(SLIGHTLY,"go online shopping", "don't spend too much money");
    }

    @Test
    public void testTaskHasTitle() {
        try{
            Task task4 = new Task(EXTREMELY, "do laundry", "wash gym clothes");
        } catch (EmptyTitleException e) {
            fail("Should not have thrown exception.");
        }
    }

    @Test
    public void testTaskEmptyTitleException() {
        try {
            Task task5 = new Task(EXTREMELY,"", "due tonight");
            fail("Should have thrown exception.");
        } catch (EmptyTitleException e) {
        }
    }

    @Test
    public void testGetters() {
        assertEquals("prepare work presentation", task1.getTitle());
        assertEquals(EXTREMELY, task1.getUrgencyLevel());
        assertEquals("clean apartment", task2.getTitle());
        assertEquals(MODERATELY, task2.getUrgencyLevel());
        assertEquals("go online shopping", task3.getTitle());
        assertEquals(SLIGHTLY, task3.getUrgencyLevel());
        assertEquals("due tomorrow", task1.getAddNotes());
    }

    @Test
    public void testToString() {
        assertEquals("TITLE: prepare work presentation, URGENCY LEVEL: EXTREMELY, " +
                "ADDITIONAL NOTES: due tomorrow", task1.toString());
    }
}
