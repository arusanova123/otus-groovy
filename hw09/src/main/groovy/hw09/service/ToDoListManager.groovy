package hw09.service

import hw09.model.Action
import hw09.model.Task
import hw09.store.TaskStore
import io.micronaut.http.HttpStatus
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Produces
import io.micronaut.http.annotation.Status

import java.time.LocalDateTime
import java.time.LocalDate
import java.time.Duration
import groovy.transform.CompileStatic
import io.micronaut.http.annotation.Controller
import jakarta.inject.Singleton

@Singleton
@CompileStatic
@Controller("/todo")
class ToDoListManager {
    TaskStore taskStore = new TaskStore()

    @Get
    @Produces(MediaType.TEXT_PLAIN)
    String index() {
        "Possible actions:\r\n"+
        "GET getAllTasks\r\n"+
        "GET getAllTasksPretty\r\n"+
        "GET showBusyTime (date)\r\n"+
        "GET getAmountOfTasksByDate (date)\r\n"+
        "GET getTasksByDate (date)\r\n"+
        "POST addTask (taskName, LocalDateTime start)\r\n"+
        "POST addAction (taskName, actionName, Long durationOfMin)\r\n"+
        "DELETE deleteTask (taskName)\r\n"+
        "DELETE deleteAction (taskName, actionName)\r\n"
    }

    @Post ("addTask")
    @Status(HttpStatus.CREATED) // Returns HTTP 201 Created on success
    def addTask(String taskName, LocalDateTime start) {
        Task task = new Task (taskName, start)
        def result = ""
        def intersect = checkIntersection(task)
        if (intersect)
            result = "Intersection with $intersect"
        else
            taskStore.add(task)
        result
    }

    @Delete ("deleteTask")
    @Status(HttpStatus.NO_CONTENT)
    void deleteTask(String taskName) {
        taskStore.deleteByName(taskName)
    }

    @Post ("addAction")
    @Status(HttpStatus.CREATED) // Returns HTTP 201 Created on success
    String addAction (String taskName, String actionName, Long durationOfMin) {
        Duration duration = Duration.ofMinutes(durationOfMin)
        Action action = new Action (actionName, duration)
        Task task = taskStore.getByName (taskName)
        if (!task)
            return "There is no tasks named \"taskName\""
        def result = ""
        def intersect = checkAddActionIntersection (task, duration)
        if (intersect)
            result = "Intersection with $intersect"
        else
            task.add(action)
        result
    }

    @Delete ("deleteAction")
    @Status(HttpStatus.NO_CONTENT)
    void deleteAction (String taskName, String actionName) {
        Task task = taskStore.getByName (taskName)
        if (task)
            task.deleteByName(actionName)
    }

    @Get ("getAllTasks")
    @Produces(MediaType.APPLICATION_JSON)
    def getAllTasks() {
        taskStore.findAll()
    }

    @Get ("getAllTasksPretty")
    @Produces(MediaType.TEXT_PLAIN)
    def getAllTasksPretty() {
        taskStore.findAll()*.toString().join("\r\n")
    }

    @Get ("getAmountOfTasksByDate")
    @Produces(MediaType.APPLICATION_JSON)
    def getAmountOfTasksByDate (LocalDate date) {
        taskStore.findByDate(date).size()
    }

    @Get ("getTasksByDate")
    @Produces(MediaType.APPLICATION_JSON)
    def getTasksByDate (LocalDate date) {
        taskStore.findByDate(date)
    }

    @Get ("showBusyTime")
    @Produces(MediaType.TEXT_PLAIN)
    String showBusyTime(LocalDate date) {
        List <Task> tasks = taskStore.findByDate(date)
        Duration busyTime = tasks*.getDuration().sum() as Duration
        busyTime ? String.format("%d:%02d", busyTime?.toHours(), busyTime?.toMinutesPart()) : "No busy time"
    }

    private Task checkIntersection(Task task) {
        LocalDateTime startNew = task.start
        Task intersect = taskStore.findAll().find {startNew >= it.start && startNew < it.getEnd() }
        return intersect
    }

    private Task checkAddActionIntersection(Task task, Duration duration) {
        def endNew = task.getEnd() + duration
        taskStore.findAll().find { Task taskEl ->
            endNew >= taskEl.start && taskEl.start > task.start}
    }
}
