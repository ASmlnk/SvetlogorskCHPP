package com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.lighting_and_other.fragment

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.svetlogorskchpp.__presentation.dialog.BaseBottomSheetDialog
import com.example.svetlogorskchpp.R
import com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.adapter.PowerSupplySelectionAdapter
import com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.factory.LightingAndOtherViewModelFactory
import com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.lighting_and_other.model.LightingUIState
import com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.lighting_and_other.view_model.LightingAndOtherViewModel
import com.example.svetlogorskchpp.databinding.DialogEquipmentLightingAndOtherBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.getValue

@AndroidEntryPoint
class LightingAndOtherDialog : BaseBottomSheetDialog<DialogEquipmentLightingAndOtherBinding>() {

    private val args: LightingAndOtherDialogArgs by navArgs()

    private val adapterPowerSupply = PowerSupplySelectionAdapter { id, name, dl ->
        val deepLink = Uri.parse(dl.link + id)
        findNavController().navigate(deepLink)
    }

    @Inject
    lateinit var viewModelFactory: LightingAndOtherViewModelFactory
    private val viewModel: LightingAndOtherViewModel by viewModels {
        LightingAndOtherViewModel.providesFactory(
            factory = viewModelFactory,
            id = args.id
        )
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): DialogEquipmentLightingAndOtherBinding {
        return DialogEquipmentLightingAndOtherBinding.bind(
            inflater.inflate(R.layout.dialog_equipment_lighting_and_other, container)
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    setupUI(it)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.powerSupplyState.collect {
                    adapterPowerSupply.submitList(it)
                }
            }
        }

        binding.apply {
            rvPowerSupply.adapter = adapterPowerSupply

            ivEditContent.setOnClickListener {
                //val action =
                //    SwitchgearOwnNeedsInfoDialogDirections.actionSwitchgearOwnNeedsInfoDialogToSwitchgearOwnNeedsEditFragment(
                //        args.id
                //    )
                //findNavController().navigate(action)
            }
        }

    }

    private fun setupUI(state: LightingUIState) {
        binding.apply {
            with(state) {
                tvName.text = name
                tvPowerSupplyContent.text =
                    if (powerSupplyCell.isEmpty()) powerSupplyName else resources.getString(
                        R.string.power_supply_item,
                        powerSupplyName,
                        powerSupplyCell
                    )
                tvSwitchTitle.isGone = typeSwitch.isEmpty()

                tvSwitchContent.apply {
                    text = typeSwitch
                    isGone = typeSwitch.isEmpty()
                }
                tvLocation.apply {
                    text = location
                    isGone = location.isEmpty()
                }
                tvAdditionally.apply {
                    text = additionally
                    isGone = additionally.isEmpty()
                }

                ivLighting.isGone = !isLighting
            }
        }
    }
}