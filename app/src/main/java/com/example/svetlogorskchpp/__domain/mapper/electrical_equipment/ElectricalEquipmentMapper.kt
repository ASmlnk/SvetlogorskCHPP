package com.example.svetlogorskchpp.__domain.mapper.electrical_equipment

import com.example.svetlogorskchpp.__data.database.electrical_equipment.OpenSwitchgearVl.OpenSwitchgearVlEntity
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.VoltageOry
import com.example.svetlogorskchpp.__presentation.electrical_equipment.model.ElectricalEquipment
import javax.inject.Inject

class ElectricalEquipmentMapper @Inject constructor() {

    fun toElectricalEquipmentVl(openSwitchgearVlEntity: OpenSwitchgearVlEntity) : ElectricalEquipment.Vl {
        return with(openSwitchgearVlEntity) {
            ElectricalEquipment.Vl (
                id = id,
                nameEquipment = name,
                isTransit = isTransit,
                bysSystem = bysSystem,
                voltageOry = voltage.let { VoltageOry.valueOf(it) },
                cell = toInt(cell)
            )
        }
    }

    private fun toInt(string: String) : Int {
        val regex = "\\d+".toRegex()
        val match = regex.find(string)
        return match?.value?.toInt() ?: 0
    }
}