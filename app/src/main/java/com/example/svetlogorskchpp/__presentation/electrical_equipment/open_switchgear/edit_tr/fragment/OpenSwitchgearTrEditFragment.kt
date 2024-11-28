package com.example.svetlogorskchpp.__presentation.electrical_equipment.open_switchgear.edit_tr.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.view.isGone
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.example.svetlogorskchpp.BaseEditFragment
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.KeyOry
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.Voltage
import com.example.svetlogorskchpp.__presentation.electrical_equipment.open_switchgear.edit_tr.view_model.OpenSwitchgearTrEditViewModel
import com.example.svetlogorskchpp.__presentation.electrical_equipment.open_switchgear.edit_vl.adapter.CustomSpinnerAdapter
import com.example.svetlogorskchpp.__presentation.electrical_equipment.open_switchgear.edit_vl.adapter.CustomSpinnerVoltageAdapter
import com.example.svetlogorskchpp.__presentation.electrical_equipment.factory.OpenSwitchgearTrEditViewModelFactory
import com.example.svetlogorskchpp.__presentation.electrical_equipment.open_switchgear.model.OpSwTrEditUIState
import com.example.svetlogorskchpp.__presentation.electrical_equipment.open_switchgear.model.SpinnerOryParameter
import com.example.svetlogorskchpp.__presentation.electrical_equipment.open_switchgear.model.VoltageSide
import com.example.svetlogorskchpp.databinding.ContentLayoutEditOryNameBinding
import com.example.svetlogorskchpp.databinding.ContentLayoutEditOryParameterBinding
import com.example.svetlogorskchpp.databinding.ContentLayoutEditOryRzaBinding
import com.example.svetlogorskchpp.databinding.ContentLayoutEditTypeTrBinding
import com.example.svetlogorskchpp.databinding.FragmentOpenSwitchgearTrEditBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.getValue

@AndroidEntryPoint
class OpenSwitchgearTrEditFragment : BaseEditFragment<FragmentOpenSwitchgearTrEditBinding>() {

    private val args: OpenSwitchgearTrEditFragmentArgs by navArgs()

    private val listKeys =
        listOf(KeyOry.KEY_0, KeyOry.KEY_1, KeyOry.KEY_2, KeyOry.KEY_3, KeyOry.KEY_4)
    private val listVoltage =
        listOf(Voltage.KV, Voltage.KV_220, Voltage.KV_110, Voltage.KV_35)

    @Inject
    lateinit var viewModelFactory: OpenSwitchgearTrEditViewModelFactory
    private val viewModel: OpenSwitchgearTrEditViewModel by viewModels {
        OpenSwitchgearTrEditViewModel.providesFactory(
            openSwitchgearTrEditViewModelFactory = viewModelFactory,
            idTr = args.id
        )
    }

    private var _includeOryVnParameterBinding: ContentLayoutEditOryParameterBinding? = null
    private val includeOryVnParameterBinding get() = _includeOryVnParameterBinding!!

    private var _includeOrySnParameterBinding: ContentLayoutEditOryParameterBinding? = null
    private val includeOrySnParameterBinding get() = _includeOrySnParameterBinding!!

    private var _includeOryNameBinding: ContentLayoutEditOryNameBinding? = null
    private val includeOryNameBinding get() = _includeOryNameBinding!!

    private var _includeOryRzaBinding: ContentLayoutEditOryRzaBinding? = null
    private val includeOryRzaBinding get() = _includeOryRzaBinding!!

    private var _includeOryTypeTr: ContentLayoutEditTypeTrBinding? = null
    private val includeOryTypeTr get() = _includeOryTypeTr!!

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentOpenSwitchgearTrEditBinding {
        return FragmentOpenSwitchgearTrEditBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _includeOryTypeTr = ContentLayoutEditTypeTrBinding.bind(binding.contentTypeTr.root)
        _includeOryNameBinding = ContentLayoutEditOryNameBinding.bind(binding.contentOryName.root)
        _includeOryVnParameterBinding =
            ContentLayoutEditOryParameterBinding.bind(binding.contentOryParameterVn.root)
        _includeOrySnParameterBinding =
            ContentLayoutEditOryParameterBinding.bind(binding.contentOryParameterSrn.root)
        _includeOryRzaBinding = ContentLayoutEditOryRzaBinding.bind(binding.contentRza.root)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.opSwTrEditUIState.collect { uiState ->
                    setupUI(uiState)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.spinnerSnUIState.collect { spinnerSnState ->
                    includeOrySnParameterBinding.apply {
                        spinner1shr.setSelection(listKeys.indexOf(spinnerSnState.keyShr1))
                        spinner2shr.setSelection(listKeys.indexOf(spinnerSnState.keyShr2))
                        spinnerLr.setSelection(listKeys.indexOf(spinnerSnState.keyLr))
                        spinnerOr.setSelection(listKeys.indexOf(spinnerSnState.keyOr))
                        spinnerVoltage.setSelection(listVoltage.indexOf(spinnerSnState.voltage))
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.spinnerVnUIState.collect { spinnerVnState ->
                    includeOryVnParameterBinding.apply {
                        spinner1shr.setSelection(listKeys.indexOf(spinnerVnState.keyShr1))
                        spinner2shr.setSelection(listKeys.indexOf(spinnerVnState.keyShr2))
                        spinnerLr.setSelection(listKeys.indexOf(spinnerVnState.keyLr))
                        spinnerOr.setSelection(listKeys.indexOf(spinnerVnState.keyOr))
                        spinnerVoltage.setSelection(listVoltage.indexOf(spinnerVnState.voltage))
                    }
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
                chipSpareSelected(isChecked)
            }
            chipThreeWinding.setOnCheckedChangeListener { _, isChecked ->
                chipThreeWinding(isChecked)
            }
            bSave.setOnClickListener {
                saveParameterTr()
            }

            bAddTsn
            bAddTg
        }

        setupSpinner(includeOryVnParameterBinding, VoltageSide.VN)
        setupSpinner(includeOrySnParameterBinding, VoltageSide.SN)
        setupProtectionView(
            binding = includeOryRzaBinding,
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

    private fun saveParameterTr() {
        val textState = saveEditText()
        viewModel.saveParameterTr(textState)
    }

    private fun chipSpareSelected(isSpare: Boolean) {
        val selectState = saveEditText().copy(isSpare = isSpare)
        viewModel.chipSelected(selectState)
    }

    private fun chipThreeWinding(isThreeWinding: Boolean) {

        includeOrySnParameterBinding.apply {
            if (!isThreeWinding) {
                etTireNumber.setText("")
                etCellNumber.setText("")
                etSwitch.setText("")
                etInstrumentTransformer.setText("")
                val selectState = saveEditText().copy(isThreeWinding = isThreeWinding)
                val spinnerState = viewModel.spinnerSnUIState.value.copy(
                    keyShr1 = KeyOry.KEY_0,
                    keyShr2 = KeyOry.KEY_0,
                    keyLr = KeyOry.KEY_0,
                    keyOr = KeyOry.KEY_0,
                    voltage = Voltage.KV
                )
                viewModel.chipSelected(selectState, spinnerState)
            } else {
                val selectState = saveEditText().copy(isThreeWinding = isThreeWinding)
                viewModel.chipSelected(selectState)
            }
        }

    }

    private fun saveEditText(): OpSwTrEditUIState {
        return viewModel.opSwTrEditUIState.value.copy(
            name = includeOryNameBinding.etName.text.toString(),
            panelMcp = includeOryNameBinding.panelMcp.text.toString(),
            type = includeOryTypeTr.etTypeTr.text.toString(),
            parameterType = includeOryTypeTr.etParameterTypeTr.text.toString(),
            transcriptType = includeOryTypeTr.etTranscriptType.text.toString(),
            additionally = includeOryTypeTr.etAdditionally.text.toString(),
            bysSystemVn = includeOryVnParameterBinding.etTireNumber.text.toString(),
            cellVn = includeOryVnParameterBinding.etCellNumber.text.toString(),
            typeSwitchVn = includeOryVnParameterBinding.etSwitch.text.toString(),
            typeInsTrVn = includeOryVnParameterBinding.etInstrumentTransformer.text.toString(),
            bysSystemSn = includeOrySnParameterBinding.etTireNumber.text.toString(),
            cellSn = includeOrySnParameterBinding.etCellNumber.text.toString(),
            typeSwitchSn = includeOrySnParameterBinding.etSwitch.text.toString(),
            typeInsTrSn = includeOrySnParameterBinding.etInstrumentTransformer.text.toString(),
            automation = includeOryRzaBinding.etAutomation.text.toString(),
            apv = includeOryRzaBinding.etApv.text.toString()
        )
    }

    private fun setupUI(uiState: OpSwTrEditUIState) {
        includeOryRzaBinding.apply {
            if (uiState.automation.isNotEmpty()) etAutomation.setText(uiState.automation)
            if (uiState.apv.isNotEmpty()) etApv.setText(uiState.apv)
        }

        includeOryTypeTr.apply {
            if (uiState.type.isNotEmpty()) etTypeTr.setText(uiState.type)
            if (uiState.parameterType.isNotEmpty()) etParameterTypeTr.setText(uiState.parameterType)
            if (uiState.transcriptType.isNotEmpty()) etTranscriptType.setText(uiState.transcriptType)
            if (uiState.additionally.isNotEmpty()) etAdditionally.setText(uiState.additionally)
        }

        includeOryNameBinding.apply {
            if (uiState.name.isNotEmpty()) etName.setText(uiState.name)
            if (uiState.panelMcp.isNotEmpty()) panelMcp.setText(uiState.panelMcp)
        }

        includeOryVnParameterBinding.apply {
            if (uiState.bysSystemVn.isNotEmpty()) etTireNumber.setText(uiState.bysSystemVn)
            if (uiState.cellVn.isNotEmpty()) etCellNumber.setText(uiState.cellVn)
            if (uiState.typeSwitchVn.isNotEmpty()) etSwitch.setText(uiState.typeSwitchVn)
            if (uiState.typeInsTrVn.isNotEmpty()) etInstrumentTransformer.setText(
                uiState.typeInsTrVn
            )
        }

        includeOrySnParameterBinding.apply {
            if (uiState.bysSystemSn.isNotEmpty()) etTireNumber.setText(uiState.bysSystemSn)
            if (uiState.cellSn.isNotEmpty()) etCellNumber.setText(uiState.cellSn)
            if (uiState.typeSwitchSn.isNotEmpty()) etSwitch.setText(uiState.typeSwitchSn)
            if (uiState.typeInsTrSn.isNotEmpty()) etInstrumentTransformer.setText(
                uiState.typeInsTrSn
            )
        }

        if (uiState.isThreeWinding) {
            includeOrySnParameterBinding.root.isGone = false
            binding.tvSn.isGone = false
        } else {
            binding.tvSn.isGone = true
            includeOrySnParameterBinding.apply {
                root.isGone = true
            }
        }

        binding.apply {
            chipSpare.isChecked = uiState.isSpare
            chipThreeWinding.isChecked = uiState.isThreeWinding
        }
    }

    private fun setupSpinner(
        binding: ContentLayoutEditOryParameterBinding,
        voltageSide: VoltageSide,
    ) {

        val adapterSpinner = CustomSpinnerAdapter(requireContext(), listKeys)
        val voltageAdapter = CustomSpinnerVoltageAdapter(requireContext(), listVoltage)

        binding.apply {
            spinner1shr.adapter = adapterSpinner
            spinner2shr.adapter = adapterSpinner
            spinnerLr.adapter = adapterSpinner
            spinnerOr.adapter = adapterSpinner
            spinnerVoltage.adapter = voltageAdapter

            spinner1shr.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long,
                ) {
                    val selectedItem = parent.getItemAtPosition(position) as KeyOry
                    viewModel.spinnerSaveState(
                        spinnerOryParameter = SpinnerOryParameter.SH_R_1,
                        selectSpinner = selectedItem,
                        voltageSide = voltageSide
                    )
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }

            spinner2shr.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long,
                ) {
                    val selectedItem = parent.getItemAtPosition(position) as KeyOry
                    viewModel.spinnerSaveState(
                        spinnerOryParameter = SpinnerOryParameter.SH_R_2,
                        selectSpinner = selectedItem,
                        voltageSide = voltageSide
                    )
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
            spinnerLr.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long,
                ) {
                    val selectedItem = parent.getItemAtPosition(position) as KeyOry
                    viewModel.spinnerSaveState(
                        spinnerOryParameter = SpinnerOryParameter.LR,
                        selectSpinner = selectedItem,
                        voltageSide = voltageSide
                    )
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
            spinnerOr.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long,
                ) {
                    val selectedItem = parent.getItemAtPosition(position) as KeyOry
                    viewModel.spinnerSaveState(
                        spinnerOryParameter = SpinnerOryParameter.OR,
                        selectSpinner = selectedItem,
                        voltageSide = voltageSide
                    )
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
            spinnerVoltage.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long,
                ) {
                    val selectedItem = parent.getItemAtPosition(position) as Voltage
                    viewModel.spinnerSaveState(
                        spinnerOryParameter = SpinnerOryParameter.VOLTAGE,
                        selectSpinner = selectedItem,
                        voltageSide = voltageSide
                    )
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
        }
    }
}
