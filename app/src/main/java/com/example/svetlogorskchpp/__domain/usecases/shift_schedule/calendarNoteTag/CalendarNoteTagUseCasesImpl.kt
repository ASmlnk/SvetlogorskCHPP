package com.example.svetlogorskchpp.__domain.usecases.shift_schedule.calendarNoteTag

import com.example.svetlogorskchpp.__data.repository.shift_schedule.calendarNoteTag.CalendarNoteTagRepository
import com.example.svetlogorskchpp.__data.repository.shift_schedule.calendarRequestWorkTag.CalendarRequestWorkTagRepository
import com.example.svetlogorskchpp.__domain.model.shift_schedule.CalendarMyNoteTag
import com.example.svetlogorskchpp.__domain.model.shift_schedule.CalendarRequestWorkTag
import com.example.svetlogorskchpp.__domain.usecases.shift_schedule.calendarDateUseCases.CalendarDateUseCases
import com.example.svetlogorskchpp.__domain.usecases.calendarPreferencesNotificationUseCases.CalendarPreferencesNotificationUseCases
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Calendar
import javax.inject.Inject

class CalendarNoteTagUseCasesImpl @Inject constructor(
    private val calendarNoteTagRepository: CalendarNoteTagRepository,
    private val calendarRequestWorkTagRepository: CalendarRequestWorkTagRepository,
    private val calendarDateUseCases: CalendarDateUseCases,
    private val preferencesNotificationUseCases: CalendarPreferencesNotificationUseCases
) : CalendarNoteTagUseCases, CalendarNoteTagWidgetUseCases {

    override suspend fun calendarMyNoteTag(month: Calendar) =
        calendarNoteTagRepository.getTagsByMonth(
            calendarDateUseCases.calendarToDateYM(month)
        ).map {
            it.toCalendarNoteTag()
        }

    override suspend fun calendarRequestWorkTag(month: Calendar): List<CalendarRequestWorkTag> {
      return  calendarRequestWorkTagRepository.getTagsByMonth(
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

    override val preferencesRequestWorkViewCalendarFlow = preferencesNotificationUseCases.getPreferencesRequestWorkViewCalendar()
    override fun getIsRequestWorkViewCalendar() = preferencesNotificationUseCases.getPreferencesRequestWorkViewCalendar()
}