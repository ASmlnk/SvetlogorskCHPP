package com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.open_switchgear_tr.fragment

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
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.KeyOry
import com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.BaseEquipmentBottomSheetDialog
import com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.adapter.PowerSupplySelectionAdapter
import com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.adapter.ProtectionDialogAdapter
import com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.factory.OpenSwitchgearTrViewModelFactory
import com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.lighting_and_other.fragment.LightingAndOtherDialogDirections
import com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.open_switchgear_tr.view_model.OpenSwitchgearTrViewModel
import com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.open_switchgear_tr.model.OpSwiTrDialogUIState
import com.example.svetlogorskchpp.databinding.ContentLayoutOryParameterTrDialogBinding
import com.example.svetlogorskchpp.databinding.ContentLayoutRzaDialogBinding
import com.example.svetlogorskchpp.databinding.DialogOpenSwitchgearTrBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class OpenSwitchgearTrDialog : BaseEquipmentBottomSheetDialog<DialogOpenSwitchgearTrBinding>() {

    private val args: OpenSwitchgearTrDialogArgs by navArgs()

    private val adapter = PowerSupplySelectionAdapter { id, name, dl ->
        val deepLink = Uri.parse(dl.link + id)
        findNavController().navigate(deepLink)
    }
    private val adapterProtection = ProtectionDialogAdapter()

    @Inject
    lateinit var viewModelFactory: OpenSwitchgearTrViewModelFactory
    private val viewModel: OpenSwitchgearTrViewModel by viewModels {
        OpenSwitchgearTrViewModel.providesFactory(
            openSwitchgearTrViewModelFactory = viewModelFactory,
            idTr = args.id
        )
    }

    private var _includeOryParameterVnBinding: ContentLayoutOryParameterTrDialogBinding? = null
    private val includeOryParameterVnBinding get() = _includeOryParameterVnBinding!!

    private var _includeOryParameterSnBinding: ContentLayoutOryParameterTrDialogBinding? = null
    private val includeOryParameterSnBinding get() = _includeOryParameterSnBinding!!

    private var _includeOryRzaBinding: ContentLayoutRzaDialogBinding? = null
    private val includeOryRzaBinding get() = _includeOryRzaBinding!!


    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): DialogOpenSwitchgearTrBinding {
        return DialogOpenSwitchgearTrBinding.bind(
            inflater.inflate(
                R.layout.dialog_open_switchgear_tr,
                container
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
                viewModel.uiState.collect { stateUI ->
                    setupUI(stateUI)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.consumerFlow.collect { equipments ->
                    adapter.submitList(equipments)
                }
            }
        }

        binding.apply {
            _includeOryParameterVnBinding =
                ContentLayoutOryParameterTrDialogBinding.bind(contentOryVnParameter.root)
            _includeOryParameterSnBinding =
                ContentLayoutOryParameterTrDialogBinding.bind(contentOrySnParameter.root)
            _includeOryRzaBinding = ContentLayoutRzaDialogBinding.bind(contentRza.root)

            rvConsumer.adapter = adapter
            includeOryRzaBinding.rv.adapter = adapterProtection

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
            OpenSwitchgearTrDialogDirections.Companion.actionOpenSwitchgearTrDialogToOpenSwitchgearTrEditFragment(
                args.id
            )
        findNavController().navigate(action)
    }

    private fun setupUI(state: OpSwiTrDialogUIState) {

        binding.apply {
            tvName.text = state.name
            tvType.text = state.type
            tvTypeParameter.text = state.parameterType
            tvSpare.visibility = if (state.isSpare) View.VISIBLE else View.INVISIBLE
            includeOryParameterSnBinding.root.isGone = !state.isThreeWinding
            tvPanelContent.text = state.panelMcp
            tvTranscriptType.text = state.transcriptType
            tvAdditionally.text = state.additionally
            tvTextOry.text = if (state.isThreeWinding) {
                with(state) {
                    resources.getString(
                        R.string.parameter_cell_vn_sn_ory,
                        bysSystemVn,
                        cellVn,
                        voltageVn.str,
                        bysSystemSn,
                        cellSn,
                        voltageSn.str
                    )
                }
            } else {
                with(state) {
                    resources.getString(
                        R.string.parameter_cell_ory,
                        bysSystemVn,
                        cellVn,
                        voltageVn.str
                    )
                }
            }
        }

        setupViewKeyOry(
            keyShr1 = state.keyShr1Vn,
            keyShr2 = state.keyShr2Vn,
            keyLr = state.keyLrVn,
            keyOr = state.keyOrVn,
            includeOryParameterVnBinding
        )
        setupViewKeyOry(
            keyShr1 = state.keyShr1Sn,
            keyShr2 = state.keyShr2Sn,
            keyLr = state.keyLrSn,
            keyOr = state.keyOrSn,
            includeOryParameterSnBinding
        )

        includeOryParameterVnBinding.apply {
            tvSwitchContent.text = state.typeSwitchVn
            tvInstrContent.text = state.typeInsTrVn
            tvVoltage.text = resources.getString(R.string.name_voltage_ory, state.voltageVn.str)
        }
        includeOryParameterSnBinding.apply {
            tvSwitchContent.text = state.typeSwitchSn
            tvInstrContent.text = state.typeInsTrSn
            tvVoltage.text = resources.getString(R.string.name_voltage_ory, state.voltageSn.str)
        }
        includeOryRzaBinding.apply {
            tvApvContent.text = state.apv
            tvAutomationContent.text = state.automation
            adapterProtection.submitList(state.phaseProtection + state.earthProtection)
            // tvEarthProtectionContent.text =state.earthProtection
            // tvPhaseProtectionContent.text =state.phaseProtection
            tvApvContent.isGone = state.apv.isEmpty()
            tvApvTitle.isGone = state.apv.isEmpty()
            tvAutomationTitle.isGone = state.automation.isEmpty()
            tvAutomationContent.isGone = state.automation.isEmpty()
        }

    }

    private fun setupViewKeyOry(
        keyShr1: KeyOry,
        keyShr2: KeyOry,
        keyLr: KeyOry,
        keyOr: KeyOry,
        includeBinding: ContentLayoutOryParameterTrDialogBinding,
    ) {
        includeBinding.apply {
            layoutKey.isGone = keyShr1 == KeyOry.KEY_0 && keyShr2 == KeyOry.KEY_0 &&
                    keyLr == KeyOry.KEY_0 && keyOr == KeyOry.KEY_0

            if (keyShr1 == KeyOry.KEY_0) {
                tvShr1.visibility = View.INVISIBLE
                ivShr1.visibility = View.INVISIBLE
            } else {
                layoutKey.isGone = false
                tvShr1.visibility = View.VISIBLE
                ivShr1.visibility = View.VISIBLE
                ivShr1.setImageResource(keyOrySrc(keyShr1))
            }

            if (keyShr2 == KeyOry.KEY_0) {
                tvShr2.visibility = View.INVISIBLE
                ivShr2.visibility = View.INVISIBLE
            } else {
                layoutKey.isGone = false
                tvShr2.visibility = View.VISIBLE
                ivShr2.visibility = View.VISIBLE
                ivShr2.setImageResource(keyOrySrc(keyShr2))
            }

            if (keyLr == KeyOry.KEY_0) {
                tvLr.visibility = View.INVISIBLE
                ivLr.visibility = View.INVISIBLE
            } else {
                layoutKey.isGone = false
                tvLr.visibility = View.VISIBLE
                ivLr.visibility = View.VISIBLE
                ivLr.setImageResource(keyOrySrc(keyLr))
            }

            if (keyOr == KeyOry.KEY_0) {
                tvOr.visibility = View.INVISIBLE
                ivOr.visibility = View.INVISIBLE
            } else {
                layoutKey.isGone = false
                tvOr.visibility = View.VISIBLE
                ivOr.visibility = View.VISIBLE
                ivOr.setImageResource(keyOrySrc(keyOr))
            }
        }
    }

    private fun keyOrySrc(key: KeyOry): Int {
        return when (key) {
            KeyOry.KEY_0 -> 0
            KeyOry.KEY_1 -> R.drawable.background_key_1_ory
            KeyOry.KEY_2 -> R.drawable.background_key_2_ory
            KeyOry.KEY_3 -> R.drawable.background_key_3_ory
            KeyOry.KEY_4 -> R.drawable.background_key_4_ory
        }
    }
}
