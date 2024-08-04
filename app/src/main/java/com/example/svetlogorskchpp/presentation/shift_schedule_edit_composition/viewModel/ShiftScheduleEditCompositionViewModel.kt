package com.example.svetlogorskchpp.presentation.shift_schedule_edit_composition.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.svetlogorskchpp.data.database.ShiftPersonalEntity
import com.example.svetlogorskchpp.data.model.ShiftPersonalDto
import com.example.svetlogorskchpp.data.repository.shiftPersonnel.ShiftPersonalRepository
import com.example.svetlogorskchpp.data.repository.shiftPersonnel.ShiftPersonalRepositoryImpl
import com.example.svetlogorskchpp.domain.en.JobTitle
import com.example.svetlogorskchpp.domain.en.Shift
import com.example.svetlogorskchpp.domain.interactor.shift_schedule.ShiftPersonal.ShiftScheduleShiftPersonalInteractor
import com.example.svetlogorskchpp.domain.interactor.shift_schedule.ShiftPersonal.ShiftScheduleShiftPersonalInteractorImpl
import com.example.svetlogorskchpp.presentation.shift_schedule_edit_composition.model.ShiftPersonal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShiftScheduleEditCompositionViewModel @Inject constructor(
    private val shiftScheduleShiftPersonalInteractor: ShiftScheduleShiftPersonalInteractor
) : ViewModel() {

    private val _editList: MutableStateFlow<List<ShiftPersonal>> = MutableStateFlow(emptyList())

    val shiftPersonalFlow: StateFlow<List<ShiftPersonal>> = shiftScheduleShiftPersonalInteractor.getShiftPersonalStream()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    init {
        viewModelScope.launch {
            shiftPersonalFlow.collect { listShiftPersonal ->
                _editList.update { listShiftPersonal }
            }
        }
    }

    fun save(jobTitle: JobTitle, shift: Shift, name: String) {
        val list = _editList.value.toMutableList()

        if (list.isNotEmpty()) {
            val shiftPersonalApi =
                list.filter { it.shift == shift }.first { it.jobTitle == jobTitle }
            list.remove(shiftPersonalApi)
            val new = shiftPersonalApi.copy(
                namePersonal = name)
            list.add(new)
            _editList.update { list }
        }
    }

    suspend fun saveBd() {
        shiftScheduleShiftPersonalInteractor.setShiftPersonalBD(_editList.value)
    }
}



