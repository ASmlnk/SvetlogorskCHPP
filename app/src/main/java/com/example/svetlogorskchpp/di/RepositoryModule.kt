package com.example.svetlogorskchpp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.example.svetlogorskchpp.data.database.CalendarNotesDao
import com.example.svetlogorskchpp.data.repository.preferences.PreferencesRepository
import com.example.svetlogorskchpp.data.repository.preferences.PreferencesRepositoryImpl
import com.example.svetlogorskchpp.data.repository.shiftPersonnel.ShiftPersonalRepository
import com.example.svetlogorskchpp.data.repository.shiftPersonnel.ShiftPersonalRepositoryImpl
import com.example.svetlogorskchpp.model.firebase.FirestoreRepository
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideDataStorePreferences(@ApplicationContext appContext: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create {
            appContext.preferencesDataStoreFile("settings")
        }
    }

    @Provides
    @Singleton
    fun providePreferencesRepository(dataStore: DataStore<Preferences>): PreferencesRepository {
        return PreferencesRepositoryImpl(dataStore)
    }

    @Provides
    @Singleton
    fun provideFirestoreRepository(remoteDB: FirebaseFirestore): FirestoreRepository {
        return FirestoreRepository(remoteDB)
    }

    @Provides
    @Singleton
    fun provideShiftPersonalRepository(remoteDB: FirebaseFirestore, shiftPersonalDao: CalendarNotesDao): ShiftPersonalRepository {
        return ShiftPersonalRepositoryImpl(remoteDB, shiftPersonalDao)
    }

}