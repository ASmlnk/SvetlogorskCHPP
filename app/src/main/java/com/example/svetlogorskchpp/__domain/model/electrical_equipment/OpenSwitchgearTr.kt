package com.example.svetlogorskchpp.__domain.model.electrical_equipment

import com.example.svetlogorskchpp.__domain.en.electrical_equipment.KeyOry
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.Voltage

data class OpenSwitchgearTr(
    val id: String,
    val name: String,
    val panelMcp: String,
    val type: String,
    val parameterType: String,
    val transcriptType: String,
    val additionally: String,
    val isSpare: Boolean,
    val isThreeWinding: Boolean,

    val bysSystemVn: String,
    val cellVn: String,
    val voltageVn: Voltage,
    val typeSwitchVn: String,
    val typeInsTrVn: String,
    val keyShr1Vn: KeyOry,
    val keyShr2Vn: KeyOry,
    val keyLrVn: KeyOry,
    val keyOrVn: KeyOry,

    val bysSystemSn: String,
    val cellSn: String,
    val voltageSn: Voltage,
    val typeSwitchSn: String,
    val typeInsTrSn: String,
    val keyShr1Sn: KeyOry,
    val keyShr2Sn: KeyOry,
    val keyLrSn: KeyOry,
    val keyOrSn: KeyOry,

    val automation: String,
    val apv: String,
    val phaseProtection: List<String>,
    val earthProtection: List<String>,
)
