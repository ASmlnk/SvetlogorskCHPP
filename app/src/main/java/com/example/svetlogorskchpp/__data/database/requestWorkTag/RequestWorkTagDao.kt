package com.example.svetlogorskchpp.__data.database.requestWorkTag

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import java.util.Date

@Dao
interface RequestWorkTagDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: List<RequestWorkTagEntity>)

    @Query("DELETE FROM request_work_tag")
    suspend fun clearTable()

    @Query("SELECT * FROM request_work_tag WHERE month = :month")
    suspend fun getTagsByMonth(month: Date): List<RequestWorkTagEntity>
}