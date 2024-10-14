package com.example.svetlogorskchpp.__presentation.shift_schedule.model

import java.util.Date

data class CalendarUpdateTag(
    val date: Date,
    val viewRequestWork: Boolean
) {
}