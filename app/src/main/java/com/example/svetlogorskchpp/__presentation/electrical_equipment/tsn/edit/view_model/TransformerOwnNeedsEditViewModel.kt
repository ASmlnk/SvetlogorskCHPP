package com.example.svetlogorskchpp.__presentation.electrical_equipment.tsn.edit.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.svetlogorskchpp.__domain.OperationResult
import com.example.svetlogorskchpp.__domain.model.electrical_equipment.TransformerOwnNeeds
import com.example.svetlogorskchpp.__domain.usecases.equipments.EquipmentsUseCases
import com.example.svetlogorskchpp.__presentation.electrical_equipment.factory.TransformerOwnNeedsEditViewModelFactory
import com.example.svetlogorskchpp.__presentation.electrical_equipment.open_switchgear.model.ProtectionUIState
import com.example.svetlogorskchpp.__presentation.electrical_equipment.tsn.edit.model.TransformerOwnNeedsUIState
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TransformerOwnNeedsEditViewModel @AssistedInject constructor(
    private val useCases: EquipmentsUseCases<TransformerOwnNeeds>,
    @Assisted private val id: String
) : ViewModel() {

    private val _transformerOwnNeedsUIState = MutableStateFlow(TransformerOwnNeedsUIState())
    val transformerOwnNeedsUIState: StateFlow<TransformerOwnNeedsUIState> = _transformerOwnNeedsUIState

    private val _protectionUIState = MutableStateFlow(ProtectionUIState())
    val protectionUIState: StateFlow<ProtectionUIState> = _protectionUIState

    private val _toastResultFlow = MutableSharedFlow<String>()
    val toastResultFlow: SharedFlow<String> = _toastResultFlow

    fun saveState(state: TransformerOwnNeedsUIState) {
        _transformerOwnNeedsUIState.update { state }
    }

    fun saveParameterTsn(tsn: TransformerOwnNeedsUIState) {
        val parameterTsn = TransformerOwnNeeds(
            id = tsn.id,
            name = tsn.name,
            panelMcp = tsn.panelMcp,
            type = tsn.type,
            parameterType = tsn.parameterType,
            transcriptType = tsn.transcriptType,
            additionally = tsn.additionally,
            isSpare = tsn.isSpare,
            powerSupplyId = tsn.powerSupplyId,
            powerSupplyCell = tsn.powerSupplyCell,
            powerSupplyName = tsn.powerSupplyName,
            voltage = tsn.voltage,
            typeSwitch = tsn.typeSwitch,
            typeInsTr = tsn.typeInsTr,
            automation = tsn.automation,
            apv = tsn.apv,
            phaseProtection = protectionUIState.value.phaseProtection,
            earthProtection = protectionUIState.value.earthProtection
        )
        viewModelScope.launch {
            _transformerOwnNeedsUIState.update { tsn }
            val resultToast = useCases.saveItemEquipment(parameterTsn)
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
            factory: TransformerOwnNeedsEditViewModelFactory,
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