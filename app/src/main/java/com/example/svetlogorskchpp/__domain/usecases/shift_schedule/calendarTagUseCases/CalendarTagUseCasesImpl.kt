package com.example.svetlogorskchpp.__domain.usecases.shift_schedule.calendarTagUseCases

import com.example.svetlogorskchpp.__domain.model.shift_schedule.CalendarMyNoteTag
import com.example.svetlogorskchpp.__domain.model.shift_schedule.CalendarRequestWorkTag
import com.example.svetlogorskchpp.__presentation.shift_schedule.shift_schedule_calendar.model.CalendarFullDayModel
import javax.inject.Inject

class CalendarTagUseCasesImpl @Inject constructor(): CalendarTagUseCases {

    override fun addNoteTagToCalendar(
        calendarFullDayModels: List<CalendarFullDayModel>,
        calendarMyNoteTags: List<CalendarMyNoteTag>,
        calendarRequestWorkTag: List<CalendarRequestWorkTag>
    ): List<CalendarFullDayModel> {
        val myNoteTagsMap = calendarMyNoteTags.associateBy { it.date } //преобразует в карту с ключом date
        val requestWorkTagMap = calendarRequestWorkTag.associateBy { it.date }

        return calendarFullDayModels.map { day ->
            val myNoteTag = myNoteTagsMap[day.data.time]
            val requestWorkTag = requestWorkTagMap[day.data.time]

            day.copy(
                calendarMyNoteTag = myNoteTag ?: day.calendarMyNoteTag,
                calendarRequestWorkTag = requestWorkTag ?: day.calendarRequestWorkTag
            )
        }
    }
}

