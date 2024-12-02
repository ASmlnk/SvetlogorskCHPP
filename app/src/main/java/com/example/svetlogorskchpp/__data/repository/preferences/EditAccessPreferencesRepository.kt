package com.example.svetlogorskchpp.__data.repository.preferences

import kotlinx.coroutines.flow.Flow

interface EditAccessPreferencesRepository {

    val editInfoKd: Flow<String>
    suspend fun setEditInfoKd(kd: String)

    val isEditInfoAccess: Flow<Boolean>
    suspend fun setIsEditInfoAccess(isAccess: Boolean)
}