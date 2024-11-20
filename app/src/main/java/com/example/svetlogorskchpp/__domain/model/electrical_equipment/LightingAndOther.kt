package com.example.svetlogorskchpp.__domain.model.electrical_equipment

data class LightingAndOther (
    val id: String,
    val name: String,
    val powerSupplyId: String,
    val powerSupplyCell: String,
    val powerSupplyName: String,
    val typeSwitch: String,
    val additionally: String,
    val isLighting: Boolean,
    val location: String
)