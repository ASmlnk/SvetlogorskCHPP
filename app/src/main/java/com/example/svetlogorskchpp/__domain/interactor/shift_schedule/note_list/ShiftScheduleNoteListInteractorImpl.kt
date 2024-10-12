package com.example.svetlogorskchpp.__domain.interactor.shift_schedule.note_list

import com.example.svetlogorskchpp.__data.repository.noteRequestWork.NoteRequestWorkRepository
import com.example.svetlogorskchpp.__data.repository.preferences.RequestWorkPreferencesRepository
import com.example.svetlogorskchpp.__domain.OperationResult
import com.example.svetlogorskchpp.__domain.SuccessResult
import com.example.svetlogorskchpp.__domain.en.RequestWorkFilter
import com.example.svetlogorskchpp.__domain.en.RequestWorkSorted
import com.example.svetlogorskchpp.__domain.model.Note
import com.example.svetlogorskchpp.__domain.usecases.RequestWorkSortedUseCases
import com.example.svetlogorskchpp.__domain.usecases.RequestFilter
import com.example.svetlogorskchpp.__domain.usecases.RequestWorkFilterFactoryUseCases
import com.example.svetlogorskchpp.__presentation.shift_schedule_list_request_works.model.RequestWorkNotesListUI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ShiftScheduleNoteListInteractorImpl @Inject constructor(
    private val preferencesRepository: RequestWorkPreferencesRepository,
    private val noteRequestWorkRepository: NoteRequestWorkRepository,
    private val sortedUseCases: RequestWorkSortedUseCases,
    private val filterUseCases: RequestWorkFilterFactoryUseCases,
) : ShiftScheduleNoteListInteractor {

    private val requestWorkNotesFlow: Flow<List<Note.NoteRequestWork>> =
        noteRequestWorkRepository.getAllFlow().map { requestWork ->
            requestWork.mapNotNull { it.toNote() as? Note.NoteRequestWork }
        }
    private val sortedPreferences: Flow<RequestWorkSorted> =
        preferencesRepository.selectSortedRequestWork
    private val filterPreferences: Flow<Set<RequestWorkFilter>> =
        preferencesRepository.selectFilterRequestWork

    override val requestWorkNotesListUIFlow: Flow<RequestWorkNotesListUI> =
        combine(
            requestWorkNotesFlow,
            sortedPreferences,
            filterPreferences
        ) { requestWorks, sorted, filter ->

            val requestFilter = filterUseCases.getFilters(filter.toList())
            val filterRequestWork = if (requestFilter.isEmpty()) {
                emptyList()
            } else if(RequestWorkFilter.ALL in filter) {
                requestWorks
            } else {
                applyFilter(requestWorks, requestFilter)
            }

            val sortedRequestWork = if (filterRequestWork.isNotEmpty()) {
                sortedUseCases.applySorted(filterRequestWork, sorted)
            } else {
                filterRequestWork
            }
            RequestWorkNotesListUI(requestWork = sortedRequestWork, sortedFlag = sorted)
        }

    override suspend fun deleteNote(note: Note.NoteRequestWork): OperationResult<SuccessResult> {
        return noteRequestWorkRepository.deleteRequestWork(note.toNoteRequestWorkEntity())
    }

    override fun getSortedFlag(): Flow<RequestWorkSorted> =
        preferencesRepository.selectSortedRequestWork

    override fun getFilterFlag(): Flow<Set<RequestWorkFilter>> =
        preferencesRepository.selectFilterRequestWork

    override suspend fun setSortedFlag(sorted: RequestWorkSorted) {
        preferencesRepository.setSelectSortedRequestWork(sorted)
    }

    override suspend fun setFilterFlag(filter: List<RequestWorkFilter>) {
        preferencesRepository.setSelectFilterRequestWork(filter.toSet())
    }

    private fun applyFilter(
        requestWorks: List<Note.NoteRequestWork>,
        requestFilter: List<RequestFilter>,
    ): List<Note.NoteRequestWork> {
        return requestFilter.fold(emptyList()) { acc, filter -> acc + filter.filter(requestWorks) }
    }
}