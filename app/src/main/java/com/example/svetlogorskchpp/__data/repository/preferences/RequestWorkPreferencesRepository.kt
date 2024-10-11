package com.example.svetlogorskchpp.__data.repository.preferences

import com.example.svetlogorskchpp.__domain.en.RequestWorkFilter
import com.example.svetlogorskchpp.__domain.en.RequestWorkSorted
import kotlinx.coroutines.flow.Flow

interface RequestWorkPreferencesRepository {
    val selectSortedRequestWork: Flow<RequestWorkSorted>
    suspend fun setSelectSortedRequestWork(sorted: RequestWorkSorted)
    val selectFilterRequestWork: Flow<Set<RequestWorkFilter>>
    suspend fun setSelectFilterRequestWork(filter: Set<RequestWorkFilter>)
}