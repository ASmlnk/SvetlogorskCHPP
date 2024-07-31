package com.example.svetlogorskchpp.presentation.shift_schedule.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.svetlogorskchpp.databinding.FragmentShiftScheduleBinding
import com.example.svetlogorskchpp.domain.model.Shift
import com.example.svetlogorskchpp.presentation.shift_schedule.adapter.CalendarFullAdapter
import com.example.svetlogorskchpp.presentation.shift_schedule.model.CalendarFullDayModel
import com.example.svetlogorskchpp.presentation.shift_schedule.adapter.ItemOffsetDecoration
import com.example.svetlogorskchpp.presentation.shift_schedule.viewModel.ShiftScheduleViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
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
                        if (state.calendarList.isNotEmpty()) {
                            delay(10)
                            adapter.setData(state.calendarList, state.selectShift)
                            binding.apply {
                                recyclerView.adapter = adapter
                                tvDateMonth.text = state.textDateMonth
                            }
                            isCheckedChipShift(state.selectShift)
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
                lifecycleScope.launch {
                    if (b) viewModel.setSelectShiftSchedule("A")
                }
            }
            chipShiftB.setOnCheckedChangeListener { _, b ->
                lifecycleScope.launch {
                    if (b) viewModel.setSelectShiftSchedule("B")
                }
            }
            chipShiftC.setOnCheckedChangeListener { _, b ->
                lifecycleScope.launch {
                    if (b) viewModel.setSelectShiftSchedule("C")
                }
            }
            chipShiftD.setOnCheckedChangeListener { _, b ->
                lifecycleScope.launch {
                    if (b) viewModel.setSelectShiftSchedule("D")
                }
            }
        }
    }

    private fun isProgressBar(isProgress: Boolean) {
        binding.apply {
            chipGroup.isGone = isProgress
            constraintLayoutCalendar.isGone = isProgress
        }
    }

    private fun isCheckedChipShift(shift: Shift) {
        when (shift) {
            Shift.A_SHIFT -> binding.chipShiftA.isChecked = true
            Shift.B_SHIFT -> binding.chipShiftB.isChecked = true
            Shift.C_SHIFT -> binding.chipShiftC.isChecked = true
            Shift.D_SHIFT -> binding.chipShiftD.isChecked = true
            Shift.NO_SHIFT -> true
        }
    }
}