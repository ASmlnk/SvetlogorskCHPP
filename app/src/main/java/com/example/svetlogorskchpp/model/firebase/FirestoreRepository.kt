package com.example.svetlogorskchpp.model.firebase

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FirestoreRepository {

    val remoteDB = Firebase.firestore


     fun getAll ( request: String ): CollectionReference {

        return remoteDB.collection(request)
    }
}