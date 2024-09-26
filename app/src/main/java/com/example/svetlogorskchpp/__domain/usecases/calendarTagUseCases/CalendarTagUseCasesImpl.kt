package com.example.svetlogorskchpp.__domain.usecases.calendarTagUseCases

import com.example.svetlogorskchpp.__domain.model.CalendarMyNoteTag
import com.example.svetlogorskchpp.__domain.model.CalendarRequestWorkTag
import com.example.svetlogorskchpp.__presentation.shift_schedule.model.CalendarFullDayModel
import javax.inject.Inject

class CalendarTagUseCasesImpl @Inject constructor(): CalendarTagUseCases {

    override fun addNoteTagToCalendar(
        calendarFullDayModels: List<CalendarFullDayModel>,
        calendarMyNoteTags: List<CalendarMyNoteTag>,
        calendarRequestWorkTag: List<CalendarRequestWorkTag>
    ): List<CalendarFullDayModel> {
        val myNoteTagsMap = calendarMyNoteTags.associateBy { it.date } //преобразует в карту с ключом date
        val requestWorkTagMap = calendarRequestWorkTag.associateBy { it.date }

        calendarFullDayModels.map { day ->
            myNoteTagsMap[day.data.time]?.let { tag ->
                day.copy(calendarMyNoteTag = tag)
            } ?: day
        }
        return calendarFullDayModels.map { day ->
            requestWorkTagMap[day.data.time]?.let { tag ->
                day.copy(calendarRequestWorkTag = tag)
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

