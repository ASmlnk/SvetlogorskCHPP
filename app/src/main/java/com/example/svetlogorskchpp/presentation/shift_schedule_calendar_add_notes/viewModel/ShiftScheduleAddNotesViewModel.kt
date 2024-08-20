package com.example.svetlogorskchpp.presentation.shift_schedule_calendar_add_notes.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.room.util.copy
import com.example.svetlogorskchpp.domain.model.CalendarNoteTag
import com.example.svetlogorskchpp.domain.model.Note
import com.example.svetlogorskchpp.domain.usecases.calendarDate.CalendarDateUseCases
import com.example.svetlogorskchpp.domain.usecases.calendarNote.CalendarNoteUseCases
import com.example.svetlogorskchpp.domain.usecases.calendarNoteTag.CalendarNoteTagUseCases
import com.example.svetlogorskchpp.domain.usecases.inspectionSchedule.InspectionUsesCases
import com.example.svetlogorskchpp.model.firebase.FirestoreRepository
import com.example.svetlogorskchpp.presentation.shift_schedule_calendar_add_notes.model.NoteUiState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.GregorianCalendar
import java.util.TimeZone

class ShiftScheduleAddNotesViewModel @AssistedInject constructor(
    private val calendarDateUseCases: CalendarDateUseCases,
    private val calendarNoteTagUseCases: CalendarNoteTagUseCases,
    private val calendarNoteUseCases: CalendarNoteUseCases,
    private val firestoreRepository: FirestoreRepository,
    @Assisted private val date: Long,
) : ViewModel() {

    private val _dateStateFlow: MutableStateFlow<Calendar> = MutableStateFlow(toCalendar(date))
    private val dateStateFlow: StateFlow<Calendar>
        get() = _dateStateFlow.asStateFlow()

    private val calendarNoteTag = CalendarNoteTag(
        date = calendarDateUseCases.calendarToDateYMD(_dateStateFlow.value),
        month = calendarDateUseCases.calendarToDateYM(_dateStateFlow.value),
        isTechnical = false
    )

    private val calendarNote = Note(
        tagDate = calendarDateUseCases.calendarToDateYMD(_dateStateFlow.value),
        dateNotes = _dateStateFlow.value,
        content = ""
    )

    private val _calendarNoteUiState = MutableStateFlow(
        NoteUiState(
            calendarNoteTag = calendarNoteTag,
            isTimeNote = false
        )
    )
    val calendarNoteUiState: StateFlow<NoteUiState>
        get() = _calendarNoteUiState.asStateFlow()

    private val calendarNoteStream = calendarNoteUseCases.getNotesByTagId(tagDate = _dateStateFlow.value)
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())


    private val _calendarNoteTagStream =
        calendarNoteTagUseCases.getTagsByDate(_dateStateFlow.value)

    init {
        viewModelScope.launch {
            firestoreRepository.getAllInspection(date = date())
        }
        viewModelScope.launch {
            _calendarNoteTagStream.collect { calendarNoteTag ->
                calendarNoteTag?.let {
                    _calendarNoteUiState.update { old ->
                        old.copy(
                            calendarNoteTag = it
                        )
                    }
                }
            }
        }
        viewModelScope.launch {
            calendarNoteStream.collect { notes ->
                _calendarNoteUiState.update { oldState ->
                    oldState.copy(
                        notes = notes
                    )
                }
            }
        }
    }

    fun insertNote(content: String) {
        viewModelScope.launch (Dispatchers.IO) {
            val note = calendarNote.copy(
                dateNotes = _calendarNoteUiState.value.timeNote?: _dateStateFlow.value,
                isTimeNotes = _calendarNoteUiState.value.isTimeNote,
                content = content
            )
            calendarNoteUseCases.insertNote(note)
            insertIsNotes(isNotes = true)
            resetTimeNote()
        }
    }

    fun resetTimeNote() {
        viewModelScope.launch {
            _calendarNoteUiState.update { oldState ->
                oldState.copy(
                    isTimeNote = false,
                    timeNote = null
                )
            }
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            calendarNoteUseCases.deleteNote(note)
            if (calendarNoteUiState.value.notes.size == 1) {
                insertIsNotes(false)
            }
        }
    }

    fun insertIsTechnical(isTechnical: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            val tag = _calendarNoteUiState.value.calendarNoteTag.copy(isTechnical = isTechnical)
            calendarNoteTagUseCases.insertTag(tag)
        }
    }

    private fun insertIsNotes(isNotes: Boolean) {
        viewModelScope.launch (Dispatchers.IO) {
            val tag = _calendarNoteUiState.value.calendarNoteTag.copy(isNotes = isNotes)
            calendarNoteTagUseCases.insertTag(tag)
        }
    }

    fun deleteNoteTag() {
        if (!_calendarNoteUiState.value.calendarNoteTag.isNotes && !_calendarNoteUiState.value.calendarNoteTag.isTechnical) {
            viewModelScope.launch {
                calendarNoteTagUseCases.deleteCalendarTag(_calendarNoteUiState.value.calendarNoteTag)
            }
        }
    }

    fun calendarDate(): String {
        return calendarDateUseCases.calendarToStringFormatDDMMMMYYYY(_dateStateFlow.value)
    }

    fun calendarDateActual(): Calendar {
        return dateStateFlow.value.clone() as Calendar
    }

    fun viewTime(calendar: Calendar) {
        _calendarNoteUiState.update { oldState ->
            oldState.copy(
                timeNote = calendar,
                isTimeNote = true
            )
        }
    }

    private fun toCalendar(millis: Long): Calendar {
        val cal = Calendar.getInstance()
        cal.timeInMillis = millis
        return cal
    }

    fun date(): String {
        val calendar = GregorianCalendar
            .getInstance(TimeZone.getTimeZone("GMT+3")).apply { firstDayOfWeek = 2 }

        return calendar.timeInMillis.toString()
    }

    @AssistedFactory
    interface ShiftShiftScheduleAddNotesViewModelFactory {
        fun create(
            @Assisted date: Long,
        ): ShiftScheduleAddNotesViewModel
    }

    @Suppress("UNCHECKED_CAST")
    companion object {
        fun providesFactory(
            assistedFactory: ShiftShiftScheduleAddNotesViewModelFactory,
            date: Long,
        ): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return assistedFactory.create(date) as T
                }
            }
        }
    }
}