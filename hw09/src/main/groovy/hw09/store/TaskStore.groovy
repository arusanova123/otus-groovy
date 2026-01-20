package hw09.store

import hw09.model.Task

import java.time.LocalDate
import java.time.LocalDateTime

class TaskStore {
    List <Task> tasks = [new Task ("Test task", LocalDateTime.of(2025,05,15,15,00))]

    void add (Task task) {
        tasks << task
    }

    void delete (Task task) {
        tasks.remove(task)
    }

    void deleteByName (String taskName) {
        tasks.removeAll {it.name == taskName}
    }

    Task getByName (String taskName) {
        tasks.find {it.name == taskName}
    }

    List <Task> findAll() {
        tasks
    }

    List <Task> findByDate (LocalDate date) {
        tasks.findAll {it.start.toLocalDate() == date}
    }
}
