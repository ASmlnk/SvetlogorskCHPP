package com.example.svetlogorskchpp.__presentation.dialog.notes_notification.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.svetlogorskchpp.__domain.usecases.calendarPreferencesNotificationUseCases.CalendarPreferencesNotificationUseCases
import com.example.svetlogorskchpp.__presentation.dialog.notes_notification.model.CalendarNotificationUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesNotificationViewModel @Inject constructor(
    private val preferencesNotification: CalendarPreferencesNotificationUseCases,
) : ViewModel() {

    val calendarNotesNotificationUIState = preferencesNotification.calendarNotificationUIFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = CalendarNotificationUI()
        )

    fun clickNotesNotification() {
        val isNotesNotification = calendarNotesNotificationUIState.value.isNotesNotification
        viewModelScope.launch(Dispatchers.IO) {
            preferencesNotification.setPreferencesNotesNotification(!isNotesNotification)
        }
    }

    fun clickRequestWorkNotification() {
        val isRequestWorkNotification = calendarNotesNotificationUIState.value.isRequestWorkNotification
        viewModelScope.launch(Dispatchers.IO) {
            preferencesNotification.setPreferencesRequestWorkNotification(!isRequestWorkNotification)
        }
    }

    fun clickRequestWorkViewCalendar() {
        val isRequestWorkViewCalendar = calendarNotesNotificationUIState.value.isRequestWorkViewCalendar
        viewModelScope.launch(Dispatchers.IO) {
            preferencesNotification.setPreferencesRequestWorkViewCalendar(!isRequestWorkViewCalendar)
        }
    }
}