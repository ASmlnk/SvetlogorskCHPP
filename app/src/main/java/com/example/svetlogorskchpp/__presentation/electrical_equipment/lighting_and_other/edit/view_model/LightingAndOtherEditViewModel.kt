package com.example.svetlogorskchpp.__presentation.electrical_equipment.lighting_and_other.edit.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.svetlogorskchpp.__domain.OperationResult
import com.example.svetlogorskchpp.__domain.model.electrical_equipment.LightingAndOther
import com.example.svetlogorskchpp.__domain.usecases.equipments.EquipmentsUseCases
import com.example.svetlogorskchpp.__presentation.electrical_equipment.factory.LightingAndOtherEditViewModelFactory
import com.example.svetlogorskchpp.__presentation.electrical_equipment.lighting_and_other.edit.model.LightingAndOtherEditUIState
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LightingAndOtherEditViewModel @AssistedInject constructor(
    @Assisted private val id: String,
    private val useCases: EquipmentsUseCases<LightingAndOther>,
) : ViewModel() {

    private val _etUIState = MutableStateFlow(LightingAndOtherEditUIState())
    val etUIState: StateFlow<LightingAndOtherEditUIState> = _etUIState

    private val _toastResultFlow = MutableSharedFlow<String>()
    val toastResultFlow: SharedFlow<String> = _toastResultFlow

    init {
        viewModelScope.launch {
            useCases.getItemEquipment(id).collect { lightingAndOther ->
                lightingAndOther?.let { item ->
                    with(item) {
                        _etUIState.update { oldState ->
                            oldState.copy(
                                id = id,
                                name = name,
                                powerSupplyId = powerSupplyId,
                                powerSupplyCell = powerSupplyCell,
                                powerSupplyName = powerSupplyName,
                                typeSwitch = typeSwitch,
                                additionally = additionally,
                                isLighting = isLighting,
                                location = location,
                            )
                        }
                    }
                }
            }
        }
    }

    fun saveState(state: LightingAndOtherEditUIState) {
        _etUIState.update { state }
    }

    fun saveParameterElMotor(uiState: LightingAndOtherEditUIState) {
        val parameter = with(uiState) {
            LightingAndOther(
                id = id,
                name = name,
                powerSupplyId = powerSupplyId,
                powerSupplyCell = powerSupplyCell,
                powerSupplyName = powerSupplyName,
                typeSwitch = typeSwitch,
                additionally = additionally,
                isLighting = isLighting,
                location = location,
            )
        }
        viewModelScope.launch {
            _etUIState.update { uiState }
            val resultToast = useCases.saveItemEquipment(parameter)
            when (resultToast) {
                is OperationResult.Error -> _toastResultFlow.emit(resultToast.massage)
                is OperationResult.Success -> _toastResultFlow.emit(resultToast.data)
            }
        }
    }

    companion object {
        fun providesFactory(
            factory: LightingAndOtherEditViewModelFactory,
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