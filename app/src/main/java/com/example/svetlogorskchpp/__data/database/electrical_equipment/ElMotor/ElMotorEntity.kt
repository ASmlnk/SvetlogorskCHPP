package com.example.svetlogorskchpp.__data.database.electrical_equipment.ElMotor

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "el_motor")
data class ElMotorEntity(
    @PrimaryKey val id: String,
    val nam: String,
    val powSuId: String,
    val powSuC: String,
    val powSuNam: String,
    val automation: String,
    val phPr: String,
    val eaPr: String,
    val addRz: String,
    val cat: String,
    val gCat: String,
    val tSw: String,
    val tIT: String,
    val addit: String,
    val isRep: Boolean,
    val tRep: String,
    val conCir: String,
    val powEl: String,
    val vol: String,
    val i: String,
    val n: String,
    val tEl: String,
    val mecTyp: String,
    val mecPer: String,
    val mecPr: String,
    val mecN: String
)
