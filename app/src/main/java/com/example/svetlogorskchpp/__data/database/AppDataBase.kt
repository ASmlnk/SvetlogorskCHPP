package com.example.svetlogorskchpp.__data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.svetlogorskchpp.__data.database.calendarNoteTag.CalendarNoteTagDao
import com.example.svetlogorskchpp.__data.database.calendarNoteTag.CalendarNoteTagEntity
import com.example.svetlogorskchpp.__data.database.note.NoteDao
import com.example.svetlogorskchpp.__data.database.note.NoteEntity

@Database(entities = [CalendarNoteTagEntity::class, NoteEntity::class], version = 2)
@TypeConverters(CalendarTypeConverter::class)
abstract class AppDataBase: RoomDatabase() {
    abstract fun calendarNoteTagDao(): CalendarNoteTagDao
    abstract fun noteDao(): NoteDao
}

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE notes ADD COLUMN is_request_work INTEGER NOT NULL DEFAULT 0")
    }
}