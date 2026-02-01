package hw09.model

import hw09.model.Action
import hw09.model.Event

import java.time.LocalDateTime

class StartActionEvent implements Event {
    LocalDateTime timestamp
    String message
    String type = "Action started"

    StartActionEvent(LocalDateTime timestamp, Action action) {
        this.timestamp = timestamp
    }
}
