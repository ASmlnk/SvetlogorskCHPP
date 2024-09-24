package com.example.svetlogorskchpp.__domain.usecases.calendarNoteNotificationUseCases

import com.example.svetlogorskchpp.__data.repository.calendarNoteTag.CalendarNoteTagRepository
import com.example.svetlogorskchpp.__data.repository.note.NoteRepository
import com.example.svetlogorskchpp.__di.App
import com.example.svetlogorskchpp.__domain.model.Note
import com.example.svetlogorskchpp.__domain.model.NoteTechnicalNotification
import com.example.svetlogorskchpp.__domain.usecases.calendarDateUseCases.CalendarDateUseCases
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

class CalendarNoteNotificationUseCasesImpl @Inject constructor(
    private val calendarNoteTagRepository: CalendarNoteTagRepository,
    private val noteRepository: NoteRepository,
    @App private val calendarDateUseCases: CalendarDateUseCases,
) : CalendarNoteNotificationUseCases {

    override suspend fun calendarNoteTechnicalNotification(calendar: Calendar): NoteTechnicalNotification {
        val dateToday = calendar.clone() as Calendar
        val dateTomorrow = calendar.clone() as Calendar
        dateTomorrow.add(Calendar.DAY_OF_MONTH, 1)

        val dateTodayQuery: Date = calendarDateUseCases.calendarToDateYMD(dateToday)
        val dateTomorrowQuery: Date = calendarDateUseCases.calendarToDateYMD(dateTomorrow)

        val eventTodayEntity = noteRepository.getNotesByTagId(dateTodayQuery)
        val eventTodays: List<Note> =
            if (eventTodayEntity.isEmpty()) emptyList() else eventTodayEntity.map { it.toNote() }

        val eventToday = contentTextNotification(eventTodays as List<Note.NoteMy>)

        val eventTomorrowEntity = noteRepository.getNotesByTagId(dateTomorrowQuery)
        val eventTomorrows: List<Note> =
            if (eventTomorrowEntity.isEmpty()) emptyList() else eventTomorrowEntity.map { it.toNote() }
        val eventTomorrow = contentTextNotification(eventTomorrows as List<Note.NoteMy>)

        val isTechnicalToday =
            calendarNoteTagRepository.getTagsByDay(dateTodayQuery).firstOrNull()?.isTechnical
                ?: false
        val isTechnicalTomorrow =
            calendarNoteTagRepository.getTagsByDay(dateTomorrowQuery).firstOrNull()?.isTechnical
                ?: false

        val isNotesToday =
            calendarNoteTagRepository.getTagsByDay(dateTodayQuery).firstOrNull()?.isNotes
                ?: false
        val isNotesTomorrow =
            calendarNoteTagRepository.getTagsByDay(dateTomorrowQuery).firstOrNull()?.isNotes
                ?: false

        val noteTechnicalNotification = NoteTechnicalNotification(
            eventToday = eventToday,
            eventTomorrow = eventTomorrow,
            isTechnicalTomorrow = isTechnicalTomorrow,
            isTechnicalToday = isTechnicalToday,
            isNoteToday = isNotesToday,
            isNoteTomorrow = isNotesTomorrow,
            dateToday = calendarDateUseCases.dateToStringFormatDDMMMMYYYY(dateTodayQuery),
            dateTomorrow = calendarDateUseCases.dateToStringFormatDDMMMMYYYY(dateTomorrowQuery)
        )
        return noteTechnicalNotification
    }

    private fun contentTextNotification(eventList: List<Note.NoteMy>): String {
        var eventToday = "<br>"
        for (note in eventList) {
            if (note.isTimeNotes) {
                val time = SimpleDateFormat("HH:mm").format(note.dateNotes.time)
                val content = "<i>$time</i> - " + note.content
                eventToday += "$content<br>"
            } else {
                val time = "--:--"
                val content = "<i>$time</i> - " + note.content
                eventToday += "$content<br>"
            }
        }
        return eventToday
    }
}