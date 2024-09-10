package com.example.svetlogorskchpp.__domain.usecases.calendarNoteTag

import com.example.svetlogorskchpp.__data.repository.calendarNoteTag.CalendarNoteTagRepository
import com.example.svetlogorskchpp.__domain.model.CalendarNoteTag
import com.example.svetlogorskchpp.__domain.usecases.calendarDateUseCases.CalendarDateUseCases
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Calendar
import javax.inject.Inject

class CalendarNoteTagUseCasesImpl @Inject constructor(
    private val calendarNoteTagRepository: CalendarNoteTagRepository,
    private val calendarDateUseCases: CalendarDateUseCases,
) : CalendarNoteTagUseCases, CalendarNoteTagWidgetUseCases{

    override suspend fun calendarNoteTagStream(month: Calendar) =
        calendarNoteTagRepository.getTagsByMonth(
            calendarDateUseCases.calendarToDateYM(month)
        ).map {
                it.toCalendarNoteTag()
            }

    override suspend fun insertTag(tagCalendarNote: CalendarNoteTag) =
        calendarNoteTagRepository.insertTag(tagCalendarNote.toCalendarNoteTagEntity())

    override fun getTagsByDate(date: Calendar): Flow<CalendarNoteTag?> =
        calendarNoteTagRepository.getTagsByDateStream(
            calendarDateUseCases.calendarToDateYMD(date)
        ).map { calendarNoteTagEntity ->
            calendarNoteTagEntity?.toCalendarNoteTag()  }

    override suspend fun deleteCalendarTag(calendarNoteTagEntity:CalendarNoteTag) =
        calendarNoteTagRepository.deleteCalendarTag(calendarNoteTagEntity.toCalendarNoteTagEntity())
}