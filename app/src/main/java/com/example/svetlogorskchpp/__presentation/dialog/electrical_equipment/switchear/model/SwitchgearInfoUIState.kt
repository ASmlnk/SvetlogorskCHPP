package com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.switchear.model

import com.example.svetlogorskchpp.__domain.en.electrical_equipment.ElAssembly
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.NameDepartment
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.Voltage

data class SwitchgearInfoUIState(
    val name: String = "",
    val typeSwitch: String = "",
    val typeInsTr: String = "",
    val additionally: String = "",
    val category: ElAssembly = ElAssembly.OTHER,
    val nameDepartment: NameDepartment = NameDepartment.OTHER,
    val voltage: Voltage = Voltage.KV,
    val automation: String = "",
    val phaseProtection: List<String> = emptyList(),
    val earthProtection: List<String> = emptyList(),
    val additionallyRza: String = "",
    val info: String = "",
    val powerSupplyId1: String = "",
    val powerSupplyContent: String = "",
    val powerSupplyId2: String = "",
    val powerSupplyReserveId1: String = "",
    val powerSupplyReserveId2: String = "",
    val powerSupplyReserveId3: String = "",
    val powerSupplyReserveContent: String = "",
    val isAccessEdit: Boolean = false
)
