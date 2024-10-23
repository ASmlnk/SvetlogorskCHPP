package com.example.svetlogorskchpp.__data.repository.preferences

import com.example.svetlogorskchpp.__domain.en.shift_schedule.RequestWorkFilter
import com.example.svetlogorskchpp.__domain.en.shift_schedule.RequestWorkSorted
import kotlinx.coroutines.flow.Flow

interface RequestWorkPreferencesRepository {
    val selectSortedRequestWork: Flow<RequestWorkSorted>
    suspend fun setSelectSortedRequestWork(sorted: RequestWorkSorted)
    val selectFilterRequestWork: Flow<Set<RequestWorkFilter>>
    suspend fun setSelectFilterRequestWork(filter: Set<RequestWorkFilter>)
}