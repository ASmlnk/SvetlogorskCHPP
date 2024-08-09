package com.example.svetlogorskchpp.data.database

import androidx.room.TypeConverter
import java.util.Date

class CalendarNotesTypeConverter {
    @TypeConverter
    fun fromDate(date: Date): Long {
        return date.time
    }

    @TypeConverter
    fun toDate(millisSinceEpoch: Long): Date {
        return Date(millisSinceEpoch)
    }
}