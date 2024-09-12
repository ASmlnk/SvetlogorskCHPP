package com.example.svetlogorskchpp.__domain.interactor.shift_schedule.calendar

import com.example.svetlogorskchpp.__data.repository.preferences.PreferencesRepository
import com.example.svetlogorskchpp.__domain.usecases.CalendarAddShiftUseCases
import com.example.svetlogorskchpp.__domain.usecases.GenerateDaysFullCalendarUseCases
import com.example.svetlogorskchpp.__domain.usecases.ShiftUseCases
import com.example.svetlogorskchpp.__presentation.shift_schedule.model.CalendarFullDayModel
import com.example.svetlogorskchpp.__presentation.shift_schedule.model.CalendarFullDayShiftModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import java.util.Calendar
import javax.inject.Inject

class ShiftScheduleCalendarInteractorImpl @Inject constructor(
    private val generateDaysFullCalendarUseCases: GenerateDaysFullCalendarUseCases,
    private val calendarAddShift: CalendarAddShiftUseCases,
    private val preferencesRepository: PreferencesRepository,
    private val shiftUseCases: ShiftUseCases
) : ShiftScheduleCalendarInteractor {

    private val _selectShiftScheduleStream = preferencesRepository.selectShiftSchedule
    private val _selectCalendarViewShiftSchedule = preferencesRepository.selectCalendarViewShiftSchedule
    private val _notificationNoteTechnical = preferencesRepository.isNotificationNoteTechnical
    private val _getDaysFullCalendarFlow =
        MutableStateFlow<List<CalendarFullDayModel>>(emptyList())

    override fun getDaysFullCalendarStream(): Flow<CalendarFullDayShiftModel> {
        return combine(
            _selectShiftScheduleStream,
            _getDaysFullCalendarFlow,
            _notificationNoteTechnical,
            _selectCalendarViewShiftSchedule
        ) { preferencesRepositoryFlow, getDaysFullCalendarFlow, notificationNoteTechnical, getSelectCalendarView ->
            CalendarFullDayShiftModel().copy(
                calendarFullDayModels = getDaysFullCalendarFlow,
                shiftSelect = shiftUseCases.stringToShift(preferencesRepositoryFlow),
                calendarView = getSelectCalendarView,
                isNotificationNoteTechnical = notificationNoteTechnical
            )
        }.stateIn(
            scope = CoroutineScope(Dispatchers.Default),
            started = SharingStarted.Lazily,
            initialValue = CalendarFullDayShiftModel()
        )
    }

    override suspend fun setSelectShiftSchedule(shift: String) {
        preferencesRepository.setSelectShiftSchedule(shift)
    }

    override suspend fun setSelectCalendarView(view: String) {
        preferencesRepository.setSelectCalendarViewShiftSchedule(view)
    }

    override suspend fun setNotificationNoteTechnical(isNotification: Boolean) {
        preferencesRepository.setNotificationNoteTechnical(isNotification)
    }

    override fun generateDaysFullCalendar(calendar: Calendar) {
        val list = generateDaysFullCalendarUseCases.generateDays(calendar)
        val listAddShift = calendarAddShift.addShiftOfCalendar(list)

        _getDaysFullCalendarFlow.update {
            listAddShift
        }
    }
}