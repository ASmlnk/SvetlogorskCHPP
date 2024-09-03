package com.example.svetlogorskchpp.__domain.usecases.calendarDate

import java.util.Calendar
import java.util.Date

interface CalendarDateUseCases {
    fun calendarToDateYMD(calendar: Calendar) : Date
    fun calendarToDateYM(calendar: Calendar) : Date
    fun calendarToStringFormatDDMMMMYYYY(calendar: Calendar): String
    fun dateToStringFormatDDMMMMYYYY(date: Date): String
}