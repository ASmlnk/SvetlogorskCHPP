package com.example.svetlogorskchpp.__presentation.electrical_equipment.tg.edit.model

import java.util.UUID

data class TurbogeneratorUIState(
    val id: String = UUID.randomUUID().toString(),
    val name: String = "",
    val panelMcp: String = "",
    val typeSwitch: String = "",
    val typeInsTr: String = "",
    val typeGenerator: String = "",
    val transcriptTypeGenerator: String = "",
    val volumeTg: String = "",
    val volumeReceiver: String = "",
    val additionallyGenerator: String = "",
    val sourceExcitation: String = "",
    val generatorStarted: String = "",
    val translationIntoRv: String = "",
    val translationFromRv: String = "",
    val typeTurbin: String = "",
    val transcriptTypeTurbin: String = "",
    val powerEl: String = "",
    val powerThermal: String = "",
    val steamConsumption: String = "",
    val additionallyTurbin: String = "",
    val powerSupplyId: String = "",
    val powerSupplyCell: String = "",
    val powerSupplyName: String = "",
    val automation: String = "",
    val additionallyRza1: String = "",
    val additionallyRza2: String = "",
)
