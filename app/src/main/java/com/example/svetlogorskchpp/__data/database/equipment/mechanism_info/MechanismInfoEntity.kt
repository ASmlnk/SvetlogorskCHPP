package com.example.svetlogorskchpp.__data.database.equipment.mechanism_info

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mechanism_info")
data class MechanismInfoEntity(
    @PrimaryKey val id: String,
    val name: String,
    val info: String,
    val category: String,
    val additionally: String,
    val genMechId: String,  //id генеральной установки
    val genMechName: String
)
