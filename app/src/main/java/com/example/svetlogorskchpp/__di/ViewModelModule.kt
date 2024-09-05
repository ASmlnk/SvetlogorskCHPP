package com.example.svetlogorskchpp.__di

import com.example.svetlogorskchpp.__domain.usecases.calendarDate.CalendarDateUseCases
import com.example.svetlogorskchpp.__domain.usecases.calendarDate.CalendarDateUseCasesImpl
import com.example.svetlogorskchpp.__domain.usecases.calendarNote.CalendarNoteUseCases
import com.example.svetlogorskchpp.__domain.usecases.calendarNote.CalendarNoteUseCasesImpl
import com.example.svetlogorskchpp.__domain.usecases.calendarNoteTag.CalendarNoteTagUseCases
import com.example.svetlogorskchpp.__domain.usecases.calendarNoteTag.CalendarNoteTagUseCasesImpl
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class ViewModelModule {

    @Binds
    abstract fun provideCalendarDate(calendarDateUseCasesImpl: CalendarDateUseCasesImpl): CalendarDateUseCases

    @Binds
    abstract fun provideCalendarNoteTagUseCases(calendarNoteTagCasesImpl: CalendarNoteTagUseCasesImpl): CalendarNoteTagUseCases

    @Binds
    abstract fun provideCalendarNoteUseCases(calendarNoteCasesImpl: CalendarNoteUseCasesImpl): CalendarNoteUseCases

    @Binds
    abstract fun provideFirestoryRepository( remoteDB: FirebaseFirestore): FirebaseFirestore





}