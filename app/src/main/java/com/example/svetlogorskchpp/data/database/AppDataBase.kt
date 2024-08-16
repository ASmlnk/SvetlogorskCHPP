package com.example.svetlogorskchpp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.svetlogorskchpp.data.database.calendarNoteTag.CalendarNoteTagDao
import com.example.svetlogorskchpp.data.database.calendarNoteTag.CalendarNoteTagEntity
import com.example.svetlogorskchpp.data.database.note.NoteDao
import com.example.svetlogorskchpp.data.database.note.NoteEntity

@Database(entities = [CalendarNoteTagEntity::class, NoteEntity::class], version = 1)
@TypeConverters(CalendarTypeConverter::class)
abstract class AppDataBase: RoomDatabase() {
    abstract fun calendarNoteTagDao(): CalendarNoteTagDao
    abstract fun noteDao(): NoteDao
}