package com.example.svetlogorskchpp.__presentation.electrical_equipment.open_switchgear.model

data class ProtectionUIState(
    val phaseProtection: List<String> = emptyList(),
    val earthProtection: List<String> = emptyList(),
)
