package com.example.svetlogorskchpp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CalendarNotesDao {

    @Query("SELECT * FROM calendar_notes")
    fun getAll(): Flow<List<CalendarNotesEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(data: List<CalendarNotesEntity>)

    @Query("DELETE FROM calendar_notes")
    suspend fun clearAll()

    @Query("SELECT COUNT(*) FROM calendar_notes")
    suspend fun countShiftPersonalEntities(): Int

}