package com.example.svetlogorskchpp.__data.database.electrical_equipment.OpenSwitchgearVl

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.KeyOry
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.VoltageOry

@Entity (tableName = "open_switchgear_vl")
data class OpenSwitchgearVlEntity(
    @PrimaryKey val id: String,
    val name: String,
    val panelMcp: String,
    val bysSystem: String,
    val cell: String,
    val voltage: String,
    val isTransit: Boolean,
    val isVl: Boolean,
    val typeSwitch: String,
    val typeInsTr: String,
    val automation: String,
    val apv: String,
    val keyShr1: String,
    val keyShr2: String,
    val keyLr: String,
    val keyOr: String,
    val phaseProtection: String,
    val earthProtection: String,
)
