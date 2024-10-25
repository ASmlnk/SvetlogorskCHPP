package com.example.svetlogorskchpp.__presentation.electrical_equipment.open_switchgear.model

import java.util.UUID

data class OpSwVlEditUIState(
    val id: String = UUID.randomUUID().toString(),
    val name: String = "",
    val panelMcp: String = "",
    val bysSystem: String = "",
    val cell: String = "",
    val isTransit: Boolean = false,
    val isVl: Boolean = false,
    val typeSwitch: String = "",
    val typeInsTr: String = "",
    val automation: String = "",
    val apv: String = "",
)
