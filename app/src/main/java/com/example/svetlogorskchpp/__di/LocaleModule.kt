package com.example.svetlogorskchpp.__di

import android.content.Context
import androidx.room.Room
import com.example.svetlogorskchpp.__data.database.AppDataBase
import com.example.svetlogorskchpp.__data.database.MIGRATION_1_2
import com.example.svetlogorskchpp.__data.database.MIGRATION_2_3
import com.example.svetlogorskchpp.__data.database.MIGRATION_3_4
import com.example.svetlogorskchpp.__data.database.MIGRATION_4_5
import com.example.svetlogorskchpp.__data.database.MIGRATION_5_6
import com.example.svetlogorskchpp.__data.database.MIGRATION_6_7
import com.example.svetlogorskchpp.__data.database.calendarNoteTag.CalendarNoteTagDao
import com.example.svetlogorskchpp.__data.database.electrical_equipment.OpenSwitchgearTr.OpenSwitchgearTrDao
import com.example.svetlogorskchpp.__data.database.electrical_equipment.OpenSwitchgearVl.OpenSwitchgearVlDao
import com.example.svetlogorskchpp.__data.database.note.NoteDao
import com.example.svetlogorskchpp.__data.database.requestWork.NoteRequestWorkDao
import com.example.svetlogorskchpp.__data.database.requestWorkTag.RequestWorkTagDao
import com.example.svetlogorskchpp.__data.hard.HardDataRepository
import com.example.svetlogorskchpp.__data.hard.RequestWorkHardDataImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocaleModule {

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext appContext: Context): AppDataBase {
        return Room.databaseBuilder(
            appContext,
            AppDataBase::class.java,
            "app_database"
        )
            .addMigrations(MIGRATION_1_2)
            .addMigrations(MIGRATION_2_3)
            .addMigrations(MIGRATION_3_4)
            .addMigrations(MIGRATION_4_5)
            .addMigrations(MIGRATION_5_6)
            .addMigrations(MIGRATION_6_7)
            .build()
    }

    @Provides
    fun provideCalendarNoteTagDao (dataBase: AppDataBase): CalendarNoteTagDao {
        return dataBase.calendarNoteTagDao()
    }

    @Provides
    fun provideNoteDao (dataBase: AppDataBase): NoteDao {
        return dataBase.noteDao()
    }

    @Provides
    fun provideRequestWorkTagDao (dataBase: AppDataBase): RequestWorkTagDao {
        return dataBase.requestWorkTagDao()
    }

    @Provides
    fun provideNoteRequestWorkDao (dataBase: AppDataBase): NoteRequestWorkDao {
        return dataBase.requestWorkDao()
    }

    @Provides
    @RequestWorkReason
    fun provideRequestWorkReasonHardData (): HardDataRepository<String> {
        return RequestWorkHardDataImpl.Reason
    }

    @Provides
    fun provideOpenSwitchgearVlDao(dataBase: AppDataBase) : OpenSwitchgearVlDao {
        return dataBase.openSwitchgearVlDao()
    }

    @Provides
    fun provideOpenSwitchgearTrDao(dataBase: AppDataBase) : OpenSwitchgearTrDao {
        return dataBase.openSwitchgearTrDao()
    }

    @Provides
    @RequestWorkAccession
    fun provideRequestWorkAccessionHardData (): HardDataRepository<String> {
        return RequestWorkHardDataImpl.Accession
    }

}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RequestWorkReason

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RequestWorkAccession