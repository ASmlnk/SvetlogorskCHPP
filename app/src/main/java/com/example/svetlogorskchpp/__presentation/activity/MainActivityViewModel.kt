package com.example.svetlogorskchpp.__presentation.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.svetlogorskchpp.__domain.task_schedule.notification.TaskSchedulerNotificationWorker
import com.example.svetlogorskchpp.__domain.task_schedule.update_request_work.TaskSchedulerUpdateRequestWorkBaseWorker
import com.example.svetlogorskchpp.__domain.usecases.calendarPreferencesNotificationUseCases.CalendarPreferencesNotificationUseCases
import com.example.svetlogorskchpp.__domain.usecases.update_locale_base.UpdateLocaleBaseUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val updateLocaleBaseUseCases: UpdateLocaleBaseUseCases,
    private val preferencesNotification: CalendarPreferencesNotificationUseCases,
    private val taskSchedulerNotificationWorker: TaskSchedulerNotificationWorker,
    private val taskSchedulerUpdateRequestWorkBaseWorker: TaskSchedulerUpdateRequestWorkBaseWorker
) : ViewModel() {

    private val _stateFlow = MutableStateFlow(Any())
    val stateFlow: StateFlow<Any> = _stateFlow

    init {
        viewModelScope.launch {
            getRequestWorkFirebase()
            updateRequestWorkWorker()
            initNotification()
            updateOpenSwitchgearVl()
        }
    }

    suspend fun getRequestWorkFirebase() {
        updateLocaleBaseUseCases.updateRequestWork()
    }

    suspend fun updateOpenSwitchgearVl() {
        updateLocaleBaseUseCases.updateOpenSwitchgearVl()
    }

    fun updateRequestWorkWorker() {
        taskSchedulerUpdateRequestWorkBaseWorker.scheduleDailyTask12hour()
    }

    suspend fun initNotification() = withContext(Dispatchers.IO) {
        val isRequestWorkNotification =
            preferencesNotification.getPreferencesRequestWorkNotification().first()
        val isMyNoteNotification = preferencesNotification.getPreferencesNotesNotification().first()

        if (isRequestWorkNotification) {
            taskSchedulerNotificationWorker.scheduleDailyTaskRequestWorkAtSixAM()
        } else {
            taskSchedulerNotificationWorker.cancelScheduleTaskRequestWork()
        }

        if (isMyNoteNotification) {
            taskSchedulerNotificationWorker.scheduleDailyTaskMyNotesAtSixAM()
        } else {
            taskSchedulerNotificationWorker.cancelScheduleTaskMyNotes()
        }
    }
}