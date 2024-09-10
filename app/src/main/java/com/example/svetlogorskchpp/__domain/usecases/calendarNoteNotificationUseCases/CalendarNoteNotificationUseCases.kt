package com.example.svetlogorskchpp.__domain.usecases.calendarNoteNotificationUseCases

import com.example.svetlogorskchpp.__domain.model.NoteTechnicalNotification
import java.util.Calendar

interface CalendarNoteNotificationUseCases {
    suspend fun calendarNoteTechnicalNotification(calendar: Calendar): NoteTechnicalNotification
}