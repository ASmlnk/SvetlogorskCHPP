package com.example.svetlogorskchpp.__data.repository.preferences

import kotlinx.coroutines.flow.Flow

interface NotesNotificationPreferencesRepository {
    val isNotificationNoteTechnical: Flow<Boolean>
    suspend fun setNotificationNoteTechnical(isNotification: Boolean)
    val isNotificationRequestWork: Flow<Boolean>
    suspend fun setNotificationRequestWork(isNotification: Boolean)
    val isRequestWorkViewCalendar: Flow<Boolean>
    suspend fun setRequestWorkViewCalendar(isViewRequestWork: Boolean)
}