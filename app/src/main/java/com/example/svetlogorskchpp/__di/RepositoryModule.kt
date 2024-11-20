package com.example.svetlogorskchpp.__di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.example.svetlogorskchpp.__data.database.calendarNoteTag.CalendarNoteTagDao
import com.example.svetlogorskchpp.__data.database.electrical_equipment.ElMotor.ElMotorDao
import com.example.svetlogorskchpp.__data.database.electrical_equipment.ElMotor.ElMotorEntity
import com.example.svetlogorskchpp.__data.database.electrical_equipment.LightingAndOther.LightingAndOtherDao
import com.example.svetlogorskchpp.__data.database.electrical_equipment.LightingAndOther.LightingAndOtherEntity
import com.example.svetlogorskchpp.__data.database.electrical_equipment.OpenSwitchgearTr.OpenSwitchgearTrDao
import com.example.svetlogorskchpp.__data.database.electrical_equipment.OpenSwitchgearTr.OpenSwitchgearTrEntity
import com.example.svetlogorskchpp.__data.database.electrical_equipment.OpenSwitchgearVl.OpenSwitchgearVlDao
import com.example.svetlogorskchpp.__data.database.electrical_equipment.OpenSwitchgearVl.OpenSwitchgearVlEntity
import com.example.svetlogorskchpp.__data.database.electrical_equipment.Switchgear.SwitchgearDao
import com.example.svetlogorskchpp.__data.database.electrical_equipment.Switchgear.SwitchgearEntity
import com.example.svetlogorskchpp.__data.database.electrical_equipment.transformerOwnNeeds.TransformerOwnNeedsDao
import com.example.svetlogorskchpp.__data.database.electrical_equipment.transformerOwnNeeds.TransformerOwnNeedsEntity
import com.example.svetlogorskchpp.__data.database.electrical_equipment.turbogenerator.TurboGeneratorDao
import com.example.svetlogorskchpp.__data.database.electrical_equipment.turbogenerator.TurboGeneratorEntity
import com.example.svetlogorskchpp.__data.database.note.NoteDao
import com.example.svetlogorskchpp.__data.database.requestWork.NoteRequestWorkDao
import com.example.svetlogorskchpp.__data.database.requestWorkTag.RequestWorkTagDao
import com.example.svetlogorskchpp.__data.repository.equipment.EquipmentConsumerRepository
import com.example.svetlogorskchpp.__data.repository.equipment.EquipmentRepository
import com.example.svetlogorskchpp.__data.repository.equipment.electrical.ElMotorRepositoryImpl
import com.example.svetlogorskchpp.__data.repository.equipment.electrical.EquipmentUpdateFirebaseRepository
import com.example.svetlogorskchpp.__data.repository.equipment.electrical.LightingAndOtherRepositoryImpl
import com.example.svetlogorskchpp.__data.repository.equipment.electrical.OpenSwitchgearTrEquipmentRepositoryImpl
import com.example.svetlogorskchpp.__data.repository.equipment.electrical.OpenSwitchgearVlEquipmentRepositoryImpl
import com.example.svetlogorskchpp.__data.repository.equipment.electrical.SwitchgearRepositoryImpl
import com.example.svetlogorskchpp.__data.repository.equipment.electrical.TransformerOwnNeedsRepositoryImpl
import com.example.svetlogorskchpp.__data.repository.equipment.electrical.TurboGeneratorRepositoryImpl
import com.example.svetlogorskchpp.__data.repository.firebase.FirebaseBigJsonRepository
import com.example.svetlogorskchpp.__data.repository.firebase.FirebaseRepository
import com.example.svetlogorskchpp.__data.repository.shift_schedule.calendarNoteTag.CalendarNoteTagRepository
import com.example.svetlogorskchpp.__data.repository.shift_schedule.calendarNoteTag.CalendarNoteTagRepositoryImpl
import com.example.svetlogorskchpp.__data.repository.shift_schedule.calendarRequestWorkTag.CalendarRequestWorkTagRepository
import com.example.svetlogorskchpp.__data.repository.shift_schedule.calendarRequestWorkTag.CalendarRequestWorkTagRepositoryImpl
import com.example.svetlogorskchpp.__data.repository.shift_schedule.note.NoteRepository
import com.example.svetlogorskchpp.__data.repository.shift_schedule.note.NoteRepositoryImpl
import com.example.svetlogorskchpp.__data.repository.shift_schedule.noteRequestWork.NoteRequestWorkRepository
import com.example.svetlogorskchpp.__data.repository.shift_schedule.noteRequestWork.NoteRequestWorkRepositoryImpl
import com.example.svetlogorskchpp.__data.repository.preferences.NotesNotificationPreferencesRepository
import com.example.svetlogorskchpp.__data.repository.preferences.PreferencesRepository
import com.example.svetlogorskchpp.__data.repository.preferences.PreferencesRepositoryImpl
import com.example.svetlogorskchpp.__data.repository.preferences.RequestWorkPreferencesRepository
import com.example.svetlogorskchpp.__data.repository.shift_schedule.shiftPersonnel.ShiftPersonalRepository
import com.example.svetlogorskchpp.__data.repository.shift_schedule.shiftPersonnel.ShiftPersonalRepositoryImpl
import com.example.svetlogorskchpp.model.firebase.FirestoreRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
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

    @Provides
    fun provideGson(): Gson {
        return GsonBuilder().create()
    }

    @Provides
    @Singleton
    fun provideFirebaseRepository(
        firebase: FirebaseFirestore,
        gson: Gson
    ): FirebaseRepository {
        return FirebaseRepository(firebase,gson)
    }

    @Provides
    @Singleton
    fun provideFirebaseBigJsonRepository(
        firebase: FirebaseFirestore,
        gson: Gson
    ): FirebaseBigJsonRepository {
        return FirebaseBigJsonRepository(firebase,gson)
    }

    @Provides
    @Singleton
    fun provideOpenSwitchgearVlRepository(
        openSwitchgearVlDao: OpenSwitchgearVlDao,
        repositoryFirebase: FirebaseRepository
    ): EquipmentRepository<OpenSwitchgearVlEntity> {
        return OpenSwitchgearVlEquipmentRepositoryImpl(openSwitchgearVlDao, repositoryFirebase)
    }

    @Provides
    @Singleton
    fun provideOpenSwitchgearTrRepository(
        openSwitchgearTrDao: OpenSwitchgearTrDao,
        repositoryFirebase: FirebaseRepository
    ): EquipmentRepository<OpenSwitchgearTrEntity> {
        return OpenSwitchgearTrEquipmentRepositoryImpl(openSwitchgearTrDao, repositoryFirebase)
    }

    @Provides
    @Singleton
    fun provideTransformerOwnNeedsRepository(
        dao: TransformerOwnNeedsDao,
        repositoryFirebase: FirebaseRepository
    ): EquipmentRepository<TransformerOwnNeedsEntity> {
        return TransformerOwnNeedsRepositoryImpl(dao, repositoryFirebase)
    }

    @Provides
    @Singleton
    fun provideTurboGeneratorRepository (
        dao: TurboGeneratorDao,
        repositoryFirebase: FirebaseRepository
    ): EquipmentRepository<TurboGeneratorEntity> {
        return TurboGeneratorRepositoryImpl(dao, repositoryFirebase)
    }

    @Provides
    @Singleton
    fun provideTransformerOwnNeedsConsumerRepository(
        dao: TransformerOwnNeedsDao,
        repositoryFirebase: FirebaseRepository
    ): EquipmentConsumerRepository<TransformerOwnNeedsEntity> {
        return TransformerOwnNeedsRepositoryImpl(dao, repositoryFirebase)
    }

    @Provides
    @Singleton
    fun provideTurboGeneratorConsumerRepository (
        dao: TurboGeneratorDao,
        repositoryFirebase: FirebaseRepository
    ): EquipmentConsumerRepository<TurboGeneratorEntity> {
        return TurboGeneratorRepositoryImpl(dao, repositoryFirebase)
    }

    @Provides
    @Singleton
    fun provideElMotorRepository (
        dao: ElMotorDao,
        repositoryFirebase: FirebaseBigJsonRepository
    ): EquipmentRepository<ElMotorEntity> {
        return ElMotorRepositoryImpl(dao, repositoryFirebase)
    }

    @Provides
    @Singleton
    fun provideElMotorConsumerRepository (
        dao: ElMotorDao,
        repositoryFirebase: FirebaseBigJsonRepository
    ): EquipmentConsumerRepository<ElMotorEntity> {
        return ElMotorRepositoryImpl(dao, repositoryFirebase)
    }


    @Provides
    @Singleton
    fun provideSwitchgearRepository (
        dao: SwitchgearDao,
        repositoryFirebase: FirebaseBigJsonRepository
    ): EquipmentRepository<SwitchgearEntity> {
        return SwitchgearRepositoryImpl(dao, repositoryFirebase)
    }

    @Provides
    @Singleton
    fun provideSwitchgearConsumerRepository (
        dao: SwitchgearDao,
        repositoryFirebase: FirebaseBigJsonRepository
    ): EquipmentConsumerRepository<SwitchgearEntity> {
        return SwitchgearRepositoryImpl(dao, repositoryFirebase)
    }


    @Provides
    @Singleton
    fun provideLightingAndOtherEntityRepository (
        dao: LightingAndOtherDao,
        repositoryFirebase: FirebaseBigJsonRepository
    ): EquipmentRepository<LightingAndOtherEntity> {
        return LightingAndOtherRepositoryImpl(dao, repositoryFirebase)
    }

    @Provides
    @Singleton
    fun provideLightingAndOtherEntityConsumerRepository (
        dao: LightingAndOtherDao,
        repositoryFirebase: FirebaseBigJsonRepository
    ): EquipmentConsumerRepository<LightingAndOtherEntity> {
        return LightingAndOtherRepositoryImpl(dao, repositoryFirebase)
    }

    @Provides
    @Singleton
    @ElMotor
    fun provideElMotorUpdateFirabaseRepository (
        dao: ElMotorDao,
        repositoryFirebase: FirebaseBigJsonRepository
    ): EquipmentUpdateFirebaseRepository {
        return ElMotorRepositoryImpl(dao, repositoryFirebase)
    }

    @Provides
    @Singleton
    @Switchgear
    fun provideSwitchgearUpdateFirabaseRepository (
        dao: SwitchgearDao,
        repositoryFirebase: FirebaseBigJsonRepository
    ): EquipmentUpdateFirebaseRepository {
        return SwitchgearRepositoryImpl(dao, repositoryFirebase)
    }

    @Provides
    @Singleton
    @LightingAndOther
    fun provideLightingAndOtherUpdateRepository (
        dao: LightingAndOtherDao,
        repositoryFirebase: FirebaseBigJsonRepository
    ): EquipmentUpdateFirebaseRepository {
        return LightingAndOtherRepositoryImpl(dao, repositoryFirebase)
    }
}



@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ElMotor

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LightingAndOther

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Switchgear