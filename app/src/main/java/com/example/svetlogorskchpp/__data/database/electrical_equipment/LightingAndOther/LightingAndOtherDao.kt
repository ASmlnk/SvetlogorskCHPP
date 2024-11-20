package com.example.svetlogorskchpp.__data.database.electrical_equipment.LightingAndOther

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.svetlogorskchpp.__data.database.electrical_equipment.ElMotor.ElMotorEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LightingAndOtherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveItemEntity (itemEntity: LightingAndOtherEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: List<LightingAndOtherEntity>)

    @Query("SELECT * FROM lighting_and_other")
    fun getAllItemEntityFlow(): Flow<List<LightingAndOtherEntity>?>

    @Query("SELECT * FROM lighting_and_other")
    fun getAllItemEntity(): List<LightingAndOtherEntity>

    @Query("SELECT * FROM lighting_and_other WHERE id = :id")
    fun getItemEntityFlow(id: String): Flow<LightingAndOtherEntity?>

    @Query("SELECT * FROM lighting_and_other WHERE powSuId = :id")
    fun getItemEntityConsumerFlow(id: String): Flow<List<LightingAndOtherEntity>>

    @Query("DELETE FROM lighting_and_other")
    suspend fun clearTable()
}