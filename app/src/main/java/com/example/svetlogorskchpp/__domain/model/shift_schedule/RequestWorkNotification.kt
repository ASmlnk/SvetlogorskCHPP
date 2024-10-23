package com.example.svetlogorskchpp.__domain.model.shift_schedule

data class RequestWorkNotification (
    val eventOpen: String,
    val eventClose: String,
    val date: String,
    val isOpenRequestWork: Boolean,
    val isCloseRequestWork: Boolean
) {
}