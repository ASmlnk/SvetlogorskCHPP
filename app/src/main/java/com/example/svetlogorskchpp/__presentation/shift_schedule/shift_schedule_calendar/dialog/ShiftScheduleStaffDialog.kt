package com.example.svetlogorskchpp.__presentation.shift_schedule.shift_schedule_calendar.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.svetlogorskchpp.R
import com.example.svetlogorskchpp.databinding.DialogShiftSchedulePersonalBinding
import com.example.svetlogorskchpp.__domain.en.shift_schedule.Shift
import com.example.svetlogorskchpp.__presentation.shift_schedule.shift_schedule_calendar.adapter.ShiftPersonalAdapter
import com.example.svetlogorskchpp.__presentation.shift_schedule.shift_schedule_calendar.viewModel.ShiftScheduleStaffDialogViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ShiftScheduleStaffDialog: BottomSheetDialogFragment() {

    private var _binding: DialogShiftSchedulePersonalBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: ShiftScheduleStaffDialogViewModel by viewModels()

    override fun getTheme() = R.style.AppBottomSheetDialogTheme

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogShiftSchedulePersonalBinding.bind(inflater.inflate(R.layout.dialog_shift_schedule_personal,container))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val shift = ShiftScheduleStaffDialogArgs.fromBundle(requireArguments()).shift as Shift
        val adapter = ShiftPersonalAdapter(shift)
        binding.apply {
            recycleView.adapter = adapter
            tvShiftPersonal.text = context?.getString(R.string.shift_personal, shift.nameApp) ?: ""
            shimmerLayout.startShimmer()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.jobTitlePersonalStream.collect { list ->
                   adapter.submitList(list)
                    if (list.isNotEmpty()) stopShimmer()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private suspend fun stopShimmer() {
        binding.apply {
            recycleView.isVisible = true
            delay(50)
            shimmerLayout.stopShimmer()
            shimmerLayout.isVisible = false

        }
    }
}