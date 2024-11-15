package com.example.svetlogorskchpp.__data.database.electrical_equipment.turbogenerator

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TurboGeneratorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveItemEntity (turboGeneratorEntity: TurboGeneratorEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: List<TurboGeneratorEntity>)

    @Query("SELECT * FROM turbogenerator")
    fun getAllItemEntityFlow(): Flow<List<TurboGeneratorEntity>?>

    @Query("SELECT * FROM turbogenerator")
     fun getAllItemEntity(): List<TurboGeneratorEntity>

    @Query("SELECT * FROM turbogenerator WHERE id = :id")
     fun getItemEntityFlow(id: String): Flow<TurboGeneratorEntity?>

     @Query("SELECT * FROM turbogenerator WHERE powerSupplyId = :id")
     fun getItemEntityConsumerFlow(id: String): Flow<List<TurboGeneratorEntity>>

    @Query("DELETE FROM turbogenerator")
     suspend fun clearTable()
}