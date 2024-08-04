package com.example.svetlogorskchpp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shift_personal")
data class ShiftPersonalEntity(
    val shift: String,
    val jobTitle: String,
    @PrimaryKey
    val namePersonal: String
) {
}