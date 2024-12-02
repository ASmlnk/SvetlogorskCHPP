package com.example.svetlogorskchpp.__di

import com.example.svetlogorskchpp.__data.repository.firebase.FirebaseDataRepository
import com.example.svetlogorskchpp.__data.repository.firebase.FirebaseRepositoryImpl
import com.example.svetlogorskchpp.model.firebase.FirestoreRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FirebaseModule {

    @Provides
    @Singleton
    fun provideFirestoreRepository(remoteDB: FirebaseFirestore): FirestoreRepository {
        return FirestoreRepository(remoteDB)
    }

    @Provides
    @Singleton
    fun provideFirebaseDataRepository(
        firebase: FirebaseFirestore,
        gson: Gson
    ): FirebaseDataRepository {
        return FirebaseRepositoryImpl(firebase, gson)
    }
}