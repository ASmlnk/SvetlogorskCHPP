package com.example.svetlogorskchpp.__presentation.dialog.request_work_filter.view_model

import androidx.lifecycle.ViewModel
import com.example.svetlogorskchpp.__domain.interactor.shift_schedule.note_list.ShiftScheduleNoteListInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RequestWorkFilterViewModel @Inject constructor(
    private val shiftScheduleNoteListInteractor: ShiftScheduleNoteListInteractor
): ViewModel() {

    val filterFlagState = shiftScheduleNoteListInteractor.

    //    stateIn(
      //  scope = CoroutineScope(Dispatchers.Default),
       // started = SharingStarted.Lazily,
       // initialValue = null
    //)

}