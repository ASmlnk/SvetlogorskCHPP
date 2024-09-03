package com.example.svetlogorskchpp.__data.repository.shiftPersonnel

import com.example.svetlogorskchpp.__data.model.ShiftPersonalDto
import kotlinx.coroutines.flow.Flow

interface ShiftPersonalRepository {
    fun getShiftPersonalStream(networkAvailable: Boolean): Flow<List<ShiftPersonalDto>>
    suspend fun setShiftPersonalBD (list: List<ShiftPersonalDto>)
}