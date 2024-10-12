package com.example.svetlogorskchpp.__presentation.dialog.request_work_sorted.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.svetlogorskchpp.__domain.en.RequestWorkSorted
import com.example.svetlogorskchpp.__domain.interactor.shift_schedule.note_list.ShiftScheduleNoteListInteractor
import com.example.svetlogorskchpp.__presentation.shift_schedule.model.CalendarFullDayShiftModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RequestWorkSortedViewModel @Inject constructor(
    private val shiftScheduleNoteListInteractor: ShiftScheduleNoteListInteractor
): ViewModel() {

    val sortedFlagState = shiftScheduleNoteListInteractor.getSortedFlag().stateIn(
        scope = CoroutineScope(Dispatchers.Default),
        started = SharingStarted.Lazily,
        initialValue = null
    )

    fun setSortedFlag(sorted: RequestWorkSorted) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                shiftScheduleNoteListInteractor.setSortedFlag(sorted)
            }
        }
    }
}