package com.example.svetlogorskchpp.__data.database.electrical_equipment.transformerOwnNeeds

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.Voltage

@Entity(tableName = "transformer_own_needs")
data class TransformerOwnNeedsEntity(
    @PrimaryKey val id: String,
    val name: String,
    val panelMcp: String,
    val type: String,
    val parameterType: String,
    val transcriptType: String,
    val additionally: String,
    val isSpare: Boolean,
    val powerSupplyId: String,
    val powerSupplyCell: String,
    val powerSupplyName: String,
    val voltage: String,
    val typeSwitch: String,
    val typeInsTr: String,
    val automation: String,
    val apv: String,
    val phaseProtection: String,
    val earthProtection: String,
)
