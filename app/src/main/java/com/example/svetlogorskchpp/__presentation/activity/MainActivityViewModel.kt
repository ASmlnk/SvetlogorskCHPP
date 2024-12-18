package com.example.svetlogorskchpp.__presentation.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.svetlogorskchpp.__domain.task_schedule.notification.TaskSchedulerNotificationWorker
import com.example.svetlogorskchpp.__domain.task_schedule.update_request_work.TaskSchedulerUpdateRequestWorkBaseWorker
import com.example.svetlogorskchpp.__domain.usecases.calendarPreferencesNotificationUseCases.CalendarPreferencesNotificationUseCases
import com.example.svetlogorskchpp.__domain.usecases.equipments.edit_access.EditAccessUseCases
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
    private val taskSchedulerUpdateRequestWorkBaseWorker: TaskSchedulerUpdateRequestWorkBaseWorker,
    private val accessUseCases: EditAccessUseCases,
) : ViewModel() {

    private val _stateFlow = MutableStateFlow(Any())
    val stateFlow: StateFlow<Any> = _stateFlow

    init {
        viewModelScope.launch {
            getRequestWorkFirebase()
            updateRequestWorkWorker()
            initNotification()
            updateOpenSwitchgearVl()
            updateOpenSwitchgearTr()
            updateTsn()
            updateTg()
            updateSwitchgear()
            updateElMotor()
            updateLightingAndOther()
            equalsEditAccess()
        }
    }

    private fun getRequestWorkFirebase() {
        viewModelScope.launch(Dispatchers.IO) { updateLocaleBaseUseCases.updateRequestWork() }
    }

    private fun updateOpenSwitchgearVl() {
        viewModelScope.launch(Dispatchers.IO) { updateLocaleBaseUseCases.updateOpenSwitchgearVl() }
    }
    private fun updateOpenSwitchgearTr() {
        viewModelScope.launch(Dispatchers.IO) { updateLocaleBaseUseCases.updateOpenSwitchgearTr() }
    }

    private fun updateRequestWorkWorker() {
        viewModelScope.launch(Dispatchers.IO) { taskSchedulerUpdateRequestWorkBaseWorker.scheduleDailyTask12hour() }
    }

    private fun updateTsn() {
        viewModelScope.launch(Dispatchers.IO) {
            updateLocaleBaseUseCases.updateTsn()
        }
    }

    private fun updateTg() {
        viewModelScope.launch(Dispatchers.IO) {
            updateLocaleBaseUseCases.updateTg()
        }
    }

    private fun updateElMotor() {
        viewModelScope.launch(Dispatchers.IO) {
            updateLocaleBaseUseCases.updateElMotor()
        }
    }

    private fun updateSwitchgear() {
        viewModelScope.launch(Dispatchers.IO) {
            updateLocaleBaseUseCases.updateSwitchgear()
        }
    }

    private fun updateLightingAndOther() {
        viewModelScope.launch(Dispatchers.IO) {
            updateLocaleBaseUseCases.updateLightingAndOther()
        }
    }

    private fun equalsEditAccess() {
        viewModelScope.launch{
            accessUseCases.equalsEditAccessPrefFb()
        }
    }

    private suspend fun initNotification() = withContext(Dispatchers.IO) {
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