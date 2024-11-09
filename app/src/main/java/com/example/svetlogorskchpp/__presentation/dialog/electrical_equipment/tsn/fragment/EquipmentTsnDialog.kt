package com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.tsn.fragment

import android.annotation.SuppressLint
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
import com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.factory.EquipmentTsnViewModelFactory
import com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.tsn.model.TsnUIState
import com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.tsn.view_model.EquipmentTsnViewModel
import com.example.svetlogorskchpp.databinding.ContentLayoutRzaDialogBinding
import com.example.svetlogorskchpp.databinding.DialogEquipmentTsnBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class EquipmentTsnDialog: BaseBottomSheetDialog<DialogEquipmentTsnBinding>() {


   val args: EquipmentTsnDialogArgs by navArgs()

    @Inject
    lateinit var viewModelFactory: EquipmentTsnViewModelFactory
    private val viewModel: EquipmentTsnViewModel by viewModels {
        EquipmentTsnViewModel.providesFactory(
            factoryTsn = viewModelFactory,
            id = args.id
        )
    }

    private var _includeOryRzaBinding: ContentLayoutRzaDialogBinding? = null
    private val includeOryRzaBinding get() = _includeOryRzaBinding!!

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): DialogEquipmentTsnBinding {
        return DialogEquipmentTsnBinding.bind(
            inflater.inflate(R.layout.dialog_equipment_tsn, container)
        )
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _includeOryRzaBinding = ContentLayoutRzaDialogBinding.bind(binding.contentRza.root)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    setupUI(it)
                }
            }
        }

        binding.apply {
            ivEditContent.setOnClickListener {
                val action =
                    EquipmentTsnDialogDirections.actionEquipmentTsnDialogToTransformerOwnNeedsEditFragment(
                        args.id
                    )
                findNavController().navigate(action)
            }
        }

    }


    private fun setupUI(state: TsnUIState) {

        binding.apply {
            tvName.text = state.name
            tvType.text = state.type
            tvTypeParameter.text = state.parameterType
            tvSpare.visibility = if (state.isSpare) View.VISIBLE else View.INVISIBLE
            tvPowerSupply.text = state.powerSupplyName +
                    if(state.powerSupplyCell.isNotEmpty()) " яч." +state.powerSupplyCell else ""
            tvSwitchContent.text = state.typeSwitch
            tvInstrContent.text = state.typeInsTr
            tvPanelContent.text = state.panelMcp
            tvTranscriptType.text = state.transcriptType
            tvAdditionally.text = state.additionally

        }

        includeOryRzaBinding.apply {
            tvApvContent.text =state.apv
            tvAutomationContent.text =state.automation
            tvEarthProtectionContent.text =state.earthProtection
            tvPhaseProtectionContent.text =state.phaseProtection
        }

    }
}