package com.example.svetlogorskchpp.__data.repository.shift_schedule.calendarRequestWorkTag

import com.example.svetlogorskchpp.__data.database.requestWorkTag.RequestWorkTagEntity
import java.util.Date

interface CalendarRequestWorkTagRepository {
    suspend fun getTagsByMonth(month: Date): List<RequestWorkTagEntity>
    suspend fun clearTable()
    suspend fun insertAll(entities: List<RequestWorkTagEntity>)
}