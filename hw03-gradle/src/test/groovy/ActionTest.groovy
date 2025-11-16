import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions
import org.example.model.Action

import java.time.Duration
import java.time.LocalDateTime

class ActionTest {
    @Test
    void testToString() {
        //given
        LocalDateTime dateTime = LocalDateTime.of(2025, 05, 15, 15, 00)
        Duration duration = Duration.ofMinutes(10)
        Action action = new Action("Action", duration, dateTime)
        //when
        String str = action.toString()
        //then
        Assertions.assertEquals("Action: Action \t Start: 15.05.25 15:00 \t Duration: 0:10", str)
    }
    @Test
    void testGetEnd() {
        //given
        Duration duration = Duration.ofMinutes(10)
        Action action = new Action("Action", duration)
        //when
        LocalDateTime dateTime = LocalDateTime.of(2025, 05, 15, 15, 00)
        action.start = dateTime
        LocalDateTime end = action.getEnd()
        //then
        LocalDateTime expectedEnd = LocalDateTime.of(2025, 05, 15, 15, 10)
        Assertions.assertEquals(expectedEnd, end)
    }
}