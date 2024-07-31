package com.example.svetlogorskchpp.di

import com.example.svetlogorskchpp.data.repository.PreferencesRepository
import com.example.svetlogorskchpp.domain.interactor.shift_schedule.ShiftScheduleInteractor
import com.example.svetlogorskchpp.domain.interactor.shift_schedule.ShiftScheduleInteractorImpl
import com.example.svetlogorskchpp.domain.usecases.CalendarAddShiftUseCases
import com.example.svetlogorskchpp.domain.usecases.GenerateDaysFullCalendarUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {

    @Provides
    @ViewModelScoped
    fun provideShiftScheduleInteractor(
        generateDaysFullCalendarUseCases: GenerateDaysFullCalendarUseCases,
        calendarAddShiftUseCases: CalendarAddShiftUseCases,
        preferencesRepository: PreferencesRepository
    ): ShiftScheduleInteractor {
        return ShiftScheduleInteractorImpl(
            generateDaysFullCalendarUseCases,
            calendarAddShiftUseCases,
            preferencesRepository
        )
    }

}