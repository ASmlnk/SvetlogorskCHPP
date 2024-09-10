package com.example.svetlogorskchpp.__domain.usecases.calendarNoteNotificationUseCases

import com.example.svetlogorskchpp.__data.repository.calendarNoteTag.CalendarNoteTagRepository
import com.example.svetlogorskchpp.__data.repository.note.NoteRepository
import com.example.svetlogorskchpp.__domain.model.NoteTechnicalNotification
import com.example.svetlogorskchpp.__domain.usecases.calendarDateUseCases.CalendarDateUseCases
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

class CalendarNoteNotificationUseCasesImpl @Inject constructor(
    private val calendarNoteTagRepository: CalendarNoteTagRepository,
    private val noteRepository: NoteRepository,
    private val calendarDateUseCases: CalendarDateUseCases
): CalendarNoteNotificationUseCases {

    override fun calendarNoteTechnicalNotification(calendar: Calendar): NoteTechnicalNotification {
        val dataQuery: Date = calendarDateUseCases.calendarToDateYMD(calendar)



    }
}