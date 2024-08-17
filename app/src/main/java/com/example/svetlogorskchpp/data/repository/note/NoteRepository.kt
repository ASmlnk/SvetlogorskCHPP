package com.example.svetlogorskchpp.data.repository.note

import com.example.svetlogorskchpp.data.database.note.NoteEntity
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface NoteRepository {
    suspend fun insertNote(note: NoteEntity)
    fun getNotesByTagId(tagDate: Date): Flow<List<NoteEntity>>
    suspend fun deleteNote(note: NoteEntity)
}