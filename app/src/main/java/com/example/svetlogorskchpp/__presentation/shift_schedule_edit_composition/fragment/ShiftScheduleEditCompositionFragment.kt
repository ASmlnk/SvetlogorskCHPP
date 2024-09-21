package com.example.svetlogorskchpp.__presentation.shift_schedule_edit_composition.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.svetlogorskchpp.databinding.FragmentShiftScheduleEditCompositionBinding
import com.example.svetlogorskchpp.__presentation.shift_schedule_edit_composition.adapter.JobTitlePersonalAdapter
import com.example.svetlogorskchpp.__presentation.shift_schedule_edit_composition.adapter.StaffListAdapter
import com.example.svetlogorskchpp.__presentation.shift_schedule_edit_composition.viewModel.ShiftScheduleEditCompositionViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ShiftScheduleEditCompositionFragment : Fragment() {

    private val viewModel: ShiftScheduleEditCompositionViewModel by viewModels()

    private var _binding: FragmentShiftScheduleEditCompositionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentShiftScheduleEditCompositionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapterStaff = StaffListAdapter(requireContext(), emptyList())

        val adapter = JobTitlePersonalAdapter(adapterStaff) { jobTitlePersonals ->
            viewModel.save(jobTitlePersonals)
            val imm =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }

        binding.apply {
            recyclerView.adapter = adapter
            shimmerLayout.startShimmer()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.jobTitlePersonalUi.collect { stateUi ->
                    if (stateUi.jobTitlePersonals.isNotEmpty()) {
                        adapter.submitList(stateUi.jobTitlePersonals)
                        stopShimmer()
                    }
                    if (stateUi.staffs.isNotEmpty()) {
                        val newAdapterStaff = StaffListAdapter(requireContext(), stateUi.staffs)
                        adapter.adapterStaff = newAdapterStaff
                    }
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        lifecycleScope.launch {
            viewModel.saveBd()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private suspend fun stopShimmer() {
        binding.apply {
            //recyclerView.isVisible = true
            delay(50)
            shimmerLayout.stopShimmer()
            shimmerLayout.isVisible = false

        }
    }
}