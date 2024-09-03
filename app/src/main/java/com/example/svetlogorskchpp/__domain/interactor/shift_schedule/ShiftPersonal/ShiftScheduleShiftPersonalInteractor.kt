package com.example.svetlogorskchpp.__domain.interactor.shift_schedule.ShiftPersonal

import com.example.svetlogorskchpp.__presentation.shift_schedule_edit_composition.model.JobTitlePersonal
import com.example.svetlogorskchpp.__presentation.shift_schedule_edit_composition.model.Staff
import kotlinx.coroutines.flow.Flow

interface ShiftScheduleShiftPersonalInteractor {

    fun getShiftPersonalStream(): Flow<List<JobTitlePersonal>>
    suspend fun setShiftPersonalBD(list: List<JobTitlePersonal>)
    fun getStaffStream(): Flow<List<Staff>>
}