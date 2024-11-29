package com.example.svetlogorskchpp.__presentation.electrical_equipment.el_motor.edit.fragment

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
import com.example.svetlogorskchpp.R
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.ElCategory
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.ElGeneralCategory
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.Voltage
import com.example.svetlogorskchpp.__presentation.electrical_equipment.adapter.CustomSpinnerElCategoryAdapter
import com.example.svetlogorskchpp.__presentation.electrical_equipment.adapter.CustomSpinnerElGeneralCategoryAdapter
import com.example.svetlogorskchpp.__presentation.electrical_equipment.el_motor.edit.view_model.ElMotorEditViewModel
import com.example.svetlogorskchpp.__presentation.electrical_equipment.el_motor.factory.ElMotorEditViewModleFactory
import com.example.svetlogorskchpp.__presentation.electrical_equipment.el_motor.model.ElMotorEditUIState
import com.example.svetlogorskchpp.__presentation.electrical_equipment.open_switchgear.edit_vl.adapter.CustomSpinnerVoltageAdapter
import com.example.svetlogorskchpp.databinding.ContentLayoutEditRzaBinding
import com.example.svetlogorskchpp.databinding.FragmentElMotorEditBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ElMotorEditFragment : BaseEditFragment<FragmentElMotorEditBinding>() {

    private val args: ElMotorEditFragmentArgs by navArgs()

    @Inject
    lateinit var viewModelFactory: ElMotorEditViewModleFactory
    private val viewModel: ElMotorEditViewModel by viewModels {
        ElMotorEditViewModel.providesFactory(
            factory = viewModelFactory,
            id = args.id
        )
    }

    private var _includeRzaBinding: ContentLayoutEditRzaBinding? = null
    private val includeRzaBinding get() = _includeRzaBinding!!

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentElMotorEditBinding {
        return FragmentElMotorEditBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _includeRzaBinding = ContentLayoutEditRzaBinding.bind(binding.contentRza.root)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.etUIState.collect {
                    setupUI(it)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.spinnerUIState.collect {
                    binding.apply {
                        spinnerVoltage.setSelection(viewModel.listVoltage.indexOf(it.voltage))
                        spinnerCategory.setSelection(viewModel.listCategory.indexOf(it.category))
                        spinnerGeneralCategory.setSelection(viewModel.listGenCategory.indexOf(it.generalCategory))
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
            chipRep.setOnCheckedChangeListener { _, isChecked ->
                val selectState = saveEditText().copy(isRep = isChecked)
                viewModel.saveState(selectState)
            }

            bAddPowerSupply.setOnClickListener {
                findNavController().navigate(R.id.action_elMotorEditFragment_to_powerSupplySelectionDialog)
            }
            ivDeletePowerSupply.setOnClickListener {
                val state = saveEditText().copy(
                    powerSupplyId = "",
                    powerSupplyName = "",
                    powerSupplyCell = ""
                )
                viewModel.saveState(state)
            }
            bSave.setOnClickListener{
                val selectState = saveEditText()
                viewModel.saveParameterElMotor(selectState)
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

    private fun setupUI(uiState: ElMotorEditUIState) {

        with(uiState) {

            includeRzaBinding.apply {
                etAutomation.setText(automation)
                etAdditionally1.setText(additionallyRza)
                etAdditionally2.isGone = true
            }

            binding.apply {
                etName.setText(name)
                etPowerSupplyCell.setText(powerSupplyCell)
                etSwitch.setText(typeSwitch)
                etInstrumentTransformer.setText(typeInsTr)
                etAdditionally.setText(additionally)
                etTypeRep.setText(typeRep)
                etControlCircuits.setText(controlCircuits)
                etElMotorPower.setText(powerEl)
                etElMotorI.setText(i)
                etElMotorN.setText(n)
                etTypeElMotor.setText(typeEl)
                etTypeMechanism.setText(mechanismType)
                etPerformanceMechanism.setText(mechanismPerformance)
                etPressureMechanism.setText(mechanismPressure)
                etMechanismN.setText(mechanismN)
                etMechanismH.setText(mechanismH)
                etMechanismNPower.setText(mechanismPowerN)
                etAdditionallyMechanism.setText(mechanismAdditionally)

                tvPowerSupplyName.text = powerSupplyName
                chipRep.isChecked = isRep
            }
        }
    }

    private fun saveEditText(): ElMotorEditUIState =
            viewModel.etUIState.value.copy(
                name = binding.etName.text.toString(),
                powerSupplyCell =binding.etPowerSupplyCell.text.toString() ,
                automation =includeRzaBinding.etAutomation.text.toString(),
                additionallyRza =includeRzaBinding.etAdditionally1.text.toString(),
                typeSwitch =binding.etSwitch.text.toString() ,
                typeInsTr =binding.etInstrumentTransformer.text.toString() ,
                additionally =binding.etAdditionally.text.toString() ,
                typeRep =binding.etTypeRep.text.toString() ,
                controlCircuits =binding.etControlCircuits.text.toString() ,
                powerEl =binding.etElMotorPower.text.toString() ,
                i =binding.etElMotorI.text.toString() ,
                n =binding.etElMotorN.text.toString() ,
                typeEl =binding.etTypeElMotor.text.toString() ,
                mechanismType =binding.etTypeMechanism.text.toString() ,
                mechanismPerformance =binding.etPerformanceMechanism.text.toString() ,
                mechanismPressure =binding.etPressureMechanism.text.toString() ,
                mechanismN =binding.etMechanismN.text.toString() ,
                mechanismH =binding.etMechanismH.text.toString() ,
                mechanismPowerN =binding.etMechanismNPower.text.toString() ,
                mechanismAdditionally =binding.etAdditionallyMechanism.text.toString() ,
            )

    private fun setupSpinner() {

        val voltageAdapter = CustomSpinnerVoltageAdapter(requireContext(), viewModel.listVoltage)
        val elCategoryAdapter =
            CustomSpinnerElCategoryAdapter(requireContext(), viewModel.listCategory)
        val elGeneralCategoryAdapter =
            CustomSpinnerElGeneralCategoryAdapter(requireContext(), viewModel.listGenCategory)

        binding.apply {
            spinnerVoltage.adapter = voltageAdapter
            spinnerCategory.adapter = elCategoryAdapter
            spinnerGeneralCategory.adapter = elGeneralCategoryAdapter

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

            spinnerCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long,
                ) {
                    val selectedItem = parent.getItemAtPosition(position) as ElCategory
                    val selectState = viewModel.spinnerUIState.value.copy(category = selectedItem)
                    viewModel.saveSpinnerState(selectState)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }

            spinnerGeneralCategory.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View?,
                        position: Int,
                        id: Long,
                    ) {
                        val selectedItem = parent.getItemAtPosition(position) as ElGeneralCategory
                        val selectState =
                            viewModel.spinnerUIState.value.copy(generalCategory = selectedItem)
                        viewModel.saveSpinnerState(selectState)
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }
                }
        }
    }

    private fun listenerPowerSupply() {
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
    }
}