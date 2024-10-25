package com.example.svetlogorskchpp.__data.database.electrical_equipment.OpenSwitchgearVl

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.svetlogorskchpp.__domain.OperationResult
import com.example.svetlogorskchpp.__domain.SuccessResult
import kotlinx.coroutines.flow.Flow

@Dao
interface OpenSwitchgearVlDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveItemOpenSwitchgear (openSwitchgearVlEntity: OpenSwitchgearVlEntity)

    @Query("SELECT * FROM open_switchgear_vl")
    fun getAllItemOpenSwitchgear(): Flow<List<OpenSwitchgearVlEntity>?>

    @Query("SELECT * FROM open_switchgear_vl WHERE id = :id")
    fun getItemOpenSwitchgear(id: String): Flow<OpenSwitchgearVlEntity?>

    @Query("DELETE FROM open_switchgear_vl")
    suspend fun clearTable()
}