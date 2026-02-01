package hw09.service

import hw09.model.Event
import hw09.service.EventBus
import hw09.model.StartActionEvent
import hw09.store.ActionStore

import java.time.LocalDateTime

class TrackActionsService {
    EventBus eventBus = new EventBus()
    ActionStore actionStore = new ActionStore()

    def onSchedule() {
        def currentTime = LocalDateTime.now()
        // search actions that start or end at current time
        def startedActions = []
        def endedActions = []

        startedActions.forEach {
            StartActionEvent event = new StartActionEvent(currentTime, it)
            produce(event)
        }
    }

    def produce(Event event) {
        eventBus.send(event)
    }
}
