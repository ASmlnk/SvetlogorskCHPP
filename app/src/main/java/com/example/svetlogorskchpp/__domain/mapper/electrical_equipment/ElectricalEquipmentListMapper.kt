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

    fun toElectricalEquipmentSwitchgear(switchgear: SwitchgearEntity, idPowerSupply: String?): ElectricalEquipment.Switchgear {
        return with(switchgear) {
            ElectricalEquipment.Switchgear(
                id = id,
                name = nam,
                category = cat.let { ElAssembly.valueOf(it) },
                nameDepartment = namDep.let { NameDepartment.valueOf(it) },
                voltage = if (vol.isEmpty()) Voltage.KV else vol.let { Voltage.valueOf(it) },
                powerSupplyName = powerSupplyNameSwitchgear(switchgear,idPowerSupply),
                powerSupplyCell = powerSupplyCellSwitchgear(switchgear, idPowerSupply),
                cell = toInt(powerSupplyCellSwitchgear(switchgear, idPowerSupply))
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

    private fun powerSupplyNameSwitchgear(switchgear: SwitchgearEntity, idPowerSupply: String?): String {
        return if (idPowerSupply == null) {
                val powS1 = with(switchgear) { if (powSuC1.isBlank()) powSuNam1 else "$powSuNam1 № $powSuC1" }.replace("\n", "")
                val powS2 = with(switchgear) { if (powSuC2.isBlank()) powSuNam2 else "$powSuNam2 № $powSuC2" }.replace("\n", "")
                val powSR1 = with(switchgear) { if (powSuRC1.isBlank()) powSuRNam1 else "$powSuRNam1 № $powSuRC1" }.replace("\n", "")
                val powSR2 = with(switchgear) { if (powSuRC2.isBlank()) powSuRNam2 else "$powSuRNam2 № $powSuRC2" }.replace("\n", "")
                val powSR3 = with(switchgear) { if (powSuRC3.isBlank()) powSuRNam3 else "$powSuRNam3 № $powSuRC3" }.replace("\n", "")
           var result = powS1
            if (powS2.isNotBlank()) result += "\n" + powS2
            if (powSR1.isNotBlank()) result += "\n" + powSR1
            if (powSR2.isNotBlank()) result += "\n" + powSR2
            if (powSR3.isNotBlank()) result += "\n" + powSR3
            result
        } else {
            when(idPowerSupply) {
                switchgear.powSuId1 -> switchgear.powSuNam1
                switchgear.powSuId2 -> switchgear.powSuNam2
                switchgear.powSuRId1 ->switchgear.powSuRNam1
                switchgear.powSuRId2 ->switchgear.powSuRNam2
                switchgear.powSuRId3 ->switchgear.powSuRNam3
                else -> ""
            }
        }
    }

    private fun powerSupplyCellSwitchgear(switchgear: SwitchgearEntity, idPowerSupply: String?): String {
        return if (idPowerSupply == null) {
            ""
        } else {
            when(idPowerSupply) {
                switchgear.powSuId1 -> switchgear.powSuC1
                switchgear.powSuId2 -> switchgear.powSuC2
                switchgear.powSuRId1 ->switchgear.powSuRC1
                switchgear.powSuRId2 ->switchgear.powSuRC2
                switchgear.powSuRId3 ->switchgear.powSuRC3
                else -> ""
            }
        }
    }

    private fun toInt(string: String): Int {
        val regex = "\\d+".toRegex()
        val match = regex.find(string)
        return match?.value?.toInt() ?: 0
    }
}