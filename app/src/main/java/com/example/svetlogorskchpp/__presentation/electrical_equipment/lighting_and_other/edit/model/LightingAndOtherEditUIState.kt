package com.example.svetlogorskchpp.__presentation.electrical_equipment.lighting_and_other.edit.model

import java.util.UUID

data class LightingAndOtherEditUIState(
    val id: String = UUID.randomUUID().toString(),
    val name: String = "",
    val powerSupplyId: String = "",
    val powerSupplyCell: String = "",
    val powerSupplyName: String = "",
    val typeSwitch: String = "",
    val additionally: String = "",
    val isLighting: Boolean = false,
    val location: String = ""
)
