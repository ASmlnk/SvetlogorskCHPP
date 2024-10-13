package com.example.svetlogorskchpp.__domain.usecases.calendarPreferencesNotificationUseCases

import com.example.svetlogorskchpp.__data.repository.preferences.NotesNotificationPreferencesRepository
import com.example.svetlogorskchpp.__presentation.dialog.notes_notification.model.CalendarNotificationUI
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class CalendarPreferencesNotificationUseCasesImpl @Inject constructor(
    private val preferencesRepository: NotesNotificationPreferencesRepository
): CalendarPreferencesNotificationUseCases {

    private val isNotesNotificationFlow = preferencesRepository.isNotificationNoteTechnical
    private val isRequestWorkNotificationFlow = preferencesRepository.isNotificationRequestWork
    private val isRequestWorkViewCalendarFlow = preferencesRepository.isRequestWorkViewCalendar

    val calendarNotificationUIFlow = combine(
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

}