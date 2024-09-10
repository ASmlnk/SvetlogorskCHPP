package com.example.svetlogorskchpp.__data.repository.note

import com.example.svetlogorskchpp.__data.database.note.NoteEntity
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface NoteRepository {
    suspend fun insertNote(note: NoteEntity)
    fun getNotesByTagIdStream(tagDate: Date): Flow<List<NoteEntity>>
    suspend fun deleteNote(note: NoteEntity)
    fun getNotesByTagId(tagDate: Date): List<NoteEntity>
}