package com.example.svetlogorskchpp.__presentation.dialog.request_work_filter.view_model

import androidx.lifecycle.ViewModel

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