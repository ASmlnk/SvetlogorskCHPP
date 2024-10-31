package com.example.svetlogorskchpp.__presentation.electrical_equipment.open_switchgear.model

import com.example.svetlogorskchpp.__domain.en.electrical_equipment.KeyOry
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.Voltage

data class SpinnerEditState(
    val keyShr1: KeyOry = KeyOry.KEY_0,
    val keyShr2: KeyOry = KeyOry.KEY_0,
    val keyLr: KeyOry = KeyOry.KEY_0,
    val keyOr: KeyOry = KeyOry.KEY_0,
    val voltage: Voltage = Voltage.KV
)
