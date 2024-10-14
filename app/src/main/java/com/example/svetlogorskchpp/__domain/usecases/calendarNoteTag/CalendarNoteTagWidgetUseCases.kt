package com.example.svetlogorskchpp.__domain.usecases.calendarNoteTag

import com.example.svetlogorskchpp.__domain.model.CalendarMyNoteTag
import com.example.svetlogorskchpp.__domain.model.CalendarRequestWorkTag
import kotlinx.coroutines.flow.Flow
import java.util.Calendar

interface CalendarNoteTagWidgetUseCases {
    suspend fun calendarMyNoteTag(month: Calendar): List<CalendarMyNoteTag>
    suspend fun calendarRequestWorkTag(month: Calendar): List<CalendarRequestWorkTag>
    fun getIsRequestWorkViewCalendar(): Flow<Boolean>
}