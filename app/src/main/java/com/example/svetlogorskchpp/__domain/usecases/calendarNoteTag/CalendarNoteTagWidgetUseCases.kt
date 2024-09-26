package com.example.svetlogorskchpp.__domain.usecases.calendarNoteTag

import com.example.svetlogorskchpp.__domain.model.CalendarMyNoteTag
import java.util.Calendar

interface CalendarNoteTagWidgetUseCases {
    suspend fun calendarNoteTagStream(month: Calendar): List<CalendarMyNoteTag>
}