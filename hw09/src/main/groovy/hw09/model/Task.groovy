package hw09.model

import hw09.model.Action
import io.micronaut.serde.annotation.Serdeable

import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Serdeable
class Task {
    LocalDateTime start
    String name
    List <Action> actions = []

    Task (String name, LocalDateTime start) {
        this.name = name
        this.start = start
    }

    void add (Action action) {
        action.start = this.getEnd()
        actions << action
    }

    void delete (Action action) {
        int index =  actions.indexOf(action)
        if (index==-1) return
        LocalDateTime start = action.start
        Duration duration = Duration.ofMinutes(0)
        actions.remove (action)
        if (index < actions.size())
            actions [index..-1].each {Action actionEl ->
                start = start + duration
                duration = actionEl.duration
                actionEl.start = start
            }
    }

    void deleteByName (String actionName) {
        def action = actions.find {it.name == actionName}
        if (action)
            delete (action)
    }

    String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm")
        "Task: $name \t [Start: ${start.format(formatter)} \t End: ${getEnd().format(formatter)} \t Actions: ${actions.size()}]\r\n" +
                this.showActions()
    }

    String showActions () {
        actions.collect {"$it"}.join ("\t\t\r\n")
    }

    LocalDateTime getEnd () {
        if (actions)
            actions.last().getEnd()
        else
            start
    }

    Duration getDuration() {
        actions*.duration.sum() as Duration
    }

    boolean equals (Task other) {
        this.name == other.name &&
        this.start == other.start &&
        this.showActions() == other.showActions()
    }
}
