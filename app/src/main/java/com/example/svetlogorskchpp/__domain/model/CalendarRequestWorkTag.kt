package com.example.svetlogorskchpp.__domain.model

import java.util.Date

data class CalendarRequestWorkTag(
    val date: Date,    //"YYYY-MM-DD"
    val month: Date,    //"YYYY-MM"
) {
}