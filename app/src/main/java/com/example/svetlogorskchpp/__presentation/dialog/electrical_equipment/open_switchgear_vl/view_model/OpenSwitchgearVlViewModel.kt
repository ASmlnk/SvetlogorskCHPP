package com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.open_switchgear_vl.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.svetlogorskchpp.__domain.model.electrical_equipment.OpenSwitchgearVl
import com.example.svetlogorskchpp.__domain.usecases.equipments.EquipmentsUseCases
import com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.factory.OpenSwitchgearVlViewModelFactory
import com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.open_switchgear_vl.model.OpSwiVlDialogUIState
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OpenSwitchgearVlViewModel @AssistedInject constructor(
    private val useCases: EquipmentsUseCases<OpenSwitchgearVl>,
    @Assisted private val id: String,
) : ViewModel() {

    private val _uiState = MutableStateFlow<OpSwiVlDialogUIState>(OpSwiVlDialogUIState())
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
                            phaseProtection = openSwitchgearVl.phaseProtection.joinToString(separator = "\n"),
                            earthProtection = openSwitchgearVl.earthProtection.joinToString(separator = "\n")
                        )
                    }
                }
            }
        }
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