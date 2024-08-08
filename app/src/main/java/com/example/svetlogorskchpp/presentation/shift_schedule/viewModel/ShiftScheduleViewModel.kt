package com.example.svetlogorskchpp.presentation.shift_schedule.viewModel

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.svetlogorskchpp.domain.interactor.shift_schedule.calendar.ShiftScheduleCalendarInteractor
import com.example.svetlogorskchpp.presentation.shift_schedule.model.ShiftScheduleUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar
import java.util.TimeZone
import javax.inject.Inject
import javax.inject.Singleton


@HiltViewModel
class ShiftScheduleViewModel @Inject constructor(
    private val shiftScheduleCalendarInteractor: ShiftScheduleCalendarInteractor,
) : ViewModel() {

    private val cal = date().time

    @SuppressLint("SimpleDateFormat")
    private val sdf = SimpleDateFormat("MMMM yyyy")

    @SuppressLint("SimpleDateFormat")
    private val patternTodayDate = SimpleDateFormat("dd MMMM yyyy")

    private val _uiState: MutableStateFlow<ShiftScheduleUiState> =
        MutableStateFlow(ShiftScheduleUiState())
    val uiState: StateFlow<ShiftScheduleUiState>
        get() = _uiState.asStateFlow()

    private val _selectDateStateFlow = MutableStateFlow(cal)
    val selectDateStateFlow: StateFlow<Date>
        get() = _selectDateStateFlow

    private val _calendarAdapterStateFlow = MutableStateFlow(cal)

    init {
        viewModelScope.launch {
            delay(200)
            setUpCalendar()

            val textTodayDate = patternTodayDate.format(cal.time)
            _uiState.update { oldState ->
                oldState.copy(
                    textTodayDate = textTodayDate
                )
            }
        }

        viewModelScope.launch {
            shiftScheduleCalendarInteractor.getDaysFullCalendarStream()
                .collect { calendarFullDayShiftModel ->
                    _uiState.update { oldState ->
                        oldState.copy(
                            calendarList = calendarFullDayShiftModel.calendarFullDayModels,
                            selectShift = calendarFullDayShiftModel.shiftSelect,
                            calendarView = calendarFullDayShiftModel.calendarView
                        )
                    }
                }
        }
    }

    private fun setUpCalendar() {
         generateDays()
        val calendar = adapterDate()
        val textDateMonth = sdf.format(calendar.time)
        // val monthCalendar = calendar.clone() as Calendar
        // monthCalendar.set(Calendar.DAY_OF_MONTH, 1)

        _uiState.update { oldState ->
            oldState.copy(
                textDateMonth = textDateMonth,
            )
        }
    }

    private fun adapterDate(): Calendar {
        val date = date()
        date.time = _calendarAdapterStateFlow.value
        return date
    }

    fun selectDateStart() {
        _calendarAdapterStateFlow.value = cal
        setUpCalendar()
    }

    fun selectDate(selectDate: Date) {
        _selectDateStateFlow.value = selectDate
        // _calendarAdapterStateFlow.value = selectDate
    }

    fun selectNext() {
        val date = adapterDate()
        date.add(Calendar.MONTH, 1)
        _calendarAdapterStateFlow.value = date.time
        setUpCalendar()
    }

    fun selectPrevs() {
        val calendar = adapterDate()
        calendar.add(Calendar.MONTH, -1)
        _calendarAdapterStateFlow.value = calendar.time
        setUpCalendar()
    }

    private fun generateDays() {
        val monthCalendar = adapterDate()
        monthCalendar.set(Calendar.DAY_OF_MONTH, 1)
        shiftScheduleCalendarInteractor.generateDaysFullCalendar(monthCalendar)
    }

    suspend fun setSelectShiftSchedule(shift: String) {
         shiftScheduleCalendarInteractor.setSelectShiftSchedule(shift)
    }

    suspend fun setSelectCalendarView(view: String) {
        shiftScheduleCalendarInteractor.setSelectCalendarView(view)
    }

    private fun date() = GregorianCalendar
        .getInstance(TimeZone.getTimeZone("GMT+3")).apply { firstDayOfWeek = 2 }
}