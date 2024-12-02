package com.example.svetlogorskchpp.__data.repository.firebase

import com.example.svetlogorskchpp.__data.model.FirebaseKey

interface FirebaseDataRepository {
    suspend fun  getDocumentInfo(collectionPath: FirebaseKey, documentId: FirebaseKey): String?
}