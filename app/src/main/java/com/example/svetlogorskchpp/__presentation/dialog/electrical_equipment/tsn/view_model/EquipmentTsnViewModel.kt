package com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.tsn.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.svetlogorskchpp.__domain.model.electrical_equipment.TransformerOwnNeeds
import com.example.svetlogorskchpp.__domain.usecases.equipments.EquipmentsUseCases
import com.example.svetlogorskchpp.__domain.usecases.equipments.all_equipment.EquipmentAllUseCases
import com.example.svetlogorskchpp.__domain.usecases.equipments.edit_access.EditAccessUseCases
import com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.BaseEquipmentDialogViewModel
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
    private val accessUseCases: EditAccessUseCases,
    @Assisted val id: String,
) : BaseEquipmentDialogViewModel(accessUseCases) {

    private val _uiState = MutableStateFlow(TsnUIState())
    val uiState: StateFlow<TsnUIState> get() = _uiState

    private val _powerSupplyState = MutableStateFlow<List<ElectricalEquipment>>(emptyList())
    val powerSupplyState : StateFlow<List<ElectricalEquipment>> get() = _powerSupplyState

    private val _consumerState = MutableStateFlow<List<ElectricalEquipment>>(emptyList())
    val consumerState: StateFlow<List<ElectricalEquipment>> get() = _consumerState


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
                            phaseProtection = tsn.phaseProtection,
                            earthProtection = tsn.earthProtection,
                        )
                    }
                    updatePowerSupply(tsn.powerSupplyId)
                    updateConsumer(tsn.id)
                }
            }
        }
        viewModelScope.launch {
            isAccess.collect {
                _uiState.update { oldState ->
                    oldState.copy(isAccessEdit = it)
                }
            }
        }
    }

    fun isEditAccess(): Boolean {
        return uiState.value.isAccessEdit
    }

    private fun updatePowerSupply(id: String)  {
        viewModelScope.launch(Dispatchers.IO) {
            val flow = useCasesAllEquipment.getEquipmentPowerSupplyFlow(id)
                flow.collect {
                _powerSupplyState.value = it
            }
        }
    }

    private fun updateConsumer(id: String)  {
        viewModelScope.launch(Dispatchers.IO) {
            val flow = useCasesAllEquipment.getEquipmentConsumersFlow(id)
            flow.collect {
                _consumerState.value = it
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