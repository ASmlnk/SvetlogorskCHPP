package com.example.svetlogorskchpp.presentation.shift_schedule.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.svetlogorskchpp.domain.interactor.shift_schedule.ShiftPersonal.ShiftScheduleShiftPersonalInteractor
import com.example.svetlogorskchpp.presentation.shift_schedule_edit_composition.model.JobTitlePersonal
import com.example.svetlogorskchpp.presentation.shift_schedule_edit_composition.model.Staff
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
