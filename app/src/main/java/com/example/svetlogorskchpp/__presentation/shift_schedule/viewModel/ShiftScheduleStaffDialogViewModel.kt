package com.example.svetlogorskchpp.__presentation.shift_schedule.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.svetlogorskchpp.__domain.interactor.shift_schedule.ShiftPersonal.ShiftScheduleShiftPersonalInteractor
import com.example.svetlogorskchpp.__presentation.shift_schedule_edit_composition.model.JobTitlePersonal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ShiftScheduleStaffDialogViewModel @Inject constructor(
    private val shiftPersonalInteractor: ShiftScheduleShiftPersonalInteractor
): ViewModel() {

    val jobTitlePersonalStream: StateFlow<List<JobTitlePersonal>> = shiftPersonalInteractor.getShiftPersonalStream()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

}
