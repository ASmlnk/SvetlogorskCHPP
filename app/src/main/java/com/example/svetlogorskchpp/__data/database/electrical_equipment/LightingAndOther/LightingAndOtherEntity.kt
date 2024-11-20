package com.example.svetlogorskchpp.__data.database.electrical_equipment.LightingAndOther

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lighting_and_other")
data class LightingAndOtherEntity(
    @PrimaryKey val id: String,
    val nam: String,
    val powSuId: String,
    val powSuC: String,
    val powSuNam: String,
    val tSw: String,
    val addit: String,
    val isLi: Boolean,
    val loc: String
)
