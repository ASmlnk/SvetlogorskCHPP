package com.example.svetlogorskchpp.__data.repository.note

import com.example.svetlogorskchpp.__data.database.note.NoteDao
import com.example.svetlogorskchpp.__data.database.note.NoteEntity
import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor (private val noteDao: NoteDao): NoteRepository {

    override suspend fun insertNote(note: NoteEntity) = noteDao.insertNote(note)
    override fun getNotesByTagId(tagDate: Date): Flow<List<NoteEntity>> = noteDao.getNotesByTagId(tagDate)
    override suspend fun deleteNote(note: NoteEntity) = noteDao.deleteNote(note)
}