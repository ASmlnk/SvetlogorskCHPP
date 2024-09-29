package com.example.svetlogorskchpp.__data.repository.noteRequestWork

import com.example.svetlogorskchpp.__data.database.requestWork.NoteRequestWorkEntity
import com.example.svetlogorskchpp.__domain.OperationResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import java.util.Date

interface NoteRequestWorkRepository {
   val noteRequestWorkFlow: StateFlow<List<NoteRequestWorkEntity>>
   val operationResultFirebaseFlow: Flow<OperationResult<Unit>>
   suspend fun setRequestWorkFirebase(noteRequestWorkEntity: NoteRequestWorkEntity)
   fun getByTagDates(tagDate: Date): Flow<List<NoteRequestWorkEntity>>
   suspend fun getRequestWorkFirebase()
   fun cleanJob()
}