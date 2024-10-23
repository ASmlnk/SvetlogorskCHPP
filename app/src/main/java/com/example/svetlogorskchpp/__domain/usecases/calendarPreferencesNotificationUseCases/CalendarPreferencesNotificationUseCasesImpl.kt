package com.example.svetlogorskchpp.__domain.usecases.calendarPreferencesNotificationUseCases

import com.example.svetlogorskchpp.__data.repository.preferences.NotesNotificationPreferencesRepository
import com.example.svetlogorskchpp.__presentation.dialog.shift_schedule.notes_notification.model.CalendarNotificationUI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class CalendarPreferencesNotificationUseCasesImpl @Inject constructor(
    private val preferencesRepository: NotesNotificationPreferencesRepository
): CalendarPreferencesNotificationUseCases {

    private val isNotesNotificationFlow = preferencesRepository.isNotificationNoteTechnical
    private val isRequestWorkNotificationFlow = preferencesRepository.isNotificationRequestWork
    private val isRequestWorkViewCalendarFlow = preferencesRepository.isRequestWorkViewCalendar

    override val calendarNotificationUIFlow = combine(
        isNotesNotificationFlow,
        isRequestWorkNotificationFlow,
        isRequestWorkViewCalendarFlow
    ) { isNotesNotification, isRequestWorkNotification, isRequestWorkViewCalendar ->
        CalendarNotificationUI(
            isNotesNotification = isNotesNotification,
            isRequestWorkNotification = isRequestWorkNotification,
            isRequestWorkViewCalendar = isRequestWorkViewCalendar
        )
    }

    override fun getPreferencesNotesNotification(): Flow<Boolean> {
       return preferencesRepository.isNotificationNoteTechnical
    }

    override suspend  fun setPreferencesNotesNotification(isNotesNotification: Boolean) {
        preferencesRepository.setNotificationNoteTechnical(isNotesNotification)
    }

    override fun getPreferencesRequestWorkNotification(): Flow<Boolean> {
        return preferencesRepository.isNotificationRequestWork
    }

    override suspend fun setPreferencesRequestWorkNotification(isRequestWorkNotification: Boolean) {
        preferencesRepository.setNotificationRequestWork(isRequestWorkNotification)
    }

    override fun getPreferencesRequestWorkViewCalendar(): Flow<Boolean> {
        return preferencesRepository.isRequestWorkViewCalendar
    }

    override suspend fun setPreferencesRequestWorkViewCalendar(isRequestWorkViewCalendar: Boolean) {
        preferencesRepository.setRequestWorkViewCalendar(isRequestWorkViewCalendar)
    }
}