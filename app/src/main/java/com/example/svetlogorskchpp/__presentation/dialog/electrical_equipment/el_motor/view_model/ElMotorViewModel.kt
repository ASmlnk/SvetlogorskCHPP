package com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.el_motor.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.svetlogorskchpp.__domain.model.electrical_equipment.ElMotor
import com.example.svetlogorskchpp.__domain.usecases.equipments.EquipmentsUseCases
import com.example.svetlogorskchpp.__domain.usecases.equipments.all_equipment.EquipmentAllUseCases
import com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.el_motor.model.ElMotorDialogUIState
import com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.factory.ElMotorViewModelFactory
import com.example.svetlogorskchpp.__presentation.electrical_equipment.model.ElectricalEquipment
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ElMotorViewModel @AssistedInject constructor(
    @Assisted private val id: String,
    private val useCases: EquipmentsUseCases<ElMotor>,
    private val useCasesAllEquipment: EquipmentAllUseCases,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ElMotorDialogUIState())
    val uiState: StateFlow<ElMotorDialogUIState> get() = _uiState

    private val _powerSupplyState = MutableStateFlow<List<ElectricalEquipment>>(emptyList())
    val powerSupplyState: StateFlow<List<ElectricalEquipment>> get() = _powerSupplyState

    init {
        viewModelScope.launch {
            useCases.getItemEquipment(id).collect { elMotor ->
                elMotor?.let {
                    _uiState.update { oldState ->
                        with(elMotor) {
                            oldState.copy(
                                name = name,
                                powerSupplyCell = powerSupplyCell,
                                powerSupplyName = powerSupplyName,
                                automation = automation,
                                phaseProtection = phaseProtection,
                                earthProtection = earthProtection,
                                additionallyRza = additionallyRza,
                                category = category,
                                generalCategory = generalCategory.str,
                                typeSwitch = typeSwitch,
                                typeInsTr = typeInsTr,
                                additionally = additionally,
                                isRep = isRep,
                                typeRep = typeRep,
                                controlCircuits = controlCircuits,
                                powerEl = powerEl,
                                voltage = voltage.str,
                                i = i,
                                n = n,
                                typeEl = typeEl,
                                mechanismType = mechanismType,
                                mechanismPerformance = mechanismPerformance,
                                mechanismPressure = mechanismPressure,
                                mechanismN = mechanismN,
                                mechanismH = mechanismH,
                                mechanismPowerN = mechanismPowerN,
                                mechanismAdditionally = mechanismAdditionally,
                            )
                        }
                    }
                    updatePowerSupply(elMotor.powerSupplyId)
                }
            }
        }
    }

    private fun updatePowerSupply(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
                useCasesAllEquipment.getEquipmentPowerSupplyFlow(id).collect {
                _powerSupplyState.value = it
            }
        }
    }


    companion object {
        fun providesFactory(
            factory: ElMotorViewModelFactory,
            id: String,
        ): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return factory.create(id) as T
                }
            }
        }
    }
}