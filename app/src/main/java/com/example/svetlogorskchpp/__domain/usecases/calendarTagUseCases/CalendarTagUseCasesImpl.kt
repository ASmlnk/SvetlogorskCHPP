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

