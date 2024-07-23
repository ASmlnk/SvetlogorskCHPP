package com.example.svetlogorskchpp.inspectionSchedule

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.example.svetlogorskchpp.R
import com.example.svetlogorskchpp.databinding.FragmentInspectionScheduleCalendarBinding
import com.example.svetlogorskchpp.model.CalendarDateModel
import com.example.svetlogorskchpp.model.firebase.FirestoreRepository
import com.example.svetlogorskchpp.model.inspectionSchedule.InSc
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar
import java.util.Locale
import java.util.TimeZone

class CalendarFragment : Fragment() {

    private val sdf = SimpleDateFormat("MMMM yyyy")
    private val sdfDate = SimpleDateFormat("dd")
    private val sdfMonth = SimpleDateFormat("MMM")
    private val sdfFullDate = SimpleDateFormat("dd MMMM yyyy")
    private val cal: Calendar = GregorianCalendar.getInstance(TimeZone.getTimeZone("GMT+3")).apply {
        firstDayOfWeek = 2
    }
    private val dates = ArrayList<Date>()
    private lateinit var adapter: CalendarAdapter
    private val calendarList2 = ArrayList<CalendarDateModel>()

    private var _binding: FragmentInspectionScheduleCalendarBinding? = null
    private val binding get() = _binding!!

    private val viewModelFactory = CalendarViewModelFactory(cal.time)
    private lateinit var viewModel: CalendarViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInspectionScheduleCalendarBinding.inflate(inflater, container, false)

        viewModel =
            ViewModelProvider(this, viewModelFactory)[CalendarViewModel::class.java]

        setUpAdapter()

        setUpClickListener()
        // setUpCalendar()
        binding.apply {
            textDayToday.text = sdfDate.format(cal.time)
            textMonthToday.text = sdfMonth.format(cal.time)
            cardViewDay.setOnClickListener {
                val date = viewModel.selectDateStateFlow.value
                val workingShift = InSc.DAY.get
                val action = CalendarFragmentDirections
                    .actionCalendarFragmentToDialogCalendarDateFragment(workingShift, date)
                findNavController().navigate(action)
            }
            cardViewNight.setOnClickListener {
                val date = viewModel.selectDateStateFlow.value
                val workingShift = InSc.NIGHT.get
                val action = CalendarFragmentDirections
                    .actionCalendarFragmentToDialogCalendarDateFragment(workingShift, date)
                findNavController().navigate(action)
            }
            cardViewDayNight.setOnClickListener {
                val date = viewModel.selectDateStateFlow.value
                val workingShift = InSc.DAY_NIGHT.get
                val action = CalendarFragmentDirections
                    .actionCalendarFragmentToDialogCalendarDateFragment(workingShift, date)
                findNavController().navigate(action)
            }

            imageSelectCalendar.setOnClickListener {
                val action =
                    CalendarFragmentDirections.actionCalendarFragmentToDialogDatePickerFragment(
                        viewModel.selectDateStateFlow.value
                    )
                findNavController().navigate(action)
            }

            constraintLayout.setOnClickListener {
                viewModel.defaultDate()
                setUpCalendar()
                lifecycleScope.launch {
                    delay(500)
                    binding.recyclerView.smoothScrollToPosition(
                        viewModel.selectDateStart().get(Calendar.DAY_OF_MONTH) - 1
                    )
                    delay(50)
                    binding.recyclerView.findViewHolderForAdapterPosition(
                        viewModel.selectDateStart().get(Calendar.DAY_OF_MONTH) - 1
                    )?.itemView?.performClick()
                }
            }
            cardViewChecklist.setOnClickListener {
                findNavController().navigate(R.id.action_calendarFragment_to_dialogCalendarFragment)
            }

            cardViewInspection.setOnClickListener {
                val action =
                    CalendarFragmentDirections.actionCalendarFragmentToChecklistInspectionFragment(
                        InSc.INSPECTION.get
                    )
                findNavController().navigate(action)
            }
        }


        lifecycleScope.launch {
            viewModel.selectDateStateFlow.collect {
                binding.textViewData.text = requireContext().getString(
                    R.string.inspection_schedule_today_data,
                    sdfFullDate.format(it)
                )
            }
        }
        lifecycleScope.launch {
            viewModel.calendarAdapterStateFlow.collect {
                setUpCalendar()
            }
        }
        setFragmentResultListener(
            DialogDatePickerFragment.REQUEST_KEY_DATE
        ) { _, bundle ->
            val newDate = bundle.getSerializable(DialogDatePickerFragment.BUNDLE_KEY_DATE) as Date
            val cal = GregorianCalendar()
            cal.time = newDate
            cal.add(Calendar.HOUR_OF_DAY, 9)
            viewModel.selectDatePicker(cal.time)
            setUpCalendar()
            lifecycleScope.launch {
                delay(50)
                binding.recyclerView.smoothScrollToPosition(cal.get(Calendar.DAY_OF_MONTH) - 1)
                delay(500)
                binding.recyclerView.findViewHolderForAdapterPosition(cal.get(Calendar.DAY_OF_MONTH) - 1)?.itemView?.performClick()
            }
        }

        lifecycleScope.launch {
            delay(50)
            binding.recyclerView.findViewHolderForAdapterPosition(
                viewModel.selectDateStart().get(Calendar.DAY_OF_MONTH) - 1
            )?.itemView?.performClick()
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpClickListener() {
        binding.ivCalendarNext.setOnClickListener {
            viewModel.selectNext()
        }
        binding.ivCalendarPrevious.setOnClickListener {
            viewModel.selectPrevs()
        }
    }

    private fun setUpAdapter() {
        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.single_calendar_margin)
        binding.recyclerView.addItemDecoration(HorizontalItemDecoration(spacingInPixels))
        val snapHelper: SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.recyclerView)
        binding.recyclerView.layoutManager?.scrollToPosition(
            viewModel.selectDateStart().get(Calendar.DAY_OF_MONTH) - 3
        )
//
        adapter = CalendarAdapter { calendarDateModel: CalendarDateModel, position: Int ->
            calendarList2.forEachIndexed { index, calendarModel ->
                calendarModel.isSelected = index == position
            }
            viewModel.selectDate(calendarList2.get(position).data)
            adapter.setData(calendarList2)
        }
        binding.recyclerView.adapter = adapter
        // binding.recyclerView.scrollToPosition(viewModel.selectDateStart().get(Calendar.DAY_OF_MONTH) - 3)
    }

    private fun setUpCalendar() {
        val calendarList = ArrayList<CalendarDateModel>()
        val calendar = viewModel.adapterDate()
        binding.tvDateMonth.text = sdf.format(calendar.time)
        val monthCalendar = calendar.clone() as Calendar
        val maxDaysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        dates.clear()
        monthCalendar.set(Calendar.DAY_OF_MONTH, 1)
        while (dates.size < maxDaysInMonth) {
            dates.add(monthCalendar.time)
            calendarList.add(CalendarDateModel(monthCalendar.time))
            monthCalendar.add(Calendar.DAY_OF_MONTH, 1)
        }
        calendarList2.clear()
        calendarList2.addAll(calendarList)
        adapter.setData(calendarList)
    }
}