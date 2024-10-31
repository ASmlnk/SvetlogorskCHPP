package com.example.svetlogorskchpp.__presentation.electrical_equipment.open_switchgear.edit_tr.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.KeyOry
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.Voltage
import com.example.svetlogorskchpp.__presentation.electrical_equipment.open_switchgear.edit_tr.fragment.OpenSwitchgearTrEditFragmentArgs
import com.example.svetlogorskchpp.__presentation.electrical_equipment.open_switchgear.edit_vl.fragment.OpenSwitchgearVlEditFragmentArgs
import com.example.svetlogorskchpp.__presentation.electrical_equipment.open_switchgear.factory.OpenSwitchgearTrEditViewModelFactory
import com.example.svetlogorskchpp.__presentation.electrical_equipment.open_switchgear.factory.OpenSwitchgearVlEditViewModelFactory
import com.example.svetlogorskchpp.__presentation.electrical_equipment.open_switchgear.model.OpSwTrEditUIState
import com.example.svetlogorskchpp.__presentation.electrical_equipment.open_switchgear.model.OpSwVlEditUIState
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
import kotlin.getValue

class OpenSwitchgearTrEditViewModel @AssistedInject constructor(
    @Assisted private val id: String,
): ViewModel() {

    private val _opSwVlEditUIState = MutableStateFlow(OpSwTrEditUIState())
    val opSwVlEditUIState: StateFlow<OpSwTrEditUIState> = _opSwVlEditUIState

    private val _spinnerVnUIState = MutableStateFlow(SpinnerEditState())
    val spinnerVnUIState: StateFlow<SpinnerEditState> = _spinnerVnUIState

    private val _spinnerSnUIState = MutableStateFlow(SpinnerEditState())
    val spinnerSnUIState: StateFlow<SpinnerEditState> = _spinnerSnUIState

    private val _protectionUIState = MutableStateFlow(ProtectionUIState())
    val protectionUIState: StateFlow<ProtectionUIState> = _protectionUIState

    private val _toastResultFlow = MutableSharedFlow<String>()
    val toastResultFlow: SharedFlow<String> = _toastResultFlow

    fun <T> spinnerSaveState(spinnerOryParameter: SpinnerOryParameter, selectSpinner: T, voltageSide: VoltageSide) {
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