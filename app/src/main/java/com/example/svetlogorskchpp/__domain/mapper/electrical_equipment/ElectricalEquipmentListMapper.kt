package com.example.svetlogorskchpp.__domain.mapper.electrical_equipment

import com.example.svetlogorskchpp.__data.database.electrical_equipment.OpenSwitchgearTr.OpenSwitchgearTrEntity
import com.example.svetlogorskchpp.__data.database.electrical_equipment.OpenSwitchgearVl.OpenSwitchgearVlEntity
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

    private fun toInt(string: String): Int {
        val regex = "\\d+".toRegex()
        val match = regex.find(string)
        return match?.value?.toInt() ?: 0
    }
}