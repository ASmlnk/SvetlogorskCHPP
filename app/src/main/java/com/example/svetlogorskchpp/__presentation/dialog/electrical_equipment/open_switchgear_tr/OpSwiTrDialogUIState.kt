package com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.open_switchgear_tr

import com.example.svetlogorskchpp.__domain.en.electrical_equipment.KeyOry
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.Voltage

data class OpSwiTrDialogUIState(
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
    val voltageVn: Voltage = Voltage.KV,
    val typeSwitchVn: String = "",
    val typeInsTrVn: String = "",
    val keyShr1Vn: KeyOry = KeyOry.KEY_0,
    val keyShr2Vn: KeyOry = KeyOry.KEY_0,
    val keyLrVn: KeyOry = KeyOry.KEY_0,
    val keyOrVn: KeyOry = KeyOry.KEY_0,
    val bysSystemSn: String = "",
    val cellSn: String = "",
    val voltageSn: Voltage = Voltage.KV,
    val typeSwitchSn: String = "",
    val typeInsTrSn: String = "",
    val keyShr1Sn: KeyOry = KeyOry.KEY_0,
    val keyShr2Sn: KeyOry = KeyOry.KEY_0,
    val keyLrSn: KeyOry = KeyOry.KEY_0,
    val keyOrSn: KeyOry = KeyOry.KEY_0,
    val automation: String = "",
    val apv: String = "",
    val phaseProtection: List<String> = emptyList(),
    val earthProtection: List<String> = emptyList(),
)