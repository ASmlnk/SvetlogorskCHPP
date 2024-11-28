package com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.switchear.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.svetlogorskchpp.__domain.model.electrical_equipment.Switchgear
import com.example.svetlogorskchpp.__domain.usecases.equipments.EquipmentsUseCases
import com.example.svetlogorskchpp.__domain.usecases.equipments.all_equipment.EquipmentAllUseCases
import com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.factory.SwitchgearOwnNeedsInfoViewModelFactory
import com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.switchear.model.SwitchgearInfoUIState
import com.example.svetlogorskchpp.__presentation.electrical_equipment.model.ElectricalEquipment
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SwitchgearOwnNeedsInfoViewModel @AssistedInject constructor(
    @Assisted private val id: String,
    private val useCases: EquipmentsUseCases<Switchgear>,
    private val useCasesAllEquipment: EquipmentAllUseCases,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SwitchgearInfoUIState())
    val uiState: StateFlow<SwitchgearInfoUIState> get() = _uiState

    private val _powerSupplyState = MutableStateFlow<List<ElectricalEquipment>>(emptyList())
    val powerSupplyState: StateFlow<List<ElectricalEquipment>> get() = _powerSupplyState

    private val _powerSupplyReserveState = MutableStateFlow<List<ElectricalEquipment>>(emptyList())
    val powerSupplyReserveState: StateFlow<List<ElectricalEquipment>> get() = _powerSupplyReserveState

    init {
        viewModelScope.launch {
            useCases.getItemEquipment(id).collect { switchgear ->
                switchgear?.let {
                    _uiState.update { oldState ->
                        oldState.copy(
                            name = switchgear.name,
                            typeSwitch = switchgear.typeSwitch,
                            typeInsTr = switchgear.typeInsTr,
                            additionally = switchgear.additionally,
                            category = switchgear.category,
                            nameDepartment = switchgear.nameDepartment,
                            voltage = switchgear.voltage,
                            automation = switchgear.automation,
                            phaseProtection = switchgear.phaseProtection,
                            earthProtection = switchgear.earthProtection,
                            additionallyRza = switchgear.additionallyRza,
                            info = switchgear.info,
                            powerSupplyId1 = switchgear.powerSupplyId1,
                            powerSupplyId2 = switchgear.powerSupplyId2,
                            powerSupplyContent = with(switchgear) {
                                powerSupplyContent(
                                    powerSupplyName1,
                                    powerSupplyCell1,
                                    powerSupplyName2,
                                    powerSupplyCell2
                                )
                            },
                            powerSupplyReserveId1 = switchgear.powerSupplyReserveId1,
                            powerSupplyReserveId2 = switchgear.powerSupplyReserveId2,
                            powerSupplyReserveId3 = switchgear.powerSupplyReserveId3,
                            powerSupplyReserveContent = with(switchgear) {
                                powerSupplyReserveContent(
                                    powerSupplyReserveName1,
                                    powerSupplyReserveCell1,
                                    powerSupplyReserveName2,
                                    powerSupplyReserveCell2,
                                    powerSupplyReserveName3,
                                    powerSupplyReserveCell3
                                )
                            },

                            )
                    }
                    updatePowerSupply(switchgear.powerSupplyId1, switchgear.powerSupplyId2)
                    updatePowerReserveSupply(
                        switchgear.powerSupplyReserveId1,
                        switchgear.powerSupplyReserveId2,
                        switchgear.powerSupplyReserveId3
                    )
                }
            }
        }
    }


    private fun updatePowerSupply(id1: String, id2: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val flow = combine(
                useCasesAllEquipment.getEquipmentPowerSupplyFlow(id1),
                useCasesAllEquipment.getEquipmentPowerSupplyFlow(id2)
            ) { ps1, ps2 ->
                ps1 + ps2
            }
            flow.collect {
                _powerSupplyState.value = it
            }
        }
    }

    private fun updatePowerReserveSupply(id1: String, id2: String, id3: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val flow = combine(
                useCasesAllEquipment.getEquipmentPowerSupplyFlow(id1),
                useCasesAllEquipment.getEquipmentPowerSupplyFlow(id2),
                useCasesAllEquipment.getEquipmentPowerSupplyFlow(id3)
            ) { ps1, ps2, ps3 ->
                ps1 + ps2 + ps3
            }
            flow.collect {
                _powerSupplyReserveState.value = it
            }
        }
    }

    private fun powerSupplyContent(
        powerSupplyName1: String,
        powerSupplyCell1: String,
        powerSupplyName2: String,
        powerSupplyCell2: String,
    ): String {
        val name1 = powerSupplyName1
        val cell1 = if (powerSupplyCell1.isNotEmpty()) "  № $powerSupplyCell1" else ""
        val name2 = if (powerSupplyName2.isNotEmpty()) "\n$powerSupplyName2" else ""
        val cell2 = if (powerSupplyCell2.isNotEmpty()) "  № $powerSupplyCell2" else ""
        return name1 + cell1 + name2 + cell2
    }

    private fun powerSupplyReserveContent(
        powerSupplyReserveName1: String,
        powerSupplyReserveCell1: String,
        powerSupplyReserveName2: String,
        powerSupplyReserveCell2: String,
        powerSupplyReserveName3: String,
        powerSupplyReserveCell3: String,
    ): String {
        val name1 = powerSupplyReserveName1
        val cell1 = if (powerSupplyReserveCell1.isNotEmpty()) "  № $powerSupplyReserveCell1" else ""
        val name2 = if (powerSupplyReserveName2.isNotEmpty()) "\n$powerSupplyReserveName2" else ""
        val cell2 = if (powerSupplyReserveCell2.isNotEmpty()) "  № $powerSupplyReserveCell2" else ""
        val name3 = if (powerSupplyReserveName3.isNotEmpty()) "\n$powerSupplyReserveName3" else ""
        val cell3 = if (powerSupplyReserveCell3.isNotEmpty()) "  № $powerSupplyReserveCell3" else ""
        return name1 + cell1 + name2 + cell2 + name3 + cell3
    }

    companion object {
        fun providesFactory(
            factory: SwitchgearOwnNeedsInfoViewModelFactory,
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