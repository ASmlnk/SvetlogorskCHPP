package com.example.svetlogorskchpp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ShiftPersonalDao {

    @Query("SELECT * FROM shift_personal")
    fun getAll(): Flow<List<ShiftPersonalEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(data: List<ShiftPersonalEntity>)

    @Query("DELETE FROM shift_personal")
    suspend fun clearAll()

    @Query("SELECT COUNT(*) FROM shift_personal")
    suspend fun countShiftPersonalEntities(): Int

}