package com.example.svetlogorskchpp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.svetlogorskchpp.__domain.model.electrical_equipment.TurboGenerator
import com.example.svetlogorskchpp.__presentation.electrical_equipment.factory.TransformerOwnNeedsEditViewModelFactory
import com.example.svetlogorskchpp.__presentation.electrical_equipment.open_switchgear.model.ProtectionUIState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

open class BaseEditViewModel: ViewModel() {

    protected val _protectionUIState = MutableStateFlow(ProtectionUIState())
    val protectionUIState: StateFlow<ProtectionUIState> = _protectionUIState

    protected  val _toastResultFlow = MutableSharedFlow<String>()
    val toastResultFlow: SharedFlow<String> = _toastResultFlow

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
}