package com.example.svetlogorskchpp.__presentation.electrical_equipment.switchgear.model

import com.example.svetlogorskchpp.__domain.en.electrical_equipment.ElAssembly
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.NameDepartment
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.Voltage

data class SwitchgearEditSpinnerUIState(
    val category: ElAssembly = ElAssembly.OTHER,
    val nameDepartment: NameDepartment = NameDepartment.OTHER,
    val voltage: Voltage = Voltage.KV,
)
