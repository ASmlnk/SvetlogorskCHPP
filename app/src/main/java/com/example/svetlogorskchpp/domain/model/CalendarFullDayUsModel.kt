package com.example.svetlogorskchpp.domain.model

import java.util.Calendar

data class CalendarFullDayUsModel(
    val data: Calendar,
    val month: MonthCalendar,
    val prevNightShift: Shift,
    val dayShift: Shift,
    val nextNightShift: Shift,
)
