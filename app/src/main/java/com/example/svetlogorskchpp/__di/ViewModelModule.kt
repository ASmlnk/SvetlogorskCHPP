package com.example.svetlogorskchpp.__di

import com.example.svetlogorskchpp.__domain.interactor.shift_schedule.calendar.ShiftScheduleCalendarInteractor
import com.example.svetlogorskchpp.__domain.interactor.shift_schedule.calendar.ShiftScheduleCalendarInteractorImpl
import com.example.svetlogorskchpp.__domain.task_schedule.notification.TaskSchedulerNotificationWorker
import com.example.svetlogorskchpp.__domain.task_schedule.notification.TaskSchedulerNotificationWorkerImpl
import com.example.svetlogorskchpp.__domain.task_schedule.update_request_work.TaskSchedulerUpdateRequestWorkBaseWorker
import com.example.svetlogorskchpp.__domain.task_schedule.update_request_work.TaskSchedulerUpdateRequestWorkBaseWorkerImpl
import com.example.svetlogorskchpp.__domain.usecases.calendarTagUseCases.CalendarTagUseCases
import com.example.svetlogorskchpp.__domain.usecases.calendarDateUseCases.CalendarDateUseCases
import com.example.svetlogorskchpp.__domain.usecases.calendarDateUseCases.CalendarDateUseCasesImpl
import com.example.svetlogorskchpp.__domain.usecases.calendarNote.CalendarNoteUseCases
import com.example.svetlogorskchpp.__domain.usecases.calendarNote.CalendarNoteUseCasesImpl
import com.example.svetlogorskchpp.__domain.usecases.calendarNoteTag.CalendarNoteTagUseCases
import com.example.svetlogorskchpp.__domain.usecases.calendarNoteTag.CalendarNoteTagUseCasesImpl
import com.example.svetlogorskchpp.__domain.usecases.calendarPreferencesNotificationUseCases.CalendarPreferencesNotificationUseCases
import com.example.svetlogorskchpp.__domain.usecases.calendarPreferencesNotificationUseCases.CalendarPreferencesNotificationUseCasesImpl
import com.example.svetlogorskchpp.__domain.usecases.calendarTagUseCases.CalendarTagUseCasesImpl
import com.example.svetlogorskchpp.__domain.usecases.hardData.HardDataUseCases
import com.example.svetlogorskchpp.__domain.usecases.hardData.RequestWorkHardDataUseCasesImpl
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


    @Binds
    abstract fun provideCalendarTagUseCases(calendarTagUseCasesImpl: CalendarTagUseCasesImpl) : CalendarTagUseCases

    @Binds
    abstract fun provideShiftScheduleCalendarInteractor (shiftScheduleCalendarInteractorImpl: ShiftScheduleCalendarInteractorImpl): ShiftScheduleCalendarInteractor

    @Binds
    abstract fun provideTaskSchedulerNotificationWorker (taskSchedulerNotificationWorkerImpl: TaskSchedulerNotificationWorkerImpl): TaskSchedulerNotificationWorker

    @Binds
    abstract fun provideHardDataUseCases (requestWorkHardDataUseCasesImpl: RequestWorkHardDataUseCasesImpl): HardDataUseCases<String>

    @Binds
    abstract fun provideCalendarPreferencesNotificationUseCases (calendarPreferencesNotificationUseCases: CalendarPreferencesNotificationUseCasesImpl): CalendarPreferencesNotificationUseCases

    @Binds
    abstract fun provideTaskSchedulerRequestWorkUpdateWorker (TaskSchedulerUpdateRequestWorkBaseWorkerImpl: TaskSchedulerUpdateRequestWorkBaseWorkerImpl): TaskSchedulerUpdateRequestWorkBaseWorker



}