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
import com.example.svetlogorskchpp.__data.repository.equipment.EquipmentElMotorChapterRepository
import com.example.svetlogorskchpp.__data.repository.equipment.EquipmentItemDeleteRepository
import com.example.svetlogorskchpp.__data.repository.equipment.EquipmentRepository
import com.example.svetlogorskchpp.__data.repository.equipment.ReservationSaveFileRepository
import com.example.svetlogorskchpp.__data.repository.equipment.electrical.ElMotorRepositoryImpl
import com.example.svetlogorskchpp.__data.repository.equipment.electrical.EquipmentUpdateFirebaseRepository
import com.example.svetlogorskchpp.__data.repository.equipment.electrical.LightingAndOtherRepositoryImpl
import com.example.svetlogorskchpp.__data.repository.equipment.electrical.OpenSwitchgearTrEquipmentRepositoryImpl
import com.example.svetlogorskchpp.__data.repository.equipment.electrical.OpenSwitchgearVlEquipmentRepositoryImpl
import com.example.svetlogorskchpp.__data.repository.equipment.electrical.SwitchgearRepositoryImpl
import com.example.svetlogorskchpp.__data.repository.equipment.electrical.TransformerOwnNeedsRepositoryImpl
import com.example.svetlogorskchpp.__data.repository.equipment.electrical.TurboGeneratorRepositoryImpl
import com.example.svetlogorskchpp.__data.repository.firebase.FirebaseBigJsonRepository
import com.example.svetlogorskchpp.__data.repository.firebase.FirebaseRepositoryImpl
import com.example.svetlogorskchpp.__data.repository.preferences.EditAccessPreferencesRepository
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
    fun provideEditAccessPreferencesRepository(dataStore: DataStore<Preferences>) : EditAccessPreferencesRepository {
        return PreferencesRepositoryImpl(dataStore)
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
        return NoteRequestWorkRepositoryImpl(
            firebase,
            calendarRequestWorkTagRepository,
            noteRequestWorkDao
        )
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
    ): FirebaseRepositoryImpl {
        return FirebaseRepositoryImpl(firebase, gson)
    }

    @Provides
    @Singleton
    fun provideFirebaseBigJsonRepository(
        firebase: FirebaseFirestore,
        gson: Gson
    ): FirebaseBigJsonRepository {
        return FirebaseBigJsonRepository(firebase, gson)
    }

    @Provides
    @Singleton
    fun provideOpenSwitchgearVlRepository(
        openSwitchgearVlDao: OpenSwitchgearVlDao,
        repositoryFirebase: FirebaseRepositoryImpl,
        reservationSaveFileRepository: ReservationSaveFileRepository
    ): EquipmentRepository<OpenSwitchgearVlEntity> {
        return OpenSwitchgearVlEquipmentRepositoryImpl(openSwitchgearVlDao, repositoryFirebase, reservationSaveFileRepository)
    }

    @Provides
    @Singleton
    fun provideOpenSwitchgearTrRepository(
        openSwitchgearTrDao: OpenSwitchgearTrDao,
        repositoryFirebase: FirebaseRepositoryImpl,
        reservationSaveFileRepository: ReservationSaveFileRepository
    ): EquipmentRepository<OpenSwitchgearTrEntity> {
        return OpenSwitchgearTrEquipmentRepositoryImpl(openSwitchgearTrDao, repositoryFirebase, reservationSaveFileRepository)
    }

    @Provides
    @Singleton
    fun provideTransformerOwnNeedsRepository(
        dao: TransformerOwnNeedsDao,
        repositoryFirebase: FirebaseRepositoryImpl,
        reservationSaveFileRepository: ReservationSaveFileRepository
    ): EquipmentRepository<TransformerOwnNeedsEntity> {
        return TransformerOwnNeedsRepositoryImpl(dao, repositoryFirebase, reservationSaveFileRepository)
    }

    @Provides
    @Singleton
    fun provideTurboGeneratorRepository(
        dao: TurboGeneratorDao,
        repositoryFirebase: FirebaseRepositoryImpl,
        reservationSaveFileRepository: ReservationSaveFileRepository
    ): EquipmentRepository<TurboGeneratorEntity> {
        return TurboGeneratorRepositoryImpl(dao, repositoryFirebase, reservationSaveFileRepository)
    }

    @Provides
    @Singleton
    fun provideTransformerOwnNeedsConsumerRepository(
        dao: TransformerOwnNeedsDao,
        repositoryFirebase: FirebaseRepositoryImpl,
        reservationSaveFileRepository: ReservationSaveFileRepository
    ): EquipmentConsumerRepository<TransformerOwnNeedsEntity> {
        return TransformerOwnNeedsRepositoryImpl(dao, repositoryFirebase, reservationSaveFileRepository)
    }

    @Provides
    @Singleton
    fun provideTurboGeneratorConsumerRepository(
        dao: TurboGeneratorDao,
        repositoryFirebase: FirebaseRepositoryImpl,
        reservationSaveFileRepository: ReservationSaveFileRepository
    ): EquipmentConsumerRepository<TurboGeneratorEntity> {
        return TurboGeneratorRepositoryImpl(dao, repositoryFirebase, reservationSaveFileRepository)
    }

    @Provides
    @Singleton
    fun provideElMotorRepository(
        dao: ElMotorDao,
        repositoryFirebase: FirebaseBigJsonRepository,
        reservationSaveFileRepository: ReservationSaveFileRepository
    ): EquipmentRepository<ElMotorEntity> {
        return ElMotorRepositoryImpl(dao, repositoryFirebase, reservationSaveFileRepository)
    }

    @Provides
    @Singleton
    fun provideElMotorConsumerRepository(
        dao: ElMotorDao,
        repositoryFirebase: FirebaseBigJsonRepository,
        reservationSaveFileRepository: ReservationSaveFileRepository
    ): EquipmentConsumerRepository<ElMotorEntity> {
        return ElMotorRepositoryImpl(dao, repositoryFirebase,reservationSaveFileRepository)
    }


    @Provides
    @Singleton
    fun provideSwitchgearRepository(
        dao: SwitchgearDao,
        repositoryFirebase: FirebaseBigJsonRepository,
        reservationSaveFileRepository: ReservationSaveFileRepository
    ): EquipmentRepository<SwitchgearEntity> {
        return SwitchgearRepositoryImpl(dao, repositoryFirebase, reservationSaveFileRepository)
    }

    @Provides
    @Singleton
    fun provideSwitchgearConsumerRepository(
        dao: SwitchgearDao,
        repositoryFirebase: FirebaseBigJsonRepository,
        reservationSaveFileRepository: ReservationSaveFileRepository
    ): EquipmentConsumerRepository<SwitchgearEntity> {
        return SwitchgearRepositoryImpl(dao, repositoryFirebase, reservationSaveFileRepository)
    }


    @Provides
    @Singleton
    fun provideLightingAndOtherEntityRepository(
        dao: LightingAndOtherDao,
        repositoryFirebase: FirebaseBigJsonRepository,
        reservationSaveFileRepository: ReservationSaveFileRepository
    ): EquipmentRepository<LightingAndOtherEntity> {
        return LightingAndOtherRepositoryImpl(dao, repositoryFirebase, reservationSaveFileRepository)
    }

    @Provides
    @Singleton
    fun provideLightingAndOtherEntityConsumerRepository(
        dao: LightingAndOtherDao,
        repositoryFirebase: FirebaseBigJsonRepository,
        reservationSaveFileRepository: ReservationSaveFileRepository
    ): EquipmentConsumerRepository<LightingAndOtherEntity> {
        return LightingAndOtherRepositoryImpl(dao, repositoryFirebase, reservationSaveFileRepository)
    }

    @Provides
    @Singleton
    @ElMotor
    fun provideElMotorUpdateFirabaseRepository(
        dao: ElMotorDao,
        repositoryFirebase: FirebaseBigJsonRepository,
        reservationSaveFileRepository: ReservationSaveFileRepository
    ): EquipmentUpdateFirebaseRepository {
        return ElMotorRepositoryImpl(dao, repositoryFirebase,reservationSaveFileRepository)
    }

    @Provides
    @Singleton
    @Switchgear
    fun provideSwitchgearUpdateFirabaseRepository(
        dao: SwitchgearDao,
        repositoryFirebase: FirebaseBigJsonRepository,
        reservationSaveFileRepository: ReservationSaveFileRepository
    ): EquipmentUpdateFirebaseRepository {
        return SwitchgearRepositoryImpl(dao, repositoryFirebase, reservationSaveFileRepository)
    }

    @Provides
    @Singleton
    @LightingAndOther
    fun provideLightingAndOtherUpdateRepository(
        dao: LightingAndOtherDao,
        repositoryFirebase: FirebaseBigJsonRepository,
        reservationSaveFileRepository: ReservationSaveFileRepository
    ): EquipmentUpdateFirebaseRepository {
        return LightingAndOtherRepositoryImpl(dao, repositoryFirebase, reservationSaveFileRepository)
    }

    @Provides
    @Singleton
    @Vl
    fun provideOpenSwitchgearVlUpdateRepository(
        dao: OpenSwitchgearVlDao,
        repositoryFirebase: FirebaseRepositoryImpl,
        reservationSaveFileRepository: ReservationSaveFileRepository
    ): EquipmentUpdateFirebaseRepository {
        return OpenSwitchgearVlEquipmentRepositoryImpl (dao, repositoryFirebase, reservationSaveFileRepository)
    }

    @Provides
    @Singleton
    @Tr
    fun provideOpenSwitchgearTrUpdateRepository(
        dao: OpenSwitchgearTrDao,
        repositoryFirebase: FirebaseRepositoryImpl,
        reservationSaveFileRepository: ReservationSaveFileRepository
    ): EquipmentUpdateFirebaseRepository {
        return OpenSwitchgearTrEquipmentRepositoryImpl (dao, repositoryFirebase, reservationSaveFileRepository)
    }

    @Provides
    @Singleton
    @Tsn
    fun provideTransformerOwnNeedsUpdateRepository(
        dao: TransformerOwnNeedsDao,
        repositoryFirebase: FirebaseRepositoryImpl,
        reservationSaveFileRepository: ReservationSaveFileRepository
        ): EquipmentUpdateFirebaseRepository {
        return TransformerOwnNeedsRepositoryImpl (dao, repositoryFirebase, reservationSaveFileRepository)
    }

    @Provides
    @Singleton
    @Tg
    fun provideTurboGeneratorUpdateRepository(
        dao: TurboGeneratorDao,
        repositoryFirebase: FirebaseRepositoryImpl,
        reservationSaveFileRepository: ReservationSaveFileRepository
    ): EquipmentUpdateFirebaseRepository {
        return TurboGeneratorRepositoryImpl (dao, repositoryFirebase, reservationSaveFileRepository)
    }

    @Provides
    @Singleton
    @ElMotorDel
    fun provideElMotorItemDeleteFirabaseRepository(
        dao: ElMotorDao,
        repositoryFirebase: FirebaseBigJsonRepository,
        reservationSaveFileRepository: ReservationSaveFileRepository
    ): EquipmentItemDeleteRepository {
        return ElMotorRepositoryImpl(dao, repositoryFirebase,reservationSaveFileRepository)
    }

    @Provides
    @Singleton
    @Switchgear
    fun provideSwitchgearItemDeleteFirabaseRepository(
        dao: SwitchgearDao,
        repositoryFirebase: FirebaseBigJsonRepository,
        reservationSaveFileRepository: ReservationSaveFileRepository
    ): EquipmentItemDeleteRepository {
        return SwitchgearRepositoryImpl(dao, repositoryFirebase, reservationSaveFileRepository)
    }

    @Provides
    @Singleton
    @LightingAndOther
    fun provideLightingAndOtherItemDeleteRepository(
        dao: LightingAndOtherDao,
        repositoryFirebase: FirebaseBigJsonRepository,
        reservationSaveFileRepository: ReservationSaveFileRepository
    ): EquipmentItemDeleteRepository {
        return LightingAndOtherRepositoryImpl(dao, repositoryFirebase, reservationSaveFileRepository)
    }

    @Provides
    @Singleton
    fun provideElMotorChapterRepository(
         dao: ElMotorDao,
         firebaseBigJsonRepository: FirebaseBigJsonRepository,
         reservationSaveFileRepository: ReservationSaveFileRepository
    ): EquipmentElMotorChapterRepository {
        return ElMotorRepositoryImpl(dao,firebaseBigJsonRepository,reservationSaveFileRepository)
    }

    @Provides
    fun provideReservationSaveFileRepository(
        gson: Gson,
        context: Context
    ): ReservationSaveFileRepository {
        return ReservationSaveFileRepository(gson, context)
    }
}


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ElMotor

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ElMotorDel

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LightingAndOther

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Switchgear

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Vl

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Tr

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Tg

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Tsn