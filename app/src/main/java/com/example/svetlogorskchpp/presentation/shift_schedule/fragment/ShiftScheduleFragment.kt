package com.example.svetlogorskchpp.presentation.shift_schedule.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.svetlogorskchpp.R
import com.example.svetlogorskchpp.databinding.FragmentShiftScheduleBinding
import com.example.svetlogorskchpp.domain.en.Shift
import com.example.svetlogorskchpp.presentation.shift_schedule.adapter.CalendarFullAdapter
import com.example.svetlogorskchpp.presentation.shift_schedule.model.CalendarFullDayModel
import com.example.svetlogorskchpp.presentation.shift_schedule.adapter.ItemOffsetDecoration
import com.example.svetlogorskchpp.presentation.shift_schedule.model.AdapterUiState
import com.example.svetlogorskchpp.presentation.shift_schedule.viewModel.ShiftScheduleViewModel
import com.example.svetlogorskchpp.presentation.shift_schedule_edit_composition.model.JobTitlePersonal
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ShiftScheduleFragment : Fragment() {

    private var _binding: FragmentShiftScheduleBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: CalendarFullAdapter
    private val viewModel: ShiftScheduleViewModel by viewModels()

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

        setUpAdapter()
        setUpClickListener()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    binding.apply {
                        isProgressBar(state.calendarList.isEmpty())
                        if (state.calendarList.isNotEmpty()) {
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
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setUpAdapter() {
        val list = emptyList<CalendarFullDayModel>()
        val itemDecoration = ItemOffsetDecoration(requireContext())
        adapter = CalendarFullAdapter { calendarDateModel: CalendarFullDayModel, position: Int ->
            list.forEachIndexed { index, calendarModel ->
            }
            //viewModel.selectDate(calendarList3.get(position).data.time)
            // adapter.setData(list, Shift.NO_SHIFT)
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