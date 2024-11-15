package com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.open_switchgear_vl.model

import com.example.svetlogorskchpp.__domain.en.electrical_equipment.KeyOry
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.Voltage

data class OpSwiVlDialogUIState(
    val name: String = "",
    val panelMcp: String= "",
    val bysSystem: String= "",
    val cell: String= "",
    val voltage: Voltage= Voltage.KV,
    val isTransit: Boolean= false,
    val typeSwitch: String= "",
    val typeInsTr: String= "",
    val automation: String= "",
    val apv: String= "",
    val keyShr1: KeyOry = KeyOry.KEY_0,
    val keyShr2: KeyOry =KeyOry.KEY_0,
    val keyLr: KeyOry=KeyOry.KEY_0,
    val keyOr: KeyOry=KeyOry.KEY_0,
    val phaseProtection: List<String> = emptyList(),
    val earthProtection: List<String> = emptyList(),
)
