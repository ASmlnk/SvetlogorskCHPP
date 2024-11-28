package com.example.svetlogorskchpp.__presentation.electrical_equipment.open_switchgear.edit_tr.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.svetlogorskchpp.__domain.OperationResult
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.KeyOry
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.Voltage
import com.example.svetlogorskchpp.__domain.model.electrical_equipment.OpenSwitchgearTr
import com.example.svetlogorskchpp.__domain.usecases.equipments.EquipmentsUseCases
import com.example.svetlogorskchpp.__presentation.electrical_equipment.factory.OpenSwitchgearTrEditViewModelFactory
import com.example.svetlogorskchpp.__presentation.electrical_equipment.open_switchgear.model.OpSwTrEditUIState
import com.example.svetlogorskchpp.__presentation.electrical_equipment.open_switchgear.model.ProtectionUIState
import com.example.svetlogorskchpp.__presentation.electrical_equipment.open_switchgear.model.SpinnerEditState
import com.example.svetlogorskchpp.__presentation.electrical_equipment.open_switchgear.model.SpinnerOryParameter
import com.example.svetlogorskchpp.__presentation.electrical_equipment.open_switchgear.model.VoltageSide
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OpenSwitchgearTrEditViewModel @AssistedInject constructor(
    private val useCases: EquipmentsUseCases<OpenSwitchgearTr>,
    @Assisted private val id: String,
) : ViewModel() {

    private val _opSwTrEditUIState = MutableStateFlow(OpSwTrEditUIState())
    val opSwTrEditUIState: StateFlow<OpSwTrEditUIState> = _opSwTrEditUIState

    private val _spinnerVnUIState = MutableStateFlow(SpinnerEditState())
    val spinnerVnUIState: StateFlow<SpinnerEditState> = _spinnerVnUIState

    private val _spinnerSnUIState = MutableStateFlow(SpinnerEditState())
    val spinnerSnUIState: StateFlow<SpinnerEditState> = _spinnerSnUIState

    private val _protectionUIState = MutableStateFlow(ProtectionUIState())
    val protectionUIState: StateFlow<ProtectionUIState> = _protectionUIState

    private val _toastResultFlow = MutableSharedFlow<String>()
    val toastResultFlow: SharedFlow<String> = _toastResultFlow

    fun <T> spinnerSaveState(
        spinnerOryParameter: SpinnerOryParameter,
        selectSpinner: T,
        voltageSide: VoltageSide,
    ) {
        when (voltageSide) {
            VoltageSide.VN -> {
                when (spinnerOryParameter) {
                    SpinnerOryParameter.SH_R_1 -> _spinnerVnUIState.update { it.copy(keyShr1 = selectSpinner as KeyOry) }
                    SpinnerOryParameter.SH_R_2 -> _spinnerVnUIState.update { it.copy(keyShr2 = selectSpinner as KeyOry) }
                    SpinnerOryParameter.LR -> _spinnerVnUIState.update { it.copy(keyLr = selectSpinner as KeyOry) }
                    SpinnerOryParameter.OR -> _spinnerVnUIState.update { it.copy(keyOr = selectSpinner as KeyOry) }
                    SpinnerOryParameter.VOLTAGE -> _spinnerVnUIState.update { it.copy(voltage = selectSpinner as Voltage) }
                }
            }

            VoltageSide.SN -> {
                when (spinnerOryParameter) {
                    SpinnerOryParameter.SH_R_1 -> _spinnerSnUIState.update { it.copy(keyShr1 = selectSpinner as KeyOry) }
                    SpinnerOryParameter.SH_R_2 -> _spinnerSnUIState.update { it.copy(keyShr2 = selectSpinner as KeyOry) }
                    SpinnerOryParameter.LR -> _spinnerSnUIState.update { it.copy(keyLr = selectSpinner as KeyOry) }
                    SpinnerOryParameter.OR -> _spinnerSnUIState.update { it.copy(keyOr = selectSpinner as KeyOry) }
                    SpinnerOryParameter.VOLTAGE -> _spinnerSnUIState.update { it.copy(voltage = selectSpinner as Voltage) }
                }
            }
        }
    }

    init {
        viewModelScope.launch {
            useCases.getItemEquipment(id).collect { tr ->
                tr?.let {
                    _opSwTrEditUIState.update { old ->
                        old.copy(
                            id = it.id,
                            name = it.name,
                            panelMcp = it.panelMcp,
                            type = it.type,
                            parameterType = it.parameterType,
                            transcriptType = it.transcriptType,
                            additionally = it.additionally,
                            isSpare = it.isSpare,
                            isThreeWinding = it.isThreeWinding,
                            bysSystemVn = it.bysSystemVn,
                            cellVn = it.cellVn,
                            typeSwitchVn = it.typeSwitchVn,
                            typeInsTrVn = it.typeInsTrVn,
                            bysSystemSn = it.bysSystemSn,
                            cellSn = it.cellSn,
                            typeSwitchSn = it.typeSwitchSn,
                            typeInsTrSn = it.typeInsTrSn,
                            automation = it.automation,
                            apv = it.apv,
                        )
                    }
                    _spinnerVnUIState.update { old ->
                        old.copy(
                            keyShr1 = it.keyShr1Vn,
                            keyShr2 = it.keyShr2Vn,
                            keyLr = it.keyLrVn,
                            keyOr = it.keyOrVn,
                            voltage = it.voltageVn
                        )
                    }
                    _spinnerSnUIState.update { old ->
                        old.copy(
                            keyShr1 = it.keyShr1Sn,
                            keyShr2 = it.keyShr2Sn,
                            keyLr = it.keyLrSn,
                            keyOr = it.keyOrSn,
                            voltage = it.voltageSn
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

    fun chipSelected(opSwTrEditUIState: OpSwTrEditUIState) {
        _opSwTrEditUIState.update { opSwTrEditUIState }

    }

    fun chipSelected(opSwTrEditUIState: OpSwTrEditUIState, spinnerState: SpinnerEditState) {
        _opSwTrEditUIState.update { opSwTrEditUIState }
        _spinnerSnUIState.update { spinnerState }
    }

    fun saveState(state: OpSwTrEditUIState) {
        _opSwTrEditUIState.update { state }
    }

    fun saveParameterTr(opSwTr: OpSwTrEditUIState) {
        val parameterTr = OpenSwitchgearTr(
            id = opSwTr.id,
            name = opSwTr.name,
            panelMcp = opSwTr.panelMcp,
            type = opSwTr.type,
            parameterType = opSwTr.parameterType,
            transcriptType = opSwTr.transcriptType,
            additionally = opSwTr.additionally,
            isSpare = opSwTr.isSpare,
            isThreeWinding = opSwTr.isThreeWinding,
            bysSystemVn = opSwTr.bysSystemVn,
            cellVn = opSwTr.cellVn,
            voltageVn = spinnerVnUIState.value.voltage,
            typeSwitchVn = opSwTr.typeSwitchVn,
            typeInsTrVn = opSwTr.typeInsTrVn,
            keyShr1Vn = spinnerVnUIState.value.keyShr1,
            keyShr2Vn = spinnerVnUIState.value.keyShr2,
            keyLrVn = spinnerVnUIState.value.keyLr,
            keyOrVn = spinnerVnUIState.value.keyOr,
            bysSystemSn = opSwTr.bysSystemSn,
            cellSn = opSwTr.cellSn,
            voltageSn = spinnerSnUIState.value.voltage,
            typeSwitchSn = opSwTr.typeSwitchSn,
            typeInsTrSn = opSwTr.typeInsTrSn,
            keyShr1Sn = spinnerSnUIState.value.keyShr1,
            keyShr2Sn = spinnerSnUIState.value.keyShr2,
            keyLrSn = spinnerSnUIState.value.keyLr,
            keyOrSn = spinnerSnUIState.value.keyOr,
            automation = opSwTr.automation,
            apv = opSwTr.apv,
            phaseProtection = protectionUIState.value.phaseProtection,
            earthProtection = protectionUIState.value.earthProtection
        )
        viewModelScope.launch {
            val resultToast = useCases.saveItemEquipment(parameterTr)
            when (resultToast) {
                is OperationResult.Error -> _toastResultFlow.emit(resultToast.massage)
                is OperationResult.Success -> _toastResultFlow.emit(resultToast.data)
            }
        }
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


    companion object {
        fun providesFactory(
            openSwitchgearTrEditViewModelFactory: OpenSwitchgearTrEditViewModelFactory,
            idTr: String,
        ): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return openSwitchgearTrEditViewModelFactory.create(idTr) as T
                }
            }
        }
    }
}