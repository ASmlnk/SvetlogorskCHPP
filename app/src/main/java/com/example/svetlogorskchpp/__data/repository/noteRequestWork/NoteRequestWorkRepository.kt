package com.example.svetlogorskchpp.__data.repository.noteRequestWork

import com.example.svetlogorskchpp.__data.database.requestWorkTag.RequestWorkTagEntity
import com.example.svetlogorskchpp.__data.model.NoteRequestWorkEntity
import kotlinx.coroutines.flow.StateFlow
import java.util.Date

interface NoteRequestWorkRepository {
   val noteRequestWorkFlow: StateFlow<List<NoteRequestWorkEntity>>
   suspend fun setRequestWorkFirebase(noteRequestWorkEntity: NoteRequestWorkEntity)

}