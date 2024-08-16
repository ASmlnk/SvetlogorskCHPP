package com.example.svetlogorskchpp.presentation.shift_schedule_calendar_add_notes.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.svetlogorskchpp.domain.usecases.calendarDate.CalendarDateUseCases
import com.example.svetlogorskchpp.domain.usecases.calendarDate.CalendarDateUseCasesImpl
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.Calendar

class ShiftScheduleAddNotesViewModel @AssistedInject constructor(
    private val calendarDateUseCases: CalendarDateUseCases,
    @Assisted("date") private val date: Long,
    @Assisted("idNote") private val idNote: Long,
) : ViewModel() {

    private val _dateStateFlow: MutableStateFlow<Calendar> = MutableStateFlow(toCalendar(date))
    private val _idNoteStateFlow: MutableStateFlow<Long> = MutableStateFlow(idNote)


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
            @Assisted("date") date: Long,
            @Assisted("idNote") idNote: Long,
        ): ShiftScheduleAddNotesViewModel
    }


    companion object {
        fun providesFactory(
            assistedFactory: ShiftShiftScheduleAddNotesViewModelFactory,
            date: Long,
            idNote: Long,
        ): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return assistedFactory.create(date, idNote) as T
                }
            }
        }
    }
}