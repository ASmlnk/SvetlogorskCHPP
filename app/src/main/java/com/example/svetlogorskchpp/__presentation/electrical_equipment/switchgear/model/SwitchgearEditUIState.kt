package com.example.svetlogorskchpp.__presentation.electrical_equipment.switchgear.model

import java.util.UUID

data class SwitchgearEditUIState(
    val id: String = UUID.randomUUID().toString(),
    val name: String = "",
    val typeSwitch: String = "",
    val typeInsTr: String = "",
    val additionally: String = "",
    val automation: String = "",
    val additionallyRza: String = "",
    val info: String = "",
    val powerSupplyId1: String = "",
    val powerSupplyCell1: String = "",
    val powerSupplyName1: String = "",
    val powerSupplyId2: String = "",
    val powerSupplyCell2: String = "",
    val powerSupplyName2: String = "",
    val powerSupplyReserveId1: String = "",
    val powerSupplyReserveCell1: String = "",
    val powerSupplyReserveName1: String = "",
    val powerSupplyReserveId2: String = "",
    val powerSupplyReserveCell2: String = "",
    val powerSupplyReserveName2: String = "",
    val powerSupplyReserveId3: String = "",
    val powerSupplyReserveCell3: String = "",
    val powerSupplyReserveName3: String = "",
)
