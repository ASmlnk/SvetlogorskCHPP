package com.example.svetlogorskchpp.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PreferencesRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : PreferencesRepository {

   override val selectShiftSchedule: Flow<String> = dataStore.data.map {
        it[SELECT_SHIFT_SCHEDULE_KEY] ?: ""
    }.distinctUntilChanged()

    override suspend fun setSelectShiftSchedule(shift: String) {
        dataStore.edit {
            it[SELECT_SHIFT_SCHEDULE_KEY] = shift
        }
    }

    override val selectCalendarViewShiftSchedule: Flow<String> = dataStore.data.map {
        it[SELECT_CALENDAR_VIEW_SHIFT_SCHEDULE_KEY] ?: "1"
    }.distinctUntilChanged()

    override suspend fun setSelectCalendarViewShiftSchedule(view: String) {
        dataStore.edit {
            it[SELECT_CALENDAR_VIEW_SHIFT_SCHEDULE_KEY] = view
        }
    }

    companion object {
        private val SELECT_SHIFT_SCHEDULE_KEY = stringPreferencesKey("select_shift_schedule")
        private val SELECT_CALENDAR_VIEW_SHIFT_SCHEDULE_KEY = stringPreferencesKey("select_calendar_view")
    }
}