package com.example.svetlogorskchpp.__presentation.dialog.shift_schedule.notes_notification.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.svetlogorskchpp.__domain.task_schedule.notification.TaskSchedulerNotificationWorker
import com.example.svetlogorskchpp.__domain.usecases.calendarPreferencesNotificationUseCases.CalendarPreferencesNotificationUseCases
import com.example.svetlogorskchpp.__presentation.dialog.shift_schedule.notes_notification.model.CalendarNotificationUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesNotificationViewModel @Inject constructor(
    private val preferencesNotification: CalendarPreferencesNotificationUseCases,
    private val taskSchedulerNotificationWorker: TaskSchedulerNotificationWorker,
) : ViewModel() {

    val calendarNotesNotificationUIState = preferencesNotification.calendarNotificationUIFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = CalendarNotificationUI()
        )

    init {
        viewModelScope.launch {
            calendarNotesNotificationUIState.collect {
                if (it.isNotesNotification) {
                    taskSchedulerNotificationWorker.scheduleDailyTaskMyNotesAtSixAM()
                } else {
                    taskSchedulerNotificationWorker.cancelScheduleTaskMyNotes()
                }
                if (it.isRequestWorkNotification) {
                    taskSchedulerNotificationWorker.scheduleDailyTaskRequestWorkAtSixAM()
                } else {
                    taskSchedulerNotificationWorker.cancelScheduleTaskRequestWork()
                }
            }
        }
    }

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