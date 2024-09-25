package com.example.svetlogorskchpp.__domain.usecases.calendarDateUseCases

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

@SuppressLint("SimpleDateFormat")
class CalendarDateUseCasesImpl @Inject constructor(): CalendarDateUseCases {

    override fun calendarToDateYMD(calendar: Calendar) : Date {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val date = Calendar.getInstance()
        date.set(year,month,day, 0, 0, 0)
        date.set(Calendar.MILLISECOND, 0)
        return date.time
    }

    override fun calendarToDateYM(calendar: Calendar) : Date {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val date = Calendar.getInstance()
        date.set(year,month, 1, 0, 0, 0)
        date.set(Calendar.MILLISECOND, 0)
        return date.time
    }


    override fun calendarToStringFormatDDMMMMYYYY(calendar: Calendar): String {
        val sdf = SimpleDateFormat("dd MMMM yyyy")
        return sdf.format(calendar.time)
    }

    override fun calendarToStringFormatDDMMMMYYYYHHmm(calendar: Calendar): String {
        val sdf = SimpleDateFormat("dd MMMM yyyy HH:mm")
        return sdf.format(calendar.time)
    }


    override fun dateToStringFormatDDMMMMYYYY(date: Date): String {
        val sdf = SimpleDateFormat("dd MMMM yyyy")
        return sdf.format(date)
    }
}