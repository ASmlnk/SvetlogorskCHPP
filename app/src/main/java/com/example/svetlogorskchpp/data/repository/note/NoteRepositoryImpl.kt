package com.example.svetlogorskchpp.data.repository.note

import com.example.svetlogorskchpp.data.database.note.NoteDao
import com.example.svetlogorskchpp.data.database.note.NoteEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor (private val noteDao: NoteDao): NoteRepository {

    override suspend fun insertNote(note: NoteEntity) = noteDao.insertNote(note)
    override suspend fun getNotesByTagId(tagId: Long): Flow<List<NoteEntity>> = noteDao.getNotesByTagId(tagId)
    override suspend fun deleteNote(note: NoteEntity) = noteDao.deleteNote(note)
}