package com.example.svetlogorskchpp.__presentation.electrical_equipment.model

import com.example.svetlogorskchpp.__domain.en.electrical_equipment.ElAssembly
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.ElCategory
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.ElGeneralCategory
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.NameDepartment
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.Voltage

sealed class ElectricalEquipment {

    abstract fun name(): String
    abstract fun cell(): Int
    abstract val isDelete: Boolean

    data class Vl(
        val id: String,
        val nameEquipment: String,
        val isTransit: Boolean,
        val isVl: Boolean,
        val bysSystem: String,
        val voltage: Voltage,
        val cell: Int,
        override val isDelete: Boolean = false,
        val deepLink: DeepLink = DeepLink.VL
    ): ElectricalEquipment() {
        override fun name(): String {
            return nameEquipment
        }

        override fun cell(): Int {
            return cell
        }
    }

    data class Tr(
        val id: String,
        val nameEquipment: String,
        val isSpare: Boolean,
        val type: String,
        val typeParameter: String,
        val parameterOry: String,
        val nameNumber: Int,
        val isThreeWinding: Boolean,
        override val isDelete: Boolean = false,
        val deepLink: DeepLink = DeepLink.TR
    ): ElectricalEquipment(){
        override fun name(): String {
            return nameEquipment
        }

        override fun cell(): Int {
            return nameNumber
        }
    }

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
        override val isDelete: Boolean = false,
        val deepLink: DeepLink = DeepLink.TSN
    ): ElectricalEquipment(){
        override fun name(): String {
            return nameEquipment
        }

        override fun cell(): Int {
            return nameNumber
        }
    }

    data class Tg(
        val id: String,
        val nameEquipment: String,
        val nameNumber: Int,
        val typeGenerator: String,
        val typeTurbin: String,
        val powerEl: String,
        val powerThermal: String,
        val steamConsumption: String,
        override val isDelete: Boolean = false,
        val deepLink: DeepLink = DeepLink.TG
    ) : ElectricalEquipment(){
        override fun name(): String {
            return nameEquipment
        }

        override fun cell(): Int {
            return nameNumber
        }
    }

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
        val isRep: Boolean,
        override val isDelete: Boolean = false,
        val deepLink: DeepLink = DeepLink.EL_MOTOR
    ): ElectricalEquipment(){
        override fun name(): String {
            return name
        }

        override fun cell(): Int {
            return cell
        }
    }

    data class Switchgear(
        val id: String,
        val name: String,
        val category: ElAssembly,
        val nameDepartment: NameDepartment,
        val voltage: Voltage,
        override val isDelete: Boolean = false,
        val deepLink: DeepLink = DeepLink.SWITCHGEAR
    ): ElectricalEquipment(){
        override fun name(): String {
            return name
        }

        override fun cell(): Int {
            return 1000
        }
    }

    data class LightOther(
        val id: String,
        val name: String,
        val powerSupplyName: String,
        val powerSupplyCell: String,
        val isLighting: Boolean,
        val cell: Int,
        override val isDelete: Boolean = false,
        val deepLink: DeepLink = DeepLink.LIGHTING_AND_OTHER
    ): ElectricalEquipment(){
        override fun name(): String {
            return name
        }

        override fun cell(): Int {
            return cell
        }
    }
}