package com.example.svetlogorskchpp.__data.repository.firebase

import com.example.svetlogorskchpp.__data.model.FirebaseKey
import com.example.svetlogorskchpp.__data.model.ResultFirebaseJson
import com.example.svetlogorskchpp.__data.model.SuccessResultFirebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FirebaseRepository @Inject constructor(
    private val firebase: FirebaseFirestore,
    private val gson: Gson
) {
    suspend fun <T> getDocument(
        collectionPath: FirebaseKey,
        documentId: FirebaseKey,
        clazz: Class<T>
    ): List<T> = withContext(Dispatchers.IO) {
        try {
            val documentSnapshot = firebase.collection(collectionPath.getString)
                .document(documentId.getString).get().await()

            if (documentSnapshot.exists()) {
                val resultFirebaseJson = documentSnapshot.toObject(ResultFirebaseJson::class.java)
                val json = resultFirebaseJson?.json ?: return@withContext emptyList<T>()
                if (json.isEmpty()) {
                    return@withContext emptyList()
                }
                val listType = TypeToken.getParameterized(List::class.java, clazz).type
                return@withContext gson.fromJson(json, listType)  ?: emptyList<T>()
            } else {
                return@withContext emptyList<T>()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return@withContext emptyList<T>()
        }
    }

    suspend fun <E> insertDocument (
        dataList: List<E>,
        collectionPath: FirebaseKey,
        documentId: FirebaseKey
    ): SuccessResultFirebase = withContext(Dispatchers.IO){
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
}