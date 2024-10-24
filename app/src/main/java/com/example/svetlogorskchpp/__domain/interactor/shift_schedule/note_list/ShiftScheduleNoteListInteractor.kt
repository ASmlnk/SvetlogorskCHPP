package com.example.svetlogorskchpp.__domain.interactor.shift_schedule.note_list

import com.example.svetlogorskchpp.__domain.OperationResult
import com.example.svetlogorskchpp.__domain.SuccessResult
import com.example.svetlogorskchpp.__domain.en.shift_schedule.RequestWorkFilter
import com.example.svetlogorskchpp.__domain.en.shift_schedule.RequestWorkSorted
import com.example.svetlogorskchpp.__domain.model.shift_schedule.Note
import com.example.svetlogorskchpp.__presentation.shift_schedule.shift_schedule_list_request_works.model.RequestWorkNotesListUI
import kotlinx.coroutines.flow.Flow

interface ShiftScheduleNoteListInteractor {
    val requestWorkNotesListUIFlow: Flow<RequestWorkNotesListUI>
    fun getSortedFlag(): Flow<RequestWorkSorted>
    fun getFilterFlag(): Flow<Set<RequestWorkFilter>>
    suspend fun setSortedFlag(sorted: RequestWorkSorted)
    suspend fun setFilterFlag(filter: List<RequestWorkFilter>)
    suspend fun deleteNote(note: Note.NoteRequestWork): OperationResult<SuccessResult>
}