package com.example.svetlogorskchpp.__domain.model

data class NoteTechnicalNotification (
    val eventToday: List<Note>,
    val eventTomorrow: List<Note>,
    val isTechnicalToday: Boolean,
    val isTechnicalTomorrow: Boolean,
    val dateToday: String,
    val dateTomorrow: String
) {
}