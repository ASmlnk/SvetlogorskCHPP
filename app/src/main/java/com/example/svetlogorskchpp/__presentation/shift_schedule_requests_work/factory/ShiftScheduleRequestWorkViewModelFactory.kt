package com.example.svetlogorskchpp.__presentation.shift_schedule_requests_work.factory

import com.example.svetlogorskchpp.__presentation.shift_schedule_calendar_add_notes.viewModel.ShiftScheduleAddNotesViewModel
import com.example.svetlogorskchpp.__presentation.shift_schedule_requests_work.viewModel.ShiftScheduleRequestWorkViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory

@AssistedFactory
interface ShiftScheduleRequestWorkViewModelFactory {
    fun create (@Assisted noteRequestWork: String): ShiftScheduleRequestWorkViewModel
}