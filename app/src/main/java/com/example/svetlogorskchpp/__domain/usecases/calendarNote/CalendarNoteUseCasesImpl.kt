package com.example.svetlogorskchpp.__domain.usecases.calendarNote

import com.example.svetlogorskchpp.__data.mapper.NoteRequestWorkDomainToEntityMapper
import com.example.svetlogorskchpp.__data.mapper.NoteRequestWorkEntityToDomainMapper
import com.example.svetlogorskchpp.__data.repository.note.NoteRepository
import com.example.svetlogorskchpp.__data.repository.noteRequestWork.NoteRequestWorkRepository
import com.example.svetlogorskchpp.__domain.OperationResult
import com.example.svetlogorskchpp.__domain.model.Note
import com.example.svetlogorskchpp.__domain.usecases.calendarDateUseCases.CalendarDateUseCases
import com.example.svetlogorskchpp.__presentation.shift_schedule.model.CalendarFullDayShiftModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.util.Calendar
import javax.inject.Inject

class CalendarNoteUseCasesImpl @Inject constructor(
    private val noteRepository: NoteRepository,
    private val calendarDateUseCases: CalendarDateUseCases,
    private val noteRequestWorkRepository: NoteRequestWorkRepository,
    private val noteRequestWorkDomainToEntityMapper: NoteRequestWorkDomainToEntityMapper,
    private val noteRequestWorkEntityToDomainMapper: NoteRequestWorkEntityToDomainMapper
) : CalendarNoteUseCases {

    override suspend fun <T> insertNote(note: T) {
        when(note) {
            is Note.NoteMy -> noteRepository.insertNote((note).toNoteEntity())
            is Note.NoteRequestWork -> noteRequestWorkRepository.setRequestWorkFirebase(
                noteRequestWorkDomainToEntityMapper.map(note)
            )
        }
    }

    override fun getNotesByTagId(tagDate: Calendar): Flow<List<Note>> {
        val myNoteFlow = noteRepository.getNotesByTagIdStream(calendarDateUseCases.calendarToDateYMD(tagDate))
            .map { notes ->
                notes.map { it.toNote() }
            }
        val requestWorkFlow = noteRequestWorkRepository.getByTagDates(calendarDateUseCases.calendarToDateYMD(tagDate))
            .map { requestWork ->
                requestWork.map { it.toNote() }
            }

        return combine (
            myNoteFlow,
            requestWorkFlow
        ) { myNote, requestWork ->
            myNote + requestWork
        }
    }

    override suspend fun <T> deleteNote(note: T) {
        when(note) {
            is Note.NoteMy -> noteRepository.deleteNote(note.toNoteEntity())
        }
    }

    override fun cleanJob() {
       // noteRequestWorkRepository.cleanJob()
    }

    override val noteRequestWorkFlow = noteRequestWorkRepository.noteRequestWorkFlow
    override val operationResultFirebaseFlow: Flow<OperationResult<Unit>> =
        noteRequestWorkRepository.operationResultFirebaseFlow
}

