package com.example.svetlogorskchpp.__data.repository.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.svetlogorskchpp.__domain.en.shift_schedule.RequestWorkFilter
import com.example.svetlogorskchpp.__domain.en.shift_schedule.RequestWorkSorted
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.lang.IllegalArgumentException
import javax.inject.Inject

class PreferencesRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : PreferencesRepository, RequestWorkPreferencesRepository, NotesNotificationPreferencesRepository,
    EditAccessPreferencesRepository {

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

    override suspend fun setSelectShiftScheduleWidget(shift: String) {
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

    override val isNotificationRequestWork: Flow<Boolean> = dataStore.data.map {
        it[PREF_IS_NOTIFICATION_REQUEST_WORK] ?: true
    }.distinctUntilChanged()

    override suspend fun setNotificationRequestWork(isNotification: Boolean) {
        dataStore.edit {
            it[PREF_IS_NOTIFICATION_REQUEST_WORK] = isNotification
        }
    }

    override val isRequestWorkViewCalendar: Flow<Boolean> = dataStore.data.map {
        it[PREF_IS_REQUEST_WORK_VIEW_CALENDAR] ?: true
    }.distinctUntilChanged()

    override suspend fun setRequestWorkViewCalendar(isViewRequestWork: Boolean) {
        dataStore.edit {
            it[PREF_IS_REQUEST_WORK_VIEW_CALENDAR] = isViewRequestWork
        }
    }

    override val selectSortedRequestWork: Flow<RequestWorkSorted> = dataStore.data.map {
        val requestWorkSortedString = it[SORTED_REQUEST_WORK] ?: RequestWorkSorted.DATE_OPEN.name
        RequestWorkSorted.valueOf(requestWorkSortedString)
    }.distinctUntilChanged()

    override suspend fun setSelectSortedRequestWork(sorted: RequestWorkSorted) {
        dataStore.edit {
            it[SORTED_REQUEST_WORK] = sorted.name
        }
    }

    override val selectFilterRequestWork: Flow<Set<RequestWorkFilter>> = dataStore.data.map {
        val jsonString =
            it[FILTER_REQUEST_WORK] ?: Json.encodeToString(setOf(RequestWorkFilter.ALL.name))
        val jsonStringList = Json.decodeFromString<List<String>>(jsonString).toSet()
        jsonStringList.mapNotNull { string ->
            try {
                RequestWorkFilter.valueOf(string)
            } catch (e: IllegalArgumentException) {
                null
            }
        }.toSet()
    }.distinctUntilChanged()

    override suspend fun setSelectFilterRequestWork(filter: Set<RequestWorkFilter>) {
        val jsonStringSet = filter.map { it.name }
        val jsonString = Json.encodeToString(jsonStringSet)
        dataStore.edit {
            it[FILTER_REQUEST_WORK] = jsonString
        }
    }


    override val editInfoKd: Flow<String> = dataStore.data.map {
        it[EDIT_INFO_KD] ?: ""
    }.distinctUntilChanged()

    override suspend fun setEditInfoKd(kd: String) {
        dataStore.edit {
            it[EDIT_INFO_KD] = kd
        }
    }

    override val isEditInfoAccess: Flow<Boolean> = dataStore.data.map {
        it[IS_EDIT_INFO_ACCESS] ?: false
    }.distinctUntilChanged()

    override suspend fun setIsEditInfoAccess(isAccess: Boolean) {
        dataStore.edit {
            it[IS_EDIT_INFO_ACCESS] = isAccess
        }
    }


    companion object {
        private val SELECT_SHIFT_SCHEDULE_KEY = stringPreferencesKey("select_shift_schedule")
        private val SELECT_SHIFT_SCHEDULE_WIDGET_KEY =
            stringPreferencesKey("select_shift_schedule_widget")

        private val SELECT_CALENDAR_VIEW_SHIFT_SCHEDULE_KEY =
            stringPreferencesKey("select_calendar_view")
        private val SELECT_CALENDAR_VIEW_SHIFT_SCHEDULE_WIDGET_KEY =
            stringPreferencesKey("select_calendar_view_widget")

        private val PREF_IS_NOTIFICATION_NOTE = booleanPreferencesKey("isNotification_note")
        private val PREF_IS_NOTIFICATION_REQUEST_WORK =
            booleanPreferencesKey("is_notification_request_work")
        private val PREF_IS_REQUEST_WORK_VIEW_CALENDAR =
            booleanPreferencesKey("is_request_work_view_calendar")

        private val SORTED_REQUEST_WORK = stringPreferencesKey("sorted_request_work")
        private val FILTER_REQUEST_WORK = stringPreferencesKey("filter_request_work")

        private val EDIT_INFO_KD = stringPreferencesKey("edit_info_kd")
        private val IS_EDIT_INFO_ACCESS = booleanPreferencesKey("is_edit_info_kd")
    }
}