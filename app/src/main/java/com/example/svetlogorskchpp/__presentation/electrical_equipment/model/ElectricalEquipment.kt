package com.example.svetlogorskchpp.__presentation.electrical_equipment.model

import com.example.svetlogorskchpp.__domain.en.electrical_equipment.Voltage

sealed class ElectricalEquipment {

    data class Vl(
        val id: String,
        val nameEquipment: String,
        val isTransit: Boolean,
        val bysSystem: String,
        val voltage: Voltage,
        val cell: Int,
    ): ElectricalEquipment()

    data class Tr(
        val id: String,
        val nameEquipment: String,
        val isSpare: Boolean,
        val type: String,
        val typeParameter: String,
        val parameterOry: String,
        val nameNumber: Int,
        val isThreeWinding: Boolean
    ): ElectricalEquipment()

}