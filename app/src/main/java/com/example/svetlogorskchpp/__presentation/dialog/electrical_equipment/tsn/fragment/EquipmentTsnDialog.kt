package com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.tsn.fragment

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isGone
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.svetlogorskchpp.R
import com.example.svetlogorskchpp.__domain.OperationResult
import com.example.svetlogorskchpp.__presentation.dialog.BaseBottomSheetDialog
import com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.BaseEquipmentBottomSheetDialog
import com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.adapter.PowerSupplySelectionAdapter
import com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.adapter.ProtectionDialogAdapter
import com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.factory.EquipmentTsnViewModelFactory
import com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.tg.fragment.TurbogeneratorDialogDirections
import com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.tsn.model.TsnUIState
import com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.tsn.view_model.EquipmentTsnViewModel
import com.example.svetlogorskchpp.databinding.ContentLayoutRzaDialogBinding
import com.example.svetlogorskchpp.databinding.DialogEquipmentTsnBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class EquipmentTsnDialog : BaseEquipmentBottomSheetDialog<DialogEquipmentTsnBinding>() {

    val args: EquipmentTsnDialogArgs by navArgs()

    @Inject
    lateinit var viewModelFactory: EquipmentTsnViewModelFactory
    private val viewModel: EquipmentTsnViewModel by viewModels {
        EquipmentTsnViewModel.providesFactory(
            factoryTsn = viewModelFactory,
            id = args.id
        )
    }

    private val adapterProtection = ProtectionDialogAdapter()
    private val adapter = PowerSupplySelectionAdapter { id, name, dl ->
        val deepLink = Uri.parse(dl.link + id)
        findNavController().navigate(deepLink)
    }

    private val adapterConsumer = PowerSupplySelectionAdapter { id, name, dl ->
        val deepLink = Uri.parse(dl.link + id)
        findNavController().navigate(deepLink)
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
        includeOryRzaBinding.rv.adapter = adapterProtection

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.toastResultFlow.collect { toast ->
                if (toast is OperationResult.Success) {
                    navigateEditFragment()
                } else {
                    Toast.makeText(
                        context,
                        (toast as OperationResult.Error).massage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

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
                    adapter.submitList(it)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.consumerState.collect {
                    adapterConsumer.submitList(it)
                }
            }
        }

        binding.apply {
            rvPowerSupply.adapter = adapter
            rvConsumer.adapter = adapterConsumer
            ivEditContent.setOnClickListener {
                if (viewModel.isEditAccess()) {
                    navigateEditFragment()
                } else {
                    showPasswordDialog(requireContext()) {
                        viewModel.equalsPassword(it)
                    }
                }
            }
        }

    }

    private fun navigateEditFragment() {
        val action =
            EquipmentTsnDialogDirections.actionEquipmentTsnDialogToTransformerOwnNeedsEditFragment(
                args.id
            )
        findNavController().navigate(action)
    }

    private fun setupUI(state: TsnUIState) {

        adapterProtection.submitList(state.phaseProtection + state.earthProtection)

        binding.apply {
            tvName.text = state.name
            tvType.text = state.type
            tvVoltage.text = state.voltage.str
            tvTypeParameter.text = state.parameterType
            tvSpare.visibility = if (state.isSpare) View.VISIBLE else View.INVISIBLE
            tvPowerSupply.text = state.powerSupplyName +
                    if (state.powerSupplyCell.isNotEmpty()) " яч." + state.powerSupplyCell else ""
            tvSwitchContent.text = state.typeSwitch
            tvInstrContent.text = state.typeInsTr
            tvPanelContent.text = state.panelMcp
            tvTranscriptType.text = state.transcriptType
            tvAdditionally.text = state.additionally

        }

        includeOryRzaBinding.apply {
            tvApvContent.text = state.apv
            tvAutomationContent.text = state.automation
            //tvEarthProtectionContent.text = state.earthProtection
            //tvPhaseProtectionContent.text = state.phaseProtection
            tvApvContent.isGone = state.apv.isEmpty()
            tvApvTitle.isGone = state.apv.isEmpty()
            tvAutomationTitle.isGone = state.automation.isEmpty()
            tvAutomationContent.isGone = state.automation.isEmpty()
        }

    }
}