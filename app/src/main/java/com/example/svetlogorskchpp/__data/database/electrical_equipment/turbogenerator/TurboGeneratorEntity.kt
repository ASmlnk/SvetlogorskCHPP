package com.example.svetlogorskchpp.__data.database.electrical_equipment.turbogenerator

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "turbogenerator")
data class TurboGeneratorEntity(
    @PrimaryKey val id: String,
    val name: String,
    val panelMcp: String,
    val typeSwitch: String,
    val typeInsTr: String,
    val typeGenerator: String,
    val transcriptTypeGenerator: String,
    val volumeTg: String,
    val volumeReceiver: String,
    val additionallyGenerator: String,
    val sourceExcitation: String,
    val generatorStarted: String,
    val translationIntoRv: String,
    val translationFromRv: String,
    val typeTurbin: String,
    val transcriptTypeTurbin: String,
    val powerEl: String,
    val powerThermal: String,
    val steamConsumption: String,
    val additionallyTurbin: String,
    val powerSupplyId: String,
    val powerSupplyCell: String,
    val powerSupplyName: String,
    val automation: String,
    val phaseProtection: String,
    val earthProtection: String,
    val additionallyRza1: String,
    val additionallyRza2: String,
    )
