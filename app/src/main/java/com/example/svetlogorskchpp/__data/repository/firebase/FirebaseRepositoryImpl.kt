package com.example.svetlogorskchpp.__data.repository.firebase

import com.example.svetlogorskchpp.__data.model.FirebaseKey
import com.example.svetlogorskchpp.__data.model.ResultFirebaseInfo
import com.example.svetlogorskchpp.__data.model.ResultFirebaseJson
import com.example.svetlogorskchpp.__data.model.SuccessResultFirebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FirebaseRepositoryImpl @Inject constructor(
    private val firebase: FirebaseFirestore,
    private val gson: Gson
): FirebaseDataRepository {
    suspend fun <E> getDocuments(
        collectionPath: FirebaseKey,
        documentId: FirebaseKey,
        clazz: Class<E>
    ): List<E> = withContext(Dispatchers.IO) {
        firebase.enableNetwork()
        try {
            val documentSnapshot = firebase.collection(collectionPath.getString)
                .document(documentId.getString).get().await()

            if (documentSnapshot.exists()) {
                val resultFirebaseJson = documentSnapshot.toObject(ResultFirebaseJson::class.java)
                val json = resultFirebaseJson?.json ?: return@withContext emptyList<E>()
                if (json.isEmpty()) {
                    return@withContext emptyList()
                }
                val listType = TypeToken.getParameterized(List::class.java, clazz).type
                return@withContext gson.fromJson(json, listType)  ?: emptyList<E>()
            } else {
                return@withContext emptyList<E>()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return@withContext emptyList<E>()
        }
    }

    suspend fun <E> insertDocuments (
        dataList: List<E>,
        collectionPath: FirebaseKey,
        documentId: FirebaseKey
    ): SuccessResultFirebase = withContext(Dispatchers.IO){
        firebase.enableNetwork()
        val jsonData = gson.toJson(dataList)
        val docRef = firebase.collection(collectionPath.getString)
            .document(documentId.getString)
        val updateJson = mapOf("json" to jsonData)
        try {
            docRef.update(updateJson).await()
            return@withContext SuccessResultFirebase.UPDATE_OK

        } catch (_: Exception) {
            return@withContext SuccessResultFirebase.UPDATE_ERROR
        }
    }

    override suspend fun  getDocumentInfo(
        collectionPath: FirebaseKey,
        documentId: FirebaseKey
    ): String? = withContext(Dispatchers.IO) {
        firebase.enableNetwork()
        try {
            val documentSnapshot = firebase.collection(collectionPath.getString)
                .document(documentId.getString).get().await()

            if (documentSnapshot.exists()) {
                val resultFirebase = documentSnapshot.toObject(ResultFirebaseInfo::class.java)
                return@withContext resultFirebase?.info
            } else {
                return@withContext null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return@withContext null
        }
    }
}