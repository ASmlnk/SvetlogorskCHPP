package com.example.svetlogorskchpp.__domain.usecases.calendarNote

import com.example.svetlogorskchpp.__data.database.requestWork.NoteRequestWorkEntity
import com.example.svetlogorskchpp.__domain.OperationResult
import com.example.svetlogorskchpp.__domain.model.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import java.util.Calendar

interface CalendarNoteUseCases {
    suspend fun <T> insertNote(note: T)
    fun getNotesByTagId(tagDate: Calendar): Flow<List<Note>>
    suspend fun <T> deleteNote(note: T)
    val noteRequestWorkFlow: StateFlow<List<NoteRequestWorkEntity>>
    val operationResultFirebaseFlow: Flow<OperationResult<Unit>>
    fun cleanJob()
}