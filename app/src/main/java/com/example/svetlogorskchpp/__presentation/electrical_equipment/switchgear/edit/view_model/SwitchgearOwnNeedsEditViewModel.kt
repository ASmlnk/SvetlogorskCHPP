package com.example.svetlogorskchpp.__presentation.electrical_equipment.switchgear.edit.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.svetlogorskchpp.BaseEditViewModel
import com.example.svetlogorskchpp.__domain.OperationResult
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.ElAssembly
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.NameDepartment
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.Voltage
import com.example.svetlogorskchpp.__domain.model.electrical_equipment.Switchgear
import com.example.svetlogorskchpp.__domain.model.electrical_equipment.TurboGenerator
import com.example.svetlogorskchpp.__domain.usecases.equipments.EquipmentsUseCases
import com.example.svetlogorskchpp.__presentation.electrical_equipment.switchgear.factory.SwitchgearOwnNeedsEditViewModelFactory
import com.example.svetlogorskchpp.__presentation.electrical_equipment.switchgear.model.SwitchgearEditSpinnerUIState
import com.example.svetlogorskchpp.__presentation.electrical_equipment.switchgear.model.SwitchgearEditUIState
import com.example.svetlogorskchpp.__presentation.electrical_equipment.tg.edit.model.TurbogeneratorUIState
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SwitchgearOwnNeedsEditViewModel @AssistedInject constructor(
    @Assisted private val id: String,
    private val useCases: EquipmentsUseCases<Switchgear>,
) : BaseEditViewModel() {

    private val _switchgearUIState = MutableStateFlow(SwitchgearEditUIState())
    val switchgearUIState: StateFlow<SwitchgearEditUIState> =
        _switchgearUIState

    private val _spinnerUIState = MutableStateFlow(SwitchgearEditSpinnerUIState())
    val spinnerUIState: StateFlow<SwitchgearEditSpinnerUIState> = _spinnerUIState

    val listVoltage = listOf(Voltage.KV, Voltage.KV_380, Voltage.KV_3, Voltage.KV_6)
    val listElAssemblys = listOf(ElAssembly.OTHER, ElAssembly.RY, ElAssembly.ASSEMBLY, ElAssembly.RTZO, ElAssembly.LIGHTING, ElAssembly.SHPT_1, ElAssembly.SHPT_2)
    val listNameDepartment = listOf(NameDepartment.OTHER, NameDepartment.KRY, NameDepartment.RY, NameDepartment.SHIELD_BLOCK, NameDepartment.KTC_TO, NameDepartment.KTC_KO,NameDepartment.KTC_TY, NameDepartment.HC, NameDepartment.POST_TOK, NameDepartment.BNS, NameDepartment.COOLING_TOWER)

    init {
        viewModelScope.launch {
            useCases.getItemEquipment(id).collect { switchgear ->
                switchgear?.let {
                    _switchgearUIState.update { oldState ->
                        oldState.copy(
                            id = it.id,
                            name = it.name,
                            typeSwitch = it.typeSwitch,
                            typeInsTr = it.typeInsTr,
                            additionally = it.additionally,
                            automation = it.automation,
                            additionallyRza = it.additionallyRza,
                            info = it.info,
                            powerSupplyId1 = it.powerSupplyId1,
                            powerSupplyCell1 = it.powerSupplyCell1,
                            powerSupplyName1 = it.powerSupplyName1,
                            powerSupplyId2 = it.powerSupplyId2,
                            powerSupplyCell2 = it.powerSupplyCell2,
                            powerSupplyName2 = it.powerSupplyName2,
                            powerSupplyReserveId1 = it.powerSupplyReserveId1,
                            powerSupplyReserveCell1 = it.powerSupplyReserveCell1,
                            powerSupplyReserveName1 = it.powerSupplyReserveName1,
                            powerSupplyReserveId2 = it.powerSupplyReserveId2,
                            powerSupplyReserveCell2 = it.powerSupplyReserveCell2,
                            powerSupplyReserveName2 = it.powerSupplyReserveName2,
                            powerSupplyReserveId3 = it.powerSupplyReserveId3,
                            powerSupplyReserveCell3 = it.powerSupplyReserveCell3,
                            powerSupplyReserveName3 = it.powerSupplyReserveName3,
                        )
                    }
                    _spinnerUIState.update { oldState ->
                        oldState.copy(
                            category = it.category,
                            nameDepartment = it.nameDepartment,
                            voltage = it.voltage,
                        )
                    }

                    _protectionUIState.update { oldState ->
                        oldState.copy(
                            phaseProtection = it.phaseProtection,
                            earthProtection = it.earthProtection
                        )
                    }
                }
            }
        }
    }

    fun saveState(state: SwitchgearEditUIState) {
        _switchgearUIState.update { state }
    }

    fun saveSpinnerState(state: SwitchgearEditSpinnerUIState) {
        _spinnerUIState.update { state }
    }

    fun saveParameterSwitchgear(switchgear: SwitchgearEditUIState) {
        val parameterSwitchgear = Switchgear(
            id = switchgear.id,
            name = switchgear.name,
            typeSwitch = switchgear.typeSwitch,
            typeInsTr = switchgear.typeInsTr,
            additionally = switchgear.additionally,
            category = spinnerUIState.value.category,
            nameDepartment = spinnerUIState.value.nameDepartment,
            voltage = spinnerUIState.value.voltage,
            automation = switchgear.automation,
            phaseProtection = protectionUIState.value.phaseProtection,
            earthProtection = protectionUIState.value.earthProtection,
            additionallyRza = switchgear.additionallyRza,
            info = switchgear.info,
            powerSupplyId1 = switchgear.powerSupplyId1,
            powerSupplyCell1 = switchgear.powerSupplyCell1,
            powerSupplyName1 = switchgear.powerSupplyName1,
            powerSupplyId2 = switchgear.powerSupplyId2,
            powerSupplyCell2 = switchgear.powerSupplyCell2,
            powerSupplyName2 = switchgear.powerSupplyName2,
            powerSupplyReserveId1 = switchgear.powerSupplyReserveId1,
            powerSupplyReserveCell1 = switchgear.powerSupplyReserveCell1,
            powerSupplyReserveName1 = switchgear.powerSupplyReserveName1,
            powerSupplyReserveId2 = switchgear.powerSupplyReserveId2,
            powerSupplyReserveCell2 = switchgear.powerSupplyReserveCell2,
            powerSupplyReserveName2 = switchgear.powerSupplyReserveName2,
            powerSupplyReserveId3 = switchgear.powerSupplyReserveId3,
            powerSupplyReserveCell3 = switchgear.powerSupplyReserveCell3,
            powerSupplyReserveName3 = switchgear.powerSupplyReserveName3,
        )
        viewModelScope.launch {
            _switchgearUIState.update { switchgear }
            val resultToast = useCases.saveItemEquipment(parameterSwitchgear)
            when (resultToast) {
                is OperationResult.Error -> _toastResultFlow.emit(resultToast.massage)
                is OperationResult.Success -> _toastResultFlow.emit(resultToast.data)
            }
        }
    }


    companion object {
        fun providesFactory(
            factory: SwitchgearOwnNeedsEditViewModelFactory,
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