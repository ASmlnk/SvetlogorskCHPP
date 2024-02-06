package com.example.svetlogorskchpp.inspectionSchedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.svetlogorskchpp.R
import com.example.svetlogorskchpp.databinding.DialogChecklistInspectionBinding
import com.example.svetlogorskchpp.databinding.DialogInspectionScheduleCalendarBinding
import com.example.svetlogorskchpp.model.firebase.FirestoreRepository
import com.example.svetlogorskchpp.model.inspectionSchedule.InspectionChecklist
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch

class DialogChecklistInspection : BottomSheetDialogFragment() {

    private var _binding: DialogChecklistInspectionBinding? = null
    private val binding get() = _binding!!

    private val data = FirestoreRepository.get()

    override fun getTheme() = R.style.AppBottomSheetDialogTheme


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogChecklistInspectionBinding.bind(
            inflater.inflate(
                R.layout.dialog_checklist_inspection,
                container
            )
        )
        val nameCategory = DialogChecklistInspectionArgs.fromBundle(requireArguments()).nameCategory
        val adapter = ChecklistInspectionCheckAdapter(nameCategory)

        binding.apply {
            recycleChecklist.adapter = adapter
            textNameChecklist.text = nameCategory
        }

        lifecycleScope.launch {
            data.listChecklistInspection.collect {
                val list = it.filter { it.nameBlank == nameCategory }
                adapter.submitList(list.sortedBy { it.numberChecklist.toDouble() })
            }
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}