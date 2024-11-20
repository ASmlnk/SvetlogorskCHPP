package com.example.svetlogorskchpp.__data.repository.firebase

import com.example.svetlogorskchpp.__data.model.FirebaseKey
import com.example.svetlogorskchpp.__data.model.ResultFirebaseBigJson
import com.example.svetlogorskchpp.__data.model.SuccessResultFirebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FirebaseBigJsonRepository@Inject constructor(
    private val firebase: FirebaseFirestore,
    private val gson: Gson
) {
    suspend fun <T> getDocument(
        collectionPath: FirebaseKey,
        documentId: FirebaseKey,
        clazz: Class<T>
    ): List<T> = withContext(Dispatchers.IO) {
        firebase.enableNetwork()
        try {
            val documentSnapshot = firebase.collection(collectionPath.getString)
                .document(documentId.getString).get().await()

            if (documentSnapshot.exists()) {
                val resultFirebaseJson = documentSnapshot.toObject(ResultFirebaseBigJson::class.java)
                if (resultFirebaseJson == null) return@withContext emptyList<T>()

                val listResult1 = async(Dispatchers.IO) {
                    val json1 = resultFirebaseJson.json1
                    val listType1 = TypeToken.getParameterized(List::class.java, clazz).type
                    gson.fromJson(json1, listType1)  ?: emptyList<T>()
                }

                val listResult2 = async(Dispatchers.IO) {
                    val json2 = resultFirebaseJson.json2
                    val listType2 = TypeToken.getParameterized(List::class.java, clazz).type
                    gson.fromJson(json2, listType2) ?: emptyList<T>()
                }

                val listResult3 = async(Dispatchers.IO) {
                    val json3 = resultFirebaseJson.json3
                    val listType3 = TypeToken.getParameterized(List::class.java, clazz).type
                    gson.fromJson(json3, listType3) ?: emptyList<T>()
                }

               return@withContext listResult1.await() + listResult2.await() +listResult3.await()

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
        firebase.enableNetwork()

        val size = dataList.size
        val chunkSize = size / 3
        val remainder = size % 3

        val list1 = dataList.subList(0, chunkSize + if (remainder > 0) 1 else 0)
        val list2 = dataList.subList(list1.size, list1.size + chunkSize + if (remainder > 1) 1 else 0)
        val list3 = dataList.subList(list1.size + list2.size, size)

        val jsonData1 = gson.toJson(list1)
        val jsonData2 = gson.toJson(list2)
        val jsonData3 = gson.toJson(list3)

        val docRef = firebase.collection(collectionPath.getString)
            .document(documentId.getString)
        val updateJson = mapOf("json1" to jsonData1,"json2" to jsonData2,"json3" to jsonData3)
        try {
            docRef.update(updateJson).await()
            return@withContext SuccessResultFirebase.UPDATE_OK

        } catch (_: Exception) {
            return@withContext SuccessResultFirebase.UPDATE_ERROR
        }
    }
}


