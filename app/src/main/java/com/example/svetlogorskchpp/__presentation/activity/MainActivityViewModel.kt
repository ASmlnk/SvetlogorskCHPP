package com.example.svetlogorskchpp.__presentation.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.svetlogorskchpp.__data.repository.shift_schedule.noteRequestWork.NoteRequestWorkRepository
import com.example.svetlogorskchpp.__domain.task_schedule.notification.TaskSchedulerNotificationWorker
import com.example.svetlogorskchpp.__domain.task_schedule.update_request_work.TaskSchedulerUpdateRequestWorkBaseWorker
import com.example.svetlogorskchpp.__domain.task_schedule.update_request_work.TaskSchedulerUpdateRequestWorkBaseWorkerImpl
import com.example.svetlogorskchpp.__domain.usecases.calendarPreferencesNotificationUseCases.CalendarPreferencesNotificationUseCases
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
    private val noteRequestWorkRepository: NoteRequestWorkRepository,
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
        }
    }

    suspend fun getRequestWorkFirebase() {
        noteRequestWorkRepository.getRequestWorkFirebase()
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