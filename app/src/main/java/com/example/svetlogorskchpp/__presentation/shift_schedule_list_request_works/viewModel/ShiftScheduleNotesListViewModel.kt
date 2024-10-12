package com.example.svetlogorskchpp.__presentation.shift_schedule_list_request_works.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.svetlogorskchpp.__domain.interactor.shift_schedule.note_list.ShiftScheduleNoteListInteractor
import com.example.svetlogorskchpp.__domain.model.Note
import com.example.svetlogorskchpp.__domain.usecases.calendarDateUseCases.CalendarDateUseCases
import com.example.svetlogorskchpp.__presentation.shift_schedule_list_request_works.model.NotesListStateUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class ShiftScheduleNotesListViewModel @Inject constructor(
    private val calendarDateUseCases: CalendarDateUseCases,
    private val shiftScheduleNoteListInteractor: ShiftScheduleNoteListInteractor,
) : ViewModel() {

    private val calendar = Calendar.getInstance()
    private val _notesListStateUI = MutableStateFlow(
        NotesListStateUI(
            todayDate = calendarDateUseCases.dateToStringFormatDDMMMMYYYY((calendar.clone() as Calendar).time)
        )
    )
    val notesListStateUI: StateFlow<NotesListStateUI> = _notesListStateUI

    init {
        viewModelScope.launch {
            /* calendarNoteUseCases.getAllNotes().collect { notes ->
                 _notesListStateUI.update { oldState ->
                     oldState.copy(notes = notes)
                 }
             }*/

            shiftScheduleNoteListInteractor.requestWorkNotesListUIFlow.collect { requestWorkNotesListUI ->
                _notesListStateUI.update { oldState ->
                    oldState.copy(
                        notes = requestWorkNotesListUI.requestWork,
                        sortedName = requestWorkNotesListUI.sortedFlag.get
                    )
                }
            }
        }
    }

    fun deleteNote(requestWork: Note) {
        viewModelScope.launch {
            shiftScheduleNoteListInteractor.deleteNote(requestWork as Note.NoteRequestWork)
        }
    }

    fun dateLong(): Long {
        return calendar.timeInMillis
    }
}