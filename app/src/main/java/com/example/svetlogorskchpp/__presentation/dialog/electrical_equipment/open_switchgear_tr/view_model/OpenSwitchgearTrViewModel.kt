package com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.open_switchgear_tr.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.svetlogorskchpp.__domain.model.electrical_equipment.OpenSwitchgearTr
import com.example.svetlogorskchpp.__domain.usecases.equipments.EquipmentsUseCases
import com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.factory.OpenSwitchgearTrViewModelFactory
import com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.open_switchgear_vl.model.OpSwiTrDialogUIState
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OpenSwitchgearTrViewModel @AssistedInject constructor(
    private val useCases: EquipmentsUseCases<OpenSwitchgearTr>,
    @Assisted private val id: String,
) : ViewModel() {

    private val _uiState = MutableStateFlow(OpSwiTrDialogUIState())
    val uiState: StateFlow<OpSwiTrDialogUIState> get() = _uiState

    init {
        viewModelScope.launch {
            useCases.getItemEquipment(id).collect { openSwitchgearTr ->
                openSwitchgearTr?.let {
                    _uiState.update { oldState ->
                        oldState.copy(
                            name = openSwitchgearTr.name,
                            panelMcp = openSwitchgearTr.panelMcp,
                            type = openSwitchgearTr.type,
                            parameterType = openSwitchgearTr.parameterType,
                            transcriptType = openSwitchgearTr.transcriptType,
                            additionally = openSwitchgearTr.additionally,
                            isSpare = openSwitchgearTr.isSpare,
                            isThreeWinding = openSwitchgearTr.isThreeWinding,
                            bysSystemVn = openSwitchgearTr.bysSystemVn,
                            cellVn = openSwitchgearTr.cellVn,
                            voltageVn = openSwitchgearTr.voltageVn,
                            typeSwitchVn = openSwitchgearTr.typeSwitchVn,
                            typeInsTrVn = openSwitchgearTr.typeInsTrVn,
                            keyShr1Vn = openSwitchgearTr.keyShr1Vn,
                            keyShr2Vn = openSwitchgearTr.keyShr2Vn,
                            keyLrVn = openSwitchgearTr.keyLrVn,
                            keyOrVn = openSwitchgearTr.keyOrVn,
                            bysSystemSn = openSwitchgearTr.bysSystemSn,
                            cellSn = openSwitchgearTr.cellSn,
                            voltageSn = openSwitchgearTr.voltageSn,
                            typeSwitchSn = openSwitchgearTr.typeSwitchSn,
                            typeInsTrSn = openSwitchgearTr.typeInsTrSn,
                            keyShr1Sn = openSwitchgearTr.keyShr1Sn,
                            keyShr2Sn = openSwitchgearTr.keyShr2Sn,
                            keyLrSn = openSwitchgearTr.keyLrSn,
                            keyOrSn = openSwitchgearTr.keyOrSn,
                            automation = openSwitchgearTr.automation,
                            apv = openSwitchgearTr.apv,
                            phaseProtection = openSwitchgearTr.phaseProtection.joinToString(separator = "\n"),
                            earthProtection = openSwitchgearTr.earthProtection.joinToString(separator = "\n"),
                        )
                    }
                }
            }
        }
    }

    companion object {
        fun providesFactory(
            openSwitchgearTrViewModelFactory: OpenSwitchgearTrViewModelFactory,
            idTr: String,
        ): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return openSwitchgearTrViewModelFactory.create(idTr) as T
                }
            }
        }
    }
}