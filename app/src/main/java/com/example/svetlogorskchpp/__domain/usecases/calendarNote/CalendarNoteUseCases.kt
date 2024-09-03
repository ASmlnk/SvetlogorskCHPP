package com.example.svetlogorskchpp.__domain.usecases.calendarNote

import com.example.svetlogorskchpp.__domain.model.Note
import kotlinx.coroutines.flow.Flow
import java.util.Calendar

interface CalendarNoteUseCases {
    suspend fun insertNote(note: Note)
    fun getNotesByTagId(tagDate: Calendar): Flow<List<Note>>
    suspend fun deleteNote(note: Note)
}