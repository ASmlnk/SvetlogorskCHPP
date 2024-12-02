package com.example.svetlogorskchpp.__data.database.electrical_equipment.transformerOwnNeeds

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.svetlogorskchpp.__data.database.electrical_equipment.Switchgear.SwitchgearEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransformerOwnNeedsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveItemTransformerOwnNeeds (transformerOwnNeedsEntity: TransformerOwnNeedsEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: List<TransformerOwnNeedsEntity>)

    @Query("SELECT * FROM transformer_own_needs")
    fun getAllItemTransformerOwnNeedsFlow(): Flow<List<TransformerOwnNeedsEntity>?>

    @Query("SELECT * FROM transformer_own_needs")
    fun getAllItemTransformerOwnNeeds(): List<TransformerOwnNeedsEntity>

    @Query("SELECT * FROM transformer_own_needs WHERE id = :id")
    fun getItemTransformerOwnNeedsFlow(id: String): Flow<TransformerOwnNeedsEntity?>

    @Query("SELECT * FROM transformer_own_needs WHERE powerSupplyId = :id")
    fun getItemTransformerOwnNeedsConsumerFlow(id: String): Flow<List<TransformerOwnNeedsEntity>>

    @Query("DELETE FROM transformer_own_needs")
    suspend fun clearTable()

    @Query("""
        SELECT * FROM transformer_own_needs WHERE LOWER(name) LIKE LOWER(:searchQuery)
        ORDER BY CASE WHEN LOWER(name) LIKE LOWER(:prefixQuery) THEN 0 ELSE 1 END, name
    """)
    fun getSearchItems(searchQuery: String, prefixQuery: String): Flow<List<TransformerOwnNeedsEntity>>
}