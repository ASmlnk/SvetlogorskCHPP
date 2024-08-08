package com.example.svetlogorskchpp.di

import com.example.svetlogorskchpp.data.repository.preferences.PreferencesRepository
import com.example.svetlogorskchpp.data.repository.shiftPersonnel.ShiftPersonalRepository
import com.example.svetlogorskchpp.domain.en.JobTitle
import com.example.svetlogorskchpp.domain.interactor.shift_schedule.ShiftPersonal.ShiftScheduleShiftPersonalInteractor
import com.example.svetlogorskchpp.domain.interactor.shift_schedule.ShiftPersonal.ShiftScheduleShiftPersonalInteractorImpl
import com.example.svetlogorskchpp.domain.interactor.shift_schedule.calendar.ShiftScheduleCalendarInteractor
import com.example.svetlogorskchpp.domain.interactor.shift_schedule.calendar.ShiftScheduleCalendarInteractorImpl
import com.example.svetlogorskchpp.domain.usecases.CalendarAddShiftUseCases
import com.example.svetlogorskchpp.domain.usecases.FilterUseCases
import com.example.svetlogorskchpp.domain.usecases.GenerateDaysFullCalendarUseCases
import com.example.svetlogorskchpp.domain.usecases.JobTitleUseCases
import com.example.svetlogorskchpp.domain.usecases.ShiftUseCases
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
    fun provideShiftScheduleCalendarInteractor(
        generateDaysFullCalendarUseCases: GenerateDaysFullCalendarUseCases,
        calendarAddShiftUseCases: CalendarAddShiftUseCases,
        preferencesRepository: PreferencesRepository,
        shiftUseCases: ShiftUseCases,
    ): ShiftScheduleCalendarInteractor {
        return ShiftScheduleCalendarInteractorImpl(
            generateDaysFullCalendarUseCases,
            calendarAddShiftUseCases,
            preferencesRepository,
            shiftUseCases
        )
    }

    @Provides
    @ViewModelScoped
    fun provideShiftScheduleShiftPersonalInteractor(
        shiftPersonalRepository: ShiftPersonalRepository,
        shiftUseCases: ShiftUseCases,
        jobTitleUseCases: JobTitleUseCases,
        filterUseCases: FilterUseCases
    ): ShiftScheduleShiftPersonalInteractor {
        return ShiftScheduleShiftPersonalInteractorImpl(
            shiftPersonalRepository,
            shiftUseCases,
            jobTitleUseCases,
            filterUseCases
        )
    }


}