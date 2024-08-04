package com.example.svetlogorskchpp.domain.interactor.shift_schedule.ShiftPersonal

import com.example.svetlogorskchpp.presentation.shift_schedule_edit_composition.model.ShiftPersonal
import kotlinx.coroutines.flow.Flow

interface ShiftScheduleShiftPersonalInteractor {

    fun getShiftPersonalStream(): Flow<List<ShiftPersonal>>
    suspend fun setShiftPersonalBD(list: List<ShiftPersonal>)
}