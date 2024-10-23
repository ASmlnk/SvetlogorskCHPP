package com.example.svetlogorskchpp.__domain.usecases.shift_schedule.calendarNoteNotificationUseCases

import com.example.svetlogorskchpp.__domain.model.shift_schedule.NoteTechnicalNotification
import com.example.svetlogorskchpp.__domain.model.shift_schedule.RequestWorkNotification
import java.util.Calendar

interface CalendarNoteNotificationUseCases {
    suspend fun calendarNoteTechnicalNotification(calendar: Calendar): NoteTechnicalNotification
    suspend fun calendarRequestWorkNotification(calendar: Calendar): RequestWorkNotification
}