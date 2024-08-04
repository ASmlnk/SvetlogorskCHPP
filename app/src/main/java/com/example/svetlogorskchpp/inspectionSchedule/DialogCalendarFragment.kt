package com.example.svetlogorskchpp.inspectionSchedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.svetlogorskchpp.R
import com.example.svetlogorskchpp.databinding.DialogInspectionScheduleCalendarBinding
import com.example.svetlogorskchpp.model.firebase.FirestoreRepository
import com.example.svetlogorskchpp.model.inspectionSchedule.InspectionChecklist
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DialogCalendarFragment : BottomSheetDialogFragment() {

    private var _binding: DialogInspectionScheduleCalendarBinding? = null
    private val binding get() = _binding!!

    @Inject lateinit var data: FirestoreRepository

    override fun getTheme() = R.style.AppBottomSheetDialogTheme
    private val adapter = InspectionChecklistAdapter()

    private val listInspectionChecklist = mutableListOf<InspectionChecklist>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogInspectionScheduleCalendarBinding.bind(
            inflater.inflate(
                R.layout.dialog_inspection_schedule_calendar,
                container
            )
        )

        binding.apply {
            recycleInspectionSchedule.adapter = adapter
        }

        lifecycleScope.launch {
            data.listInspectionStateFlow.collect {
                val list = it.map {
                    InspectionChecklist(
                        workingShift = it.workingShift,
                        executor = it.executor,
                        content = it.content,
                        timeSpending = it.timeSpending,
                        withoutNumber = it.withoutNumber)
                }
                adapter.submitList(list.toSet().toList())
                listInspectionChecklist.addAll(list.toSet().toList())
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            chipNse.setOnCheckedChangeListener { _, b ->
                if (b) adapter.submitList(listInspectionChecklist.filter { it.executor == "НСЭ" })
            }
            chipDem.setOnCheckedChangeListener { _, b ->
                if (b) adapter.submitList(listInspectionChecklist.filter { it.executor == "ДЭМ" })
            }
            chipAll.setOnCheckedChangeListener { _, b ->
                if (b) adapter.submitList(listInspectionChecklist)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}