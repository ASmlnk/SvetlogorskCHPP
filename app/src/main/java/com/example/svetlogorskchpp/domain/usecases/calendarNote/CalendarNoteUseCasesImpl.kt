package com.example.svetlogorskchpp.domain.usecases.calendarNote

import com.example.svetlogorskchpp.data.repository.note.NoteRepository
import com.example.svetlogorskchpp.domain.model.Note
import com.example.svetlogorskchpp.domain.usecases.calendarDate.CalendarDateUseCases
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

