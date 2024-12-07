package com.example.svetlogorskchpp.__domain.mapper.electrical_equipment

import com.example.svetlogorskchpp.__data.database.electrical_equipment.ElMotor.ElMotorEntity
import com.example.svetlogorskchpp.__data.database.electrical_equipment.LightingAndOther.LightingAndOtherEntity
import com.example.svetlogorskchpp.__data.database.electrical_equipment.OpenSwitchgearTr.OpenSwitchgearTrEntity
import com.example.svetlogorskchpp.__data.database.electrical_equipment.OpenSwitchgearVl.OpenSwitchgearVlEntity
import com.example.svetlogorskchpp.__data.database.electrical_equipment.Switchgear.SwitchgearEntity
import com.example.svetlogorskchpp.__data.database.electrical_equipment.transformerOwnNeeds.TransformerOwnNeedsEntity
import com.example.svetlogorskchpp.__data.database.electrical_equipment.turbogenerator.TurboGeneratorEntity
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.ElAssembly
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.ElCategory
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.ElGeneralCategory
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.NameDepartment
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.Voltage
import com.example.svetlogorskchpp.__presentation.electrical_equipment.model.ElectricalEquipment
import javax.inject.Inject

class ElectricalEquipmentListMapper @Inject constructor() {

    fun toElectricalEquipmentVl(openSwitchgearVlEntity: OpenSwitchgearVlEntity): ElectricalEquipment.Vl {
        return with(openSwitchgearVlEntity) {
            ElectricalEquipment.Vl(
                id = id,
                nameEquipment = name,
                isTransit = isTransit,
                bysSystem = bysSystem,
                voltage = voltage.let { Voltage.valueOf(it) },
                cell = toInt(cell),
                isVl = isVl
            )
        }
    }

    fun toElectricalEquipmentTr(openSwitchgearTrEntity: OpenSwitchgearTrEntity): ElectricalEquipment.Tr {
        val voltageVn = openSwitchgearTrEntity.voltageVn.let { Voltage.valueOf(it) }
        val voltageSn = openSwitchgearTrEntity.voltageSn.let { Voltage.valueOf(it) }
        val parameter = if (openSwitchgearTrEntity.isThreeWinding) {
            "  ${openSwitchgearTrEntity.bysSystemVn}СШ яч. ${openSwitchgearTrEntity.cellVn} ОРУ-${voltageVn.str}" + "\n" +
                    "${openSwitchgearTrEntity.bysSystemSn}СШ яч. ${openSwitchgearTrEntity.cellSn} ОРУ-${voltageSn.str}"
        } else {
            "${openSwitchgearTrEntity.bysSystemVn}СШ яч.${openSwitchgearTrEntity.cellVn} ОРУ-${voltageVn.str}"
        }

        return with(openSwitchgearTrEntity) {

            ElectricalEquipment.Tr(
                id = id,
                nameEquipment = name,
                isSpare = isSpare,
                type = type,
                typeParameter = parameterType,
                parameterOry = parameter,
                isThreeWinding = isThreeWinding,
                nameNumber = toInt(name)
            )
        }
    }

    fun toElectricalEquipmentTsn(transformerOwnNeedsEntity: TransformerOwnNeedsEntity): ElectricalEquipment.Tsn {
        return with(transformerOwnNeedsEntity) {
            ElectricalEquipment.Tsn(
                id = id,
                nameEquipment = name,
                isSpare = isSpare,
                type = type,
                typeParameter = parameterType,
                powerSupplyName = powerSupplyName,
                powerSupplyCell = powerSupplyCell,
                nameNumber = toInt(name),
                voltage = voltage.let { Voltage.valueOf(it) },
            )
        }
    }

    fun toElectricalEquipmentTg(turboGeneratorEntity: TurboGeneratorEntity): ElectricalEquipment.Tg {
        return with(turboGeneratorEntity) {
            ElectricalEquipment.Tg(
                id = id,
                nameEquipment = name,
                nameNumber = toInt(name),
                typeGenerator = typeGenerator,
                typeTurbin = typeTurbin,
                powerEl = powerEl,
                powerThermal = powerThermal,
                steamConsumption = steamConsumption
            )
        }
    }

    fun toElectricalEquipmentElMotor(elMotor: ElMotorEntity): ElectricalEquipment.ElMotor {
        return with(elMotor) {
            ElectricalEquipment.ElMotor(
                id = id,
                name = nam,
                category = cat.let { ElCategory.valueOf(it) },
                generalCategory = gCat.let { ElGeneralCategory.valueOf(it) },
                powerEl = powEl,
                voltage = vol.let { Voltage.valueOf(it) },
                i = i,
                powerSupplyName = powSuNam,
                powerSupplyCell = powSuC,
                isRep = isRep,
                cell = toInt(powSuC)
            )
        }
    }

    fun toElectricalEquipmentSwitchgear(switchgear: SwitchgearEntity): ElectricalEquipment.Switchgear {
        return with(switchgear) {
            ElectricalEquipment.Switchgear(
                id = id,
                name = nam,
                category = cat.let { ElAssembly.valueOf(it) },
                nameDepartment = namDep.let { NameDepartment.valueOf(it) },
                voltage = if (vol.isEmpty()) Voltage.KV else vol.let { Voltage.valueOf(it) }
            )
        }
    }

    fun toElectricalEquipmentLightOther(lihgtOther: LightingAndOtherEntity): ElectricalEquipment.LightOther {
        return with(lihgtOther) {
            ElectricalEquipment.LightOther(
                id = id,
                name = nam,
                powerSupplyName = powSuNam,
                powerSupplyCell = powSuC,
                isLighting = isLi,
                cell = toInt(powSuC),
            )
        }
    }


    private fun toInt(string: String): Int {
        val regex = "\\d+".toRegex()
        val match = regex.find(string)
        return match?.value?.toInt() ?: 0
    }
}