package com.example.svetlogorskchpp.__domain.usecases.calendarPreferencesNotificationUseCases

import com.example.svetlogorskchpp.__presentation.dialog.shift_schedule.notes_notification.model.CalendarNotificationUI
import kotlinx.coroutines.flow.Flow

interface CalendarPreferencesNotificationUseCases {
    val calendarNotificationUIFlow: Flow<CalendarNotificationUI>

    fun getPreferencesNotesNotification(): Flow<Boolean>
    suspend fun setPreferencesNotesNotification(isNotesNotification: Boolean)

    fun getPreferencesRequestWorkNotification(): Flow<Boolean>
    suspend fun setPreferencesRequestWorkNotification(isRequestWorkNotification: Boolean)

    fun getPreferencesRequestWorkViewCalendar(): Flow<Boolean>
    suspend fun setPreferencesRequestWorkViewCalendar(isRequestWorkViewCalendar: Boolean)
}