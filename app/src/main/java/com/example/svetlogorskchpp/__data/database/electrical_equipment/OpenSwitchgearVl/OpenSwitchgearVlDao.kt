package com.example.svetlogorskchpp.__data.database.electrical_equipment.OpenSwitchgearVl

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.svetlogorskchpp.__data.database.electrical_equipment.OpenSwitchgearTr.OpenSwitchgearTrEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface OpenSwitchgearVlDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveItemOpenSwitchgear (openSwitchgearVlEntity: OpenSwitchgearVlEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: List<OpenSwitchgearVlEntity>)

    @Query("SELECT * FROM open_switchgear_vl")
    fun getAllItemOpenSwitchgearStream(): Flow<List<OpenSwitchgearVlEntity>?>

    @Query("SELECT * FROM open_switchgear_vl")
    fun getAllItemOpenSwitchgear(): List<OpenSwitchgearVlEntity>

    @Query("SELECT * FROM open_switchgear_vl WHERE id = :id")
    fun getItemOpenSwitchgear(id: String): Flow<OpenSwitchgearVlEntity?>

    @Query("DELETE FROM open_switchgear_vl")
    suspend fun clearTable()

    @Query("""
        SELECT * FROM open_switchgear_vl WHERE LOWER(name) LIKE LOWER(:searchQuery)
        ORDER BY CASE WHEN LOWER(name) LIKE LOWER(:prefixQuery) THEN 0 ELSE 1 END, name
    """)
    fun getSearchItems(searchQuery: String, prefixQuery: String): Flow<List<OpenSwitchgearVlEntity>>
}