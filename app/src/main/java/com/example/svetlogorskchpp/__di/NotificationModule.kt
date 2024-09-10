package com.example.svetlogorskchpp.__di

import android.app.NotificationManager
import android.content.Context
import com.example.svetlogorskchpp.__data.repository.calendarNoteTag.CalendarNoteTagRepository
import com.example.svetlogorskchpp.__data.repository.note.NoteRepository
import com.example.svetlogorskchpp.__domain.usecases.calendarDateUseCases.CalendarDateUseCases
import com.example.svetlogorskchpp.__domain.usecases.calendarNoteNotificationUseCases.CalendarNoteNotificationUseCases
import com.example.svetlogorskchpp.__domain.usecases.calendarNoteNotificationUseCases.CalendarNoteNotificationUseCasesImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object NotificationModule {

    @Provides
    fun provideNotificationManager(
        @ApplicationContext context: Context,
    ): NotificationManager {
        return context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    @Provides
    fun provideCalendarNoteNotificationUseCases(
        calendarNoteTagRepository: CalendarNoteTagRepository,
        noteRepository: NoteRepository,
        calendarDateUseCases: CalendarDateUseCases,
    ): CalendarNoteNotificationUseCases {
        return CalendarNoteNotificationUseCasesImpl(
            calendarNoteTagRepository,
            noteRepository,
            calendarDateUseCases
        )
    }
}