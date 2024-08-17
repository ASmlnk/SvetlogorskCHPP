package com.example.svetlogorskchpp.presentation.shift_schedule_calendar_add_notes.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.svetlogorskchpp.domain.model.CalendarNoteTag
import com.example.svetlogorskchpp.domain.usecases.calendarDate.CalendarDateUseCases
import com.example.svetlogorskchpp.domain.usecases.calendarNoteTag.CalendarNoteTagUseCases
import com.example.svetlogorskchpp.presentation.shift_schedule_calendar_add_notes.model.NoteUiState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar

class ShiftScheduleAddNotesViewModel @AssistedInject constructor(
    private val calendarDateUseCases: CalendarDateUseCases,
    private val calendarNoteTagUseCases: CalendarNoteTagUseCases,
    @Assisted private val date: Long,
) : ViewModel() {

    private val _dateStateFlow: MutableStateFlow<Calendar> = MutableStateFlow(toCalendar(date))

    private val calendarNoteTag = CalendarNoteTag(
        date = calendarDateUseCases.calendarToDateYMD(_dateStateFlow.value),
        month = calendarDateUseCases.calendarToDateYM(_dateStateFlow.value),
        isTechnical = false
    )

    private val _calendarNoteTagState = MutableStateFlow(
        NoteUiState(
            calendarNoteTag = calendarNoteTag
        )
    )
    val calendarNoteTagState: StateFlow<NoteUiState>
        get() = _calendarNoteTagState.asStateFlow()

    private val _calendarNoteTagStream =
        calendarNoteTagUseCases.getTagsByDate(_dateStateFlow.value)

    init {
        viewModelScope.launch {
            _calendarNoteTagStream.collect { calendarNoteTag ->
                calendarNoteTag?.let {
                    _calendarNoteTagState.update { old ->
                        old.copy(
                            calendarNoteTag = it
                        )
                    }
                }
            }
        }
    }

    fun insertIsTechnical(isTechnical: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            val tag = _calendarNoteTagState.value.calendarNoteTag.copy(isTechnical = isTechnical)
            calendarNoteTagUseCases.insertTag(tag)
        }
    }

    fun deleteNoteTag() {
        if (!_calendarNoteTagState.value.calendarNoteTag.isNotes && !_calendarNoteTagState.value.calendarNoteTag.isTechnical) {
            viewModelScope.launch {
                calendarNoteTagUseCases.deleteCalendarTag(_calendarNoteTagState.value.calendarNoteTag)
            }
        }
    }

    fun calendarDate(): String {
        return calendarDateUseCases.calendarToStringFormatDDMMMMYYYY(_dateStateFlow.value)
    }

    private fun toCalendar(millis: Long): Calendar {
        val cal = Calendar.getInstance()
        cal.timeInMillis = millis
        return cal
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