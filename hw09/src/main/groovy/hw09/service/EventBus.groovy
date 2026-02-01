package hw09.service

import hw09.model.Event

class EventBus {
    def consumers = []

    //todo: bus to send event to consumers
    def send(Event event) {
        consumers.forEach {
            it(event)
        }
    }

    def registerConsumer(Closure closure) {
        consumers << closure
    }
}
