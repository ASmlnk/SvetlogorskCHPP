package com.example.svetlogorskchpp.__presentation.dialog.shift_schedule.request_work_filter.model

data class RequestWorkFilterUI(
    val isDispatcher: Boolean = false,
    val isChiefEngineer: Boolean = false,
    val isOther: Boolean = false,
    val isClosed: Boolean = false
) {
}