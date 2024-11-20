package com.example.svetlogorskchpp.__domain.model.electrical_equipment

import com.example.svetlogorskchpp.__domain.en.electrical_equipment.ElAssembly
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.NameDepartment
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.Voltage

data class Switchgear (
    val id: String,
    val name: String,
    val typeSwitch: String,
    val typeInsTr: String,
    val additionally: String,
    val category: ElAssembly,
    val nameDepartment: NameDepartment,
    val voltage: Voltage,

    val automation: String,
    val phaseProtection: List<String>,
    val earthProtection: List<String>,
    val additionallyRza: String,
    val info: String,

    val powerSupplyId1: String,
    val powerSupplyCell1: String,
    val powerSupplyName1: String,

    val powerSupplyId2: String,
    val powerSupplyCell2: String,
    val powerSupplyName2: String,

    val powerSupplyReserveId1: String,
    val powerSupplyReserveCell1: String,
    val powerSupplyReserveName1: String,

    val powerSupplyReserveId2: String,
    val powerSupplyReserveCell2: String,
    val powerSupplyReserveName2: String,

    val powerSupplyReserveId3: String,
    val powerSupplyReserveCell3: String,
    val powerSupplyReserveName3: String,
)