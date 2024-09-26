package com.example.svetlogorskchpp.__data.repository.noteRequestWork

import com.example.svetlogorskchpp.__data.mapper.NoteRequestWorkEntityToDomainMapper
import com.example.svetlogorskchpp.__data.model.NoteRequestWorkEntity
import com.example.svetlogorskchpp.__data.model.NoteRequestWorkJSON
import com.example.svetlogorskchpp.__data.model.NoteRequestWorkJsonList
import com.example.svetlogorskchpp.__domain.model.Note
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NoteRequestWorkRepositoryImpl @Inject constructor(
    private val firebase: FirebaseFirestore,
): NoteRequestWorkRepository {

    private val _noteRequestWorkFlow = MutableStateFlow<List<NoteRequestWorkEntity>>(emptyList())
   override val noteRequestWorkFlow: StateFlow<List<NoteRequestWorkEntity>> = _noteRequestWorkFlow

    private val gson = Gson()

    init {
        CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {
            getRequestWorkFirebase()
        }

    }

   suspend fun getRequestWorkFirebase() {
       withContext(Dispatchers.IO) {
           val data = firebase.collection(COLLECTION_FIREBASE).document(DOCUMENT_FIREBASE).get()
           val noteRequestWorkJSON = data.await().toObject<NoteRequestWorkJSON>() ?: NoteRequestWorkJSON(json = "")
           val json = Gson().toJson(noteRequestWorkJSON.json)
           val noteRequestWorkEntity: List<NoteRequestWorkEntity> = if (noteRequestWorkJSON.json.isEmpty()) {
               emptyList()
           } else {
               gson.fromJson(noteRequestWorkJSON.json, NoteRequestWorkJsonList::class.java).listRequestWork
           }
           _noteRequestWorkFlow.update { noteRequestWorkEntity }
       }
    }

   override suspend fun setRequestWorkFirebase(noteRequestWorkEntity: NoteRequestWorkEntity) {
        withContext(Dispatchers.IO) {
            val noteRequestWorks = _noteRequestWorkFlow.value.toMutableList()
            noteRequestWorks.add(noteRequestWorkEntity)
            val json = gson.toJson(
                NoteRequestWorkJsonList(listRequestWork = noteRequestWorks)
            )
            firebase.collection(COLLECTION_FIREBASE).document(DOCUMENT_FIREBASE).update(mapOf("json" to json))
                .addOnSuccessListener {
                    CoroutineScope(Dispatchers.Main).launch {
                        getRequestWorkFirebase()
                    }
                }
        }
    }


    companion object {
        private const val COLLECTION_FIREBASE = "Заявки"
        private const val DOCUMENT_FIREBASE = "requestWork"
    }
}