package com.example.svetlogorskchpp.__domain.model.shift_schedule

data class NoteTechnicalNotification (
    val eventToday: String,
    val eventTomorrow: String,
    val isTechnicalToday: Boolean,
    val isTechnicalTomorrow: Boolean,
    val isNoteToday: Boolean,
    val isNoteTomorrow: Boolean,
    val dateToday: String,
    val dateTomorrow: String
) {
}