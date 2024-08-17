package com.example.svetlogorskchpp.domain.usecases.calendarNoteTag

import com.example.svetlogorskchpp.data.repository.calendarNoteTag.CalendarNoteTagRepository
import com.example.svetlogorskchpp.domain.model.CalendarNoteTag
import com.example.svetlogorskchpp.domain.usecases.calendarDate.CalendarDateUseCases
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

class CalendarNoteTagUseCasesImpl @Inject constructor(
    private val calendarNoteTagRepository: CalendarNoteTagRepository,
    private val calendarDateUseCases: CalendarDateUseCases,
) : CalendarNoteTagUseCases {

    override fun calendarNoteTagStream(month: Calendar) =
        calendarNoteTagRepository.getTagsByMonth(
            calendarDateUseCases.calendarToDateYM(month)
        ).map { calendarNoteTagEntitys ->
            calendarNoteTagEntitys.map {
                it.toCalendarNoteTag()
            }
        }

    override suspend fun insertTag(tagCalendarNote: CalendarNoteTag) =
        calendarNoteTagRepository.insertTag(tagCalendarNote.toCalendarNoteTagEntity())

    override fun getTagsByDate(date: Calendar): Flow<CalendarNoteTag?> =
        calendarNoteTagRepository.getTagsByDate(
            calendarDateUseCases.calendarToDateYMD(date)
        ).map { calendarNoteTagEntity ->
            calendarNoteTagEntity?.toCalendarNoteTag()  }

    override suspend fun deleteCalendarTag(date: Date) =
        calendarNoteTagRepository.deleteCalendarTag(date)
}