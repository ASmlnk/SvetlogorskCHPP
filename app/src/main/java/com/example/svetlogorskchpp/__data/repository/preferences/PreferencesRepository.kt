package com.example.svetlogorskchpp.__data.repository.preferences

import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {

    val selectShiftSchedule: Flow<String>
    suspend fun setSelectShiftSchedule(shift: String)

    val selectCalendarViewShiftSchedule: Flow<String>
    suspend fun setSelectCalendarViewShiftSchedule(view: String)

    val selectShiftScheduleWidget: Flow<String>
    suspend fun setSelectShiftScheduleWidget(shift: String)

    suspend fun setSelectCalendarViewShiftScheduleWidget(view: String)
    val selectCalendarViewShiftScheduleWidget: Flow<String>
}