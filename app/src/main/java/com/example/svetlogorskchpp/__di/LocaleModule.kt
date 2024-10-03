package com.example.svetlogorskchpp.__di

import android.content.Context
import androidx.room.Room
import com.example.svetlogorskchpp.__data.database.AppDataBase
import com.example.svetlogorskchpp.__data.database.MIGRATION_1_2
import com.example.svetlogorskchpp.__data.database.MIGRATION_2_3
import com.example.svetlogorskchpp.__data.database.MIGRATION_3_4
import com.example.svetlogorskchpp.__data.database.MIGRATION_4_5
import com.example.svetlogorskchpp.__data.database.calendarNoteTag.CalendarNoteTagDao
import com.example.svetlogorskchpp.__data.database.note.NoteDao
import com.example.svetlogorskchpp.__data.database.requestWork.NoteRequestWorkDao
import com.example.svetlogorskchpp.__data.database.requestWorkTag.RequestWorkTagDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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
}