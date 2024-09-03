package com.example.svetlogorskchpp.__data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.svetlogorskchpp.__data.database.calendarNoteTag.CalendarNoteTagDao
import com.example.svetlogorskchpp.__data.database.calendarNoteTag.CalendarNoteTagEntity
import com.example.svetlogorskchpp.__data.database.note.NoteDao
import com.example.svetlogorskchpp.__data.database.note.NoteEntity

@Database(entities = [CalendarNoteTagEntity::class, NoteEntity::class], version = 1)
@TypeConverters(CalendarTypeConverter::class)
abstract class AppDataBase: RoomDatabase() {
    abstract fun calendarNoteTagDao(): CalendarNoteTagDao
    abstract fun noteDao(): NoteDao
}