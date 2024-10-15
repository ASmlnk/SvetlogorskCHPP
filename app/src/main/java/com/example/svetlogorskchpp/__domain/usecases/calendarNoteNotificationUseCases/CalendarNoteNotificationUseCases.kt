package com.example.svetlogorskchpp.__domain.usecases.calendarNoteNotificationUseCases

import com.example.svetlogorskchpp.__domain.model.NoteTechnicalNotification
import com.example.svetlogorskchpp.__domain.model.RequestWorkNotification
import java.util.Calendar

interface CalendarNoteNotificationUseCases {
    suspend fun calendarNoteTechnicalNotification(calendar: Calendar): NoteTechnicalNotification
    suspend fun calendarRequestWorkNotification(calendar: Calendar): RequestWorkNotification
}