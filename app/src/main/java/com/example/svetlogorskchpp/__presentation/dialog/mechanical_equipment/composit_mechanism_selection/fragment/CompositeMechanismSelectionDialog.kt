package com.example.svetlogorskchpp.__presentation.dialog.mechanical_equipment.composit_mechanism_selection.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.svetlogorskchpp.__presentation.dialog.BaseBottomSheetDialog
import com.example.svetlogorskchpp.R
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.ElGeneralCategory
import com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.adapter.PowerSupplySelectionAdapter
import com.example.svetlogorskchpp.__presentation.dialog.mechanical_equipment.composit_mechanism_selection.view_model.CompositeMechanicalSelectionViewModel
import com.example.svetlogorskchpp.databinding.DialogCompositeMechanismSelectionBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CompositeMechanismSelectionDialog: BaseBottomSheetDialog<DialogCompositeMechanismSelectionBinding>() {

    private val viewModel: CompositeMechanicalSelectionViewModel by viewModels()

    private val adapter = PowerSupplySelectionAdapter { id, name, dl ->
        val selectedId = id // Логика выбора ID
        val selectedName = name // Логика выбора имени
        val selectedData = Pair(selectedId, selectedName)
        val data =  "selectedMechanismData"

        findNavController().previousBackStackEntry?.savedStateHandle?.set(data, selectedData)
        findNavController().popBackStack()
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): DialogCompositeMechanismSelectionBinding {
        return DialogCompositeMechanismSelectionBinding.bind(
            inflater.inflate(R.layout.dialog_composite_mechanism_selection, container)
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            rv.adapter = adapter
            chipHc.setOnClickListener {applyFilter()}
            chipOther.setOnClickListener {applyFilter()}
            chipKa.setOnClickListener {applyFilter()}
            chipTg.setOnClickListener {applyFilter()}
            chipKtcTy.setOnClickListener {applyFilter()}
            chipKtcKo.setOnClickListener {applyFilter()}
            chipKtcTo.setOnClickListener {applyFilter()}
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.dataState.collect {
                    adapter.submitList(it)
                }
            }
        }
    }

    private fun DialogCompositeMechanismSelectionBinding.applyFilter() {
        val activeFilters = mutableListOf<ElGeneralCategory>()
       if  (chipHc.isChecked) activeFilters.add(ElGeneralCategory.HOV)
       if  (chipOther.isChecked) activeFilters.add(ElGeneralCategory.OTHER)
       if  (chipKa.isChecked) activeFilters.add(ElGeneralCategory.KA)
       if  (chipTg.isChecked) activeFilters.add(ElGeneralCategory.RG)
       if  (chipKtcTy.isChecked) activeFilters.add(ElGeneralCategory.TY)
       if  (chipKtcKo.isChecked) activeFilters.add(ElGeneralCategory.KTC_KO)
       if  (chipKtcTo.isChecked) activeFilters.add(ElGeneralCategory.KTC_TO)

        viewModel.filterData(activeFilters)
    }
}