package com.example.svetlogorskchpp.domain.usecases

import com.example.svetlogorskchpp.domain.model.CalendarNoteTag
import com.example.svetlogorskchpp.presentation.shift_schedule.model.CalendarFullDayModel
import javax.inject.Inject

class CalendarTagUseCases @Inject constructor() {

    fun addNoteTagToCalendar(
        calendarFullDayModels: List<CalendarFullDayModel>,
        calendarNoteTags: List<CalendarNoteTag>,
    ): List<CalendarFullDayModel> {
        val result = mutableListOf<CalendarFullDayModel>()
        val noteTags = calendarNoteTags.toMutableList()

        calendarFullDayModels.forEach { day ->
            var isTag = false
            for (tag in noteTags) {
                if (day.data.time == tag.date) {
                    val newDay = day.copy(
                        calendarNoteTag = tag
                    )
                    noteTags.remove(tag)
                    result.add(newDay)
                    isTag = true
                    continue
                }
            }
            if (!isTag) result.add(day)
        }
        return result
    }
}