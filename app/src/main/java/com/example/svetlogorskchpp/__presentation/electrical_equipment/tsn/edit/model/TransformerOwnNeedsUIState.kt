package com.example.svetlogorskchpp.__presentation.electrical_equipment.tsn.edit.model

import com.example.svetlogorskchpp.__domain.en.electrical_equipment.Voltage
import java.util.UUID

data class TransformerOwnNeedsUIState(
    val id: String = UUID.randomUUID().toString(),
    val name: String = "",
    val panelMcp: String = "",
    val type: String = "",
    val parameterType: String = "",
    val transcriptType: String = "",
    val additionally: String = "",
    val isSpare: Boolean = false,
    val powerSupplyId: String = "",
    val powerSupplyCell: String = "",
    val powerSupplyName: String = "",
    val voltage: Voltage = Voltage.KV,
    val typeSwitch: String = "",
    val typeInsTr: String = "",
    val automation: String = "",
    val apv: String = "",
)
