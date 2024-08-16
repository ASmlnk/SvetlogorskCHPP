package com.example.svetlogorskchpp.data.repository.note

import com.example.svetlogorskchpp.data.database.note.NoteEntity
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    suspend fun insertNote(note: NoteEntity)
    suspend fun getNotesByTagId(tagId: Long): Flow<List<NoteEntity>>
    suspend fun deleteNote(note: NoteEntity)
}