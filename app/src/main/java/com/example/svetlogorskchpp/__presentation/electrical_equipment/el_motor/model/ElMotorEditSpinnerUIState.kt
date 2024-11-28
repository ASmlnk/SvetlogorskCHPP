package com.example.svetlogorskchpp.__presentation.electrical_equipment.el_motor.model

import com.example.svetlogorskchpp.__domain.en.electrical_equipment.ElCategory
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.ElGeneralCategory
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.Voltage

data class ElMotorEditSpinnerUIState(
    val category: ElCategory = ElCategory.OTHER,
    val generalCategory: ElGeneralCategory = ElGeneralCategory.OTHER,
    val voltage: Voltage = Voltage.KV
    )
