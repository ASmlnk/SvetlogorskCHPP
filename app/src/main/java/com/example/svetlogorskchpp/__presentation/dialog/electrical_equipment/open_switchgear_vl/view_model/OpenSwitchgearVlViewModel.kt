package com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.open_switchgear_vl.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.svetlogorskchpp.__domain.OperationResult
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.EditAccessResult
import com.example.svetlogorskchpp.__domain.model.electrical_equipment.OpenSwitchgearVl
import com.example.svetlogorskchpp.__domain.usecases.equipments.EquipmentsUseCases
import com.example.svetlogorskchpp.__domain.usecases.equipments.edit_access.EditAccessUseCases
import com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.BaseEquipmentDialogViewModel
import com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.factory.OpenSwitchgearVlViewModelFactory
import com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.open_switchgear_vl.model.OpSwiVlDialogUIState
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OpenSwitchgearVlViewModel @AssistedInject constructor(
    private val useCases: EquipmentsUseCases<OpenSwitchgearVl>,
    private val accessUseCases: EditAccessUseCases,
    @Assisted private val id: String,
) : BaseEquipmentDialogViewModel(accessUseCases) {

    private val _uiState = MutableStateFlow(OpSwiVlDialogUIState())
    val uiState: StateFlow<OpSwiVlDialogUIState> get() = _uiState

    init {
        viewModelScope.launch {
            useCases.getItemEquipment(id).collect { openSwitchgearVl ->
                openSwitchgearVl?.let {
                    _uiState.update { oldState ->
                        oldState.copy(
                            name = openSwitchgearVl.name,
                            panelMcp = openSwitchgearVl.panelMcp,
                            bysSystem = openSwitchgearVl.bysSystem,
                            cell = openSwitchgearVl.cell,
                            voltage = openSwitchgearVl.voltage,
                            isTransit = openSwitchgearVl.isTransit,
                            typeSwitch = openSwitchgearVl.typeSwitch,
                            typeInsTr = openSwitchgearVl.typeInsTr,
                            automation = openSwitchgearVl.automation,
                            apv = openSwitchgearVl.apv,
                            keyShr1 = openSwitchgearVl.keyShr1,
                            keyShr2 = openSwitchgearVl.keyShr2,
                            keyLr = openSwitchgearVl.keyLr,
                            keyOr = openSwitchgearVl.keyOr,
                            phaseProtection = openSwitchgearVl.phaseProtection,
                            earthProtection = openSwitchgearVl.earthProtection
                        )
                    }
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

    companion object {
        fun providesFactory(
            openSwitchgearVlViewModelFactory: OpenSwitchgearVlViewModelFactory,
            idVl: String,
        ): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return openSwitchgearVlViewModelFactory.create(idVl) as T
                }
            }
        }
    }
}