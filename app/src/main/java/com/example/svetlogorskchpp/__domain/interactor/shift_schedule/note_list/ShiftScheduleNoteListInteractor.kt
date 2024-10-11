package com.example.svetlogorskchpp.__domain.interactor.shift_schedule.note_list

import com.example.svetlogorskchpp.__domain.en.RequestWorkFilter
import com.example.svetlogorskchpp.__domain.en.RequestWorkSorted
import com.example.svetlogorskchpp.__domain.model.Note
import com.example.svetlogorskchpp.__presentation.shift_schedule_list_notes.model.RequestWorkNotesListUI
import kotlinx.coroutines.flow.Flow

interface ShiftScheduleNoteListInteractor {
    val requestWorkNotesListUIFlow: Flow<RequestWorkNotesListUI>
    fun getSortedFlag(): Flow<RequestWorkSorted>
    fun getFilterFlag(): Flow<Set<RequestWorkFilter>>
    suspend fun setSortedFlag(sorted: RequestWorkSorted)
    suspend fun setFilterFlag(filter: List<RequestWorkFilter>)
}