package com.example.svetlogorskchpp.__data.repository.noteRequestWork

import com.example.svetlogorskchpp.__data.model.NoteRequestWorkEntity
import kotlinx.coroutines.flow.StateFlow

interface NoteRequestWorkRepository {
   val noteRequestWorkFlow: StateFlow<List<NoteRequestWorkEntity>>
   suspend fun setRequestWorkFirebase(noteRequestWorkEntity: NoteRequestWorkEntity)
}