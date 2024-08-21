package com.example.svetlogorskchpp.domain.usecases

import com.example.svetlogorskchpp.domain.model.CalendarNoteTag
import com.example.svetlogorskchpp.domain.usecases.calendarDate.CalendarDateUseCases
import com.example.svetlogorskchpp.presentation.shift_schedule.model.CalendarFullDayModel
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

class CalendarTagUseCases @Inject constructor() {

    fun addNoteTagToCalendar(
        calendarFullDayModels: List<CalendarFullDayModel>,
        calendarNoteTags: List<CalendarNoteTag>,
    ): List<CalendarFullDayModel> {
        val noteTagsMap = calendarNoteTags.associateBy { it.date } //преобразует в карту с ключом date

        return calendarFullDayModels.map { day ->
            noteTagsMap[day.data.time]?.let { tag ->
                day.copy(calendarNoteTag = tag)
            } ?: day
        }
    }
       /* val result = mutableListOf<CalendarFullDayModel>()
        val noteTags = calendarNoteTags.toMutableList()

        calendarFullDayModels.forEach { day ->
            var isTag = false
            for (tag in noteTags) {
                if (day.data.time == tag.date) {
                    val newDay = day.copy(
                        calendarNoteTag = tag
                    )
                    //noteTags.remove(tag)
                    result.add(newDay)
                    isTag = true
                    continue
                }
            }
            if (!isTag) result.add(day)
        }
        return result
    }*/

}

