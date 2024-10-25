package com.example.svetlogorskchpp.__data.repository.shift_schedule.calendarRequestWorkTag

import com.example.svetlogorskchpp.__data.database.requestWorkTag.RequestWorkTagDao
import com.example.svetlogorskchpp.__data.database.requestWorkTag.RequestWorkTagEntity
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CalendarRequestWorkTagRepositoryImpl @Inject constructor(
    private val requestWorkTagDao: RequestWorkTagDao,
) : CalendarRequestWorkTagRepository {

    override suspend fun getTagsByMonth(month: Date): List<RequestWorkTagEntity> {
        return requestWorkTagDao.getTagsByMonth(month)
    }

    override suspend fun clearTable() {
        requestWorkTagDao.clearTable()
    }

    override suspend fun insertAll(entities: List<RequestWorkTagEntity>) {
        requestWorkTagDao.insertAll(entities)
    }
}