package com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.power_supply_selection.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.svetlogorskchpp.R
import com.example.svetlogorskchpp.__presentation.dialog.BaseBottomSheetDialog
import com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.adapter.PowerSupplySelectionAdapter
import com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.model.PSFilter
import com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.power_supply_selection.view_model.PowerSupplySelectionViewModel
import com.example.svetlogorskchpp.__presentation.electrical_equipment.switchgear.model.SwitchgearPowerSupply
import com.example.svetlogorskchpp.databinding.DialogPowerSupplySelectionBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PowerSupplySelectionDialog : BaseBottomSheetDialog<DialogPowerSupplySelectionBinding>() {

    private val viewModel: PowerSupplySelectionViewModel by viewModels()

    private val args: PowerSupplySelectionDialogArgs by navArgs()

    private val adapter = PowerSupplySelectionAdapter { id, name, dl ->
        val selectedId = id // Логика выбора ID
        val selectedName = name // Логика выбора имени
        val selectedData = Pair(selectedId, selectedName)
        val data = when(args.switchgearPowerSupply) {
            SwitchgearPowerSupply.NULL -> "selectedData"
            SwitchgearPowerSupply.MAIN_1 -> SwitchgearPowerSupply.MAIN_1.name
            SwitchgearPowerSupply.MAIN_2 -> SwitchgearPowerSupply.MAIN_2.name
            SwitchgearPowerSupply.RESERVE_1 -> SwitchgearPowerSupply.RESERVE_1.name
            SwitchgearPowerSupply.RESERVE_2 -> SwitchgearPowerSupply.RESERVE_2.name
            SwitchgearPowerSupply.RESERVE_3 -> SwitchgearPowerSupply.RESERVE_3.name
        }
        findNavController().previousBackStackEntry?.savedStateHandle?.set(data, selectedData)
        findNavController().popBackStack()
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): DialogPowerSupplySelectionBinding {
        return DialogPowerSupplySelectionBinding.bind(
            inflater.inflate(
                R.layout.dialog_power_supply_selection,
                container
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            rv.adapter = adapter
            chipHc.setOnClickListener {applyFilter()}
            chipOther.setOnClickListener {applyFilter()}
            chipTransformer.setOnClickListener {applyFilter()}
            chipSection04.setOnClickListener {applyFilter()}
            chipSection3.setOnClickListener {applyFilter()}
            chipShieldBlock.setOnClickListener {applyFilter()}
            chipKtcTo.setOnClickListener {applyFilter()}
            chipKtcKo.setOnClickListener {applyFilter()}
            chipPostTok.setOnClickListener {applyFilter()}
        }


        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.dataState.collect {
                    adapter.submitList(it)
                }
            }
        }
    }

    private fun DialogPowerSupplySelectionBinding.applyFilter() {
        val activeFilters = mutableListOf<PSFilter>()
        if (chipHc.isChecked) activeFilters.add(PSFilter.HC)
        if (chipOther.isChecked) activeFilters.add(PSFilter.OTHER)
        if (chipTransformer.isChecked) activeFilters.add(PSFilter.TR)
        if (chipSection04.isChecked) activeFilters.add(PSFilter.S_04)
        if (chipSection3.isChecked) activeFilters.add(PSFilter.S_3_6)
        if (chipShieldBlock.isChecked) activeFilters.add(PSFilter.SHIELD_BLOCK)
        if (chipKtcTo.isChecked) activeFilters.add(PSFilter.TO)
        if (chipKtcKo.isChecked) activeFilters.add(PSFilter.KO)
        if (chipPostTok.isChecked) activeFilters.add(PSFilter.POST_TOK)

        viewModel.filterData(activeFilters)
    }
}