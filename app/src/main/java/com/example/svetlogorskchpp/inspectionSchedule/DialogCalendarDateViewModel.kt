package com.example.svetlogorskchpp.inspectionSchedule

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.svetlogorskchpp.model.firebase.FirestoreRepository
import com.example.svetlogorskchpp.model.inspectionSchedule.InSc
import com.example.svetlogorskchpp.model.inspectionSchedule.Inspection
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Math.abs
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar
import java.util.TimeZone

class DialogCalendarDateViewModel(private val date: Date, private val workingShift: String) :
    ViewModel() {
    private val data = FirestoreRepository.get()
    private val _inspectionStateFlow = MutableStateFlow<List<Inspection>>(emptyList())
    val inspectionStateFlow: StateFlow<List<Inspection>>
        get() = _inspectionStateFlow

    private val listDateInspection = mutableListOf<Inspection>()
    private val dateList = mutableListOf<Inspection>()

    init {
        viewModelScope.launch {
            data.listInspectionStateFlow.collect { list->
                listDateInspection.addAll(list)
                getInspectorSchedule(date, workingShift)
                _inspectionStateFlow.value = dateList
                    .sortedBy { it.content }
                    .sortedBy { it.dayOfTheWeek }
                    .sortedBy { it.workingShift }
                    .sortedBy { !it.withoutNumber }
            }
        }
    }

    fun getInspectorSchedule(date: Date, workingShift: String) { //workingShift -день executor-нсэ

        val calendar = getCalendar(workingShift, date)

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayMonth = calendar.get(Calendar.DAY_OF_MONTH)
        val dayWeek = calendar.get(Calendar.DAY_OF_WEEK)
        val week = calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH)
        val maxDayMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

        val todayData = GregorianCalendar(year, month, dayMonth)
        val firstData = GregorianCalendar(2023, 0, 13)
        val intervalCalculation =
            (abs((todayData.timeInMillis - firstData.timeInMillis)) / (1000 * 60 * 60 * 24)) % 10

        var monthData = InSc.ALL.get
        var dayMonthData = InSc.ALL.get
        var dayWeekData = InSc.ALL.get
        var weekData = InSc.ALL.get

        when (month) {
            0 -> monthData = InSc.JANUARY.get
            1 -> monthData = InSc.FEBRUARY.get
            2 -> monthData = InSc.MARCH.get
            3 -> monthData = InSc.APRIL.get
            4 -> monthData = InSc.MAY.get
            5 -> monthData = InSc.JUNE.get
            6 -> monthData = InSc.JULY.get
            7 -> monthData = InSc.AUGUST.get
            8 -> monthData = InSc.SEPTEMBER.get
            9 -> monthData = InSc.OCTOBER.get
            10 -> monthData = InSc.NOVEMBER.get
            11 -> monthData = InSc.DECEMBER.get
        }

        when (dayMonth) {
            1 -> dayMonthData = "1"
            2 -> dayMonthData = "2"
            15 -> dayMonthData = "15"
            19 -> dayMonthData = "19"
            20 -> dayMonthData = "20"
        }

        when (dayWeek) {
            1 -> dayWeekData = InSc.SUNDAY.get
            2 -> dayWeekData = InSc.MONDAY.get
            3 -> dayWeekData = InSc.TUESDAY.get
            4 -> dayWeekData = InSc.WEDNESDAY.get
            5 -> dayWeekData = InSc.THURSDAY.get
            6 -> dayWeekData = InSc.FRIDAY.get
            7 -> dayWeekData = InSc.SATURDAY.get
        }

        when (week) {
            1 -> weekData = InSc.FIRST.get
            2 -> weekData = InSc.SECOND.get
            3 -> weekData = InSc.THIRD.get
        }

        val lastWeek = maxDayMonth - dayMonth
        Log.d("calendar", lastWeek.toString())
        if (lastWeek < 7) weekData = InSc.LAST.get

        val listMonth = listOf(0, 2, 3, 5, 6, 8, 9, 11)
        val listDayMonth = listOf(1, 2, 15, 19, 20)
        val listDayWeek = listOf(1, 2, 3, 4, 5, 6, 7)
        val listWeek = listOf(InSc.FIRST.get, InSc.SECOND.get, InSc.THIRD.get, InSc.LAST.get)

        if (intervalCalculation.toInt() == 0) {
            getDataAll(workingShift, listOf(0, 10))
        } else {
            getDataAll(workingShift, listOf(0))
        }

        if (month in listMonth && dayMonth in listDayMonth) {
            getDataMonthDayMonth(workingShift, monthData, dayMonthData)
        }

        if (month in listMonth) {
            getDataMonth(workingShift, monthData)
        }
        if (dayWeek in listDayWeek && weekData in listWeek) {
            getDataWeek(workingShift, listOf(InSc.ALL.get, monthData), weekData, dayWeekData)
        }
        if (dayMonth in listDayMonth) {
            getDataDayMonth(workingShift, dayMonthData)
        }
        if (dayWeek in listDayWeek) {
            getDataDayWeek(workingShift, dayWeekData)
        }
    }

    fun getCalendar(workingShift: String, date: Date): Calendar {

        /*val calendar = */
        val calendar = GregorianCalendar.getInstance(TimeZone.getTimeZone("GMT+3")).apply{
            firstDayOfWeek = 2
        }
        calendar.time = date
        Log.d("calendar", calendar.toString())

        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        if (workingShift == InSc.NIGHT.get && hour in 0..7) calendar.add(Calendar.DAY_OF_WEEK, -1)

        return calendar
    }

    fun getNextDate(): Calendar {
        val calendar = getCalendar(InSc.NIGHT.get, date)
        calendar.add(Calendar.DAY_OF_WEEK, +1)
        return calendar
    }

    private fun getDataAll(workingShift: String, list: List<Int>) {

        if (workingShift == InSc.DAY_NIGHT.get) {
            list.forEach { inter ->
                val list1 = listDateInspection
                    .filter { it.month == InSc.ALL.get }
                    .filter { it.dayOfTheWeek == InSc.ALL.get }
                    .filter { it.week == InSc.ALL.get }
                    .filter { it.dayMonth == InSc.ALL.get }
                    .filter { it.interval == inter }
                dateList.addAll(list1)
            }
            val list2 = listDateInspection
                .filter { it.month == InSc.ALL.get }
                .filter { it.withoutNumber }
            dateList.addAll(list2)
        } else {
            list.forEach { inter ->
                val list1 = listDateInspection
                    .filter { it.workingShift == workingShift }
                    .filter { it.month == InSc.ALL.get }
                    .filter { it.dayOfTheWeek == InSc.ALL.get }
                    .filter { it.week == InSc.ALL.get }
                    .filter { it.dayMonth == InSc.ALL.get }
                    .filter { it.interval == inter }
                dateList.addAll(list1)
            }
            val list2 = listDateInspection
                .filter { it.workingShift == workingShift }
                .filter { it.month == InSc.ALL.get }
                .filter { it.withoutNumber }
            dateList.addAll(list2)
        }
    }

    fun getDataMonthDayMonth(workingShift: String, month: String, dayMonthData: String) {

        if (workingShift == InSc.DAY_NIGHT.get) {
            val list1 = listDateInspection
                .filter { !it.withoutNumber }
                .filter { it.month == month }
                .filter { it.dayOfTheWeek == InSc.ALL.get }
                .filter { it.week == InSc.ALL.get }
                .filter { it.dayMonth == dayMonthData }
                .filter { it.interval == 0 }
            dateList.addAll(list1)
        } else {
            val list1 = listDateInspection
                .filter { it.workingShift == workingShift }
                .filter { !it.withoutNumber }
                .filter { it.month == month }
                .filter { it.dayOfTheWeek == InSc.ALL.get }
                .filter { it.week == InSc.ALL.get }
                .filter { it.dayMonth == dayMonthData }
                .filter { it.interval == 0 }
            dateList.addAll(list1)
        }
    }

    fun getDataMonth(workingShift: String, month: String) {

        if (workingShift == InSc.DAY_NIGHT.get) {
            val list1 = listDateInspection
                .filter { it.month == month }
                .filter { it.withoutNumber }
            dateList.addAll(list1)
        } else {
            val list1 = listDateInspection
                .filter { it.workingShift == workingShift }
                .filter { it.month == month }
                .filter { it.withoutNumber }
            dateList.addAll(list1)
        }
    }

    fun getDataDayMonth(workingShift: String, dayMonth: String) {

        if (workingShift == InSc.DAY_NIGHT.get) {
            val list1 = listDateInspection
                .filter { !it.withoutNumber }
                .filter { it.month == InSc.ALL.get }
                .filter { it.dayOfTheWeek == InSc.ALL.get }
                .filter { it.week == InSc.ALL.get }
                .filter { it.dayMonth == dayMonth }
                .filter { it.interval == 0 }
            dateList.addAll(list1)
        } else {
            val list1 = listDateInspection
                .filter { it.workingShift == workingShift }
                .filter { !it.withoutNumber }
                .filter { it.month == InSc.ALL.get }
                .filter { it.dayOfTheWeek == InSc.ALL.get }
                .filter { it.week == InSc.ALL.get }
                .filter { it.dayMonth == dayMonth }
                .filter { it.interval == 0 }
            dateList.addAll(list1)
        }
    }

    fun getDataDayWeek(workingShift: String, dayOfTheWeek: String) {

        if (workingShift == InSc.DAY_NIGHT.get) {
            val list1 = listDateInspection
                .filter { !it.withoutNumber }
                .filter { it.month == InSc.ALL.get }
                .filter { it.dayOfTheWeek == dayOfTheWeek }
                .filter { it.week == InSc.ALL.get }
                .filter { it.dayMonth == InSc.ALL.get }
                .filter { it.interval == 0 }
            dateList.addAll(list1)
        } else{
            val list1 = listDateInspection
                .filter { it.workingShift == workingShift }
                .filter { !it.withoutNumber }
                .filter { it.month == InSc.ALL.get }
                .filter { it.dayOfTheWeek == dayOfTheWeek }
                .filter { it.week == InSc.ALL.get }
                .filter { it.dayMonth == InSc.ALL.get }
                .filter { it.interval == 0 }
            dateList.addAll(list1)
        }
    }

    fun getDataWeek(workingShift: String, list: List<String>, week: String, dayOfTheWeek: String) {

        Log.d("calendar" , "week $week")

        if (workingShift == InSc.DAY_NIGHT.get) {
            list.forEach { month ->
                val list1 = listDateInspection
                    .filter { !it.withoutNumber }
                    .filter { it.month == month }
                    .filter { it.dayOfTheWeek == dayOfTheWeek }
                    .filter { it.week == week }
                    .filter { it.dayMonth == InSc.ALL.get }
                    .filter { it.interval == 0 }
                dateList.addAll(list1)
            }
        } else {
            list.forEach { month ->
                val list1 = listDateInspection
                    .filter { it.workingShift == workingShift }
                    .filter { !it.withoutNumber }
                    .filter { it.month == month }
                    .filter { it.dayOfTheWeek == dayOfTheWeek }
                    .filter { it.week == week }
                    .filter { it.dayMonth == InSc.ALL.get }
                    .filter { it.interval == 0 }
                dateList.addAll(list1)
            }
        }
    }
}