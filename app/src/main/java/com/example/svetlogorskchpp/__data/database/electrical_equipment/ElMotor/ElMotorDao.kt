package com.example.svetlogorskchpp.__data.database.electrical_equipment.ElMotor

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.svetlogorskchpp.__data.database.electrical_equipment.turbogenerator.TurboGeneratorEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ElMotorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveItemEntity (itemEntity: ElMotorEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: List<ElMotorEntity>)

    @Query("SELECT * FROM el_motor")
    fun getAllItemEntityFlow(): Flow<List<ElMotorEntity>?>

    @Query("SELECT * FROM el_motor")
    fun getAllItemEntity(): List<ElMotorEntity>

    @Query("SELECT * FROM el_motor WHERE id = :id")
    fun getItemEntityFlow(id: String): Flow<ElMotorEntity?>

    @Query("SELECT * FROM el_motor WHERE powSuId = :id")
    fun getItemEntityConsumerFlow(id: String): Flow<List<ElMotorEntity>>

    @Query("DELETE FROM el_motor")
    suspend fun clearTable()
}