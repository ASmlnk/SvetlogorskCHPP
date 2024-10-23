package com.example.svetlogorskchpp.__presentation.open_switchgear.edit_vl.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.svetlogorskchpp.__domain.model.electrical_equipment.OpenSwitchgearVl
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.KeyOry
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.VoltageOry

import com.example.svetlogorskchpp.__presentation.open_switchgear.factory.OpenSwitchgearVlEditViewModelFactory
import com.example.svetlogorskchpp.__presentation.open_switchgear.model.OpSwVlEditUIState
import com.example.svetlogorskchpp.__presentation.open_switchgear.model.ProtectionUIState
import com.example.svetlogorskchpp.__presentation.open_switchgear.model.SpinnerOryParameter
import com.example.svetlogorskchpp.__presentation.open_switchgear.model.SpinnerEditState
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class OpenSwitchgearVlEditViewModel @AssistedInject constructor(
    @Assisted private val idVl: String,
) : ViewModel() {

    private val _opSwVlEditUIState = MutableStateFlow(OpSwVlEditUIState())
    val opSwVlEditUIState: StateFlow<OpSwVlEditUIState> = _opSwVlEditUIState

    private val _spinnerUIState = MutableStateFlow(SpinnerEditState())
    val spinnerUIState: StateFlow<SpinnerEditState> = _spinnerUIState

    private val _protectionUIState = MutableStateFlow(ProtectionUIState())
    val protectionUIState: StateFlow<ProtectionUIState> = _protectionUIState

    fun <T> spinnerSaveState(spinnerOryParameter: SpinnerOryParameter, selectSpinner: T) {
        when (spinnerOryParameter) {
            SpinnerOryParameter.SH_R_1 -> _spinnerUIState.update { it.copy(keyShr1 = selectSpinner as KeyOry) }
            SpinnerOryParameter.SH_R_2 -> _spinnerUIState.update { it.copy(keyShr2 = selectSpinner as KeyOry) }
            SpinnerOryParameter.LR -> _spinnerUIState.update { it.copy(keyLr = selectSpinner as KeyOry) }
            SpinnerOryParameter.OR -> _spinnerUIState.update { it.copy(keyOr = selectSpinner as KeyOry) }
            SpinnerOryParameter.VOLTAGE -> _spinnerUIState.update { it.copy(voltageOry = selectSpinner as VoltageOry) }
        }
    }

    fun chipSelected(opSwVlEditUIState: OpSwVlEditUIState) {
        _opSwVlEditUIState.update { opSwVlEditUIState }
    }

    fun saveParameterVl(opSwVlEditUIState: OpSwVlEditUIState) {
        val parameterVl = OpenSwitchgearVl(
            id = opSwVlEditUIState.id,
            name = opSwVlEditUIState.name,
            panelMcp = opSwVlEditUIState.panelMcp,
            bysSystem = opSwVlEditUIState.bysSystem,
            cell = opSwVlEditUIState.cell,
            voltage = spinnerUIState.value.voltageOry,
            isTransit = opSwVlEditUIState.isTransit,
            typeSwitch = opSwVlEditUIState.typeSwitch,
            typeInsTr = opSwVlEditUIState.typeInsTr,
            automation = opSwVlEditUIState.automation,
            apv = opSwVlEditUIState.apv,
            keyShr1 = spinnerUIState.value.keyShr1,
            keyShr2 = spinnerUIState.value.keyShr2,
            keyLr = spinnerUIState.value.keyLr,
            keyOr = spinnerUIState.value.keyOr,
            phaseProtection = protectionUIState.value.phaseProtection,
            earthProtection = protectionUIState.value.earthProtection,
        )
    }

    companion object {
        fun providesFactory(
            openSwitchgearVlEditViewModelFactory: OpenSwitchgearVlEditViewModelFactory,
            idVl: String,
        ): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return openSwitchgearVlEditViewModelFactory.create(idVl) as T
                }
            }
        }
    }
}