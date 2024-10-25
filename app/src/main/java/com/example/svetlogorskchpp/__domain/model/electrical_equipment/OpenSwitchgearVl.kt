package com.example.svetlogorskchpp.__domain.model.electrical_equipment

import com.example.svetlogorskchpp.__domain.en.electrical_equipment.KeyOry
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.VoltageOry

data class OpenSwitchgearVl(
    val id: String,
    val name: String,
    val panelMcp: String,
    val bysSystem: String,
    val cell: String,
    val voltage: VoltageOry,
    val isTransit: Boolean,
    val isVl: Boolean,
    val typeSwitch: String,
    val typeInsTr: String,
    val automation: String,
    val apv: String,
    val keyShr1: KeyOry,
    val keyShr2: KeyOry,
    val keyLr: KeyOry,
    val keyOr: KeyOry,
    val phaseProtection: List<String>,
    val earthProtection: List<String>,
)
