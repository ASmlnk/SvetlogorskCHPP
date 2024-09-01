package com.example.svetlogorskchpp.di

import com.example.svetlogorskchpp.data.repository.preferences.PreferencesRepository
import com.example.svetlogorskchpp.domain.interactor.shift_schedule.calendar.ShiftScheduleCalendarInteractor
import com.example.svetlogorskchpp.domain.interactor.shift_schedule.calendar.ShiftScheduleCalendarInteractorImpl
import com.example.svetlogorskchpp.domain.interactor.shift_schedule.calendar.ShiftScheduleCalendarInteractorWidgetImpl
import com.example.svetlogorskchpp.domain.usecases.CalendarAddShiftUseCases
import com.example.svetlogorskchpp.domain.usecases.GenerateDaysFullCalendarUseCases
import com.example.svetlogorskchpp.domain.usecases.ShiftUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ServiceScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Qualifier

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
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Widget