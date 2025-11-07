package org.example.model

import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Action {
    LocalDateTime start
    String name
    Duration duration // seconds

    // конструктор
    def Action (String name, Duration duration, LocalDateTime start =  null) {
        this.name = name
        this.duration = duration
        this.start = start
    }

    String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm")
        String hm = String.format("%d:%02d", duration.toHours(), duration.toMinutesPart())
        String startStr = start ? "Start: ${start.format(formatter)} \t": ""
        "Action: $name \t $startStr Duration: $hm"
    }

    LocalDateTime getEnd () {
        if (start)
            start + duration
        else null
    }
}
