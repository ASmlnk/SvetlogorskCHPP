package com.example.svetlogorskchpp.__data.database.electrical_equipment.OpenSwitchgearTr

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.KeyOry
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.Voltage
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.VoltageTr

@Entity(tableName = "open_switchgear_tr")
data class OpenSwitchgearTrEntity(
    @PrimaryKey val id: String,
    val name: String,
    val panelMcp: String,
    val type: String,
    val parameterType: String,
    val transcriptType: String,
    val additionally: String,
    val isSpare: Boolean,
    val isThreeWinding: Boolean,

    val bysSystemVn: String,
    val cellVn: String,
    val voltageVn: String,
    val typeSwitchVn: String,
    val typeInsTrVn: String,
    val keyShr1Vn: String,
    val keyShr2Vn: String,
    val keyLrVn: String,
    val keyOrVn: String,

    val bysSystemSn: String,
    val cellSn: String,
    val voltageSn: String,
    val typeSwitchSn: String,
    val typeInsTrSn: String,
    val keyShr1Sn: String,
    val keyShr2Sn: String,
    val keyLrSn: String,
    val keyOrSn: String,

    val automation: String,
    val apv: String,
    val phaseProtection: String,
    val earthProtection: String,
)
