package com.example.svetlogorskchpp.__data.database

import androidx.room.TypeConverter
import java.util.Date

class CalendarTypeConverter {
    @TypeConverter
    fun fromDate(date: Date): Long {
        return date.time
    }

    @TypeConverter
    fun toDate(millisSinceEpoch: Long): Date {
        return Date(millisSinceEpoch)
    }
}