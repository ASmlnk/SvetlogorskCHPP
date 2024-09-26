package com.example.svetlogorskchpp.__domain.usecases.calendarNoteTag

import com.example.svetlogorskchpp.__data.repository.calendarNoteTag.CalendarNoteTagRepository
import com.example.svetlogorskchpp.__data.repository.noteRequestWork.NoteRequestWorkRepository
import com.example.svetlogorskchpp.__data.repository.noteRequestWork.NoteRequestWorkRepositoryImpl
import com.example.svetlogorskchpp.__domain.model.CalendarMyNoteTag
import com.example.svetlogorskchpp.__domain.model.CalendarRequestWorkTag
import com.example.svetlogorskchpp.__domain.usecases.calendarDateUseCases.CalendarDateUseCases
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Calendar
import javax.inject.Inject

class CalendarNoteTagUseCasesImpl @Inject constructor(
    private val calendarNoteTagRepository: CalendarNoteTagRepository,
    private val noteRequestWorkRepository: NoteRequestWorkRepository,
    private val calendarDateUseCases: CalendarDateUseCases,
) : CalendarNoteTagUseCases, CalendarNoteTagWidgetUseCases {

    override suspend fun calendarMyNoteTag(month: Calendar) =
        calendarNoteTagRepository.getTagsByMonth(
            calendarDateUseCases.calendarToDateYM(month)
        ).map {
            it.toCalendarNoteTag()
        }

    override suspend fun calendarRequestWorkTag(month: Calendar): List<CalendarRequestWorkTag> {
      return  noteRequestWorkRepository.getTagsByMonth(
          calendarDateUseCases.calendarToDateYM(month)
      ).map {
          it.toCalendarRequestWorkTag()
      }
    }

    override suspend fun insertTag(tagCalendarNote: CalendarMyNoteTag) =
        calendarNoteTagRepository.insertTag(tagCalendarNote.toCalendarNoteTagEntity())

    override fun getTagsByDate(dateToCalendar: Calendar): Flow<CalendarMyNoteTag?> {
        val date = calendarDateUseCases.calendarToDateYMD(dateToCalendar)

        return calendarNoteTagRepository.getTagsByDateStream(
            date
        ).map { calendarNoteTagEntity ->
            calendarNoteTagEntity?.toCalendarNoteTag()
        }
    }

    override suspend fun deleteCalendarTag(calendarMyNoteTagEntity: CalendarMyNoteTag) =
        calendarNoteTagRepository.deleteCalendarTag(calendarMyNoteTagEntity.toCalendarNoteTagEntity())
}