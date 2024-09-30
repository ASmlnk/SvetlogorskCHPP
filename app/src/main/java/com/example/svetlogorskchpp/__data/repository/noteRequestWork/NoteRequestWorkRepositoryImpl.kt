package com.example.svetlogorskchpp.__data.repository.noteRequestWork

import android.util.Log
import com.example.svetlogorskchpp.__data.database.requestWork.NoteRequestWorkDao
import com.example.svetlogorskchpp.__data.database.requestWorkTag.RequestWorkTagEntity
import com.example.svetlogorskchpp.__data.database.requestWork.NoteRequestWorkEntity
import com.example.svetlogorskchpp.__data.model.NoteRequestWorkJSON
import com.example.svetlogorskchpp.__data.model.NoteRequestWorkJsonList
import com.example.svetlogorskchpp.__data.repository.calendarRequestWorkTag.CalendarRequestWorkTagRepository
import com.example.svetlogorskchpp.__domain.OperationResult
import com.example.svetlogorskchpp.__presentation.activity.MainActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.Date
import javax.inject.Inject

class NoteRequestWorkRepositoryImpl @Inject constructor(
    private val firebase: FirebaseFirestore,
    private val calendarRequestWorkTagRepository: CalendarRequestWorkTagRepository,
    private val noteRequestWorkDao: NoteRequestWorkDao,
) : NoteRequestWorkRepository {

    private val _noteRequestWorkFlow = MutableStateFlow<List<NoteRequestWorkEntity>>(emptyList())
    override val noteRequestWorkFlow: StateFlow<List<NoteRequestWorkEntity>> = _noteRequestWorkFlow

    private val _operationResultFirebaseFlow = MutableSharedFlow<OperationResult<Unit>>()
    override val operationResultFirebaseFlow: Flow<OperationResult<Unit>> =
        _operationResultFirebaseFlow

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    private val gson = Gson()

    override suspend fun getRequestWorkFirebase() {
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

            val tags = tagsRequestWork(noteRequestWorkEntity)
            calendarRequestWorkTagRepository.clearTable()
            calendarRequestWorkTagRepository.insertAll(tags)
            Log.d("11111111", "11111111"+NoteRequestWorkRepositoryImpl.toString())
            noteRequestWorkDao.clearTable()
            noteRequestWorkDao.insertAll(noteRequestWorkEntity)
            _noteRequestWorkFlow.update { noteRequestWorkEntity }
        }
    }

    override suspend fun setRequestWorkFirebase(noteRequestWorkEntity: NoteRequestWorkEntity) {
        Log.d("11111111", "41111111"+NoteRequestWorkRepositoryImpl.toString())

            val noteRequestWorks = _noteRequestWorkFlow.value.toMutableList()
            noteRequestWorks.add(noteRequestWorkEntity)
            val json = gson.toJson(
                NoteRequestWorkJsonList(listRequestWork = noteRequestWorks)
            )
            firebase.collection(COLLECTION_FIREBASE).document(DOCUMENT_FIREBASE)
                .update(mapOf("json" to json))


                .addOnSuccessListener {
                    Log.d("11111111", "31111111"+NoteRequestWorkRepositoryImpl.toString())
                    coroutineScope.launch {
                        Log.d("11111111", "21111111"+NoteRequestWorkRepositoryImpl.toString())
                        getRequestWorkFirebase()
                        _operationResultFirebaseFlow.emit(OperationResult.Success(Unit))
                    }

                }.addOnFailureListener { e ->
                    Log.d("11111111", "e1111111"+NoteRequestWorkRepositoryImpl.toString())
                }

    }

    override fun cleanJob() {
        coroutineScope.cancel()
    }

    override fun getByTagDates(tagDate: Date): Flow<List<NoteRequestWorkEntity>> {
        return noteRequestWorkDao.getByTagDates(tagDate.time)
    }


    private fun tagsRequestWork(noteRequestWorkEntity: List<NoteRequestWorkEntity>): List<RequestWorkTagEntity> {
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