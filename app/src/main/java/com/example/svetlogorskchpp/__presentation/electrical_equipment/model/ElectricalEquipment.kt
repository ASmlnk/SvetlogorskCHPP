package com.example.svetlogorskchpp.__presentation.electrical_equipment.model

import com.example.svetlogorskchpp.__domain.en.electrical_equipment.VoltageOry

sealed class ElectricalEquipment {

    data class Vl(
        val id: String,
        val nameEquipment: String,
        val isTransit: Boolean,
        val bysSystem: String,
        val voltageOry: VoltageOry,
        val cell: Int,
    ): ElectricalEquipment()


}