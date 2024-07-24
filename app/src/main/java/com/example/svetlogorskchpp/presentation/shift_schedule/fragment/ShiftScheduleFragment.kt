package com.example.svetlogorskchpp.presentation.shift_schedule.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.svetlogorskchpp.databinding.FragmentShiftScheduleBinding
import com.example.svetlogorskchpp.inspectionSchedule.CalendarViewModel
import com.example.svetlogorskchpp.inspectionSchedule.CalendarViewModelFactory
import com.example.svetlogorskchpp.model.CalendarDateModel
import com.example.svetlogorskchpp.presentation.shift_schedule.adapter.CalendarFullAdapter
import com.example.svetlogorskchpp.presentation.shift_schedule.model.CalendarFullDayModel
import com.example.svetlogorskchpp.presentation.shift_schedule.adapter.ItemOffsetDecoration
import com.example.svetlogorskchpp.presentation.shift_schedule.model.MonthCalendar
import com.example.svetlogorskchpp.presentation.shift_schedule.model.Shift
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.lang.Math.abs
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar
import java.util.TimeZone

//@AndroidEntryPoint
class ShiftScheduleFragment: Fragment() {

    private var _binding: FragmentShiftScheduleBinding? = null
    private val binding get() = _binding!!

    private val cal: Calendar = GregorianCalendar.getInstance(TimeZone.getTimeZone("GMT+3")).apply {
        firstDayOfWeek = 2
    }
    private val sdf = SimpleDateFormat("MMMM yyyy")
    private val dates = ArrayList<Date>()
    private val calendarList2 = ArrayList<CalendarDateModel>()
    private val calendarList3 = ArrayList<CalendarFullDayModel>()
    private lateinit var adapter: CalendarFullAdapter

    private val viewModelFactory = CalendarViewModelFactory(cal.time)
    private lateinit var viewModel: CalendarViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentShiftScheduleBinding.inflate(inflater,container, false)
        viewModel =
            ViewModelProvider(this, viewModelFactory)[CalendarViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpAdapter()
        setUpClickListener()
        lifecycleScope.launch {
            viewModel.calendarAdapterStateFlow.collect {

                setUpCalendar()

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setUpCalendar() {
        val calendarList = ArrayList<CalendarDateModel>()
        val calendar = viewModel.adapterDate()
        binding.tvDateMonth.text = sdf.format(calendar.time)
        val monthCalendar = calendar.clone() as Calendar
        val maxDaysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        dates.clear()
        monthCalendar.set(Calendar.DAY_OF_MONTH, 1)
        val list = generateDays(monthCalendar)

        calendarList3.clear()
        calendarList3.addAll(list)

        adapter.setData(list)
        binding.recyclerView.adapter = adapter
    }

    private fun setUpAdapter() {

        val itemDecoration = ItemOffsetDecoration(requireContext() )
        binding.recyclerView.layoutManager?.scrollToPosition(
            viewModel.selectDateStart().get(Calendar.DAY_OF_MONTH) - 3
        )
        binding.recyclerView.setItemViewCacheSize(0)  // Отключение кэширования представлений
       binding.recyclerView.addItemDecoration(itemDecoration)

        adapter = CalendarFullAdapter { calendarDateModel: CalendarFullDayModel, position: Int ->
            calendarList3.forEachIndexed { index, calendarModel ->
               // calendarModel.isSelected = index == position
            }
            viewModel.selectDate(calendarList3.get(position).data.time)
            adapter.setData(calendarList3)
        }
        binding.recyclerView.adapter = adapter
    }

    private fun setUpClickListener() {
        binding.ivCalendarNext.setOnClickListener {
            viewModel.selectNext()
        }
        binding.ivCalendarPrevious.setOnClickListener {
            viewModel.selectPrevs()
        }
    }

    private fun generateDays(calendar: Calendar): List<CalendarFullDayModel> {
        val days = mutableListOf<CalendarFullDayModel>()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)

        val firstOfMonth = Calendar.getInstance()
        firstOfMonth.set(year, month, 1)
        val daysInMonth = firstOfMonth.getActualMaximum(Calendar.DAY_OF_MONTH)
        val firstDayWeek = firstOfMonth.get(Calendar.DAY_OF_WEEK)
        val firstDayOfWeek = if(firstDayWeek==1) 7 else firstDayWeek - 1

        // Заполнение дней предыдущего месяца
        val prevMonth = Calendar.getInstance()
        prevMonth.time = firstOfMonth.time
        prevMonth.add(Calendar.MONTH, -1)
        val daysInPrevMonth = prevMonth.getActualMaximum(Calendar.DAY_OF_MONTH)
        for (i in 1 until firstDayOfWeek) {
            val prevMonthDay = Calendar.getInstance()
            prevMonthDay.time = prevMonth.time
            prevMonthDay.set(Calendar.DAY_OF_MONTH, daysInPrevMonth - firstDayOfWeek + i + 1)
            days.add(CalendarFullDayModel(prevMonthDay, MonthCalendar.PREV_MONTH, Shift.A_SHIFT, Shift.A_SHIFT, Shift.A_SHIFT ))
        }

        // Заполнение текущего месяца
        for (i in 1..daysInMonth) {
            val day = Calendar.getInstance()
            day.set(year, month, i)
            val todayData = GregorianCalendar(year, month, i)
            val firstData = GregorianCalendar(2022, 6, 7)
            val intervalCalculation =
                (abs((todayData.timeInMillis - firstData.timeInMillis)) / (1000 * 60 * 60 * 24)) % 8
            when (intervalCalculation.toInt()) {
                0->days.add(CalendarFullDayModel(day, MonthCalendar.ACTUAL_MONTH, Shift.D_SHIFT, Shift.A_SHIFT, Shift.D_SHIFT))
                1->days.add(CalendarFullDayModel(day, MonthCalendar.ACTUAL_MONTH, Shift.D_SHIFT, Shift.A_SHIFT, Shift.B_SHIFT))
                2->days.add(CalendarFullDayModel(day, MonthCalendar.ACTUAL_MONTH, Shift.B_SHIFT, Shift.C_SHIFT, Shift.B_SHIFT))
                3->days.add(CalendarFullDayModel(day, MonthCalendar.ACTUAL_MONTH, Shift.B_SHIFT, Shift.C_SHIFT, Shift.A_SHIFT))
                4->days.add(CalendarFullDayModel(day, MonthCalendar.ACTUAL_MONTH, Shift.A_SHIFT, Shift.D_SHIFT, Shift.A_SHIFT))
                5->days.add(CalendarFullDayModel(day, MonthCalendar.ACTUAL_MONTH, Shift.A_SHIFT, Shift.D_SHIFT, Shift.C_SHIFT))
                6->days.add(CalendarFullDayModel(day, MonthCalendar.ACTUAL_MONTH, Shift.C_SHIFT, Shift.B_SHIFT, Shift.C_SHIFT))
                7->days.add(CalendarFullDayModel(day, MonthCalendar.ACTUAL_MONTH, Shift.C_SHIFT, Shift.B_SHIFT, Shift.D_SHIFT))
            }
        }

        // Заполнение дней следующего месяца
        val remainingDays = 42 - days.size
        for (i in 1..remainingDays) {
            val nextMonthDay = Calendar.getInstance()
            nextMonthDay.set(year, month, daysInMonth + i)
            days.add(CalendarFullDayModel(nextMonthDay, MonthCalendar.NEXT_MONTH, Shift.A_SHIFT, Shift.A_SHIFT, Shift.A_SHIFT))
        }
        return days
    }
}