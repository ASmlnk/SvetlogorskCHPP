package com.example.svetlogorskchpp.presentation.shift_schedule_edit_composition.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.svetlogorskchpp.domain.interactor.shift_schedule.ShiftPersonal.ShiftScheduleShiftPersonalInteractor
import com.example.svetlogorskchpp.presentation.shift_schedule_edit_composition.model.JobTitlePersonal
import com.example.svetlogorskchpp.presentation.shift_schedule_edit_composition.model.ShiftScheduleEditUiState
import com.example.svetlogorskchpp.presentation.shift_schedule_edit_composition.model.Staff
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShiftScheduleEditCompositionViewModel @Inject constructor(
    private val shiftScheduleShiftPersonalInteractor: ShiftScheduleShiftPersonalInteractor
) : ViewModel() {

    private val _jobTitlePersonalUi: MutableStateFlow<ShiftScheduleEditUiState> = MutableStateFlow(ShiftScheduleEditUiState())
    val jobTitlePersonalUi: StateFlow<ShiftScheduleEditUiState>
        get() = _jobTitlePersonalUi.asStateFlow()

    private val _staffStream: StateFlow<List<Staff>> = shiftScheduleShiftPersonalInteractor.getStaffStream()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val _jobTitlePersonalStream: StateFlow<List<JobTitlePersonal>> = shiftScheduleShiftPersonalInteractor.getShiftPersonalStream()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val _stateSave: MutableStateFlow<Boolean> = MutableStateFlow(false)

    init {
        viewModelScope.launch {
            _jobTitlePersonalStream.collect { listJobTitlePersonals ->
                _jobTitlePersonalUi.update { oldState ->
                    oldState.copy(
                        jobTitlePersonals = listJobTitlePersonals
                    )
                }
            }
        }
        viewModelScope.launch {
            _staffStream.collect { listStaff ->
                _jobTitlePersonalUi.update { oldState ->
                    oldState.copy(
                        staffs = listStaff
                    )
                }
            }
        }
    }

    fun save(jobTitlePersonal: JobTitlePersonal) {
        val list = _jobTitlePersonalUi.value.jobTitlePersonals.toMutableList()

        if (list.isNotEmpty()) {
            val shiftPersonalApi =
                list.first { it.jobTitle == jobTitlePersonal.jobTitle }
            val index = list.indexOf(shiftPersonalApi)
            list.remove(shiftPersonalApi)
            list.add(index, jobTitlePersonal)
            _jobTitlePersonalUi.update { oldState ->
                oldState.copy(
                    jobTitlePersonals = list
                )
            }
            _stateSave.update { true }
        }
    }

    suspend fun saveBd() {
       if (_stateSave.value) shiftScheduleShiftPersonalInteractor.setShiftPersonalBD(_jobTitlePersonalUi.value.jobTitlePersonals)
    }
}



