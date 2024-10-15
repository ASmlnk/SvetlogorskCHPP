package com.example.svetlogorskchpp.__di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.example.svetlogorskchpp.__data.database.calendarNoteTag.CalendarNoteTagDao
import com.example.svetlogorskchpp.__data.database.note.NoteDao
import com.example.svetlogorskchpp.__data.database.requestWork.NoteRequestWorkDao
import com.example.svetlogorskchpp.__data.database.requestWorkTag.RequestWorkTagDao
import com.example.svetlogorskchpp.__data.repository.calendarNoteTag.CalendarNoteTagRepository
import com.example.svetlogorskchpp.__data.repository.calendarNoteTag.CalendarNoteTagRepositoryImpl
import com.example.svetlogorskchpp.__data.repository.calendarRequestWorkTag.CalendarRequestWorkTagRepository
import com.example.svetlogorskchpp.__data.repository.calendarRequestWorkTag.CalendarRequestWorkTagRepositoryImpl
import com.example.svetlogorskchpp.__data.repository.note.NoteRepository
import com.example.svetlogorskchpp.__data.repository.note.NoteRepositoryImpl
import com.example.svetlogorskchpp.__data.repository.noteRequestWork.NoteRequestWorkRepository
import com.example.svetlogorskchpp.__data.repository.noteRequestWork.NoteRequestWorkRepositoryImpl
import com.example.svetlogorskchpp.__data.repository.preferences.NotesNotificationPreferencesRepository
import com.example.svetlogorskchpp.__data.repository.preferences.PreferencesRepository
import com.example.svetlogorskchpp.__data.repository.preferences.PreferencesRepositoryImpl
import com.example.svetlogorskchpp.__data.repository.preferences.RequestWorkPreferencesRepository
import com.example.svetlogorskchpp.__data.repository.shiftPersonnel.ShiftPersonalRepository
import com.example.svetlogorskchpp.__data.repository.shiftPersonnel.ShiftPersonalRepositoryImpl
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
    fun provideRequestWorkPreferencesRepository(dataStore: DataStore<Preferences>): RequestWorkPreferencesRepository {
        return PreferencesRepositoryImpl(dataStore)
    }

    @Provides
    @Singleton
    fun provideNotesNotificationPreferencesRepository(dataStore: DataStore<Preferences>): NotesNotificationPreferencesRepository {
        return PreferencesRepositoryImpl(dataStore)
    }

    @Provides
    @Singleton
    fun provideFirestoreRepository(remoteDB: FirebaseFirestore): FirestoreRepository {
        return FirestoreRepository(remoteDB)
    }

    @Provides
    @Singleton
    fun provideShiftPersonalRepository(remoteDB: FirebaseFirestore): ShiftPersonalRepository {
        return ShiftPersonalRepositoryImpl(remoteDB)
    }

    @Provides
    @Singleton
    fun provideCalendarNoteTagRepository(calendarNoteTagDao: CalendarNoteTagDao): CalendarNoteTagRepository {
        return CalendarNoteTagRepositoryImpl(calendarNoteTagDao)
    }

    @Provides
    @Singleton
    fun provideNoteRepository(noteDao: NoteDao): NoteRepository {
        return NoteRepositoryImpl(noteDao)
    }

    @Provides
    @Singleton
    fun provideNoteRequestWorkRepository(
        firebase: FirebaseFirestore,
        calendarRequestWorkTagRepository: CalendarRequestWorkTagRepository,
        noteRequestWorkDao: NoteRequestWorkDao
    ): NoteRequestWorkRepository {
        return NoteRequestWorkRepositoryImpl(firebase,calendarRequestWorkTagRepository, noteRequestWorkDao)
    }

    @Provides
    @Singleton
    fun provideRequestWorkTagRepository(
        requestWorkTagDao: RequestWorkTagDao
    ): CalendarRequestWorkTagRepository {
        return CalendarRequestWorkTagRepositoryImpl(requestWorkTagDao)
    }

    @Provides
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }

}