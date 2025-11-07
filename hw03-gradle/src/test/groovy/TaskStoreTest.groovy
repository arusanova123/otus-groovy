import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions
import org.example.model.Action
import org.example.model.Task
import org.example.store.TaskStore

import java.lang.invoke.VarHandleInts
import java.time.LocalDateTime

class TaskStoreTest {
    //given
    TaskStore store = new TaskStore()
    Task task1 = new Task ("Task1", LocalDateTime.of(2025,05,15,15,00))
    Task task2 = new Task ("Task2", LocalDateTime.of(2025,05,15,17,00))
    Task task3 = new Task ("Task3", LocalDateTime.of(2025,05,16,10,00))
    @Test
    void testAdd() {
        //when
        store.add(task1)
        //then
        Assertions.assertTrue(store.tasks.equals([task1]))
    }
    @Test
    void testDelete() {
        //given
        store.add(task1)
        store.add(task2)
        //when
        store.delete (task2)
        //then
        Assertions.assertTrue(store.tasks.equals([task1]))
    }
    @Test
    void testGetByName() {
        //given
        store.add(task1)
        store.add(task2)
        //when
        def foundTask = store.getByName ("Task1")
        //then
        Assertions.assertEquals(task1, foundTask)
    }
    @Test
    void testDeleteByName() {
        //given
        store.add(task1)
        store.add(task2)
        //when
        store.deleteByName ("Task2")
        //then
        Assertions.assertTrue(store.tasks.equals([task1]))
    }
    @Test
    void testFindByDate() {
        //given
        store.add(task1)
        store.add(task2)
        store.add(task3)
        //when
        def tasksOfDate = store.findByDate(LocalDate.of(2025,05,15))
        def tasksOfDate2 = store.findByDate(LocalDate.of(2025,05,16))
        //then
        Assertions.assertTrue(tasksOfDate.equals([task1, task2]))
        Assertions.assertTrue(tasksOfDate2.equals([task3]))
    }
}
