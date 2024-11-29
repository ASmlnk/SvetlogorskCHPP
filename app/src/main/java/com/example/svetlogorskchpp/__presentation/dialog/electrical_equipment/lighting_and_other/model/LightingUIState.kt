package com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.lighting_and_other.model

data class LightingUIState(
    val name: String = "",
    val powerSupplyCell: String = "",
    val powerSupplyName: String = "",
    val typeSwitch: String = "",
    val additionally: String = "",
    val isLighting: Boolean = false,
    val location: String = ""
)
