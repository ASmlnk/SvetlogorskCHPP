package com.example.svetlogorskchpp.__domain.usecases.calendarNote

import com.example.svetlogorskchpp.__domain.model.Note
import kotlinx.coroutines.flow.Flow
import java.util.Calendar

interface CalendarNoteUseCases {
    suspend fun <T> insertNote(note: T)
    fun getNotesByTagId(tagDate: Calendar): Flow<List<Note>>
    suspend fun <T> deleteNote(note: T)
}