package com.example.svetlogorskchpp.data.repository.shiftPersonnel

import com.example.svetlogorskchpp.data.model.ShiftPersonalDto
import kotlinx.coroutines.flow.Flow

interface ShiftPersonalRepository {
    fun getShiftPersonalStream(): Flow<List<ShiftPersonalDto>>
    suspend fun setShiftPersonalBD (list: List<ShiftPersonalDto>)
}