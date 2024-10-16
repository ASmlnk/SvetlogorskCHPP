package com.example.svetlogorskchpp.__data.database.note

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface NoteDao {

    @Insert
    suspend fun insertNote(note: NoteEntity)

    @Query("SELECT * FROM notes WHERE tagDate =:tagDate")
    fun getNotesByTagIdStream(tagDate: Date): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE tagDate =:tagDate")
    fun getNotesByTagId(tagDate: Date): List<NoteEntity>?

    @Delete
    suspend fun deleteNote(note: NoteEntity)
}