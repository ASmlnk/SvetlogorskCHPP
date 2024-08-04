package com.example.svetlogorskchpp.data.model

import com.example.svetlogorskchpp.data.database.ShiftPersonalEntity

data class ShiftPersonalDto(
    val shift: String,
    val jobTitle: String,
    val namePersonal: String
) {
    fun toShitPersonalEntity(): ShiftPersonalEntity {
        return ShiftPersonalEntity(
            shift = this.shift,
            jobTitle = this.jobTitle,
            namePersonal = this.namePersonal
        )
    }
}
