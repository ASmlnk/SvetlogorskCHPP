package com.example.svetlogorskchpp.__data.repository.calendarNoteTag

import com.example.svetlogorskchpp.__data.database.calendarNoteTag.CalendarNoteTagDao
import com.example.svetlogorskchpp.__data.database.calendarNoteTag.CalendarMyNoteTagEntity
import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject

class CalendarNoteTagRepositoryImpl @Inject constructor(
    private val calendarNoteTagDao: CalendarNoteTagDao,
) : CalendarNoteTagRepository {

    override suspend fun insertTag(tagCalendarNote: CalendarMyNoteTagEntity) =
        calendarNoteTagDao.insertTag(tagCalendarNote)

    override fun getTagsByDateStream(date: Date): Flow<CalendarMyNoteTagEntity?> =
        calendarNoteTagDao.getTagsByDateStream(date)

    override suspend fun getTagsByMonth(month: Date): List<CalendarMyNoteTagEntity> =
        calendarNoteTagDao.getTagsByMonth(month)

    override fun getTagsByDay(date: Date): List<CalendarMyNoteTagEntity> =
        calendarNoteTagDao.getTagsByDay(date) ?: emptyList()

    override suspend fun deleteCalendarTag(calendarMyNoteTagEntity: CalendarMyNoteTagEntity) =
        calendarNoteTagDao.deleteCalendarTag(calendarMyNoteTagEntity)

}