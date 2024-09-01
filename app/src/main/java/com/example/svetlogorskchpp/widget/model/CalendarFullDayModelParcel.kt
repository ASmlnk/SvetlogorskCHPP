package com.example.svetlogorskchpp.widget.model

import android.os.Parcelable
import com.example.svetlogorskchpp.domain.en.Shift
import com.example.svetlogorskchpp.domain.model.CalendarNoteTag
import com.example.svetlogorskchpp.domain.model.MonthCalendar
import kotlinx.parcelize.Parcelize
import java.util.Calendar

@Parcelize
data class CalendarFullDayModelParcel(
    val data: Long ,
    val month: MonthCalendar,
    val prevNightShift: Shift,
    val dayShift: Shift,
    val nextNightShift: Shift,
    val calendarNoteTag: CalendarNoteTagParc? = null
) : Parcelable {


    val calendarDate: String
        get() {
            val cal = Calendar.getInstance()
            cal.timeInMillis = data
            return cal[Calendar.DAY_OF_MONTH].toString()
        }

    /*val dateDay: Boolean
        get() {
            val cal = Calendar.getInstance()
            return data.get(Calendar.YEAR) == cal.get(Calendar.YEAR) &&
                    data.get(Calendar.MONTH) == cal.get(Calendar.MONTH) &&
                    data.get(Calendar.DAY_OF_MONTH) == cal.get(Calendar.DAY_OF_MONTH)
        }*/
}
