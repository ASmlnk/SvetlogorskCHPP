package com.example.svetlogorskchpp.data.repository.preferences

import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {

    val selectShiftSchedule: Flow<String>
    suspend fun setSelectShiftSchedule(shift: String)

    val selectCalendarViewShiftSchedule: Flow<String>
    suspend fun setSelectCalendarViewShiftSchedule(view: String)
}