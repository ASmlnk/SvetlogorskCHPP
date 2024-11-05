package com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.open_switchgear_tr.fragment

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
import com.example.svetlogorskchpp.__presentation.dialog.BaseBottomSheetDialog
import com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.factory.OpenSwitchgearTrViewModelFactory
import com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.open_switchgear_tr.view_model.OpenSwitchgearTrViewModel
import com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.open_switchgear_vl.model.OpSwiTrDialogUIState
import com.example.svetlogorskchpp.databinding.ContentLayoutOryParameterDialogBinding
import com.example.svetlogorskchpp.databinding.ContentLayoutOryParameterTrDialogBinding
import com.example.svetlogorskchpp.databinding.ContentLayoutRzaDialogBinding
import com.example.svetlogorskchpp.databinding.DialogOpenSwitchgearTrBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class OpenSwitchgearTrDialog : BaseBottomSheetDialog<DialogOpenSwitchgearTrBinding>() {

    private val args: OpenSwitchgearTrDialogArgs by navArgs()

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
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { stateUI ->
                    setupUI(stateUI)
                }
            }
        }

        binding.apply {
            _includeOryParameterVnBinding =
                ContentLayoutOryParameterTrDialogBinding.bind(contentOryVnParameter.root)
            _includeOryParameterSnBinding =
                ContentLayoutOryParameterTrDialogBinding.bind(contentOrySnParameter.root)
            _includeOryRzaBinding = ContentLayoutRzaDialogBinding.bind(contentRza.root)

            ivEditContent.setOnClickListener {
                val action =
                    OpenSwitchgearTrDialogDirections.Companion.actionOpenSwitchgearTrDialogToOpenSwitchgearTrEditFragment(
                        args.id
                    )
                findNavController().navigate(action)
            }
        }
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

    }
}