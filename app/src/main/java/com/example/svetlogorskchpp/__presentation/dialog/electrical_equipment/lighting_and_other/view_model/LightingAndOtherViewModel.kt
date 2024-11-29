package com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.lighting_and_other.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.svetlogorskchpp.__domain.model.electrical_equipment.LightingAndOther
import com.example.svetlogorskchpp.__domain.usecases.equipments.EquipmentsUseCases
import com.example.svetlogorskchpp.__domain.usecases.equipments.all_equipment.EquipmentAllUseCases
import com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.factory.LightingAndOtherViewModelFactory
import com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.lighting_and_other.model.LightingUIState
import com.example.svetlogorskchpp.__presentation.electrical_equipment.model.ElectricalEquipment
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LightingAndOtherViewModel @AssistedInject constructor(
    @Assisted private val id: String,
    private val useCases: EquipmentsUseCases<LightingAndOther>,
    private val useCasesAllEquipment: EquipmentAllUseCases,
) : ViewModel() {

    private val _uiState = MutableStateFlow(LightingUIState())
    val uiState: StateFlow<LightingUIState> get() = _uiState

    private val _powerSupplyState = MutableStateFlow<List<ElectricalEquipment>>(emptyList())
    val powerSupplyState: StateFlow<List<ElectricalEquipment>> get() = _powerSupplyState

    init {
        viewModelScope.launch {
            useCases.getItemEquipment(id).collect { item ->
                item?.let {
                    _uiState.update { oldState ->
                        with(item) {
                            oldState.copy(
                                name = name,
                                powerSupplyCell = powerSupplyCell,
                                powerSupplyName = powerSupplyName,
                                typeSwitch = typeSwitch,
                                additionally = additionally,
                                isLighting = isLighting,
                                location = location,
                            )
                        }
                    }
                    updatePowerSupply(item.powerSupplyId)
                }
            }
        }
    }

    private fun updatePowerSupply(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            useCasesAllEquipment.getEquipmentPowerSupplyFlow(id).collect {
                _powerSupplyState.value = it
            }
        }
    }

    companion object {
        fun providesFactory(
            factory: LightingAndOtherViewModelFactory,
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