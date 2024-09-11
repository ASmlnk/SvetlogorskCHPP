package com.example.svetlogorskchpp.__presentation.shift_schedule.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.children
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.svetlogorskchpp.R
import com.example.svetlogorskchpp.databinding.FragmentShiftScheduleBinding
import com.example.svetlogorskchpp.__domain.en.Shift
import com.example.svetlogorskchpp.__frameworks.CalendarNotesWorker
import com.example.svetlogorskchpp.__presentation.shift_schedule.adapter.CalendarFullAdapter
import com.example.svetlogorskchpp.__presentation.shift_schedule.adapter.ItemOffsetDecoration
import com.example.svetlogorskchpp.__presentation.shift_schedule.model.AdapterUiState
import com.example.svetlogorskchpp.__presentation.shift_schedule.model.NavigateAddNoteArgs
import com.example.svetlogorskchpp.__presentation.shift_schedule.viewModel.ShiftScheduleViewModel
import com.example.svetlogorskchpp.__presentation.shift_schedule_calendar_add_notes.viewModel.ShiftScheduleAddNotesViewModel
import com.example.svetlogorskchpp.__widget.ShiftScheduleWidget
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.sql.Time
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class ShiftScheduleFragment : Fragment() {

    private var _binding: FragmentShiftScheduleBinding? = null
    private val binding get() = _binding!!

    private val args: ShiftScheduleFragmentArgs by navArgs()

    @Inject
    lateinit var viewModelFactory: ShiftScheduleViewModel.ShiftShiftScheduleViewModelFactory

    private lateinit var adapter: CalendarFullAdapter
    //private val viewModel: ShiftScheduleViewModel by viewModels()
    private val viewModel: ShiftScheduleViewModel by viewModels {
        ShiftScheduleViewModel.providesFactory(
            assistedFactory = viewModelFactory,
            date = args.date
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val per = PeriodicWorkRequestBuilder<CalendarNotesWorker>(15, TimeUnit.MINUTES).build()
        WorkManager.getInstance(requireContext()).enqueueUniquePeriodicWork(
            "POLL_WORK",
            ExistingPeriodicWorkPolicy.KEEP,
            per
        )
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentShiftScheduleBinding.inflate(inflater, container, false)

        binding.apply {
            swipeRefreshLayout.setOnRefreshListener {
                swipeRefreshLayout.postDelayed({
                    swipeRefreshLayout.isRefreshing = false
                    viewModel.selectDateStart()
                }, 100)
            }
        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().finish() // Закрывает приложение
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    binding.apply {
                        isProgressBar(state.calendarList.isEmpty())
                        if (state.calendarList.isNotEmpty()) {
                            viewModel.updateTag()

                            val adapterState = AdapterUiState(shift = state.selectShift, calendarView = state.calendarView)
                            adapter.setData(state.calendarList, adapterState)
                            binding.apply {
                                recyclerView.adapter = adapter
                                tvDateMonth.text = state.textDateMonth
                                todayDateTextView.text = state.textTodayDate
                            }
                            isCheckedChipShift(state.selectShift)
                            isCheckedChipCalendarView(state.calendarView)

                        }
                    }
                }
            }
        }

        setUpAdapter()
        setUpClickListener()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun onStop() {
        super.onStop()
        val updateIntent = Intent(
            requireContext(),
            ShiftScheduleWidget::class.java
        ).apply {
            action = "ACTION_UPDATE_WIDGET"
        }

        requireContext().sendBroadcast(updateIntent)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setUpAdapter() {
        val itemDecoration = ItemOffsetDecoration(requireContext())
        adapter = CalendarFullAdapter {calendarFullDateModel ->
            val navigateAddNoteArgs = NavigateAddNoteArgs(
                date = calendarFullDateModel.data.time.time,
                prevNightShift = calendarFullDateModel.prevNightShift,
                dayShift = calendarFullDateModel.dayShift,
                nextNightShift = calendarFullDateModel.nextNightShift,
                isTechnical = calendarFullDateModel.calendarNoteTag?.isTechnical?: false
            )
            val action = ShiftScheduleFragmentDirections.actionShiftScheduleFragmentToShiftScheduleAddNotesFragment(navigateAddNoteArgs)
            findNavController().navigate(action)
        }

        binding.apply {
            recyclerView.addItemDecoration(itemDecoration)
            recyclerView.adapter = adapter
        }
    }

    private fun setUpClickListener() {

        binding.apply {
            ivCalendarNext.setOnClickListener {
                viewModel.selectNext()
            }
            ivCalendarPrevious.setOnClickListener {
                viewModel.selectPrevs()
            }
            chipShiftA.setOnCheckedChangeListener { _, b ->
                selectChip(b, "A")
            }
            chipShiftB.setOnCheckedChangeListener { _, b ->
                selectChip(b, "B")
            }
            chipShiftC.setOnCheckedChangeListener { _, b ->
                selectChip(b, "C")
            }
            chipShiftD.setOnCheckedChangeListener { _, b ->
                selectChip(b, "D")
            }
            chipCalendarView1.setOnCheckedChangeListener { _, b ->
                lifecycleScope.launch {
                    if (b) viewModel.setSelectCalendarView("1") else viewModel.setSelectCalendarView("2")
                }
            }

            shiftCompositionEditor.setOnClickListener {
                findNavController().navigate(R.id.action_shiftScheduleFragment_to_shiftScheduleEditCompositionFragment)
            }

            dialogStaffA.setOnClickListener {
                val action = ShiftScheduleFragmentDirections.actionShiftScheduleFragmentToShiftScheduleStaffDialog(Shift.A_SHIFT)
                findNavController().navigate(action)
            }
            dialogStaffB.setOnClickListener {
                val action = ShiftScheduleFragmentDirections.actionShiftScheduleFragmentToShiftScheduleStaffDialog(Shift.B_SHIFT)
                findNavController().navigate(action)
            }
            dialogStaffC.setOnClickListener {
                val action = ShiftScheduleFragmentDirections.actionShiftScheduleFragmentToShiftScheduleStaffDialog(Shift.C_SHIFT)
                findNavController().navigate(action)
            }
            dialogStaffD.setOnClickListener {
                val action = ShiftScheduleFragmentDirections.actionShiftScheduleFragmentToShiftScheduleStaffDialog(Shift.D_SHIFT)
                findNavController().navigate(action)
            }
            dialogStaffE.setOnClickListener {
                val action = ShiftScheduleFragmentDirections.actionShiftScheduleFragmentToShiftScheduleStaffDialog(Shift.E_SHIFT)
                findNavController().navigate(action)
            }
        }
    }

    private fun isProgressBar(isProgress: Boolean) {
        binding.apply {
            chipGroup.isGone = isProgress
            constraintLayoutCalendar.isGone = isProgress
            textShiftName.isGone = isProgress
            chipCalendarView1.isGone = isProgress
            constraintEditShift.isGone = isProgress
            progressBar.isGone = !isProgress
        }
    }

    private fun selectChip(isChecked: Boolean, shift: String) {
        val isAnyChipChecked = binding.chipGroup.children.filterIsInstance<Chip>().any { it.isChecked }
        lifecycleScope.launch {
            if (isChecked) viewModel.setSelectShiftSchedule( shift ) else if (!isAnyChipChecked) viewModel.setSelectShiftSchedule( "" )
        }
    }

    private fun isCheckedChipShift(shift: Shift) {
        when (shift) {
            Shift.A_SHIFT -> binding.chipShiftA.isChecked = true
            Shift.B_SHIFT -> binding.chipShiftB.isChecked = true
            Shift.C_SHIFT -> binding.chipShiftC.isChecked = true
            Shift.D_SHIFT -> binding.chipShiftD.isChecked = true
            Shift.E_SHIFT -> Any()
            Shift.NO_SHIFT -> Any()
        }
    }

    private fun isCheckedChipCalendarView(view: String) {
        when (view) {
            "1" -> binding.chipCalendarView1.isChecked = true
            else -> binding.chipCalendarView1.isChecked = false
        }
    }
}