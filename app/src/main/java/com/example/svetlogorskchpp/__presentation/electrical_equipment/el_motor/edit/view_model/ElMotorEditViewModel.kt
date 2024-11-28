package com.example.svetlogorskchpp.__presentation.electrical_equipment.el_motor.edit.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.svetlogorskchpp.BaseEditViewModel
import com.example.svetlogorskchpp.__domain.OperationResult
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.ElCategory
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.ElGeneralCategory
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.Voltage
import com.example.svetlogorskchpp.__domain.model.electrical_equipment.ElMotor
import com.example.svetlogorskchpp.__domain.usecases.equipments.EquipmentsUseCases
import com.example.svetlogorskchpp.__presentation.electrical_equipment.el_motor.factory.ElMotorEditViewModleFactory
import com.example.svetlogorskchpp.__presentation.electrical_equipment.el_motor.model.ElMotorEditSpinnerUIState
import com.example.svetlogorskchpp.__presentation.electrical_equipment.el_motor.model.ElMotorEditUIState
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ElMotorEditViewModel @AssistedInject constructor(
    @Assisted private val id: String,
    private val useCases: EquipmentsUseCases<ElMotor>,
) : BaseEditViewModel() {

    private val _etUIState = MutableStateFlow(ElMotorEditUIState())
    val etUIState: StateFlow<ElMotorEditUIState> = _etUIState

    private val _spinnerUIState = MutableStateFlow(ElMotorEditSpinnerUIState())
    val spinnerUIState: StateFlow<ElMotorEditSpinnerUIState> = _spinnerUIState

    val listVoltage = listOf(Voltage.KV, Voltage.KV_380, Voltage.KV_3, Voltage.KV_6)

    val listCategory = listCategory()
    val listGenCategory = listGeneralCategory()

    init {
        viewModelScope.launch {
            useCases.getItemEquipment(id).collect { elMotor ->
                elMotor?.let { item ->
                    with(item) {
                        _etUIState.update { oldState ->
                            oldState.copy(
                                id = id,
                                name = name,
                                powerSupplyId = powerSupplyId,
                                powerSupplyCell = powerSupplyCell,
                                powerSupplyName = powerSupplyName,
                                automation = automation,
                                additionallyRza = additionallyRza,
                                typeSwitch = typeSwitch,
                                typeInsTr = typeInsTr,
                                additionally = additionally,
                                isRep = isRep,
                                typeRep = typeRep,
                                controlCircuits = controlCircuits,
                                powerEl = powerEl,
                                i = i,
                                n = n,
                                typeEl = typeEl,
                                mechanismType = mechanismType,
                                mechanismPerformance = mechanismPerformance,
                                mechanismPressure = mechanismPressure,
                                mechanismN = mechanismN,
                                mechanismH = mechanismH,
                                mechanismPowerN = mechanismPowerN,
                                mechanismAdditionally = mechanismAdditionally
                            )
                        }
                        _spinnerUIState.update { oldState ->
                            oldState.copy(
                                category = category,
                                generalCategory = generalCategory,
                                voltage = voltage,
                            )
                        }
                        _protectionUIState.update { oldState ->
                            oldState.copy(
                                phaseProtection = phaseProtection,
                                earthProtection = earthProtection
                            )
                        }
                    }
                }
            }
        }
    }

    fun saveState(state: ElMotorEditUIState) {
        _etUIState.update { state }
    }

    fun saveSpinnerState(state: ElMotorEditSpinnerUIState) {
        _spinnerUIState.update { state }
    }

    fun saveParameterElMotor(elMotorUiState: ElMotorEditUIState) {
        val parameterElMotor = with(elMotorUiState) {
            ElMotor(
                id = id,
                name = name,
                powerSupplyId = powerSupplyId,
                powerSupplyCell = powerSupplyCell,
                powerSupplyName = powerSupplyName,
                automation = automation,
                phaseProtection = protectionUIState.value.phaseProtection,
                earthProtection = protectionUIState.value.earthProtection,
                additionallyRza = additionallyRza,
                category = spinnerUIState.value.category,
                generalCategory = spinnerUIState.value.generalCategory,
                typeSwitch = typeSwitch,
                typeInsTr = typeInsTr,
                additionally = additionally,
                isRep = isRep,
                typeRep = typeRep,
                controlCircuits = controlCircuits,
                powerEl = powerEl,
                voltage = spinnerUIState.value.voltage,
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
        viewModelScope.launch {
            _etUIState.update { elMotorUiState }
            val resultToast = useCases.saveItemEquipment(parameterElMotor)
            when (resultToast) {
                is OperationResult.Error -> _toastResultFlow.emit(resultToast.massage)
                is OperationResult.Success -> _toastResultFlow.emit(resultToast.data)
            }
        }
    }


    fun listCategory(): List<ElCategory> {
        return listOf(
            ElCategory.OTHER,
            ElCategory.TREATMENT_FACILITIES,
            ElCategory.DESALTING,
            ElCategory.BNT,
            ElCategory.Bagernaya,
            ElCategory.KO,
            ElCategory.KTC_KO,
            ElCategory.KTC_TO,
            ElCategory.NDV,
            ElCategory.PN,
            ElCategory.PEN,
            ElCategory.PRETREATMENT,
            ElCategory.SN,
            ElCategory.TY,
            ElCategory.CN,
            ElCategory.CCR,
            ElCategory.AMMONIA,
            ElCategory.GIDROZIYNOE,
            ElCategory.ACIDIC,
            ElCategory.COAGULANT,
            ElCategory.N_TS,
            ElCategory.SOVEVOE,
            ElCategory.LIMESTONE,
            ElCategory.PHOSPHATE,
            ElCategory.NVK,
            ElCategory.ALKALINE,
            ElCategory.TG_1,
            ElCategory.TG_3,
            ElCategory.TG_4,
            ElCategory.TG_5,
            ElCategory.TG_6,
            ElCategory.KA_1,
            ElCategory.KA_6,
            ElCategory.KA_7,
            ElCategory.KA_8,
            ElCategory.KA_9
        )
    }

    fun listGeneralCategory(): List<ElGeneralCategory> {
        return listOf(
            ElGeneralCategory.OTHER,
            ElGeneralCategory.HOV,
            ElGeneralCategory.KTC_TO,
            ElGeneralCategory.KTC_KO,
            ElGeneralCategory.TY,
            ElGeneralCategory.EC,
            ElGeneralCategory.KA,
            ElGeneralCategory.RG,
        )
    }

    companion object {
        fun providesFactory(
            factory: ElMotorEditViewModleFactory,
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