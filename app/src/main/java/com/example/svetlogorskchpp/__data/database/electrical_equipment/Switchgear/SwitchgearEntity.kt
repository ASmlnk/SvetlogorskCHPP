package com.example.svetlogorskchpp.__data.database.electrical_equipment.Switchgear

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "switchgear")
data class SwitchgearEntity(
    @PrimaryKey val id: String,
    val nam: String,
    val tSw: String,
    val tIT: String,
    val addit: String,
    val cat: String,
    val namDep: String,
    val vol: String,
    val aut: String,
    val phPr: String,
    val eaPr: String,
    val addRz: String,
    val inf: String,
    val powSuId1: String,
    val powSuC1: String,
    val powSuNam1: String,
    val powSuId2: String,
    val powSuC2: String,
    val powSuNam2: String,
    val powSuRId1: String,
    val powSuRC1: String,
    val powSuRNam1: String,
    val powSuRId2: String,
    val powSuRC2: String,
    val powSuRNam2: String,
    val powSuRId3: String,
    val powSuRC3: String,
    val powSuRNam3: String,
)
