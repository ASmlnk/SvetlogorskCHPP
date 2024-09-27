package com.example.svetlogorskchpp.__data.repository.noteRequestWork

import com.example.svetlogorskchpp.__data.database.requestWorkTag.RequestWorkTagEntity
import com.example.svetlogorskchpp.__data.model.NoteRequestWorkEntity
import com.example.svetlogorskchpp.__data.model.NoteRequestWorkJSON
import com.example.svetlogorskchpp.__data.model.NoteRequestWorkJsonList
import com.example.svetlogorskchpp.__data.repository.calendarRequestWorkTag.CalendarRequestWorkTagRepository
import com.example.svetlogorskchpp.__data.repository.calendarRequestWorkTag.CalendarRequestWorkTagRepositoryImpl
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.Date
import javax.inject.Inject

class NoteRequestWorkRepositoryImpl @Inject constructor(
    private val firebase: FirebaseFirestore,
    private val calendarRequestWorkTagRepository: CalendarRequestWorkTagRepository
) : NoteRequestWorkRepository {

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
            val noteRequestWorkJSON =
                data.await().toObject<NoteRequestWorkJSON>() ?: NoteRequestWorkJSON(json = "")

            val noteRequestWorkEntity: List<NoteRequestWorkEntity> =
                if (noteRequestWorkJSON.json.isEmpty()) {
                    emptyList()
                } else {
                    gson.fromJson(
                        noteRequestWorkJSON.json,
                        NoteRequestWorkJsonList::class.java
                    ).listRequestWork
                }
            _noteRequestWorkFlow.update { noteRequestWorkEntity }
            val tags = tagsRequestWork(noteRequestWorkEntity)
            calendarRequestWorkTagRepository.clearTable()
            calendarRequestWorkTagRepository.insertAll(tags)
        }
    }

    override suspend fun setRequestWorkFirebase(noteRequestWorkEntity: NoteRequestWorkEntity) {
        withContext(Dispatchers.IO) {
            val noteRequestWorks = _noteRequestWorkFlow.value.toMutableList()
            noteRequestWorks.add(noteRequestWorkEntity)
            val json = gson.toJson(
                NoteRequestWorkJsonList(listRequestWork = noteRequestWorks)
            )
            firebase.collection(COLLECTION_FIREBASE).document(DOCUMENT_FIREBASE)
                .update(mapOf("json" to json))
                .addOnSuccessListener {
                    CoroutineScope(Dispatchers.Main).launch {
                        getRequestWorkFirebase()
                    }
                }
        }
    }



    private fun tagsRequestWork ( noteRequestWorkEntity: List<NoteRequestWorkEntity>) : List<RequestWorkTagEntity> {
        val setNoteRequestWork = mutableSetOf<RequestWorkTagEntity>()
        for (note in noteRequestWorkEntity) {
            val openTag = RequestWorkTagEntity(
                date = Date(note.tagDateOpen),
                month = Date(note.tagMonthOpen)
            )
            val closeTag = RequestWorkTagEntity(
                date = Date(note.tagDateClose),
                month = Date(note.tagMonthClose)
            )
            setNoteRequestWork.apply {
                add(openTag)
                add(closeTag)
            }
        }
        return setNoteRequestWork.toList()
    }


    companion object {
        private const val COLLECTION_FIREBASE = "Заявки"
        private const val DOCUMENT_FIREBASE = "requestWork"
    }
}