package com.example.svetlogorskchpp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [CalendarNotesEntity::class], version = 1)
@TypeConverters(CalendarNotesTypeConverter::class)
abstract class AppDataBase: RoomDatabase() {
    abstract fun calendarNotesLocaleDao(): CalendarNotesDao
}