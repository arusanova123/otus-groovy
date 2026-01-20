package hw09.view

import hw09.model.Event
import hw09.service.EventBus
import hw09.service.ToDoListManager

class ConsoleView {
    ToDoListManager manager

    ConsoleView(ToDoListManager manager, EventBus eventBus) {
        this.manager = manager
        eventBus.registerConsumer(this::consumeEvent)
    }

    def consumeEvent(Event event) {
        println event
    }
}
