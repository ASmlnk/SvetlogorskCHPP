package com.example.svetlogorskchpp.__presentation.electrical_equipment.switchgear.edit.fragment

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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.svetlogorskchpp.BaseEditFragment
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.ElAssembly
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.NameDepartment
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.Voltage
import com.example.svetlogorskchpp.__presentation.electrical_equipment.adapter.CustomSpinnerElAssemblyAdapter
import com.example.svetlogorskchpp.__presentation.electrical_equipment.adapter.CustomSpinnerNameDepartment
import com.example.svetlogorskchpp.__presentation.electrical_equipment.open_switchgear.edit_vl.adapter.CustomSpinnerVoltageAdapter
import com.example.svetlogorskchpp.__presentation.electrical_equipment.switchgear.edit.view_model.SwitchgearOwnNeedsEditViewModel
import com.example.svetlogorskchpp.__presentation.electrical_equipment.switchgear.factory.SwitchgearOwnNeedsEditViewModelFactory
import com.example.svetlogorskchpp.__presentation.electrical_equipment.switchgear.model.SwitchgearEditUIState
import com.example.svetlogorskchpp.__presentation.electrical_equipment.switchgear.model.SwitchgearPowerSupply
import com.example.svetlogorskchpp.databinding.ContentLayoutEditRzaBinding
import com.example.svetlogorskchpp.databinding.FragmentSwitchgearEditBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SwitchgearOwnNeedsEditFragment : BaseEditFragment<FragmentSwitchgearEditBinding>() {

    private val args: SwitchgearOwnNeedsEditFragmentArgs by navArgs()

    @Inject
    lateinit var viewModelFactory: SwitchgearOwnNeedsEditViewModelFactory
    private val viewModel: SwitchgearOwnNeedsEditViewModel by viewModels {
        SwitchgearOwnNeedsEditViewModel.providesFactory(
            factory = viewModelFactory,
            id = args.id
        )
    }

    private var _includeRzaBinding: ContentLayoutEditRzaBinding? = null
    private val includeRzaBinding get() = _includeRzaBinding!!

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentSwitchgearEditBinding {
        return FragmentSwitchgearEditBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _includeRzaBinding = ContentLayoutEditRzaBinding.bind(binding.contentRza.root)


        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.switchgearUIState.collect {
                    setupUI(it)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.spinnerUIState.collect {
                    binding.apply {
                        spinnerVoltage.setSelection(viewModel.listVoltage.indexOf(it.voltage))
                        spinnerElAssembly.setSelection(viewModel.listElAssemblys.indexOf(it.category))
                        spinnerNameDepartment.setSelection(viewModel.listNameDepartment.indexOf(it.nameDepartment))
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
            bAddPowerSupply1.setOnClickListener {
                val action =
                    SwitchgearOwnNeedsEditFragmentDirections.actionSwitchgearOwnNeedsEditFragmentToPowerSupplySelectionDialog(
                        SwitchgearPowerSupply.MAIN_1
                    )
                findNavController().navigate(action)
            }
            bAddPowerSupply2.setOnClickListener {
                val action =
                    SwitchgearOwnNeedsEditFragmentDirections.actionSwitchgearOwnNeedsEditFragmentToPowerSupplySelectionDialog(
                        SwitchgearPowerSupply.MAIN_2
                    )
                findNavController().navigate(action)
            }
            bAddReservePowerSupply1.setOnClickListener {
                val action =
                    SwitchgearOwnNeedsEditFragmentDirections.actionSwitchgearOwnNeedsEditFragmentToPowerSupplySelectionDialog(
                        SwitchgearPowerSupply.RESERVE_1
                    )
                findNavController().navigate(action)
            }
            bAddReservePowerSupply2.setOnClickListener {
                val action =
                    SwitchgearOwnNeedsEditFragmentDirections.actionSwitchgearOwnNeedsEditFragmentToPowerSupplySelectionDialog(
                        SwitchgearPowerSupply.RESERVE_2
                    )
                findNavController().navigate(action)
            }
            bAddReservePowerSupply3.setOnClickListener {
                val action =
                    SwitchgearOwnNeedsEditFragmentDirections.actionSwitchgearOwnNeedsEditFragmentToPowerSupplySelectionDialog(
                        SwitchgearPowerSupply.RESERVE_3
                    )
                findNavController().navigate(action)
            }

            ivDeletePowerSupply1.setOnClickListener {
                val state = saveEditText().copy(
                    powerSupplyId1 = "",
                    powerSupplyName1 = "",
                    powerSupplyCell1 = ""
                )
                viewModel.saveState(state)
            }
            ivDeletePowerSupply2.setOnClickListener {
                val state = saveEditText().copy(
                    powerSupplyId2 = "",
                    powerSupplyName2 = "",
                    powerSupplyCell2 = ""
                )
                viewModel.saveState(state)
            }
            ivDeleteReservePowerSupply1.setOnClickListener {
                val state = saveEditText().copy(
                    powerSupplyReserveId1 = "",
                    powerSupplyReserveName1 = "",
                    powerSupplyReserveCell1 = ""
                )
                viewModel.saveState(state)
            }
            ivDeleteReservePowerSupply2.setOnClickListener {
                val state = saveEditText().copy(
                    powerSupplyReserveId2 = "",
                    powerSupplyReserveName2 = "",
                    powerSupplyReserveCell2 = ""
                )
                viewModel.saveState(state)
            }
            ivDeleteReservePowerSupply3.setOnClickListener {
                val state = saveEditText().copy(
                    powerSupplyReserveId3 = "",
                    powerSupplyReserveName3 = "",
                    powerSupplyReserveCell3 = ""
                )
                viewModel.saveState(state)
            }
            bSave.setOnClickListener {
                val selectState = saveEditText()
                viewModel.saveParameterSwitchgear (selectState)
            }
        }

        listenerPowerSupply()
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

    private fun setupUI(uiState: SwitchgearEditUIState) {

        includeRzaBinding.apply {
            etAutomation.setText(uiState.automation)
            etAdditionally1.setText(uiState.additionallyRza)
            etAdditionally2.isGone = true
        }

        binding.apply {
            etName.setText(uiState.name)
            etSwitch.setText(uiState.typeSwitch)
            etInstrumentTransformer.setText(uiState.typeInsTr)
            etInfo.setText(uiState.info)
            etAdditionally.setText(uiState.additionally)

            tvPowerSupplyName1.text = uiState.powerSupplyName1
            etPowerSupplyCell1.setText(uiState.powerSupplyCell1)

            tvPowerSupplyName2.text = uiState.powerSupplyName2
            etPowerSupplyCell2.setText(uiState.powerSupplyCell2)

            val stateViewPowerSupply2 = uiState.powerSupplyId1.isEmpty()
            bAddPowerSupply2.isGone = stateViewPowerSupply2
            ivDeletePowerSupply2.isGone = stateViewPowerSupply2
            tvPowerSupplyName2.isGone = stateViewPowerSupply2
            etPowerSupplyCell2.isGone = stateViewPowerSupply2


            tvReservePowerSupplyName1.text = uiState.powerSupplyReserveName1
            etReservePowerSupplyCell1.setText(uiState.powerSupplyReserveCell1)

            tvReservePowerSupplyName2.text = uiState.powerSupplyReserveName2
            etReservePowerSupplyCell2.setText(uiState.powerSupplyReserveCell2)

            val stateViewReserve2 = uiState.powerSupplyReserveId1.isEmpty()
            bAddReservePowerSupply2.isGone = stateViewReserve2
            ivDeleteReservePowerSupply2.isGone = stateViewReserve2
            tvReservePowerSupplyName2.isGone = stateViewReserve2
            etReservePowerSupplyCell2.isGone = stateViewReserve2


            tvReservePowerSupplyName3.text = uiState.powerSupplyReserveName3
            etReservePowerSupplyCell3.setText(uiState.powerSupplyReserveCell3)


            val stateViewReserve3 =
                uiState.powerSupplyReserveId1.isEmpty() || uiState.powerSupplyReserveId2.isEmpty()
            bAddReservePowerSupply3.isGone = stateViewReserve3
            ivDeleteReservePowerSupply3.isGone = stateViewReserve3
            tvReservePowerSupplyName3.isGone = stateViewReserve3
            etReservePowerSupplyCell3.isGone = stateViewReserve3

        }

    }

    private fun saveEditText(): SwitchgearEditUIState {
        return viewModel.switchgearUIState.value.copy(
            name = binding.etName.text.toString(),
            typeSwitch = binding.etSwitch.text.toString(),
            typeInsTr = binding.etInstrumentTransformer.text.toString(),
            additionally = binding.etAdditionally.text.toString(),
            automation = includeRzaBinding.etAutomation.text.toString(),
            additionallyRza = includeRzaBinding.etAdditionally1.text.toString(),
            info = binding.etInfo.text.toString(),
            powerSupplyCell1 = binding.etPowerSupplyCell1.text.toString(),
            powerSupplyCell2 = binding.etPowerSupplyCell2.text.toString(),
            powerSupplyReserveCell1 = binding.etReservePowerSupplyCell1.text.toString(),
            powerSupplyReserveCell2 = binding.etReservePowerSupplyCell2.text.toString(),
            powerSupplyReserveCell3 = binding.etReservePowerSupplyCell3.text.toString(),
        )
    }

    private fun setupSpinner() {

        val voltageAdapter = CustomSpinnerVoltageAdapter(requireContext(), viewModel.listVoltage)
        val elAssemblyAdapter =
            CustomSpinnerElAssemblyAdapter(requireContext(), viewModel.listElAssemblys)
        val nameDepartmentAdapter =
            CustomSpinnerNameDepartment(requireContext(), viewModel.listNameDepartment)

        binding.apply {
            spinnerVoltage.adapter = voltageAdapter
            spinnerElAssembly.adapter = elAssemblyAdapter
            spinnerNameDepartment.adapter = nameDepartmentAdapter

            spinnerVoltage.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long,
                ) {
                    val selectedItem = parent.getItemAtPosition(position) as Voltage
                    val selectState = viewModel.spinnerUIState.value.copy(voltage = selectedItem)
                    viewModel.saveSpinnerState(selectState)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }

            spinnerElAssembly.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long,
                ) {
                    val selectedItem = parent.getItemAtPosition(position) as ElAssembly
                    val selectState = viewModel.spinnerUIState.value.copy(category = selectedItem)
                    viewModel.saveSpinnerState(selectState)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }

            spinnerNameDepartment.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View?,
                        position: Int,
                        id: Long,
                    ) {
                        val selectedItem = parent.getItemAtPosition(position) as NameDepartment
                        val selectState =
                            viewModel.spinnerUIState.value.copy(nameDepartment = selectedItem)
                        viewModel.saveSpinnerState(selectState)
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }
                }
        }
    }

    private fun listenerPowerSupply() {
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Pair<String, String>>(
            SwitchgearPowerSupply.MAIN_1.name
        )?.observe(viewLifecycleOwner) { selectedData ->
            val (id, name) = selectedData
            val selectState = saveEditText().copy(
                powerSupplyId1 = id,
                powerSupplyName1 = name
            )
            viewModel.saveState(selectState)
        }

        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Pair<String, String>>(
            SwitchgearPowerSupply.MAIN_2.name
        )?.observe(viewLifecycleOwner) { selectedData ->
            val (id, name) = selectedData
            val selectState = saveEditText().copy(
                powerSupplyId2 = id,
                powerSupplyName2 = name
            )
            viewModel.saveState(selectState)
        }

        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Pair<String, String>>(
            SwitchgearPowerSupply.RESERVE_1.name
        )?.observe(viewLifecycleOwner) { selectedData ->
            val (id, name) = selectedData
            val selectState = saveEditText().copy(
                powerSupplyReserveId1 = id,
                powerSupplyReserveName1 = name
            )
            viewModel.saveState(selectState)
        }

        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Pair<String, String>>(
            SwitchgearPowerSupply.RESERVE_2.name
        )?.observe(viewLifecycleOwner) { selectedData ->
            val (id, name) = selectedData
            val selectState = saveEditText().copy(
                powerSupplyReserveId2 = id,
                powerSupplyReserveName2 = name
            )
            viewModel.saveState(selectState)
        }

        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Pair<String, String>>(
            SwitchgearPowerSupply.RESERVE_3.name
        )?.observe(viewLifecycleOwner) { selectedData ->
            val (id, name) = selectedData
            val selectState = saveEditText().copy(
                powerSupplyReserveId3 = id,
                powerSupplyReserveName3 = name
            )
            viewModel.saveState(selectState)
        }

    }

}