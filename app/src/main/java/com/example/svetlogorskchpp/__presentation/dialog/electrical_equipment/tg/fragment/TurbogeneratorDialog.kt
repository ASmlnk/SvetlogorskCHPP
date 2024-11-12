package com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.tg.fragment

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
import com.example.svetlogorskchpp.R
import com.example.svetlogorskchpp.__presentation.dialog.BaseBottomSheetDialog
import com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.adapter.PowerSupplySelectionAdapter
import com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.factory.EquipmentTsnViewModelFactory
import com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.factory.TurbogeneratorViewModelFactory
import com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.tg.model.TgUIState
import com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.tg.view_model.TurbogeneratorViewModel
import com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.tsn.fragment.EquipmentTsnDialogDirections
import com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.tsn.view_model.EquipmentTsnViewModel
import com.example.svetlogorskchpp.databinding.ContentLayoutRzaDialogBinding
import com.example.svetlogorskchpp.databinding.ContentLayoutRzaEquipmentDialogBinding
import com.example.svetlogorskchpp.databinding.DialogEquipmentTgBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.getValue

@AndroidEntryPoint
class TurbogeneratorDialog : BaseBottomSheetDialog<DialogEquipmentTgBinding>() {

    val args: TurbogeneratorDialogArgs by navArgs()

    @Inject
    lateinit var viewModelFactory: TurbogeneratorViewModelFactory
    private val viewModel: TurbogeneratorViewModel by viewModels {
        TurbogeneratorViewModel.providesFactory(
            factory = viewModelFactory,
            id = args.id
        )
    }

    private val adapter = PowerSupplySelectionAdapter { id, name, dl ->
        val deepLink = Uri.parse(dl.link + id)
        findNavController().navigate(deepLink)
    }

    private var _includeRzaBinding: ContentLayoutRzaEquipmentDialogBinding? = null
    private val includeRzaBinding get() = _includeRzaBinding!!

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): DialogEquipmentTgBinding {
        return DialogEquipmentTgBinding.bind(
            inflater.inflate(R.layout.dialog_equipment_tg, container)
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _includeRzaBinding = ContentLayoutRzaEquipmentDialogBinding.bind(binding.contentRza.root)

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

        binding.apply {
            rvPowerSupply.adapter = adapter
            ivEditContent.setOnClickListener {
                val action =
                    TurbogeneratorDialogDirections.actionTurbogeneratorDialogToTurbogeneratorEditFragment(
                        args.id
                    )
                findNavController().navigate(action)
            }
            tvGeneratorStartTitle.setOnClickListener {
                viewModel.onClickGeneratorStarted()
            }
            tvTranslationFromRvTitle.setOnClickListener {
                viewModel.onClickTranslationFromRv()
            }
            tvTranslationIntoRvTitle.setOnClickListener {
                viewModel.onClickTranslationIntoRv()
            }
        }
    }

    private fun setupUI(state: TgUIState) {

        binding.apply {
            tvName.apply {
                text = state.name
            }
            tvTypeGenerator.apply {
                text = state.typeGenerator
            }
            tvTypeTurbin.apply {
                text = state.typeTurbin
            }
            tvTypeParameter.apply {
                text = resources.getString(
                    R.string.type_parameter_tg,
                    state.powerEl,
                    state.powerThermal,
                    state.steamConsumption
                )
            }
            tvSwitchContent.apply {
                isGone = state.typeSwitch.isEmpty()
                text = state.typeSwitch
            }
            tvInstrContent.apply {
                isGone = state.typeInsTr.isEmpty()
                text = state.typeInsTr
            }
            tvTranscriptTypeGeneratorContent.apply {
                isGone = state.transcriptTypeGenerator.isEmpty()
                text = state.transcriptTypeGenerator
            }
            tvSourceExcitationContent.apply {
                isGone = state.sourceExcitation.isEmpty()
                text = state.sourceExcitation
            }
            tvTranscriptTypeTurbinContent.apply {
                isGone = state.transcriptTypeTurbin.isEmpty()
                text = state.transcriptTypeTurbin
            }
            tvPanelContent.apply {
                isGone = state.panelMcp.isEmpty()
                text = state.panelMcp
            }
            tvGeneratorStartContent.apply {
                isGone = !state.isViewGeneratorStarted
                text = state.generatorStarted
            }
            tvTranslationFromRvContent.apply {
                isGone = !state.isViewTranslationFromRv
                text = state.translationFromRv
            }
            tvTranslationIntoRvContent.apply {
                isGone = !state.isViewTranslationIntoRv
                text = state.translationIntoRv
            }
            tvAdditionallyGenerator.apply {
                isGone = state.additionallyGenerator.isEmpty()
                text = state.additionallyGenerator
            }
            tvAdditionallyTurbin.apply {
                //isGone = state.additionallyTurbin.isEmpty()
                text = state.additionallyTurbin
            }


        }

        includeRzaBinding.apply {
            tvAutomationContent.text = state.automation
            tvEarthProtectionContent.text = state.earthProtection
            tvPhaseProtectionContent.text = state.phaseProtection
            tvAdditionally1.apply {
                text = state.additionallyRza1
                isGone = state.additionallyRza1.isEmpty()
            }
            tvAdditionally2.apply {
                text = state.additionallyRza2
                isGone = state.additionallyRza2.isEmpty()
            }
            layoutAdditionally1.isGone = state.additionallyRza1.isEmpty()
            layoutAdditionally2.isGone = state.additionallyRza2.isEmpty()

        }
    }
}