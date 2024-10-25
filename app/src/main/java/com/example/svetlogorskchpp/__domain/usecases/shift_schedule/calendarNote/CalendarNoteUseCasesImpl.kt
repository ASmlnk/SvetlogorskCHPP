package com.example.svetlogorskchpp.__domain.usecases.shift_schedule.calendarNote

import com.example.svetlogorskchpp.__data.mapper.NoteRequestWorkDomainToEntityMapper
import com.example.svetlogorskchpp.__data.mapper.NoteRequestWorkEntityToDomainMapper
import com.example.svetlogorskchpp.__data.repository.shift_schedule.note.NoteRepository
import com.example.svetlogorskchpp.__data.repository.shift_schedule.noteRequestWork.NoteRequestWorkRepository
import com.example.svetlogorskchpp.__data.repository.preferences.NotesNotificationPreferencesRepository
import com.example.svetlogorskchpp.__domain.OperationResult
import com.example.svetlogorskchpp.__domain.SuccessResult
import com.example.svetlogorskchpp.__domain.model.shift_schedule.Note
import com.example.svetlogorskchpp.__domain.usecases.shift_schedule.calendarDateUseCases.CalendarDateUseCases
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import java.util.Calendar
import javax.inject.Inject

class CalendarNoteUseCasesImpl @Inject constructor(
    private val noteRepository: NoteRepository,
    private val calendarDateUseCases: CalendarDateUseCases,
    private val noteRequestWorkRepository: NoteRequestWorkRepository,
    private val noteRequestWorkDomainToEntityMapper: NoteRequestWorkDomainToEntityMapper,
    private val notesNotificationPreferencesRepository: NotesNotificationPreferencesRepository,
    private val noteRequestWorkEntityToDomainMapper: NoteRequestWorkEntityToDomainMapper
) : CalendarNoteUseCases {

    override suspend fun <T> insertNote(note: T): OperationResult<SuccessResult> {

        return when(note) {
            is Note.NoteMy -> {
                noteRepository.insertNote((note).toNoteEntity())
                OperationResult.Success(SuccessResult.INSERT_NOTE)
            }
            is Note.NoteRequestWork -> noteRequestWorkRepository.insertRequestWork(
                noteRequestWorkDomainToEntityMapper.map(note)
            )
            else -> OperationResult.Error(ERROR_INSERT)
        }
    }

    override fun getNotesByTagId(tagDate: Calendar): Flow<List<Note>> {
        val dateYMD = calendarDateUseCases.calendarToDateYMD(tagDate)
        val isRequestWorkView = notesNotificationPreferencesRepository.isRequestWorkViewCalendar

        val myNoteFlow = noteRepository.getNotesByTagIdStream(dateYMD)
            .map { notes ->
                notes.map { it.toNote() }
            }
        val requestWorkFlow = isRequestWorkView.flatMapLatest { flag ->
            if (flag) {
                noteRequestWorkRepository.getByTagDatesFlow(dateYMD)
                    .map { requestWork ->
                        requestWork.map { it.toNote() }
                    }
            } else {
                flowOf(emptyList())
            }
        }


        return combine (
            myNoteFlow,
            requestWorkFlow
        ) { myNote, requestWork ->
            myNote + requestWork
        }
    }

    override fun getAllNotes(): Flow<List<Note>> {
       return noteRequestWorkRepository.getAllFlow() .map { requestWork ->
           requestWork.map { it.toNote() }
       }
    }

    override suspend fun <T> deleteNote(note: T): OperationResult<SuccessResult> {
       return when(note) {
            is Note.NoteMy -> {
                noteRepository.deleteNote(note.toNoteEntity())
                OperationResult.Success(SuccessResult.DELETE_REQUEST_WORK)
            }
            is Note.NoteRequestWork -> {
                noteRequestWorkRepository.deleteRequestWork(note.toNoteRequestWorkEntity())
            }
           else -> {OperationResult.Error(ERROR_DELETE)}
       }
    }

    companion object {
        private const val ERROR_INSERT = "Ошибка добавления!"
        private const val ERROR_DELETE = "Ошибка удаления!"
    }
}

