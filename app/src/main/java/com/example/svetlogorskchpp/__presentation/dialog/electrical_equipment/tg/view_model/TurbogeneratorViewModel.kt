package com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.tg.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.svetlogorskchpp.__domain.model.electrical_equipment.TurboGenerator
import com.example.svetlogorskchpp.__domain.usecases.equipments.EquipmentsUseCases
import com.example.svetlogorskchpp.__domain.usecases.equipments.all_equipment.EquipmentAllUseCases
import com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.factory.TurbogeneratorViewModelFactory
import com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.tg.model.TgUIState
import com.example.svetlogorskchpp.__presentation.electrical_equipment.model.ElectricalEquipment
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TurbogeneratorViewModel @AssistedInject constructor(
    private val useCasesTg: EquipmentsUseCases<TurboGenerator>,
    private val useCasesAllEquipment: EquipmentAllUseCases,
    @Assisted val id: String,
) : ViewModel() {

    private val _uiState = MutableStateFlow(TgUIState())
    val uiState: StateFlow<TgUIState> get() = _uiState

    private val _powerSupplyState = MutableStateFlow<List<ElectricalEquipment>>(emptyList())
    val powerSupplyState: StateFlow<List<ElectricalEquipment>> get() = _powerSupplyState

    init {
        viewModelScope.launch {
            useCasesTg.getItemEquipment(id).collect { tg ->
                tg?.let {
                    _uiState.update { oldState ->
                        oldState.copy(
                            name = tg.name,
                            panelMcp = tg.panelMcp,
                            typeSwitch = tg.typeSwitch,
                            typeInsTr = tg.typeInsTr,
                            typeGenerator = tg.typeGenerator,
                            transcriptTypeGenerator = tg.transcriptTypeGenerator,
                            volumeTg = tg.volumeTg,
                            volumeReceiver = tg.volumeReceiver,
                            additionallyGenerator = tg.additionallyGenerator,
                            sourceExcitation = tg.sourceExcitation,
                            generatorStarted = tg.generatorStarted,
                            translationIntoRv = tg.translationIntoRv,
                            translationFromRv = tg.translationFromRv,
                            isViewGeneratorStarted = false,
                            isViewTranslationIntoRv = false,
                            isViewTranslationFromRv = false,
                            typeTurbin = tg.typeTurbin,
                            transcriptTypeTurbin = tg.transcriptTypeTurbin,
                            powerEl = tg.powerEl,
                            powerThermal = tg.powerThermal,
                            steamConsumption = tg.steamConsumption,
                            additionallyTurbin = tg.additionallyTurbin,
                            powerSupplyId = tg.powerSupplyId,
                            powerSupplyCell = tg.powerSupplyCell,
                            powerSupplyName = tg.powerSupplyName,
                            automation = tg.automation,
                            additionallyRza1 = tg.additionallyRza1,
                            additionallyRza2 = tg.additionallyRza2,
                            phaseProtection = tg.phaseProtection,
                            earthProtection = tg.earthProtection,
                        )
                    }
                    updatePowerSupply(tg.powerSupplyId)
                }
            }
        }
    }

    fun onClickGeneratorStarted() {
        val state = _uiState.value.isViewGeneratorStarted
        _uiState.update { it.copy(isViewGeneratorStarted = !state) }
    }

    fun onClickTranslationIntoRv() {
        val state = _uiState.value.isViewTranslationIntoRv
        _uiState.update { it.copy(isViewTranslationIntoRv = !state) }
    }

    fun onClickTranslationFromRv() {
        val state = _uiState.value.isViewTranslationFromRv
        _uiState.update { it.copy(isViewTranslationFromRv = !state) }
    }

    private fun updatePowerSupply(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val flow = useCasesAllEquipment.getEquipmentPowerSupplyFlow(id)
            flow.collect {
                _powerSupplyState.value = it
            }
        }
    }

    companion object {
        fun providesFactory(
            factory: TurbogeneratorViewModelFactory,
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