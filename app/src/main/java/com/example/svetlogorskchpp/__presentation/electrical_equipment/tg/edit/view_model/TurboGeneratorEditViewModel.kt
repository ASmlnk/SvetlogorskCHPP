package com.example.svetlogorskchpp.__presentation.electrical_equipment.tg.edit.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.svetlogorskchpp.BaseEditViewModel
import com.example.svetlogorskchpp.__domain.OperationResult
import com.example.svetlogorskchpp.__domain.model.electrical_equipment.TurboGenerator
import com.example.svetlogorskchpp.__domain.usecases.equipments.EquipmentsUseCases
import com.example.svetlogorskchpp.__presentation.electrical_equipment.factory.TurboGeneratorEditViewModelFactory
import com.example.svetlogorskchpp.__presentation.electrical_equipment.tg.edit.model.TurbogeneratorUIState
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TurboGeneratorEditViewModel @AssistedInject constructor(
    private val useCases: EquipmentsUseCases<TurboGenerator>,
    @Assisted private val id: String,
) : BaseEditViewModel() {

    private val _turbogeneratorUIState = MutableStateFlow(TurbogeneratorUIState())
    val turbogeneratorUIState: StateFlow<TurbogeneratorUIState> =
        _turbogeneratorUIState

    init {
        viewModelScope.launch {
            useCases.getItemEquipment(id).collect { tg ->
                tg?.let {
                    _turbogeneratorUIState.update { old ->
                        old.copy(
                            id = it.id,
                            name = it.name,
                            panelMcp = it.panelMcp,
                            typeSwitch = it.typeSwitch,
                            typeInsTr = it.typeInsTr,
                            typeGenerator = it.typeGenerator,
                            transcriptTypeGenerator = it.transcriptTypeGenerator,
                            volumeTg = it.volumeTg,
                            volumeReceiver = it.volumeReceiver,
                            additionallyGenerator = it.additionallyGenerator,
                            sourceExcitation = it.sourceExcitation,
                            generatorStarted = it.generatorStarted,
                            translationIntoRv = it.translationIntoRv,
                            translationFromRv = it.translationFromRv,
                            typeTurbin = it.typeTurbin,
                            transcriptTypeTurbin = it.transcriptTypeTurbin,
                            powerEl = it.powerEl,
                            powerThermal = it.powerThermal,
                            steamConsumption = it.steamConsumption,
                            additionallyTurbin = it.additionallyTurbin,
                            powerSupplyId = it.powerSupplyId,
                            powerSupplyCell = it.powerSupplyCell,
                            powerSupplyName = it.powerSupplyName,
                            automation = it.automation,
                            additionallyRza1 = it.additionallyRza1,
                            additionallyRza2 = it.additionallyRza2,
                        )
                    }


                    _protectionUIState.update { old ->
                        old.copy(
                            phaseProtection = it.phaseProtection,
                            earthProtection = it.earthProtection
                        )
                    }
                }
            }
        }
    }


    fun saveState(state: TurbogeneratorUIState) {
        _turbogeneratorUIState.update { state }
    }

    fun saveParameterTg (tg: TurbogeneratorUIState) {
        val parameterTg = TurboGenerator(
            id = tg.id,
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
            phaseProtection = protectionUIState.value.phaseProtection,
            earthProtection = protectionUIState.value.earthProtection,
            additionallyRza1 = tg.additionallyRza1,
            additionallyRza2 = tg.additionallyRza2
        )
        viewModelScope.launch {
            _turbogeneratorUIState.update { tg }
            val resultToast = useCases.saveItemEquipment(parameterTg)
            when (resultToast) {
                is OperationResult.Error -> _toastResultFlow.emit(resultToast.massage)
                is OperationResult.Success -> _toastResultFlow.emit(resultToast.data)
            }
        }
    }

    companion object {
        fun providesFactory(
            factory: TurboGeneratorEditViewModelFactory,
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