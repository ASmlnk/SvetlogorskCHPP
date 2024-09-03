package com.example.svetlogorskchpp.__domain.usecases.calendarNote

import com.example.svetlogorskchpp.__data.repository.note.NoteRepository
import com.example.svetlogorskchpp.__domain.model.Note
import com.example.svetlogorskchpp.__domain.usecases.calendarDate.CalendarDateUseCases
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Calendar
import javax.inject.Inject

class CalendarNoteUseCasesImpl @Inject constructor(
    private val noteRepository: NoteRepository,
    private val calendarDateUseCases: CalendarDateUseCases,
) : CalendarNoteUseCases {
    override suspend fun insertNote(note: Note) {
        noteRepository.insertNote(note.toNoteEntity())
    }

    override fun getNotesByTagId(tagDate: Calendar): Flow<List<Note>> =
        noteRepository.getNotesByTagId(calendarDateUseCases.calendarToDateYMD(tagDate)).map { notes ->
            notes.map { it.toNote() }
        }

    override suspend fun deleteNote(note: Note) {
        noteRepository.deleteNote(note.toNoteEntity())
    }
}

