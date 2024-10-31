package com.example.svetlogorskchpp.__presentation.electrical_equipment.open_switchgear.model

import java.util.UUID

data class OpSwTrEditUIState(
    val id: String = UUID.randomUUID().toString(),
    val name: String = "",
    val panelMcp: String = "",
    val type: String = "",
    val parameterType: String = "",
    val transcriptType: String = "",
    val additionally: String = "",
    val isSpare: Boolean = false,
    val isThreeWinding: Boolean = false,
    val bysSystemVn: String = "",
    val cellVn: String = "",
    val typeSwitchVn: String = "",
    val typeInsTrVn: String = "",
    val bysSystemSn: String = "",
    val cellSn: String = "",
    val typeSwitchSn: String = "",
    val typeInsTrSn: String = "",
    val automation: String = "",
    val apv: String = "",
)
