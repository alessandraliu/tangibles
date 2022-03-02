package model;

import model.exceptions.EmptyTitleException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static model.UrgencyLevel.*;
import static org.junit.jupiter.api.Assertions.*;

public class ToDoListTest {

    private ToDoList testList;
    private Task task1;
    private Task task2;
    private Task task3;

    @BeforeEach
    public void runBefore() throws EmptyTitleException {
        task1 = new Task(EXTREMELY,"prepare work presentation", "due tomorrow");
        task2 = new Task(MODERATELY,"clean apartment", "remember to vacuum");
        task3 = new Task(SLIGHTLY,"go online shopping", "don't spend too much money");
        testList = new ToDoList("Ally's to-do list");
    }

    @Test
    public void testAddTask() {
        testList.addTask(task1);
        assertTrue(testList.contains(task1));
    }

    @Test
    public void testDeleteTask() {
        testList.addTask(task2);
        testList.addTask(task3);
        testList.deleteTask(task2);
        assertFalse(testList.contains(task2));
        assertTrue(testList.contains(task3));
    }

    @Test
    public void testSize() {
        testList.addTask(task1);
        testList.addTask(task2);
        testList.addTask(task3);
        assertEquals(3, testList.size());
    }

    @Test
    public void testIsEmpty() {
        testList.addTask(task2);
        assertFalse(testList.isEmpty());
        testList.deleteTask(task2);
        assertTrue(testList.isEmpty());
    }

    @Test
    public void testRemove() {
        testList.addTask(task1);
        testList.addTask(task3);
        testList.remove(0);
        assertFalse(testList.contains(task1));
        assertTrue(testList.contains(task3));
    }

    @Test
    public void testGetTask() {
        testList.addTask(task1);
        testList.addTask(task2);
        testList.addTask(task3);
        assertEquals(task1, testList.getTask(0));
    }
}

