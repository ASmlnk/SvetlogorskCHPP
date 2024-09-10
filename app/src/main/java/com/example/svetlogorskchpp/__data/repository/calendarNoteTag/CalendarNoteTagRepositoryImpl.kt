package com.example.svetlogorskchpp.__data.repository.calendarNoteTag

import com.example.svetlogorskchpp.__data.database.calendarNoteTag.CalendarNoteTagDao
import com.example.svetlogorskchpp.__data.database.calendarNoteTag.CalendarNoteTagEntity
import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject

class CalendarNoteTagRepositoryImpl @Inject constructor(private val calendarNoteTagDao: CalendarNoteTagDao) :
    CalendarNoteTagRepository {

    override suspend fun insertTag(tagCalendarNote: CalendarNoteTagEntity) =
        calendarNoteTagDao.insertTag(tagCalendarNote)

    override fun getTagsByDateStream(date: Date): Flow<CalendarNoteTagEntity?> =
        calendarNoteTagDao.getTagsByDateStream(date)

    override suspend fun getTagsByMonth(month: Date): List<CalendarNoteTagEntity> =
        calendarNoteTagDao.getTagsByMonth(month)

    override fun getTagsByDay(date: Date): List<CalendarNoteTagEntity> =
        calendarNoteTagDao.getTagsByDay(date)

    override suspend fun deleteCalendarTag(calendarNoteTagEntity:CalendarNoteTagEntity) =
        calendarNoteTagDao.deleteCalendarTag(calendarNoteTagEntity)

}