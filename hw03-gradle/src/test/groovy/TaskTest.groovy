import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions
import org.example.model.Action
import org.example.model.Task

import java.time.Duration
import java.time.LocalDateTime

class TaskTest {
    //given
    LocalDateTime dateTime = LocalDateTime.of(2025,05,15,15,00)

    Duration duration1 = Duration.ofMinutes (10)
    Duration duration2 = Duration.ofMinutes (25)

    Action action1 = new Action("Action1", duration1)
    Action action2 = new Action("Action2", duration2)

    @Test
    void testAdd () {
        //given
        Task task = new Task ("Task", dateTime)
        //when
        task.add(action1)
        task.add(action2)
        //then
        Assertions.assertTrue(task.actions.equals([action1,action2]))
        Assertions.assertEquals (dateTime, action1.start)
        Assertions.assertEquals (dateTime+duration1, action2.start)
    }
    @Test
    void testDelete () {
        //given
        Task task = new Task ("Task", dateTime)
        task.add(action1)
        task.add(action2)
        //when
        task.delete(action1)
        //then
        Assertions.assertTrue(task.actions.equals([action2]))
        Assertions.assertEquals (dateTime, task.actions[0].start)
    }

    @Test
    void testDeleteByName () {
        //given
        Task task = new Task ("Task", dateTime)
        task.add(action1)
        task.add(action2)
        //when
        task.deleteByName("Action1")
        //then
        Assertions.assertTrue(task.actions.equals([action2]))
        Assertions.assertEquals (dateTime, task.actions[0].start)
    }

    @Test
    void testToString () {
        //given
        Task task = new Task ("Task", dateTime)
        task.add(action1)
        task.add(action2)
        //when
        String res = task.toString()
        //then
        Assertions.assertEquals ("Task: Task \t [Start: 15.05.25 15:00 \t End: 15.05.25 15:35 \t Actions: 2]",res)
    }
    @Test
    void testShowActions () {
        //given
        Task task = new Task ("Task", dateTime)
        task.add(action1)
        task.add(action2)
        //when
        String res = task.showActions()
        //then
        Assertions.assertEquals ("Action: Action1 \t Start: 15.05.25 15:00 \t Duration: 0:10\r\n" +
                "Action: Action2 \t Start: 15.05.25 15:10 \t Duration: 0:25",res)
    }
    @Test
    void testGetStart () {
        //given
        Task task = new Task ("Task", dateTime)
        task.add(action1)
        task.add(action2)
        //when
        LocalDateTime startTask = task.getStart()
        //then
        Assertions.assertEquals(dateTime, startTask)
    }
    @Test
    void testGetEnd () {
        //given
        Task task = new Task ("Task", dateTime)
        task.add(action1)
        task.add(action2)
        //when
        LocalDateTime endTask = task.getEnd()
        //then
        LocalDateTime expectedEnd = LocalDateTime.of(2025,05,15,15,35)
        Assertions.assertEquals(expectedEnd, endTask)
    }

    @Test
    void testGetDuration() {
        //given
        Task task = new Task ("Task", dateTime)
        task.add(action1)
        task.add(action2)
        //when
        Duration duration = task.getDuration()
        //then
        Duration expectedDuration = Duration.ofMinutes(35)
        Assertions.assertEquals(expectedDuration, duration)
    }

    @Test
    void testEquals() {
        //given
        Task task = new Task ("Task", dateTime)
        Task task2 = new Task ("Task", dateTime)
        Task task3 = new Task ("Task3", dateTime)
        Task task4 = new Task ("Task", dateTime)
        task4.add(action1)
        //then
        Assertions.assertTrue (task==task2)
        Assertions.assertTrue (task!=task3)
        Assertions.assertTrue (task!=task4)
    }
}
