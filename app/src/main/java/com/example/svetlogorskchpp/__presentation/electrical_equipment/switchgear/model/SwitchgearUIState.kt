package com.example.svetlogorskchpp.__presentation.electrical_equipment.switchgear.model

import com.example.svetlogorskchpp.__presentation.electrical_equipment.adapter.CustomSpinnerNameDepartment
import com.example.svetlogorskchpp.__presentation.electrical_equipment.model.ElectricalEquipment

data class SwitchgearUIState(
    val name: String = "",
    val listSwitchgear: List<ElectricalEquipment> = emptyList(),
    val id: String = "",
    val nameDepartment: String = "",
    val isAccessEdit: Boolean = false
) {
}