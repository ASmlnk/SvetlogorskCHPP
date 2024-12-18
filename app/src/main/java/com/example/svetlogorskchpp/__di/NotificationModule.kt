package com.example.svetlogorskchpp.__di

import android.app.NotificationManager
import android.content.Context
import com.example.svetlogorskchpp.__data.repository.shift_schedule.calendarNoteTag.CalendarNoteTagRepository
import com.example.svetlogorskchpp.__data.repository.shift_schedule.note.NoteRepository
import com.example.svetlogorskchpp.__data.repository.shift_schedule.noteRequestWork.NoteRequestWorkRepository
import com.example.svetlogorskchpp.__domain.usecases.shift_schedule.calendarDateUseCases.CalendarDateUseCases
import com.example.svetlogorskchpp.__domain.usecases.shift_schedule.calendarDateUseCases.CalendarDateUseCasesImpl
import com.example.svetlogorskchpp.__domain.usecases.shift_schedule.calendarNoteNotificationUseCases.CalendarNoteNotificationUseCases
import com.example.svetlogorskchpp.__domain.usecases.shift_schedule.calendarNoteNotificationUseCases.CalendarNoteNotificationUseCasesImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

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
        requestWorkRepository: NoteRequestWorkRepository,
        @App calendarDateUseCases: CalendarDateUseCases,
    ): CalendarNoteNotificationUseCases {
        return CalendarNoteNotificationUseCasesImpl(
            calendarNoteTagRepository,
            noteRepository,
            requestWorkRepository,
            calendarDateUseCases
        )
    }

    @App
    @Provides
    fun provideCalendarDateUseCases(): CalendarDateUseCases {
        return CalendarDateUseCasesImpl()
    }
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class App