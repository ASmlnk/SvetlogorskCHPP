package com.example.svetlogorskchpp.__di

import android.content.Context
import com.example.svetlogorskchpp.__data.repository.calendarNoteTag.CalendarNoteTagRepository
import com.example.svetlogorskchpp.__data.repository.calendarRequestWorkTag.CalendarRequestWorkTagRepository
import com.example.svetlogorskchpp.__data.repository.preferences.NotesNotificationPreferencesRepository
import com.example.svetlogorskchpp.__data.repository.preferences.PreferencesRepository
import com.example.svetlogorskchpp.__domain.interactor.shift_schedule.calendar.ShiftScheduleCalendarInteractor
import com.example.svetlogorskchpp.__domain.interactor.shift_schedule.calendar.ShiftScheduleCalendarInteractorWidgetImpl
import com.example.svetlogorskchpp.__domain.usecases.shift_schedule.CalendarAddShiftUseCases
import com.example.svetlogorskchpp.__domain.usecases.shift_schedule.GenerateDaysFullCalendarUseCases
import com.example.svetlogorskchpp.__domain.usecases.shift_schedule.ShiftUseCases
import com.example.svetlogorskchpp.__domain.usecases.shift_schedule.calendarDateUseCases.CalendarDateUseCases
import com.example.svetlogorskchpp.__domain.usecases.shift_schedule.calendarDateUseCases.CalendarDateUseCasesImpl
import com.example.svetlogorskchpp.__domain.usecases.shift_schedule.calendarNoteTag.CalendarNoteTagUseCasesImpl
import com.example.svetlogorskchpp.__domain.usecases.shift_schedule.calendarNoteTag.CalendarNoteTagWidgetUseCases
import com.example.svetlogorskchpp.__domain.usecases.calendarPreferencesNotificationUseCases.CalendarPreferencesNotificationUseCases
import com.example.svetlogorskchpp.__domain.usecases.calendarPreferencesNotificationUseCases.CalendarPreferencesNotificationUseCasesImpl
import com.example.svetlogorskchpp.__domain.usecases.shift_schedule.calendarTagUseCases.CalendarTagUseCases
import com.example.svetlogorskchpp.__domain.usecases.shift_schedule.calendarTagUseCases.CalendarTagUseCasesImpl
import com.example.svetlogorskchpp.__domain.usecases.manager.SchedulerUpdateWidgetUseCases
import com.example.svetlogorskchpp.__domain.usecases.manager.SchedulerUpdateWidgetUseCasesImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class WidgetModule {

    @Provides
    @Widget
    fun provideShiftScheduleCalendarInteractorS(
        generateDaysFullCalendarUseCases: GenerateDaysFullCalendarUseCases,
        calendarAddShiftUseCases: CalendarAddShiftUseCases,
        preferencesRepository: PreferencesRepository,
        shiftUseCases: ShiftUseCases,
    ): ShiftScheduleCalendarInteractor {
        return ShiftScheduleCalendarInteractorWidgetImpl(
            generateDaysFullCalendarUseCases,
            calendarAddShiftUseCases,
            preferencesRepository,
            shiftUseCases
        )
    }

    @Provides
    fun provideCalendarNoteTagUseCasesS(
        calendarNoteTagRepository: CalendarNoteTagRepository,
        calendarRequestWorkTagRepository: CalendarRequestWorkTagRepository,
        @Widget preferencesNotificationUseCases: CalendarPreferencesNotificationUseCases,
        @Widget calendarDateUseCases: CalendarDateUseCases,
    ): CalendarNoteTagWidgetUseCases {
        return CalendarNoteTagUseCasesImpl(calendarNoteTagRepository, calendarRequestWorkTagRepository, calendarDateUseCases,preferencesNotificationUseCases)
    }

    @Provides
    @Widget
    fun provideCalendarDateUseCasesS(): CalendarDateUseCases {
        return CalendarDateUseCasesImpl()
    }

    @Provides
    @Widget
    fun provideCalendarTagUseCasesS(): CalendarTagUseCases {
        return CalendarTagUseCasesImpl()
    }

    @Provides
    @Widget
    @Singleton
    fun provideUpdateWidgetUseCases(@ApplicationContext applicationContext: Context): SchedulerUpdateWidgetUseCases {
        return SchedulerUpdateWidgetUseCasesImpl(applicationContext)
    }

    @Provides
    @Widget
    fun provideCalendarPreferencesNotificationWidget (
        preferencesRepository: NotesNotificationPreferencesRepository
    ): CalendarPreferencesNotificationUseCases {
        return CalendarPreferencesNotificationUseCasesImpl(preferencesRepository)
    }

}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Widget