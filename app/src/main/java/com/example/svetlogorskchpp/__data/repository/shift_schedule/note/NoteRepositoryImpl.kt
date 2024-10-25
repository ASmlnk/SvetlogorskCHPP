package com.example.svetlogorskchpp.__data.repository.shift_schedule.note

import com.example.svetlogorskchpp.__data.database.note.NoteDao
import com.example.svetlogorskchpp.__data.database.note.NoteEntity
import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject


class NoteRepositoryImpl @Inject constructor (private val noteDao: NoteDao): NoteRepository {

    override suspend fun insertNote(note: NoteEntity) = noteDao.insertNote(note)
    override fun getNotesByTagIdStream(tagDate: Date): Flow<List<NoteEntity>> = noteDao.getNotesByTagIdStream(tagDate)
    override fun getNotesByTagId(tagDate: Date): List<NoteEntity> = noteDao.getNotesByTagId(tagDate) ?: emptyList()
    override suspend fun deleteNote(note: NoteEntity) = noteDao.deleteNote(note)
}