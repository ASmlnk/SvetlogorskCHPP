package com.example.svetlogorskchpp.__data.database.requestWork

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.svetlogorskchpp.__data.database.note.NoteEntity
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface NoteRequestWorkDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: List<NoteRequestWorkEntity>)

    @Query("DELETE FROM request_work")
    suspend fun clearTable()

    @Query("SELECT * FROM request_work WHERE tagDateOpen = :tagDate OR tagDateClose = :tagDate")
    fun getByTagDates(tagDate: Long): Flow<List<NoteRequestWorkEntity>>

    @Query("SELECT * FROM request_work")
    suspend fun getAll(): List<NoteRequestWorkEntity>

    @Query("SELECT * FROM request_work")
    fun getAllFlow(): Flow<List<NoteRequestWorkEntity>>

    @Delete
    suspend fun deleteNote(note: NoteRequestWorkEntity)
}