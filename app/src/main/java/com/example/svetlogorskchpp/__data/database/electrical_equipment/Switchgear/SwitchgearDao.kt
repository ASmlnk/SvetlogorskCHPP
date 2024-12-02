package com.example.svetlogorskchpp.__data.database.electrical_equipment.Switchgear

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.svetlogorskchpp.__data.database.electrical_equipment.LightingAndOther.LightingAndOtherEntity
import com.example.svetlogorskchpp.__data.database.electrical_equipment.OpenSwitchgearVl.OpenSwitchgearVlEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SwitchgearDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveItemEntity (itemEntity: SwitchgearEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: List<SwitchgearEntity>)

    @Query("SELECT * FROM switchgear")
    fun getAllItemEntityFlow(): Flow<List<SwitchgearEntity>?>

    @Query("SELECT * FROM switchgear")
    fun getAllItemEntity(): List<SwitchgearEntity>

    @Query("SELECT * FROM switchgear WHERE id = :id")
    fun getItemEntityFlow(id: String): Flow<SwitchgearEntity?>

    @Query("SELECT * FROM switchgear WHERE powSuId1 = :id OR powSuId2 = :id OR powSuRId1 = :id OR powSuRId2 = :id OR powSuRId3 = :id")
    fun getItemEntityConsumerFlow(id: String): Flow<List<SwitchgearEntity>>

    @Query("DELETE FROM switchgear")
    suspend fun clearTable()

    @Query("""
        SELECT * FROM switchgear WHERE LOWER(nam) LIKE LOWER(:searchQuery)
        ORDER BY CASE WHEN LOWER(nam) LIKE LOWER(:prefixQuery) THEN 0 ELSE 1 END, nam
    """)
    fun getSearchItems(searchQuery: String, prefixQuery: String): Flow<List<SwitchgearEntity>>
}