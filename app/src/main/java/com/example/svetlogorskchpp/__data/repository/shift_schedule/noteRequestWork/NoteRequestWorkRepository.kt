package com.example.svetlogorskchpp.__data.repository.shift_schedule.noteRequestWork

import com.example.svetlogorskchpp.__data.database.requestWork.NoteRequestWorkEntity
import com.example.svetlogorskchpp.__domain.OperationResult
import com.example.svetlogorskchpp.__domain.SuccessResult
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface NoteRequestWorkRepository {
   suspend fun insertRequestWork(noteRequestWorkEntity: NoteRequestWorkEntity): OperationResult<SuccessResult>
   fun getByTagDatesFlow(tagDate: Date): Flow<List<NoteRequestWorkEntity>>
   suspend fun getByTagDates(tagDate: Date): List<NoteRequestWorkEntity>
   suspend fun getRequestWorkFirebase()
   fun getAllFlow(): Flow<List<NoteRequestWorkEntity>>
   suspend fun deleteRequestWork(noteRequestWorkEntity: NoteRequestWorkEntity): OperationResult<SuccessResult>
}