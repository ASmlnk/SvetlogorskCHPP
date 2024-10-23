package com.example.svetlogorskchpp.__presentation.shift_schedule.shift_schedule_calendar_add_notes.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.svetlogorskchpp.__domain.model.shift_schedule.CalendarMyNoteTag
import com.example.svetlogorskchpp.__domain.model.shift_schedule.Note
import com.example.svetlogorskchpp.__domain.usecases.shift_schedule.calendarDateUseCases.CalendarDateUseCases
import com.example.svetlogorskchpp.__domain.usecases.shift_schedule.calendarNote.CalendarNoteUseCases
import com.example.svetlogorskchpp.__domain.usecases.shift_schedule.calendarNoteTag.CalendarNoteTagUseCases
import com.example.svetlogorskchpp.model.firebase.FirestoreRepository
import com.example.svetlogorskchpp.__presentation.shift_schedule.shift_schedule_calendar_add_notes.model.NoteUiState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    private val calendarMyNoteTag = CalendarMyNoteTag(
        date = calendarDateUseCases.calendarToDateYMD(_dateStateFlow.value),
        month = calendarDateUseCases.calendarToDateYM(_dateStateFlow.value),
        isTechnical = false
    )

    private val calendarNoteMy = Note.NoteMy(
        tagDate = calendarDateUseCases.calendarToDateYMD(_dateStateFlow.value),
        dateNotes = _dateStateFlow.value,
        content = ""
    )

    private val _calendarNoteUiState = MutableStateFlow(
        NoteUiState(
            calendarMyNoteTag = calendarMyNoteTag,
            isTimeNote = false
        )
    )
    val calendarNoteUiState: StateFlow<NoteUiState>
        get() = _calendarNoteUiState.asStateFlow()

    private val calendarNoteStream = calendarNoteUseCases.getNotesByTagId(tagDate = _dateStateFlow.value)
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())


    private val _calendarNoteTagStream =
        calendarNoteTagUseCases.getTagsByDate(_dateStateFlow.value)
            .stateIn(viewModelScope, SharingStarted.Lazily, calendarMyNoteTag)

    init {
        viewModelScope.launch {
            firestoreRepository.getAllInspection(date = date())
        }
        viewModelScope.launch {
            _calendarNoteTagStream.collect { calendarNoteTag ->
                calendarNoteTag?.let {
                    _calendarNoteUiState.update { old ->
                        old.copy(
                            calendarMyNoteTag = it
                        )
                    }
                }
            }
        }
        viewModelScope.launch {
            calendarNoteStream.collect { notes ->
                _calendarNoteUiState.update { oldState ->
                    oldState.copy(
                        noteMIES = notes
                    )
                }
            }
        }
    }

    fun calendarNoteDateAdapter(calendar: Calendar): String {
       return calendarDateUseCases.calendarToStringFormatDDMMMMYYYYHHmm(calendar)
    }

    fun insertNote(content: String) {
        viewModelScope.launch (Dispatchers.IO) {
            val note = calendarNoteMy.copy(
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

    fun deleteNote(noteMy: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            calendarNoteUseCases.deleteNote(noteMy)
            if (noteMy is Note.NoteMy) {
                val myNote = calendarNoteUiState.value.noteMIES.filterIsInstance<Note.NoteMy>()
                if (myNote.size == 1) {
                    insertIsNotes(false)
                }
            }
        }
    }

    fun insertIsTechnical(isTechnical: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            val tag = _calendarNoteUiState.value.calendarMyNoteTag.copy(isTechnical = isTechnical)
            calendarNoteTagUseCases.insertTag(tag)
        }
    }

    private fun insertIsNotes(isNotes: Boolean) {
        viewModelScope.launch (Dispatchers.IO) {
            val tag = _calendarNoteUiState.value.calendarMyNoteTag.copy(isNotes = isNotes)
            calendarNoteTagUseCases.insertTag(tag)
        }
    }

    fun deleteNoteTag() {
       val myNote = _calendarNoteUiState.value.noteMIES.filterIsInstance<Note.NoteMy>()
        if (myNote.isNotEmpty()) {
            val calendarNoteTagNew = _calendarNoteUiState.value.calendarMyNoteTag.copy(isNotes = true)
            _calendarNoteUiState.update { oldState ->
                oldState.copy(calendarMyNoteTag = calendarNoteTagNew)
            }
            viewModelScope.launch {
                insertIsNotes(true)
            }
        } else {
            val calendarNoteTagNew = _calendarNoteUiState.value.calendarMyNoteTag.copy(isNotes = false)
            _calendarNoteUiState.update { oldState ->
                oldState.copy(calendarMyNoteTag = calendarNoteTagNew)
            }
            viewModelScope.launch {
                insertIsNotes(false)
            }
        }

        if (!_calendarNoteUiState.value.calendarMyNoteTag.isNotes && !_calendarNoteUiState.value.calendarMyNoteTag.isTechnical) {
            viewModelScope.launch {
                calendarNoteTagUseCases.deleteCalendarTag(_calendarNoteUiState.value.calendarMyNoteTag)
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