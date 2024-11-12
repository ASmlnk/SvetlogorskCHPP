package com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.tsn.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.svetlogorskchpp.__domain.model.electrical_equipment.TransformerOwnNeeds
import com.example.svetlogorskchpp.__domain.usecases.equipments.EquipmentsUseCases
import com.example.svetlogorskchpp.__domain.usecases.equipments.all_equipment.EquipmentAllUseCases
import com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.factory.EquipmentTsnViewModelFactory
import com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.tsn.model.TsnUIState
import com.example.svetlogorskchpp.__presentation.electrical_equipment.model.ElectricalEquipment
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EquipmentTsnViewModel @AssistedInject constructor(
    private val useCasesTsn: EquipmentsUseCases<TransformerOwnNeeds>,
    private val useCasesAllEquipment: EquipmentAllUseCases,
    @Assisted val id: String,
) : ViewModel() {

    private val _uiState = MutableStateFlow(TsnUIState())
    val uiState: StateFlow<TsnUIState> get() = _uiState

    private val _powerSupplyState = MutableStateFlow<List<ElectricalEquipment>>(emptyList())
    val powerSupplyState : StateFlow<List<ElectricalEquipment>> get() = _powerSupplyState


    init {
        viewModelScope.launch {
            useCasesTsn.getItemEquipment(id).collect { tsn ->
                tsn?.let {
                    _uiState.update { oldState ->
                        oldState.copy(
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
                            phaseProtection = tsn.phaseProtection.joinToString(separator = "\n"),
                            earthProtection = tsn.earthProtection.joinToString(separator = "\n"),
                        )
                    }
                    updatePowerSupply(tsn.powerSupplyId)
                }
            }
        }
    }

    private fun updatePowerSupply(id: String)  {
        viewModelScope.launch(Dispatchers.IO) {
            val flow = useCasesAllEquipment.getEquipmentPowerSupplyFlow(id)
                flow.collect {
                _powerSupplyState.value = it
            }
        }

    }

    companion object {
        fun providesFactory(
            factoryTsn: EquipmentTsnViewModelFactory,
            id: String,
        ): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return factoryTsn.create(id) as T
                }
            }
        }
    }
}