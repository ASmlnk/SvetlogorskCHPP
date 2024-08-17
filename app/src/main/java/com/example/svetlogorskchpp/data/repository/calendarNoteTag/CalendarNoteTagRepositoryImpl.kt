package com.example.svetlogorskchpp.data.repository.calendarNoteTag

import com.example.svetlogorskchpp.data.database.calendarNoteTag.CalendarNoteTagDao
import com.example.svetlogorskchpp.data.database.calendarNoteTag.CalendarNoteTagEntity
import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject

class CalendarNoteTagRepositoryImpl @Inject constructor(private val calendarNoteTagDao: CalendarNoteTagDao) :
    CalendarNoteTagRepository {

    override suspend fun insertTag(tagCalendarNote: CalendarNoteTagEntity) =
        calendarNoteTagDao.insertTag(tagCalendarNote)

    override fun getTagsByDate(date: Date): Flow<CalendarNoteTagEntity> =
        calendarNoteTagDao.getTagsByDate(date)

    override fun getTagsByMonth(month: Date): Flow<List<CalendarNoteTagEntity>> =
        calendarNoteTagDao.getTagsByMonth(month)

    override suspend fun deleteCalendarTag(date: Date) =
        calendarNoteTagDao.deleteCalendarTag(date)

}