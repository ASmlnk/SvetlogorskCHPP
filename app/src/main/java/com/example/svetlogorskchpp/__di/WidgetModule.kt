package com.example.svetlogorskchpp.__di

import android.content.Context
import com.example.svetlogorskchpp.__data.repository.calendarNoteTag.CalendarNoteTagRepository
import com.example.svetlogorskchpp.__data.repository.preferences.PreferencesRepository
import com.example.svetlogorskchpp.__domain.interactor.shift_schedule.calendar.ShiftScheduleCalendarInteractor
import com.example.svetlogorskchpp.__domain.interactor.shift_schedule.calendar.ShiftScheduleCalendarInteractorWidgetImpl
import com.example.svetlogorskchpp.__domain.usecases.CalendarAddShiftUseCases
import com.example.svetlogorskchpp.__domain.usecases.GenerateDaysFullCalendarUseCases
import com.example.svetlogorskchpp.__domain.usecases.ShiftUseCases
import com.example.svetlogorskchpp.__domain.usecases.calendarDate.CalendarDateUseCases
import com.example.svetlogorskchpp.__domain.usecases.calendarDate.CalendarDateUseCasesImpl
import com.example.svetlogorskchpp.__domain.usecases.calendarNoteTag.CalendarNoteTagUseCases
import com.example.svetlogorskchpp.__domain.usecases.calendarNoteTag.CalendarNoteTagUseCasesImpl
import com.example.svetlogorskchpp.__domain.usecases.calendarNoteTag.CalendarNoteTagWidgetUseCases
import com.example.svetlogorskchpp.__domain.usecases.calendarTagUseCases.CalendarTagUseCases
import com.example.svetlogorskchpp.__domain.usecases.calendarTagUseCases.CalendarTagUseCasesImpl
import com.example.svetlogorskchpp.__domain.usecases.manager.SchedulerUpdateWidgetUseCases
import com.example.svetlogorskchpp.__domain.usecases.manager.SchedulerUpdateWidgetUseCasesImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
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
       @Widget calendarDateUseCases: CalendarDateUseCases,
    ): CalendarNoteTagWidgetUseCases {
        return CalendarNoteTagUseCasesImpl(calendarNoteTagRepository, calendarDateUseCases)
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
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Widget