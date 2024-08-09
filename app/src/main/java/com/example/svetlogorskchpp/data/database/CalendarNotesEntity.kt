package com.example.svetlogorskchpp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import java.util.UUID

@Entity(tableName = "calendar_notes")
data class CalendarNotesEntity (
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    val date: Date,
    val technical: Boolean,
    val haircut: Boolean,
    val dateHaircut: String,
    val notes: String
) {
}