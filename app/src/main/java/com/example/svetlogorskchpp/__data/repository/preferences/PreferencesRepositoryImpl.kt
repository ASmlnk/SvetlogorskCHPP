package com.example.svetlogorskchpp.__data.repository.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
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

    override val selectShiftScheduleWidget: Flow<String> = dataStore.data.map {
        it[SELECT_SHIFT_SCHEDULE_WIDGET_KEY] ?: ""
    }.distinctUntilChanged()

    override suspend fun setSelectShiftScheduleWidget (shift: String) {
        dataStore.edit {
            it[SELECT_SHIFT_SCHEDULE_WIDGET_KEY] = shift
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

    override val selectCalendarViewShiftScheduleWidget: Flow<String> = dataStore.data.map {
        it[SELECT_CALENDAR_VIEW_SHIFT_SCHEDULE_WIDGET_KEY] ?: "1"
    }.distinctUntilChanged()

    override suspend fun setSelectCalendarViewShiftScheduleWidget(view: String) {
        dataStore.edit {
            it[SELECT_CALENDAR_VIEW_SHIFT_SCHEDULE_WIDGET_KEY] = view
        }
    }

    override val isNotificationNoteTechnical: Flow<Boolean> = dataStore.data.map {
        it[PREF_IS_NOTIFICATION_NOTE] ?: true
    }.distinctUntilChanged()

    override suspend fun setNotificationNoteTechnical(isNotification: Boolean) {
        dataStore.edit {
            it[PREF_IS_NOTIFICATION_NOTE] = isNotification
        }
    }



    companion object {
        private val SELECT_SHIFT_SCHEDULE_KEY = stringPreferencesKey("select_shift_schedule")
        private val SELECT_SHIFT_SCHEDULE_WIDGET_KEY = stringPreferencesKey("select_shift_schedule_widget")

        private val SELECT_CALENDAR_VIEW_SHIFT_SCHEDULE_KEY = stringPreferencesKey("select_calendar_view")
        private val SELECT_CALENDAR_VIEW_SHIFT_SCHEDULE_WIDGET_KEY = stringPreferencesKey("select_calendar_view_widget")

        private val PREF_IS_NOTIFICATION_NOTE = booleanPreferencesKey("isNotification_note")
    }
}