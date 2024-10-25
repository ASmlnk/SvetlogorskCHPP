package com.example.svetlogorskchpp.__data.repository.shift_schedule.noteRequestWork

import com.example.svetlogorskchpp.__data.database.requestWork.NoteRequestWorkDao
import com.example.svetlogorskchpp.__data.database.requestWorkTag.RequestWorkTagEntity
import com.example.svetlogorskchpp.__data.database.requestWork.NoteRequestWorkEntity
import com.example.svetlogorskchpp.__data.model.NoteRequestWorkJSON
import com.example.svetlogorskchpp.__data.model.NoteRequestWorkJsonList
import com.example.svetlogorskchpp.__data.repository.shift_schedule.calendarRequestWorkTag.CalendarRequestWorkTagRepository
import com.example.svetlogorskchpp.__domain.OperationResult
import com.example.svetlogorskchpp.__domain.SuccessResult
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.Date
import javax.inject.Inject

class NoteRequestWorkRepositoryImpl @Inject constructor(
    private val firebase: FirebaseFirestore,
    private val calendarRequestWorkTagRepository: CalendarRequestWorkTagRepository,
    private val noteRequestWorkDao: NoteRequestWorkDao,
) : NoteRequestWorkRepository {

    private val gson = Gson()

    override suspend fun getRequestWorkFirebase() {
        withContext(Dispatchers.IO) {
            val data = firebase.collection(COLLECTION_FIREBASE).document(DOCUMENT_FIREBASE).get()
            val noteRequestWorkJSON =
                data.await().toObject<NoteRequestWorkJSON>() ?: NoteRequestWorkJSON(json = "")

            val noteRequestWorkEntityFirestore: List<NoteRequestWorkEntity> =
                if (noteRequestWorkJSON.json.isEmpty()) {
                    emptyList()
                } else {
                    gson.fromJson(
                        noteRequestWorkJSON.json,
                        NoteRequestWorkJsonList::class.java
                    ).listRequestWork
                }

             val noteRequestWorkEntity = noteRequestWorkDao.getAll()
            if (noteRequestWorkEntityFirestore == noteRequestWorkEntity) return@withContext

            val tags = tagsRequestWork(noteRequestWorkEntityFirestore)
            calendarRequestWorkTagRepository.clearTable()
            if (tags.isNotEmpty()) calendarRequestWorkTagRepository.insertAll(tags)

            noteRequestWorkDao.clearTable()
            if (noteRequestWorkEntityFirestore.isNotEmpty()) noteRequestWorkDao.insertAll(
                noteRequestWorkEntityFirestore
            )
        }
    }

    override suspend fun insertRequestWork(noteRequestWorkEntity: NoteRequestWorkEntity): OperationResult<SuccessResult> =
        withContext(Dispatchers.IO) {

            firebase.enableNetwork()
            val noteRequestWorks = noteRequestWorkDao.getAll().toMutableList()
            val removeEntity = noteRequestWorks.filter { it.id == noteRequestWorkEntity.id}
            if (removeEntity.isNotEmpty()) noteRequestWorks.removeAll(removeEntity)
            noteRequestWorks.add(noteRequestWorkEntity)

            return@withContext insertJsonFirebase(noteRequestWorks, SuccessResult.INSERT_REQUEST_WORK)
        }

    override suspend fun deleteRequestWork(noteRequestWorkEntity: NoteRequestWorkEntity): OperationResult<SuccessResult> =
        withContext(Dispatchers.IO) {

            firebase.enableNetwork()
            val noteRequestWorks = noteRequestWorkDao.getAll().toMutableList()
            noteRequestWorks.remove(noteRequestWorkEntity)

            return@withContext insertJsonFirebase(noteRequestWorks, SuccessResult.DELETE_REQUEST_WORK)
    }

    private suspend fun insertJsonFirebase(noteRequestWorks: List<NoteRequestWorkEntity>, success: SuccessResult): OperationResult<SuccessResult> {
        val json = gson.toJson(
            NoteRequestWorkJsonList(listRequestWork = noteRequestWorks)
        )
        val docRef = firebase.collection(COLLECTION_FIREBASE).document(DOCUMENT_FIREBASE)
        val updateJson = mapOf("json" to json)

        try {
            docRef.update(updateJson).await()
            getRequestWorkFirebase()
            return OperationResult.Success(success)

        } catch (e: Exception) {
            return OperationResult.Error(ERROR_FIREBASE)
        }
    }

    override fun getByTagDatesFlow(tagDate: Date): Flow<List<NoteRequestWorkEntity>> {
        return noteRequestWorkDao.getByTagDatesFlow(tagDate.time)
    }

    override suspend fun getByTagDates(tagDate: Date): List<NoteRequestWorkEntity> {
        return noteRequestWorkDao.getByTagDates(tagDate.time)
    }

    override fun getAllFlow(): Flow<List<NoteRequestWorkEntity>> {
        return noteRequestWorkDao.getAllFlow()
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
        private const val ERROR_FIREBASE = "Заяка не добавлена!"
    }
}