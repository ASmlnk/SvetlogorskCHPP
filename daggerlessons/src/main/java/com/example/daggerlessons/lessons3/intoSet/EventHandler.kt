package com.example.daggerlessons.lessons3.intoSet

//оброботчик события event
interface EventHandler {
    fun handle(event: Event)
}

class Analytics: EventHandler {
    override fun handle(event: Event) {
        TODO("Not yet implemented")
    }
}

class Logger: EventHandler {
    override fun handle(event: Event) {
        TODO("Not yet implemented")
    }
}