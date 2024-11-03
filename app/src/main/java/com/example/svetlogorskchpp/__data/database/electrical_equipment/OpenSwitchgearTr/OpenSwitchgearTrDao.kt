package com.example.svetlogorskchpp.__data.database.electrical_equipment.OpenSwitchgearTr

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.svetlogorskchpp.__data.database.electrical_equipment.OpenSwitchgearVl.OpenSwitchgearVlEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface OpenSwitchgearTrDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveItemOpenSwitchgear (openSwitchgearTrEntity: OpenSwitchgearTrEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: List<OpenSwitchgearTrEntity>)

    @Query("SELECT * FROM open_switchgear_tr")
    fun getAllItemOpenSwitchgearStream(): Flow<List<OpenSwitchgearTrEntity>?>

    @Query("SELECT * FROM open_switchgear_tr")
    fun getAllItemOpenSwitchgear(): List<OpenSwitchgearTrEntity>

    @Query("SELECT * FROM open_switchgear_tr WHERE id = :id")
    fun getItemOpenSwitchgear(id: String): Flow<OpenSwitchgearTrEntity?>

    @Query("DELETE FROM open_switchgear_tr")
    suspend fun clearTable()
}