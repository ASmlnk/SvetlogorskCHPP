package com.example.svetlogorskchpp.__domain.model.shift_schedule

import java.util.Date

data class CalendarRequestWorkTag(
    val date: Date,    //"YYYY-MM-DD"
    val month: Date,    //"YYYY-MM"
) {
}