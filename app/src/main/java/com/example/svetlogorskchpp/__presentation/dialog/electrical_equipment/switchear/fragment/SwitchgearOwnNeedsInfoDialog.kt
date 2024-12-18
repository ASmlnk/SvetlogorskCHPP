package com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.switchear.fragment

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
import com.example.svetlogorskchpp.__presentation.dialog.BaseBottomSheetDialog
import com.example.svetlogorskchpp.R
import com.example.svetlogorskchpp.__domain.OperationResult
import com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.BaseEquipmentBottomSheetDialog
import com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.adapter.PowerSupplySelectionAdapter
import com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.adapter.ProtectionDialogAdapter
import com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.factory.SwitchgearOwnNeedsInfoViewModelFactory
import com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.open_switchgear_tr.fragment.OpenSwitchgearTrDialogDirections
import com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.switchear.model.SwitchgearInfoUIState
import com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.switchear.view_model.SwitchgearOwnNeedsInfoViewModel
import com.example.svetlogorskchpp.databinding.ContentLayoutRzaEquipmentDialogBinding
import com.example.svetlogorskchpp.databinding.DialogEquipmentSwitchgearBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.getValue

@AndroidEntryPoint
class SwitchgearOwnNeedsInfoDialog : BaseEquipmentBottomSheetDialog<DialogEquipmentSwitchgearBinding>() {

    private val args: SwitchgearOwnNeedsInfoDialogArgs by navArgs()

    private val adapterProtection = ProtectionDialogAdapter()
    private val adapterPowerSupply = PowerSupplySelectionAdapter { id, name, dl ->
        val deepLink = Uri.parse(dl.link + id)
        findNavController().navigate(deepLink)
    }

    private var _includeRzaBinding: ContentLayoutRzaEquipmentDialogBinding? = null
    private val includeRzaBinding get() = _includeRzaBinding!!

    @Inject
    lateinit var viewModelFactory: SwitchgearOwnNeedsInfoViewModelFactory
    private val viewModel: SwitchgearOwnNeedsInfoViewModel by viewModels {
        SwitchgearOwnNeedsInfoViewModel.providesFactory(
            factory = viewModelFactory,
            id = args.id
        )
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): DialogEquipmentSwitchgearBinding {
        return DialogEquipmentSwitchgearBinding.bind(
            inflater.inflate(R.layout.dialog_equipment_switchgear, container)
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _includeRzaBinding = ContentLayoutRzaEquipmentDialogBinding.bind(binding.contentRza.root)
        includeRzaBinding.rv.adapter = adapterProtection

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
                    adapterPowerSupply.submitList(it)
                }
            }
        }


        binding.apply {
            rvPowerSource.adapter = adapterPowerSupply

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
            SwitchgearOwnNeedsInfoDialogDirections.actionSwitchgearOwnNeedsInfoDialogToSwitchgearOwnNeedsEditFragment(
                args.id
            )
        findNavController().navigate(action)
    }

    private fun setupUI(state: SwitchgearInfoUIState) {

        adapterProtection.submitList(state.phaseProtection + state.earthProtection)

        binding.apply {
            tvName.apply {
                text = state.name
            }
            tvVoltage.text = state.voltage.str
            tvNameDepartment.text = state.nameDepartment.str

            tvSwitchTitle.isGone = state.typeSwitch.isEmpty()
            tvSwitchContent.apply {
                isGone = state.typeSwitch.isEmpty()
                text = state.typeSwitch
            }

            tvInstrTitle.isGone = state.typeInsTr.isEmpty()
            tvInstrContent.apply {
                isGone = state.typeInsTr.isEmpty()
                text = state.typeInsTr
            }

            tvMainPowerSourceContent.text = state.powerSupplyContent
            tvBackupPowerSupplyContent.apply {
                text = state.powerSupplyReserveContent
                isGone = state.powerSupplyReserveContent.isEmpty()
            }
            tvBackupPowerSupply.isGone = state.powerSupplyReserveContent.isEmpty()

            tvInfo.apply {
                text = state.info
                isGone = state.info.isEmpty()
            }

            tvAdditionally.apply {
                text = state.additionally
                isGone = state.additionally.isEmpty()
            }
        }

        includeRzaBinding.apply {
            tvAutomationContent.text = state.automation

            tvAdditionally1.apply {
                text = state.additionallyRza
                isGone = state.additionallyRza.isEmpty()
            }
            tvAdditionally2.apply {
                isGone = true
            }
            val listProtection = with(state) { phaseProtection + earthProtection }
            tvProtectionTitle.isGone = listProtection.isEmpty()

            tvAutomationTitle.isGone = state.automation.isEmpty()
            tvAutomationContent.isGone = state.automation.isEmpty()

            with(state) {
                layoutRza.isGone = automation.isEmpty() && additionallyRza.isEmpty() && phaseProtection.isEmpty() && earthProtection.isEmpty()
            }

        }

    }
}