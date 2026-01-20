package org.example.service

import org.example.model.Action
import org.example.model.Task
import org.example.store.TaskStore
import java.time.LocalDateTime
import java.time.LocalDate
import java.time.Duration

class ToDoListManager {
    TaskStore taskStore

    ToDoListManager(TaskStore taskStore = []) {
        this.taskStore = taskStore
    }

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

    void deleteTask(String taskName) {
        taskStore.deleteByName(taskName)
    }

    String addAction (String taskName, String actionName, Duration duration) {
        Action action = new Action (actionName, duration)
        Task task = taskStore.getByName (taskName)
        if (!task) return
        def result = ""
        def intersect = checkAddActionIntersection (task, duration)
        if (intersect)
            result = "Intersection with $intersect"
        else
            task.add(action)
        result
    }

    void deleteAction (String taskName, String actionName) {
        Task task = taskStore.getByName (taskName)
        if (task)
            task.deleteByName (actionName)
    }

    def getAllTasks() {
        taskStore.findAll()
    }

    def getAmountOfTasksByDate (LocalDate date) {
        taskStore.findByDate(date).size()
    }

    def getTasksByDate (LocalDate date) {
        taskStore.findByDate(date)
    }

    String showBusyTime(LocalDate date) {
        def busyTime = taskStore.findByDate(date)*.getDuration().sum()
        String.format("%d:%02d", busyTime.toHours(), busyTime.toMinutesPart())
    }

    private Task checkIntersection(Task task) {
        LocalDateTime startNew = task.start
        Task intersect = taskStore.findAll().find {startNew >= it.start && startNew < it.getEnd() }
        return intersect
    }

    private Task checkAddActionIntersection(Task task, Duration duration) {
        def endNew = task.getEnd() + duration
        taskStore.findAll().find {endNew >= it.start && it.start > task.start}
    }
}
