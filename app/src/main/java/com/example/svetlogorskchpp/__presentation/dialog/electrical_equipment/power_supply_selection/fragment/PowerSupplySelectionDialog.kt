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
import com.example.svetlogorskchpp.R
import com.example.svetlogorskchpp.__presentation.dialog.BaseBottomSheetDialog
import com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.adapter.PowerSupplySelectionAdapter
import com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.power_supply_selection.view_model.PowerSupplySelectionViewModel
import com.example.svetlogorskchpp.databinding.DialogPowerSupplySelectionBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PowerSupplySelectionDialog : BaseBottomSheetDialog<DialogPowerSupplySelectionBinding>() {

    private val viewModel: PowerSupplySelectionViewModel by viewModels()

    private val adapter = PowerSupplySelectionAdapter { id, name ->
        val selectedId = id // Логика выбора ID
        val selectedName = name // Логика выбора имени
        val selectedData = Pair(selectedId, selectedName)
        findNavController().previousBackStackEntry?.savedStateHandle?.set("selectedData", selectedData)
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

        binding.rv.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.equipments.collect {
                    adapter.submitList(it)
                }
            }
        }
    }
}