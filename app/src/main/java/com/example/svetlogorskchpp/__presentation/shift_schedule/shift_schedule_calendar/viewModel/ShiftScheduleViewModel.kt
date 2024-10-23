package com.example.svetlogorskchpp.__presentation.shift_schedule.shift_schedule_calendar.viewModel

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.svetlogorskchpp.__domain.interactor.shift_schedule.calendar.ShiftScheduleCalendarInteractor
import com.example.svetlogorskchpp.__domain.usecases.shift_schedule.calendarTagUseCases.CalendarTagUseCases
import com.example.svetlogorskchpp.__domain.usecases.shift_schedule.calendarNoteTag.CalendarNoteTagUseCases
import com.example.svetlogorskchpp.__presentation.shift_schedule.shift_schedule_calendar.model.CalendarFullDayShiftModel
import com.example.svetlogorskchpp.__presentation.shift_schedule.shift_schedule_calendar.model.CalendarUpdateTag
import com.example.svetlogorskchpp.__presentation.shift_schedule.shift_schedule_calendar.model.NoteTagsUI
import com.example.svetlogorskchpp.__presentation.shift_schedule.shift_schedule_calendar.model.ShiftScheduleUiState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.TimeZone

class ShiftScheduleViewModel @AssistedInject constructor(
    private val shiftScheduleCalendarInteractor: ShiftScheduleCalendarInteractor,
    private val calendarNoteTagUseCases: CalendarNoteTagUseCases,
    private val calendarTagUseCases: CalendarTagUseCases,

    @Assisted private val date: Long,
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

    private val _preferencesRequestWorkViewCalendarStateFlow = calendarNoteTagUseCases.preferencesRequestWorkViewCalendarFlow

    private val _calendarAdapterStateFlow = MutableStateFlow(cal)

    private val _updateCalendar: StateFlow<CalendarUpdateTag?> = combine(
        _preferencesRequestWorkViewCalendarStateFlow,
        _calendarAdapterStateFlow
    ) { preferencesRequestWorkViewCalendar, calendarAdapter ->
        CalendarUpdateTag(
            date = calendarAdapter,
            viewRequestWork = preferencesRequestWorkViewCalendar
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = null)

    private val _calendarNoteTag: MutableStateFlow<NoteTagsUI> = MutableStateFlow(
        NoteTagsUI(myNoteTags = emptyList(), requestWorkTags = emptyList())
    )

    private val _calendarFullDayShift = shiftScheduleCalendarInteractor.getDaysFullCalendarStream()
        .stateIn(viewModelScope, SharingStarted.Lazily, CalendarFullDayShiftModel())

    private val _calendarShiftAndNoteTag = combine(
        _calendarNoteTag, _calendarFullDayShift
    ) { calendarNoteTags, calendarFullDayShift ->
        CalendarFullDayShiftModel().copy(
            calendarFullDayModels = calendarTagUseCases.addNoteTagToCalendar(
                calendarFullDayModels = calendarFullDayShift.calendarFullDayModels,
                calendarMyNoteTags = calendarNoteTags.myNoteTags,
                calendarRequestWorkTag = calendarNoteTags.requestWorkTags
            ),
            shiftSelect = calendarFullDayShift.shiftSelect,
            calendarView = calendarFullDayShift.calendarView,
        )
    }.stateIn(
        scope = CoroutineScope(Dispatchers.Default),
        started = SharingStarted.Lazily,
        initialValue = CalendarFullDayShiftModel()
    )

    init {

        viewModelScope.launch {
           // _calendarAdapterStateFlow.collect {
            _updateCalendar.collect {
                it?.let {
                    withContext(Dispatchers.IO) {
                        val tagMyNotes = calendarNoteTagUseCases.calendarMyNoteTag(adapterDate())

                        val tagRequestWork = if (it.viewRequestWork) {
                            calendarNoteTagUseCases.calendarRequestWorkTag(adapterDate())
                        } else {
                            emptyList()
                        }
                        _calendarNoteTag.update {
                            NoteTagsUI(myNoteTags = tagMyNotes, requestWorkTags = tagRequestWork)
                        }
                    }
                }
            }
        }

        viewModelScope.launch {
            delay(150)
            setUpCalendar()

            val textTodayDate = patternTodayDate.format(cal.time)
            _uiState.update { oldState ->
                oldState.copy(
                    textTodayDate = textTodayDate
                )
            }
        }

        viewModelScope.launch {
            _calendarShiftAndNoteTag
                .collect { calendarFullDayShiftModel ->
                    _uiState.update { oldState ->
                        oldState.copy(
                            calendarList = calendarFullDayShiftModel.calendarFullDayModels,
                            selectShift = calendarFullDayShiftModel.shiftSelect,
                            calendarView = calendarFullDayShiftModel.calendarView,
                        )
                    }
                }
        }
    }

    private fun setUpCalendar() {

        generateDays()

        val calendar = adapterDate()
        val textDateMonth = sdf.format(calendar.time)

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
        _calendarAdapterStateFlow.value =
            Calendar.getInstance(TimeZone.getTimeZone("GMT+3")).apply { firstDayOfWeek = 2 }.time
        setUpCalendar()
    }

    fun selectDate(selectDate: Date) {
        _selectDateStateFlow.value = selectDate
        // _calendarAdapterStateFlow.value = selectDate
    }

    fun selectNext() {
        val date = adapterDate().clone() as Calendar
        date.add(Calendar.MONTH, 1)
        _calendarAdapterStateFlow.update { date.time }
        setUpCalendar()
    }

    fun selectPrevs() {
        val calendar = adapterDate().clone() as Calendar
        calendar.add(Calendar.MONTH, -1)
        _calendarAdapterStateFlow.update { calendar.time }
        setUpCalendar()
    }

    fun isSnackbarShowOff() {
        _uiState.update {
            it.copy(isSnackbarShow = false)
        }
    }

    private fun generateDays() {
        val monthCalendar = adapterDate().clone() as Calendar
        monthCalendar.set(Calendar.DAY_OF_MONTH, 1)
        shiftScheduleCalendarInteractor.generateDaysFullCalendar(monthCalendar)
    }

    suspend fun setSelectShiftSchedule(shift: String) {
        shiftScheduleCalendarInteractor.setSelectShiftSchedule(shift)
    }

    suspend fun setSelectCalendarView(view: String) {
        shiftScheduleCalendarInteractor.setSelectCalendarView(view)
    }

    private fun date(): Calendar {
        val cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+3")).apply { firstDayOfWeek = 2 }
        if (date != 0L) cal.timeInMillis = date
        return cal
    }

    @AssistedFactory
    interface ShiftShiftScheduleViewModelFactory {
        fun create(
            @Assisted date: Long,
        ): ShiftScheduleViewModel
    }

    @Suppress("UNCHECKED_CAST")
    companion object {
        fun providesFactory(
            assistedFactory: ShiftShiftScheduleViewModelFactory,
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