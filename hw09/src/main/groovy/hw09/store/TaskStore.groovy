package org.example.store
import org.example.model.Task

import java.time.LocalDate

class TaskStore {
    List tasks = []

    void add (task) {
        tasks << task
    }

    void delete (task) {
        tasks.remove(task)
    }

    void deleteByName (String taskName) {
        tasks.removeAll {it.name == taskName}
    }

    Task getByName (String taskName) {
        tasks.find {it.name == taskName}
    }

    List findAll() {
        tasks
    }

    List findByDate (LocalDate date) {
        tasks.findAll {it.start.toLocalDate() == date}
    }
}
