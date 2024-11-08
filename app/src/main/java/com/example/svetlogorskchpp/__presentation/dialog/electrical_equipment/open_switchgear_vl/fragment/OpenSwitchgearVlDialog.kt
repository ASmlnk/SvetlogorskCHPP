package com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.open_switchgear_vl.fragment

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
import com.example.svetlogorskchpp.R
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.KeyOry
import com.example.svetlogorskchpp.__presentation.dialog.BaseBottomSheetDialog
import com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.factory.OpenSwitchgearVlViewModelFactory
import com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.open_switchgear_vl.model.OpSwiVlDialogUIState
import com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.open_switchgear_vl.view_model.OpenSwitchgearVlViewModel
import com.example.svetlogorskchpp.databinding.ContentLayoutOryParameterDialogBinding
import com.example.svetlogorskchpp.databinding.ContentLayoutRzaDialogBinding
import com.example.svetlogorskchpp.databinding.DialogOpenSwitchgearVlBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.getValue

@AndroidEntryPoint
class OpenSwitchgearVlDialog : BaseBottomSheetDialog<DialogOpenSwitchgearVlBinding>() {

    private val args: OpenSwitchgearVlDialogArgs by navArgs()

    @Inject
    lateinit var viewModelFactory: OpenSwitchgearVlViewModelFactory
    private val viewModel: OpenSwitchgearVlViewModel by viewModels {
        OpenSwitchgearVlViewModel.providesFactory(
            openSwitchgearVlViewModelFactory = viewModelFactory,
            idVl = args.id
        )
    }

    private var _includeOryParameterBinding: ContentLayoutOryParameterDialogBinding? = null
    private val includeOryParameterBinding get() = _includeOryParameterBinding!!

    private var _includeOryRzaBinding: ContentLayoutRzaDialogBinding? = null
    private val includeOryRzaBinding get() = _includeOryRzaBinding!!

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): DialogOpenSwitchgearVlBinding {
        return DialogOpenSwitchgearVlBinding.bind(
            inflater.inflate(
                R.layout.dialog_open_switchgear_vl,
                container
            )
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

        binding.apply {
            _includeOryParameterBinding =
                ContentLayoutOryParameterDialogBinding.bind(contentOryParameter.root)
            _includeOryRzaBinding = ContentLayoutRzaDialogBinding.bind(contentRza.root)
        }

        includeOryParameterBinding.apply {
            ivEditContent.setOnClickListener {
                val action =
                    OpenSwitchgearVlDialogDirections.Companion.actionOpenSwitchgearVlDialogToOpenSwitchgearVlEditFragment(
                        args.id
                    )
                findNavController().navigate(action)
            }
        }
    }

    private fun setupUI(state: OpSwiVlDialogUIState) {
        binding.apply {
            tvName.text = state.name
            tvTransit.visibility = if (state.isTransit) View.VISIBLE else View.INVISIBLE
            tvParameterCell.text = resources.getString(
                R.string.parameter_cell_ory,
                state.bysSystem,
                state.cell,
                state.voltage.str
            )
            tvPanelContent.text = state.panelMcp
        }

        includeOryParameterBinding.apply {
            tvSwitchContent.text = state.typeSwitch
            tvInstrContent.text = state.typeInsTr
        }

        setupViewKeyOry(state)

        includeOryRzaBinding.apply {
            tvApvContent.text = state.apv
            tvAutomationContent.text = state.automation
            tvPhaseProtectionContent.text = state.phaseProtection
            tvEarthProtectionContent.text = state.earthProtection
        }
    }

    private fun setupViewKeyOry(state: OpSwiVlDialogUIState) {
        includeOryParameterBinding.apply {
            layoutKey.isGone = state.keyShr1 == KeyOry.KEY_0 && state.keyShr2 == KeyOry.KEY_0 &&
                    state.keyLr == KeyOry.KEY_0 && state.keyOr == KeyOry.KEY_0

            if (state.keyShr1 == KeyOry.KEY_0) {
                tvShr1.visibility = View.INVISIBLE
                ivShr1.visibility = View.INVISIBLE
            } else {
                layoutKey.isGone = false
                tvShr1.visibility = View.VISIBLE
                ivShr1.visibility = View.VISIBLE
                ivShr1.setImageResource(keyOrySrc(state.keyShr1))
            }

            if (state.keyShr2 == KeyOry.KEY_0) {
                tvShr2.visibility = View.INVISIBLE
                ivShr2.visibility = View.INVISIBLE
            } else {
                layoutKey.isGone = false
                tvShr2.visibility = View.VISIBLE
                ivShr2.visibility = View.VISIBLE
                ivShr2.setImageResource(keyOrySrc(state.keyShr2))
            }

            if (state.keyLr == KeyOry.KEY_0) {
                tvLr.visibility = View.INVISIBLE
                ivLr.visibility = View.INVISIBLE
            } else {
                layoutKey.isGone = false
                tvLr.visibility = View.VISIBLE
                ivLr.visibility = View.VISIBLE
                ivLr.setImageResource(keyOrySrc(state.keyLr))
            }

            if (state.keyOr == KeyOry.KEY_0) {
                tvOr.visibility = View.INVISIBLE
                ivOr.visibility = View.INVISIBLE
            } else {
                layoutKey.isGone = false
                tvOr.visibility = View.VISIBLE
                ivOr.visibility = View.VISIBLE
                ivOr.setImageResource(keyOrySrc(state.keyOr))
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