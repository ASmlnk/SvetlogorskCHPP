package com.example.svetlogorskchpp.data.database.note

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert
    suspend fun insertNote(note: NoteEntity)

    @Query("SELECT * FROM notes WHERE tagId =:tagId")
    fun getNotesByTagId(tagId: Long): Flow<List<NoteEntity>>

    @Delete
    suspend fun deleteNote(note: NoteEntity)
}