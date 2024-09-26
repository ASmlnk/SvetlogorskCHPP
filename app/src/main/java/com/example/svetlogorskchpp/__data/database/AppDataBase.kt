package com.example.svetlogorskchpp.__data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.svetlogorskchpp.__data.database.calendarNoteTag.CalendarNoteTagDao
import com.example.svetlogorskchpp.__data.database.calendarNoteTag.CalendarMyNoteTagEntity
import com.example.svetlogorskchpp.__data.database.note.NoteDao
import com.example.svetlogorskchpp.__data.database.note.NoteEntity
import com.example.svetlogorskchpp.__data.database.requestWorkTag.RequestWorkTagDao
import com.example.svetlogorskchpp.__data.database.requestWorkTag.RequestWorkTagEntity

@Database(
    entities = [CalendarMyNoteTagEntity::class, NoteEntity::class, RequestWorkTagEntity::class],
    version = 3
)
@TypeConverters(CalendarTypeConverter::class)
abstract class AppDataBase : RoomDatabase() {
    abstract fun calendarNoteTagDao(): CalendarNoteTagDao
    abstract fun noteDao(): NoteDao
    abstract fun requestWorkTagDao(): RequestWorkTagDao
}

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE notes ADD COLUMN is_request_work INTEGER NOT NULL DEFAULT 0")
    }
}

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(dataBase: SupportSQLiteDatabase) {
        dataBase.execSQL("CREATE TABLE IF NOT EXISTS request_work_tag (date INTEGER PRIMARY KEY NOT NULL, month INTEGER NOT NULL)")
    }

}