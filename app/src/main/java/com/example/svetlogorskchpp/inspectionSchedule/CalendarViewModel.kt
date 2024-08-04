package com.example.svetlogorskchpp.inspectionSchedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.svetlogorskchpp.model.firebase.FirestoreRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar
import java.util.TimeZone
import javax.inject.Inject

class CalendarViewModel(
    private val date: Date,
    private val data: FirestoreRepository): ViewModel() {



    private val _selectDateStateFlow = MutableStateFlow(date)
    val selectDateStateFlow: StateFlow<Date>
        get() = _selectDateStateFlow

    private val _calendarAdapterStateFlow = MutableStateFlow(date)
    val calendarAdapterStateFlow: StateFlow<Date>
        get() = _calendarAdapterStateFlow

    init {
        viewModelScope.launch {
            data.getAllInspection(date())
        }
    }

    fun date(): String {
        val calendar = GregorianCalendar
            .getInstance(TimeZone.getTimeZone("GMT+3")).apply { firstDayOfWeek = 2 }

        return calendar.timeInMillis.toString()
    }

    fun selectDate(selectDate: Date) {
        _selectDateStateFlow.value = selectDate
       // _calendarAdapterStateFlow.value = selectDate
    }

    fun selectDateStart(): Calendar {
        val cal = GregorianCalendar
            .getInstance(TimeZone.getTimeZone("GMT+3")).apply { firstDayOfWeek = 2 }
        cal.time = _selectDateStateFlow.value
        return cal
    }

    fun selectNext() {
        val cal = adapterDate()
        cal.add(Calendar.MONTH, 1)
        _calendarAdapterStateFlow.value = cal.time
    }

    fun selectPrevs() {
        val cal = adapterDate()
        cal.add(Calendar.MONTH, -1)
        _calendarAdapterStateFlow.value = cal.time
    }

    fun adapterDate(): Calendar {
        val cal = GregorianCalendar
            .getInstance(TimeZone.getTimeZone("GMT+3")).apply { firstDayOfWeek = 2 }
        cal.time = _calendarAdapterStateFlow.value
        return cal
    }

    fun selectDatePicker(selectDate: Date) {
        _selectDateStateFlow.value = selectDate
        _calendarAdapterStateFlow.value = selectDate
    }

    fun defaultDate() {
        _selectDateStateFlow.value = date
        _calendarAdapterStateFlow.value = date
    }
}