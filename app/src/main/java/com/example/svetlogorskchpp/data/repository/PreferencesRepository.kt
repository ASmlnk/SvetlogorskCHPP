package com.example.svetlogorskchpp.data.repository

import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {

    val selectShiftSchedule: Flow<String>
    suspend fun setSelectShiftSchedule(shift: String)
}