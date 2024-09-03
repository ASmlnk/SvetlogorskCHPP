package com.example.svetlogorskchpp.__di

import com.example.svetlogorskchpp.__data.repository.preferences.PreferencesRepository
import com.example.svetlogorskchpp.__domain.interactor.shift_schedule.calendar.ShiftScheduleCalendarInteractor
import com.example.svetlogorskchpp.__domain.interactor.shift_schedule.calendar.ShiftScheduleCalendarInteractorWidgetImpl
import com.example.svetlogorskchpp.__domain.usecases.CalendarAddShiftUseCases
import com.example.svetlogorskchpp.__domain.usecases.GenerateDaysFullCalendarUseCases
import com.example.svetlogorskchpp.__domain.usecases.ShiftUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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