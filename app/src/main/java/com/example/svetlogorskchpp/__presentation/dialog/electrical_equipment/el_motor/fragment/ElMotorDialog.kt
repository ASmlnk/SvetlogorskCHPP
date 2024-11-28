package com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.el_motor.fragment

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
import com.example.svetlogorskchpp.__presentation.dialog.BaseBottomSheetDialog
import com.example.svetlogorskchpp.R
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.ElCategory
import com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.adapter.PowerSupplySelectionAdapter
import com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.adapter.ProtectionDialogAdapter
import com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.el_motor.model.ElMotorDialogUIState
import com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.el_motor.view_model.ElMotorViewModel
import com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.factory.ElMotorViewModelFactory
import com.example.svetlogorskchpp.databinding.ContentLayoutRzaEquipmentDialogBinding
import com.example.svetlogorskchpp.databinding.DialogEquipmentElMotorBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.getValue

@AndroidEntryPoint
class ElMotorDialog : BaseBottomSheetDialog<DialogEquipmentElMotorBinding>() {

    private val args: ElMotorDialogArgs by navArgs()

    private val adapterProtection = ProtectionDialogAdapter()

    private val adapterPowerSupply = PowerSupplySelectionAdapter { id, name, dl ->
        val deepLink = Uri.parse(dl.link + id)
        findNavController().navigate(deepLink)
    }

    private var _includeRzaBinding: ContentLayoutRzaEquipmentDialogBinding? = null
    private val includeRzaBinding get() = _includeRzaBinding!!

    @Inject
    lateinit var viewModelFactory: ElMotorViewModelFactory
    private val viewModel: ElMotorViewModel by viewModels {
        ElMotorViewModel.providesFactory(
            factory = viewModelFactory,
            id = args.id
        )
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): DialogEquipmentElMotorBinding {
        return DialogEquipmentElMotorBinding.bind(
            inflater.inflate(R.layout.dialog_equipment_el_motor, container)
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _includeRzaBinding = ContentLayoutRzaEquipmentDialogBinding.bind(binding.contentRza.root)
        includeRzaBinding.rv.adapter = adapterProtection


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
            rvPowerSupply.adapter = adapterPowerSupply

            ivEditContent.setOnClickListener {
                 val action =
                     ElMotorDialogDirections.actionElMotorDialogToElMotorEditFragment(args.id)
                 findNavController().navigate(action)
            }
        }
    }

    private fun setupUI(state: ElMotorDialogUIState) {

        adapterProtection.submitList(state.phaseProtection + state.earthProtection)

        binding.apply {
            with(state) {
                tvName.text = name
                tvRep.visibility = if (isRep) View.VISIBLE else View.INVISIBLE
                tvCategory.text = category.str

                tvTypeElMotor.text = typeEl
                tvVoltage.text = resources.getString(R.string.voltage_el_motor_item, voltage)
                tvPowerEl.apply {
                    text = resources.getString(R.string.power_el_motor_item, powerEl)
                    isGone = powerEl.isEmpty()
                }
                tvI.apply {
                    text = resources.getString(R.string.i_el_motor_item, i)
                    isGone = i.isEmpty()
                }
                tvN.apply {
                    text = resources.getString(R.string.n_el_motor_item, n)
                    isGone = n.isEmpty()
                }
                tvPowerSupply.apply {
                    text = if (powerSupplyCell.isEmpty()) powerSupplyName
                    else resources.getString(
                        R.string.power_supply_item,
                        powerSupplyName,
                        powerSupplyCell
                    )
                }

                tvSwitchTitle.isGone = typeSwitch.isEmpty()
                tvSwitchContent.apply {
                    text = typeSwitch
                    isGone = typeSwitch.isEmpty()
                }

                tvInstrTitle.isGone = typeInsTr.isEmpty()
                tvInstrContent.apply {
                    text = typeInsTr
                    isGone = typeInsTr.isEmpty()
                }

                tvControlCircuits.isGone = controlCircuits.isEmpty()
                tvControlCircuitsContent.apply {
                    text = controlCircuits
                    isGone = controlCircuits.isEmpty()
                }

                layoutRep.isGone = !isRep
                tvTypeRep.isGone = !isRep
                tvTypeRepContent.apply {
                    text = typeRep
                    isGone = !isRep
                }

                val mechanismContent = (mechanismType.isEmpty() && mechanismPerformance.isEmpty() && mechanismPressure.isEmpty() && mechanismH.isEmpty() && mechanismPowerN.isEmpty() && mechanismN.isEmpty() && mechanismAdditionally.isEmpty())
                layoutMechanism.isGone = mechanismContent

                tvMechanismTitle.apply {
                    isGone = mechanismContent
                }
                tvTypeMechanismContent.apply {
                    isGone = mechanismType.isEmpty()
                    text = mechanismType
                }
                tvPerformanceMechanismContent.apply {
                    isGone = mechanismPerformance.isEmpty()
                    text = resources.getString(R.string.performance_mechanism,  mechanismPerformance)
                }
                tvPressureMechanismContent.apply {
                    isGone = mechanismPressure.isEmpty()
                    text = resources.getString(R.string.pressure_mechanism,  mechanismPressure)
                }
                tvHMechanismContent.apply {
                    isGone = mechanismH.isEmpty()
                    text = resources.getString(R.string.mechanism_h,  mechanismH)
                }
                tvPowerNMechanismContent.apply {
                    isGone = mechanismPowerN.isEmpty()
                    text = resources.getString(R.string.mechanism_n_power,  mechanismPowerN)
                }
                tvNMechanismContent.apply {
                    isGone = mechanismN.isEmpty()
                    text = resources.getString(R.string.mechanism_n,  mechanismN)
                }
                tvAdditionallyMechanismContent.apply {
                    isGone = mechanismAdditionally.isEmpty()
                    text = mechanismAdditionally
                }
                tvAdditionally.text = additionally
                tvAdditionally.isGone = additionally.isEmpty()
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
                if (automation.isEmpty() && additionallyRza.isEmpty() && phaseProtection.isEmpty() && earthProtection.isEmpty()) {
                    layoutRza.isGone = true
                }
            }
        }

        setupImageCategory(state.category)
    }

    fun setupImageCategory(category: ElCategory) {
        binding.ivCategory.apply {
            when (category) {
                ElCategory.OTHER -> setImageResource(R.drawable.high_voltage_8107242)
                ElCategory.TREATMENT_FACILITIES -> setImageResource(R.drawable.chemistry_class_6837437)
                ElCategory.DESALTING -> setImageResource(R.drawable.chemistry_class_6837437)
                ElCategory.BNT -> setImageResource(R.drawable.factory_1643683)
                ElCategory.Bagernaya -> setImageResource(R.drawable.water_boiler)
                ElCategory.KO -> setImageResource(R.drawable.water_boiler)
                ElCategory.KTC_KO -> setImageResource(R.drawable.water_boiler)
                ElCategory.KTC_TO -> setImageResource(R.drawable.factory_1643683)
                ElCategory.NDV -> setImageResource(R.drawable.icon_station)
                ElCategory.PN -> setImageResource(R.drawable.factory_1643683)
                ElCategory.PEN -> setImageResource(R.drawable.factory_1643683)
                ElCategory.PRETREATMENT -> setImageResource(R.drawable.chemistry_class_6837437)
                ElCategory.SN -> setImageResource(R.drawable.factory_1643683)
                ElCategory.TY -> setImageResource(R.drawable.factory_1643683)
                ElCategory.CN -> setImageResource(R.drawable.icon_nuclear_power)
                ElCategory.CCR -> setImageResource(R.drawable.flash_4049918)
                ElCategory.AMMONIA -> setImageResource(R.drawable.chemistry_class_6837437)
                ElCategory.GIDROZIYNOE -> setImageResource(R.drawable.chemistry_class_6837437)
                ElCategory.ACIDIC -> setImageResource(R.drawable.chemistry_class_6837437)
                ElCategory.COAGULANT -> setImageResource(R.drawable.chemistry_class_6837437)
                ElCategory.N_TS -> setImageResource(R.drawable.chemistry_class_6837437)
                ElCategory.SOVEVOE -> setImageResource(R.drawable.chemistry_class_6837437)
                ElCategory.LIMESTONE -> setImageResource(R.drawable.chemistry_class_6837437)
                ElCategory.PHOSPHATE -> setImageResource(R.drawable.chemistry_class_6837437)
                ElCategory.NVK -> setImageResource(R.drawable.chemistry_class_6837437)
                ElCategory.ALKALINE -> setImageResource(R.drawable.chemistry_class_6837437)
                ElCategory.TG_1 -> setImageResource(R.drawable.generator_1)
                ElCategory.TG_3 -> setImageResource(R.drawable.generator_1)
                ElCategory.TG_4 -> setImageResource(R.drawable.generator_1)
                ElCategory.TG_5 -> setImageResource(R.drawable.generator_1)
                ElCategory.TG_6 -> setImageResource(R.drawable.generator_1)
                ElCategory.KA_1 -> setImageResource(R.drawable.water_boiler)
                ElCategory.KA_6 -> setImageResource(R.drawable.water_boiler)
                ElCategory.KA_7 -> setImageResource(R.drawable.water_boiler)
                ElCategory.KA_8 -> setImageResource(R.drawable.water_boiler)
                ElCategory.KA_9 -> setImageResource(R.drawable.water_boiler)
            }
        }
    }
}