import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions
import org.example.model.Action
import org.example.model.Task
import org.example.service.ToDoListManager

import java.time.LocalDateTime

public class ToDoListManagerTest{
    ToDoListManager toDo = new ToDoListManager()
    Task task1 = new Task ("Task1", LocalDateTime.of(2025,05,15,15,00))
    Task task2 = new Task ("Task2", LocalDateTime.of(2025,05,15,17,00))
    Task task3 = new Task ("Task3", LocalDateTime.of(2025,05,16,10,00))

    @Test
    void testAddTask() {
        //when
        String res1 = toDo.addTask("Task1", LocalDateTime.of(2025,05,15,15,00))
        //then
        Assertions.assertTrue (toDo.getAllTasks().equals ([task1]))
    }
    @Test
    void testDeleteTask() {
        //when
        toDo.addTask("Task1", LocalDateTime.of(2025,05,15,15,00))
        toDo.addTask("Task2", LocalDateTime.of(2025,05,15,17,00))
        toDo.deleteTask("Task1")
        //then
        Assertions.assertTrue (toDo.getAllTasks().equals ([task2]))
    }
    @Test
    void testAddAction() {
        //given
        toDo.addTask("Task1", LocalDateTime.of(2025,05,15,15,00))
        //when
        toDo.addAction ("Task1", "Action1", Duration.ofMinutes(15))
        //then
        String expected = "[Task: Task1 \t [Start: 15.05.25 15:00 \t End: 15.05.25 15:15 \t Actions: 1]]"
        Assertions.assertEquals(expected, toDo.getAllTasks().toString())
    }
    @Test
    void testDeleteAction() {
        //given
        toDo.addTask("Task1", LocalDateTime.of(2025,05,15,15,00))
        toDo.addAction ("Task1", "Action1", Duration.ofMinutes(15))
        toDo.addAction ("Task1", "Action2", Duration.ofMinutes(20))
        //when
        toDo.deleteAction ("Task1", "Action1")
        //then
        String expected = "[Task: Task1 \t [Start: 15.05.25 15:00 \t End: 15.05.25 15:20 \t Actions: 1]]"
        Assertions.assertEquals(expected, toDo.getAllTasks().toString())
    }
    @Test
    void testGetAllTasks() {
        //given
        toDo.addTask("Task1", LocalDateTime.of(2025,05,15,15,00))
        toDo.addAction ("Task1", "Action1", Duration.ofMinutes(15))
        //when
        String expected = "[Task: Task1 \t [Start: 15.05.25 15:00 \t End: 15.05.25 15:15 \t Actions: 1]]"
        //then
        Assertions.assertEquals(expected, toDo.getAllTasks().toString())
    }
    @Test
    void testGetAmountOfTasksByDate() {
        //given
        toDo.addTask("Task1", LocalDateTime.of(2025,05,15,15,00))
        toDo.addTask("Task2", LocalDateTime.of(2025,05,15,16,00))
        toDo.addTask("Task3", LocalDateTime.of(2025,05,17,10,00))
        //then
        Assertions.assertEquals(2, toDo.getAmountOfTasksByDate(LocalDate.of(2025,05,15)))
    }
    @Test
    void testGetTasksByDate() {
        //given
        toDo.addTask("Task1", LocalDateTime.of(2025,05,15,15,00))
        toDo.addTask("Task2", LocalDateTime.of(2025,05,15,16,00))
        toDo.addTask("Task3", LocalDateTime.of(2025,05,17,10,00))
        //then
        String expected =
                "[Task: Task1 \t [Start: 15.05.25 15:00 \t End: 15.05.25 15:00 \t Actions: 0], "+
                 "Task: Task2 \t [Start: 15.05.25 16:00 \t End: 15.05.25 16:00 \t Actions: 0]]"
        Assertions.assertEquals(expected, toDo.getTasksByDate(LocalDate.of(2025,05,15)).toString())
    }
    @Test
    void testShowBusyTime() {
        //given
        toDo.addTask("Task1", LocalDateTime.of(2025,05,15,15,00))
        toDo.addAction ("Task1", "Action1", Duration.ofMinutes(15))
        toDo.addTask ("Task2", LocalDateTime.of(2025,05,15,15,30))
        toDo.addAction ("Task2", "Action3", Duration.ofMinutes(35))
        //then
        Assertions.assertEquals ("0:50", toDo.showBusyTime(LocalDate.of(2025,05,15)))
    }
    @Test
    void testInterSections() {
        //given
        toDo.addTask("Task1", LocalDateTime.of(2025,05,15,15,00))
        toDo.addAction ("Task1", "Action1", Duration.ofMinutes(15))
        //try1
        String err1 = toDo.addTask("Task2", LocalDateTime.of(2025,05,15,15,00))
        String expectedErr1 = "Intersection with Task: Task1 \t [Start: 15.05.25 15:00 \t End: 15.05.25 15:15 \t Actions: 1]"
        Assertions.assertEquals(expectedErr1, err1)
        //try2
        toDo.addTask("Task2", LocalDateTime.of(2025,05,15,15,20))
        String err2 = toDo.addAction ("Task1", "Action1", Duration.ofMinutes(25))
        String expectedErr2 = "Intersection with Task: Task2 \t [Start: 15.05.25 15:20 \t End: 15.05.25 15:20 \t Actions: 0]"
        Assertions.assertEquals(expectedErr2, err2)
        //try3
        toDo.addTask("Task2", LocalDateTime.of(2025,05,17,15,00))
        String err3 = toDo.addTask("Task3", LocalDateTime.of(2025,05,17,15,00))
        Assertions.assertEquals("", err3)
        String err4 = toDo.addAction ("Task3", "Action3", Duration.ofMinutes(25))
        String expectedErr3 = "Intersection with Task: Task2 \t [Start: 15.05.25 15:00 \t End: 15.05.25 15:00 \t Actions: 0]"
        Assertions.assertEquals(expectedErr2, err2)
    }
}
