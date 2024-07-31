package com.example.svetlogorskchpp.domain.interactor.shift_schedule

import com.example.svetlogorskchpp.data.repository.PreferencesRepository
import com.example.svetlogorskchpp.domain.model.CalendarFullDayUsModel
import com.example.svetlogorskchpp.domain.model.Shift
import com.example.svetlogorskchpp.domain.usecases.CalendarAddShiftUseCases
import com.example.svetlogorskchpp.domain.usecases.GenerateDaysFullCalendarUseCases
import com.example.svetlogorskchpp.presentation.shift_schedule.model.CalendarFullDayModel
import com.example.svetlogorskchpp.presentation.shift_schedule.model.CalendarFullDayShiftModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import java.util.Calendar
import javax.inject.Inject

class ShiftScheduleInteractorImpl @Inject constructor(
    private val generateDaysFullCalendarUseCases: GenerateDaysFullCalendarUseCases,
    private val calendarAddShift: CalendarAddShiftUseCases,
    private val preferencesRepository: PreferencesRepository,
) : ShiftScheduleInteractor {

    private val _preferencesRepositoryFlow = preferencesRepository.selectShiftSchedule
    private val _getDaysFullCalendarFlow =
        MutableStateFlow<List<CalendarFullDayModel>>(emptyList())

    override fun getDaysFullCalendarStream(): Flow<CalendarFullDayShiftModel> {
        return combine(
            _preferencesRepositoryFlow,
            _getDaysFullCalendarFlow
        ) { preferencesRepositoryFlow, getDaysFullCalendarFlow ->
            CalendarFullDayShiftModel().copy(
                calendarFullDayModels = getDaysFullCalendarFlow,
                shiftSelect = shift(preferencesRepositoryFlow))
        }.stateIn(
            scope = CoroutineScope(Dispatchers.Default),
            started = SharingStarted.Lazily,
            initialValue = CalendarFullDayShiftModel()
        )
    }

    override suspend fun setSelectShiftSchedule(shift: String) {
        preferencesRepository.setSelectShiftSchedule(shift)
    }

    override fun generateDaysFullCalendar(calendar: Calendar) {
        val list = generateDaysFullCalendarUseCases.generateDays(calendar)
        val listAddShift = calendarAddShift.addShiftOfCalendar(list)
        _getDaysFullCalendarFlow.update {
            listAddShift
        }
    }

    fun shift(str: String): Shift {
        return when (str) {
            "A" -> Shift.A_SHIFT
            "B" -> Shift.B_SHIFT
            "C" -> Shift.C_SHIFT
            "D" -> Shift.D_SHIFT
            else -> Shift.NO_SHIFT
        }
    }
}