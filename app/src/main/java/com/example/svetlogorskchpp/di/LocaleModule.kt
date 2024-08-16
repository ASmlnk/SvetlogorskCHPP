package com.example.svetlogorskchpp.di

import android.content.Context
import androidx.room.Room
import com.example.svetlogorskchpp.data.database.AppDataBase
import com.example.svetlogorskchpp.data.database.calendarNoteTag.CalendarNoteTagDao
import com.example.svetlogorskchpp.data.database.note.NoteDao
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
        ).build()
    }

    @Provides
    fun provideCalendarNoteTagDao (dataBase: AppDataBase): CalendarNoteTagDao {
        return dataBase.calendarNoteTagDao()
    }

    @Provides
    fun provideNoteDao (dataBase: AppDataBase): NoteDao {
        return dataBase.noteDao()
    }


}