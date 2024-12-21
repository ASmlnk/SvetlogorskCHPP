package com.example.svetlogorskchpp.__data.database.equipment.mechanism_info

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MechanismInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveItemEntity(itemEntity: MechanismInfoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: List<MechanismInfoEntity>)

    @Query("SELECT * FROM mechanism_info")
    fun getAllItemEntityFlow(): Flow<List<MechanismInfoEntity>?>

    @Query("SELECT * FROM mechanism_info")
    fun getAllItemEntity(): List<MechanismInfoEntity>

    @Query("SELECT * FROM mechanism_info WHERE id = :id")
    fun getItemEntityFlow(id: String): Flow<MechanismInfoEntity?>

    @Query("SELECT * FROM mechanism_info WHERE genMechId = :id")
    fun getItemEntityEnteredFlow(id: String): Flow<List<MechanismInfoEntity>>  //запрос из генерального механизма на полученее принадлежащего меганизма

    @Query("DELETE FROM mechanism_info")
    suspend fun clearTable()

    @Query("SELECT * FROM mechanism_info WHERE category = :category")
    fun getItemCategory(category: String): Flow<List<MechanismInfoEntity>?>

    @Query(
        """
        SELECT * FROM mechanism_info WHERE LOWER(name) LIKE LOWER(:searchQuery)
        ORDER BY CASE WHEN LOWER(name) LIKE LOWER(:prefixQuery) THEN 0 ELSE 1 END, name
    """
    )
    fun getSearchItems(searchQuery: String, prefixQuery: String): Flow<List<MechanismInfoEntity>>
}