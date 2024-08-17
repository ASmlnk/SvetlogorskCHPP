package com.example.svetlogorskchpp.domain.usecases.calendarDate

import java.util.Calendar
import java.util.Date
import javax.inject.Inject

interface CalendarDateUseCases {
    fun calendarToDateYMD(calendar: Calendar) : Date
    fun calendarToDateYM(calendar: Calendar) : Date
    fun calendarToStringFormatDDMMMMYYYY(calendar: Calendar): String
    fun dateToStringFormatDDMMMMYYYY(date: Date): String
}