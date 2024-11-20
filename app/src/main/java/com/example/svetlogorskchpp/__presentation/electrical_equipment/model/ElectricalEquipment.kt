package com.example.svetlogorskchpp.__presentation.electrical_equipment.model

import com.example.svetlogorskchpp.__domain.en.electrical_equipment.ElAssembly
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.ElCategory
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.ElGeneralCategory
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.NameDepartment
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.Voltage

sealed class ElectricalEquipment {

    data class Vl(
        val id: String,
        val nameEquipment: String,
        val isTransit: Boolean,
        val isVl: Boolean,
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
        val isThreeWinding: Boolean,
        val deepLink: DeepLink = DeepLink.TR
    ): ElectricalEquipment()

    data class Tsn(
        val id: String,
        val nameEquipment: String,
        val isSpare: Boolean,
        val type: String,
        val typeParameter: String,
        val powerSupplyName: String,
        val powerSupplyCell: String,
        val nameNumber: Int,
        val voltage: Voltage,
        val deepLink: DeepLink = DeepLink.TSN
    ): ElectricalEquipment()

    data class Tg(
        val id: String,
        val nameEquipment: String,
        val nameNumber: Int,
        val typeGenerator: String,
        val typeTurbin: String,
        val powerEl: String,
        val powerThermal: String,
        val steamConsumption: String,
        val deepLink: DeepLink = DeepLink.TG
    ) : ElectricalEquipment()

    data class ElMotor(
        val id: String,
        val name: String,
        val category: ElCategory,
        val generalCategory: ElGeneralCategory,
        val powerEl: String,
        val voltage: Voltage,
        val i: String,
        val powerSupplyName: String,
        val powerSupplyCell: String,
        val cell: Int,
    ): ElectricalEquipment()

    data class Switchgear(
        val id: String,
        val name: String,
        val category: ElAssembly,
        val nameDepartment: NameDepartment,
        val voltage: Voltage
    ): ElectricalEquipment()

    data class LightOther(
        val id: String,
        val name: String,
        val powerSupplyName: String,
        val powerSupplyCell: String,
        val isLighting: Boolean,
        val cell: Int
    ): ElectricalEquipment()
}