package com.example.svetlogorskchpp.presentation.shift_schedule_calendar_add_notes.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.svetlogorskchpp.domain.model.CalendarNoteTag
import com.example.svetlogorskchpp.domain.usecases.calendarDate.CalendarDateUseCases
import com.example.svetlogorskchpp.domain.usecases.calendarDate.CalendarDateUseCasesImpl
import com.example.svetlogorskchpp.domain.usecases.calendarNoteTag.CalendarNoteTagUseCases
import com.example.svetlogorskchpp.presentation.shift_schedule_calendar_add_notes.model.NoteUiState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar

class ShiftScheduleAddNotesViewModel @AssistedInject constructor(
    private val calendarDateUseCases: CalendarDateUseCases,
    private val calendarNoteTagUseCases: CalendarNoteTagUseCases,
    @Assisted private val date: Long,
) : ViewModel() {

    private val _dateStateFlow: MutableStateFlow<Calendar> = MutableStateFlow(toCalendar(date))

    private val _calendarNoteTagState  = MutableStateFlow(CalendarNoteTag(
        date = calendarDateUseCases.calendarToDateYMD(_dateStateFlow.value),
        month = calendarDateUseCases.calendarToDateYM(_dateStateFlow.value),
        isTechnical = false
    ))

    private val _calendarNoteTagStream =
        calendarNoteTagUseCases.getTagsByDate(_dateStateFlow.value)

    init {
        viewModelScope.launch {
            _calendarNoteTagStream.collect { calendarNoteTag ->
                calendarNoteTag?.let {  _calendarNoteTagState.update { calendarNoteTag} }

            }
        }

    }



    fun insertIsTechnical(isTechnical: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            val tag = _calendarNoteTagState.value.copy(isTechnical = isTechnical)
            calendarNoteTagUseCases.insertTag(tag)
        }
    }

    fun deleteNoteTag() {
        val value = _calendarNoteTagState.value
        if (!_calendarNoteTagState.value.isNotes && !_calendarNoteTagState.value.isTechnical) {
            viewModelScope.launch(Dispatchers.IO) {
                calendarNoteTagUseCases.deleteCalendarTag(value.date)
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