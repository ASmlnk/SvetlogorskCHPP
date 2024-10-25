package com.example.svetlogorskchpp.__presentation.electrical_equipment.open_switchgear.edit_vl.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.svetlogorskchpp.__domain.model.electrical_equipment.OpenSwitchgearVl
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.KeyOry
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.VoltageOry
import com.example.svetlogorskchpp.__domain.usecases.electrical_equipment.open_switchgear.OpenSwitchgearUseCases
import com.example.svetlogorskchpp.__presentation.electrical_equipment.open_switchgear.factory.OpenSwitchgearVlEditViewModelFactory
import com.example.svetlogorskchpp.__presentation.electrical_equipment.open_switchgear.model.OpSwVlEditUIState
import com.example.svetlogorskchpp.__presentation.electrical_equipment.open_switchgear.model.ProtectionUIState
import com.example.svetlogorskchpp.__presentation.electrical_equipment.open_switchgear.model.SpinnerOryParameter
import com.example.svetlogorskchpp.__presentation.electrical_equipment.open_switchgear.model.SpinnerEditState
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OpenSwitchgearVlEditViewModel @AssistedInject constructor(
    private val useCases: OpenSwitchgearUseCases<OpenSwitchgearVl>,
    @Assisted private val id: String,
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

    init {
        viewModelScope.launch {
            useCases.getItemOpenSwitchgear(id).collect { vl ->
                vl?.let {
                    _opSwVlEditUIState.update { old ->
                        old.copy(
                            id = it.id,
                            name = it.name,
                            panelMcp = it.panelMcp,
                            bysSystem = it.bysSystem,
                            cell = it.cell,
                            isTransit = it.isTransit,
                            isVl = it.isVl,
                            typeSwitch = it.typeSwitch,
                            typeInsTr = it.typeInsTr,
                            automation = it.automation,
                            apv = it.apv
                        )
                    }
                    _spinnerUIState.update { old ->
                        old.copy(
                            keyShr1 = it.keyShr1,
                            keyShr2 = it.keyShr2,
                            keyLr = it.keyLr,
                            keyOr = it.keyOr,
                            voltageOry = it.voltage
                        )
                    }
                    _protectionUIState.update { old ->
                        old.copy(
                            phaseProtection = it.phaseProtection,
                            earthProtection = it.earthProtection
                        )
                    }
                }
            }
        }
    }

    fun chipSelected(opSwVlEditUIState: OpSwVlEditUIState) {
        _opSwVlEditUIState.update { opSwVlEditUIState }
    }

    fun addEarthProtection(protection: String) {
        val earthProtections = _protectionUIState.value.earthProtection.toMutableList().apply {
            add(protection)
        }
        _protectionUIState.update {
            it.copy(earthProtection = earthProtections)
        }
    }

    fun deleteEarthProtection(protection: String) {
        val earthProtections = _protectionUIState.value.earthProtection.toMutableList().apply {
            remove(protection)
        }
        _protectionUIState.update {
            it.copy(earthProtection = earthProtections)
        }
    }

    fun addPhaseProtection(protection: String) {
        val phaseProtections = _protectionUIState.value.phaseProtection.toMutableList().apply {
            add(protection)
        }
        _protectionUIState.update {
            it.copy(phaseProtection = phaseProtections)
        }
    }

    fun deletePhaseProtection(protection: String) {
        val phaseProtections = _protectionUIState.value.phaseProtection.toMutableList().apply {
            remove(protection)
        }
        _protectionUIState.update {
            it.copy(phaseProtection = phaseProtections)
        }
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
            isVl = opSwVlEditUIState.isVl,
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
        viewModelScope.launch {
            useCases.saveItemOpenSwitchgear(parameterVl)
        }

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