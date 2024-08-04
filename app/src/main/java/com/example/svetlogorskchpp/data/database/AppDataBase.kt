package com.example.svetlogorskchpp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ShiftPersonalEntity::class], version = 1)
abstract class AppDataBase: RoomDatabase() {
    abstract fun shiftPersonalLocaleDao(): ShiftPersonalDao
}