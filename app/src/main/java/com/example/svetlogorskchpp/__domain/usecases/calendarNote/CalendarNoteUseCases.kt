package com.example.svetlogorskchpp.__domain.usecases.calendarNote

import com.example.svetlogorskchpp.__domain.OperationResult
import com.example.svetlogorskchpp.__domain.SuccessResult
import com.example.svetlogorskchpp.__domain.model.Note
import kotlinx.coroutines.flow.Flow
import java.util.Calendar

interface CalendarNoteUseCases {
    suspend fun <T> insertNote(note: T): OperationResult<SuccessResult>
    fun getNotesByTagId(tagDate: Calendar): Flow<List<Note>>
    fun getAllNotes(): Flow<List<Note>>
    suspend fun <T> deleteNote(note: T): OperationResult<SuccessResult>
}