package com.example.svetlogorskchpp.__domain.usecases.shift_schedule.calendarNoteNotificationUseCases

import com.example.svetlogorskchpp.__data.repository.shift_schedule.calendarNoteTag.CalendarNoteTagRepository
import com.example.svetlogorskchpp.__data.repository.shift_schedule.note.NoteRepository
import com.example.svetlogorskchpp.__data.repository.shift_schedule.noteRequestWork.NoteRequestWorkRepository
import com.example.svetlogorskchpp.__di.App
import com.example.svetlogorskchpp.__domain.model.shift_schedule.Note
import com.example.svetlogorskchpp.__domain.model.shift_schedule.NoteTechnicalNotification
import com.example.svetlogorskchpp.__domain.model.shift_schedule.RequestWorkNotification
import com.example.svetlogorskchpp.__domain.usecases.shift_schedule.calendarDateUseCases.CalendarDateUseCases
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

class CalendarNoteNotificationUseCasesImpl @Inject constructor(
    private val calendarNoteTagRepository: CalendarNoteTagRepository,
    private val noteRepository: NoteRepository,
    private val requestWorkRepository: NoteRequestWorkRepository,
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

        val eventToday = contentTextMyNoteNotification(eventTodays.filterIsInstance<Note.NoteMy>() )

        val eventTomorrowEntity = noteRepository.getNotesByTagId(dateTomorrowQuery)
        val eventTomorrows: List<Note> =
            if (eventTomorrowEntity.isEmpty()) emptyList() else eventTomorrowEntity.map { it.toNote() }
        val eventTomorrow = contentTextMyNoteNotification(eventTomorrows as List<Note.NoteMy>)

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

    override suspend fun calendarRequestWorkNotification(calendar: Calendar): RequestWorkNotification {
        val dateToday = calendar.clone() as Calendar
        val dateTodayQuery: Date = calendarDateUseCases.calendarToDateYMD(dateToday)

        val eventToday = requestWorkRepository.getByTagDates(dateTodayQuery).takeIf { it.isNotEmpty() }?.map {
            it.toNote()
        } ?: emptyList()

        val eventOpens = eventToday.filterIsInstance<Note.NoteRequestWork>().filter { it.tagDateOpen == dateTodayQuery }
        val eventOpenString = contentTextRequestWorkOpenNotification(eventOpens)

        val eventCloses = eventToday.filterIsInstance<Note.NoteRequestWork>().filter { it.tagDateClose == dateTodayQuery }
        val eventCloseString = contentTextRequestWorkCloseNotification(eventCloses)

        return RequestWorkNotification(
            eventOpen = eventOpenString,
            eventClose = eventCloseString,
            date = calendarDateUseCases.dateToStringFormatDDMMMMYYYY(dateTodayQuery),
            isOpenRequestWork = eventOpens.isNotEmpty(),
            isCloseRequestWork = eventCloses.isNotEmpty()
        )
    }

    private fun contentTextMyNoteNotification(eventList: List<Note.NoteMy>): String {
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

    private fun contentTextRequestWorkOpenNotification(eventList: List<Note.NoteRequestWork>): String {
        var eventToday = "<br>"
        for (note in eventList) {
            val time = SimpleDateFormat("HH:mm").format(note.dateOpen.time)
            val content = "<i>$time</i> - <b>${note.accession}</b> " + note.reason
            eventToday += "$content<br>"
        }
        return eventToday
    }

    private fun contentTextRequestWorkCloseNotification(eventList: List<Note.NoteRequestWork>): String {
        var eventToday = "<br>"
        for (note in eventList) {
            val time = SimpleDateFormat("HH:mm").format(note.dateClose.time)
            val content = "<i>$time</i> - <b>${note.accession}</b> " + note.reason
            eventToday += "$content<br>"
        }
        return eventToday
    }
}