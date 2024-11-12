package com.example.svetlogorskchpp.__presentation.electrical_equipment.tg.edit.fragment

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
import com.example.svetlogorskchpp.BaseEditFragment
import com.example.svetlogorskchpp.R
import com.example.svetlogorskchpp.__presentation.electrical_equipment.factory.TurboGeneratorEditViewModelFactory
import com.example.svetlogorskchpp.__presentation.electrical_equipment.tg.edit.model.TurbogeneratorUIState
import com.example.svetlogorskchpp.__presentation.electrical_equipment.tg.edit.view_model.TurboGeneratorEditViewModel
import com.example.svetlogorskchpp.databinding.ContentLayoutEditOryNameBinding
import com.example.svetlogorskchpp.databinding.ContentLayoutEditRzaBinding
import com.example.svetlogorskchpp.databinding.ContentLayoutEditTypeTgBinding
import com.example.svetlogorskchpp.databinding.ContentLayoutEditTypeTurbinBinding
import com.example.svetlogorskchpp.databinding.FragmentTgEditBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.getValue

@AndroidEntryPoint
class TurbogeneratorEditFragment : BaseEditFragment<FragmentTgEditBinding>() {

    private val args: TurbogeneratorEditFragmentArgs by navArgs()

    @Inject
    lateinit var viewModelFactory: TurboGeneratorEditViewModelFactory
    private val viewModel: TurboGeneratorEditViewModel by viewModels {
        TurboGeneratorEditViewModel.providesFactory(
            factory = viewModelFactory,
            id = args.id
        )
    }

    private var _includeNameBinding: ContentLayoutEditOryNameBinding? = null
    private val includeNameBinding get() = _includeNameBinding!!

    private var _includeRzaBinding: ContentLayoutEditRzaBinding? = null
    private val includeRzaBinding get() = _includeRzaBinding!!

    private var _includeTypeTgBinding: ContentLayoutEditTypeTgBinding? = null
    private val includeTypeTgBinding get() = _includeTypeTgBinding!!

    private var _includeTypeTurbinBinding: ContentLayoutEditTypeTurbinBinding? = null
    private val includeTypeTurbinBinding get() = _includeTypeTurbinBinding!!

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentTgEditBinding {
        return FragmentTgEditBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _includeNameBinding = ContentLayoutEditOryNameBinding.bind(binding.contentName.root)
        _includeRzaBinding = ContentLayoutEditRzaBinding.bind(binding.contentRza.root)
        _includeTypeTgBinding = ContentLayoutEditTypeTgBinding.bind(binding.contentTypeTg.root)
        _includeTypeTurbinBinding =
            ContentLayoutEditTypeTurbinBinding.bind(binding.contentTypeTurbin.root)

        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Pair<String, String>>(
            "selectedData"
        )?.observe(viewLifecycleOwner) { selectedData ->
                val (id, name) = selectedData
                val selectState = saveEditText().copy(
                    powerSupplyId = id,
                    powerSupplyName = name
                )
                viewModel.saveState(selectState)
            }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.turbogeneratorUIState.collect { state ->
                    setupUI(state)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.protectionUIState.collect { protectionState ->
                    phaseProtectionAdapter.submitList(protectionState.phaseProtection)
                    earthProtectionAdapter.submitList(protectionState.earthProtection)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.toastResultFlow.collect { toast ->
                    showCustomSnackbar(binding.root, toast)
                }
            }
        }

        binding.apply {
            bAddPowerSupply.setOnClickListener {
                findNavController().navigate(R.id.action_turbogeneratorEditFragment_to_powerSupplySelectionDialog)
            }
            bSave.setOnClickListener{
                val selectState = saveEditText()
                viewModel.saveParameterTg (selectState)
            }
        }
        setupProtectionView(
            binding = includeRzaBinding,
            onClickPhaseProtection = viewModel::deletePhaseProtection,
            onClickEarthProtection = viewModel::deleteEarthProtection,
            addPhaseProtection = viewModel::addPhaseProtection,
            addEarthProtection = viewModel::addEarthProtection
        )
    }

    private fun setupUI(uiState: TurbogeneratorUIState) {

        includeRzaBinding.apply {
            etAutomation.setText(uiState.automation)
            etAdditionally1.setText(uiState.additionallyRza1)
            etAdditionally2.setText(uiState.additionallyRza2)
        }

        includeTypeTgBinding.apply {
            etAdditionally.setText(uiState.additionallyGenerator)
            etVolumeTg.setText(uiState.volumeTg)
            etTypeTg.setText(uiState.typeGenerator)
            etVolumeReceiver.setText(uiState.volumeReceiver)
            etTranscriptType.setText(uiState.transcriptTypeGenerator)
            etSourceExcitation.setText(uiState.sourceExcitation)
            etTranslationFromRv.setText(uiState.translationFromRv)
            etTranslationIntoRv.setText(uiState.translationIntoRv)
            etGeneratorStart.setText(uiState.generatorStarted)
        }

        includeTypeTurbinBinding.apply {
            etAdditionally.setText(uiState.additionallyTurbin)
            etTranscriptType.setText(uiState.transcriptTypeTurbin)
            etTypeTurbin.setText(uiState.typeTurbin)
            etPowerEl.setText(uiState.powerEl)
            etPowerThermal.setText(uiState.powerThermal)
            etSteamConsumption.setText(uiState.steamConsumption)
        }

        includeNameBinding.apply {
            etName.setText(uiState.name)
            panelMcp.setText(uiState.panelMcp)
        }


        binding.apply {
            etInstrumentTransformer.setText(uiState.typeInsTr)
            etSwitch.setText(uiState.typeSwitch)
            etPowerSupplyNameCell.setText(uiState.powerSupplyCell)
            tvPowerSupplyName.text = uiState.powerSupplyName
        }
    }

    private fun saveEditText(): TurbogeneratorUIState {
        return viewModel.turbogeneratorUIState.value.copy(
            name = includeNameBinding.etName.text.toString(),
            panelMcp = includeNameBinding.panelMcp.text.toString(),
            typeSwitch = binding.etSwitch.text.toString(),
            typeInsTr = binding.etInstrumentTransformer.text.toString(),
            typeGenerator = includeTypeTgBinding.etTypeTg.text.toString() ,
            transcriptTypeGenerator = includeTypeTgBinding.etTranscriptType.text.toString(),
            volumeTg = includeTypeTgBinding.etVolumeTg.text.toString(),
            volumeReceiver = includeTypeTgBinding.etVolumeReceiver.text.toString(),
            additionallyGenerator = includeTypeTgBinding.etAdditionally.text.toString(),
            sourceExcitation = includeTypeTgBinding.etSourceExcitation.text.toString(),
            generatorStarted = includeTypeTgBinding.etGeneratorStart.text.toString(),
            translationIntoRv = includeTypeTgBinding.etTranslationIntoRv.text.toString(),
            translationFromRv = includeTypeTgBinding.etTranslationFromRv.text.toString(),
            typeTurbin = includeTypeTurbinBinding.etTypeTurbin.text.toString(),
            transcriptTypeTurbin = includeTypeTurbinBinding.etTranscriptType.text.toString(),
            powerEl = includeTypeTurbinBinding.etPowerEl.text.toString(),
            powerThermal = includeTypeTurbinBinding.etPowerThermal.text.toString(),
            steamConsumption = includeTypeTurbinBinding.etSteamConsumption.text.toString(),
            additionallyTurbin = includeTypeTurbinBinding.etAdditionally.text.toString(),
            powerSupplyCell = binding.etPowerSupplyNameCell.text.toString(),
            automation = includeRzaBinding.etAutomation.text.toString(),
            additionallyRza1 = includeRzaBinding.etAdditionally1.text.toString(),
            additionallyRza2 = includeRzaBinding.etAdditionally2.text.toString(),
        )
    }
}