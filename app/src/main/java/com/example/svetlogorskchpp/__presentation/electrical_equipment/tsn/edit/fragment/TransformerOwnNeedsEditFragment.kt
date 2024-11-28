package com.example.svetlogorskchpp.__presentation.electrical_equipment.tsn.edit.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.svetlogorskchpp.BaseEditFragment
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.Voltage
import com.example.svetlogorskchpp.__presentation.electrical_equipment.factory.TransformerOwnNeedsEditViewModelFactory
import com.example.svetlogorskchpp.__presentation.electrical_equipment.open_switchgear.edit_vl.adapter.CustomSpinnerVoltageAdapter
import com.example.svetlogorskchpp.__presentation.electrical_equipment.tsn.edit.model.TransformerOwnNeedsUIState
import com.example.svetlogorskchpp.__presentation.electrical_equipment.tsn.edit.view_model.TransformerOwnNeedsEditViewModel
import com.example.svetlogorskchpp.databinding.ContentLayoutEditOryNameBinding
import com.example.svetlogorskchpp.databinding.ContentLayoutEditOryRzaBinding
import com.example.svetlogorskchpp.databinding.ContentLayoutEditTypeTrBinding
import com.example.svetlogorskchpp.databinding.FragmentTsnEditBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import com.example.svetlogorskchpp.R
import javax.inject.Inject
import kotlin.getValue

@AndroidEntryPoint
class TransformerOwnNeedsEditFragment : BaseEditFragment<FragmentTsnEditBinding>() {

    private val args: TransformerOwnNeedsEditFragmentArgs by navArgs()

    private val listVoltage =
        listOf(Voltage.KV, Voltage.KV10_KV3, Voltage.KV10_KV6, Voltage.KV6_KV04, Voltage.KV3_KV04)

    @Inject
    lateinit var viewModelFactory: TransformerOwnNeedsEditViewModelFactory
    private val viewModel: TransformerOwnNeedsEditViewModel by viewModels {
        TransformerOwnNeedsEditViewModel.providesFactory(
            factory = viewModelFactory,
            id = args.id
        )
    }

    private var _includeNameBinding: ContentLayoutEditOryNameBinding? = null
    private val includeNameBinding get() = _includeNameBinding!!

    private var _includeRzaBinding: ContentLayoutEditOryRzaBinding? = null
    private val includeRzaBinding get() = _includeRzaBinding!!

    private var _includeTypeTr: ContentLayoutEditTypeTrBinding? = null
    private val includeTypeTr get() = _includeTypeTr!!


    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentTsnEditBinding {
        return FragmentTsnEditBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _includeNameBinding = ContentLayoutEditOryNameBinding.bind(binding.contentName.root)
        _includeRzaBinding = ContentLayoutEditOryRzaBinding.bind(binding.contentRza.root)
        _includeTypeTr = ContentLayoutEditTypeTrBinding.bind(binding.contentTypeTr.root)

        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Pair<String, String>>("selectedData")
            ?.observe(viewLifecycleOwner) { selectedData ->
                val (id, name) = selectedData
                val selectState = saveEditText().copy(
                    powerSupplyId = id,
                    powerSupplyName = name
                )
                viewModel.saveState(selectState)
            }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.transformerOwnNeedsUIState.collect { state ->
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
            chipSpare.setOnCheckedChangeListener { _, isChecked ->
                val selectState = saveEditText().copy(isSpare = isChecked)
                viewModel.saveState(selectState)
            }
            bAddPowerSupply.setOnClickListener {
                findNavController().navigate(R.id.action_transformerOwnNeedsEditFragment_to_powerSupplySelectionDialog)
            }
            bSave.setOnClickListener{
                val selectState = saveEditText()
                viewModel.saveParameterTsn(selectState)
            }
        }
        setupSpinner()
        setupProtectionView(
            binding = includeRzaBinding,
            onClickPhaseProtection = viewModel::deletePhaseProtection,
            onClickEarthProtection = viewModel::deleteEarthProtection,
            addPhaseProtection = viewModel::addPhaseProtection,
            addEarthProtection = viewModel::addEarthProtection
        )
    }

    override fun onStop() {
        super.onStop()
        val state = saveEditText()
        viewModel.saveState(state)
    }

    private fun setupUI(uiState: TransformerOwnNeedsUIState) {

        includeRzaBinding.apply {
            etAutomation.setText(uiState.automation)
            etApv.setText(uiState.apv)
        }

        includeTypeTr.apply {
            etTypeTr.setText(uiState.type)
            etParameterTypeTr.setText(uiState.parameterType)
            etTranscriptType.setText(uiState.transcriptType)
            etAdditionally.setText(uiState.additionally)
        }

        includeNameBinding.apply {
            etName.setText(uiState.name)
            panelMcp.setText(uiState.panelMcp)
        }


        binding.apply {
            spinnerVoltage.setSelection(listVoltage.indexOf(uiState.voltage))
            etInstrumentTransformer.setText(uiState.typeInsTr)
            etSwitch.setText(uiState.typeSwitch)
            etPowerSupplyNameCell.setText(uiState.powerSupplyCell)
            tvPowerSupplyName.text = uiState.powerSupplyName
            chipSpare.isChecked = uiState.isSpare
        }
    }

    private fun saveEditText(): TransformerOwnNeedsUIState {
        return viewModel.transformerOwnNeedsUIState.value.copy(
            name = includeNameBinding.etName.text.toString(),
            panelMcp =includeNameBinding.panelMcp.text.toString(),
            type = includeTypeTr.etTypeTr.text.toString(),
            parameterType = includeTypeTr.etParameterTypeTr.text.toString(),
            transcriptType = includeTypeTr.etTranscriptType.text.toString(),
            additionally = includeTypeTr.etAdditionally.text.toString(),
            powerSupplyCell = binding.etPowerSupplyNameCell.text.toString() ,
            typeSwitch = binding.etSwitch.text.toString(),
            typeInsTr = binding.etInstrumentTransformer.text.toString(),
            automation = includeRzaBinding.etAutomation.text.toString(),
            apv = includeRzaBinding.etApv.text.toString(),
        )
    }

    private fun setupSpinner() {

        val voltageAdapter = CustomSpinnerVoltageAdapter(requireContext(), listVoltage)

        binding.apply {
            spinnerVoltage.adapter = voltageAdapter

            spinnerVoltage.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long,
                ) {
                    val selectedItem = parent.getItemAtPosition(position) as Voltage
                    val selectState = saveEditText().copy(voltage = selectedItem)
                    viewModel.saveState(selectState)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
        }
    }
}