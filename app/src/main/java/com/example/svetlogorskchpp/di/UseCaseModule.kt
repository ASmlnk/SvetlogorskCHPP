package com.example.svetlogorskchpp.di

import com.example.svetlogorskchpp.data.repository.calendarNoteTag.CalendarNoteTagRepository
import com.example.svetlogorskchpp.data.repository.inspection.InspectionRepository
import com.example.svetlogorskchpp.data.repository.note.NoteRepository
import com.example.svetlogorskchpp.data.repository.preferences.PreferencesRepository
import com.example.svetlogorskchpp.data.repository.shiftPersonnel.ShiftPersonalRepository
import com.example.svetlogorskchpp.domain.interactor.shift_schedule.ShiftPersonal.ShiftScheduleShiftPersonalInteractor
import com.example.svetlogorskchpp.domain.interactor.shift_schedule.ShiftPersonal.ShiftScheduleShiftPersonalInteractorImpl
import com.example.svetlogorskchpp.domain.interactor.shift_schedule.calendar.ShiftScheduleCalendarInteractor
import com.example.svetlogorskchpp.domain.interactor.shift_schedule.calendar.ShiftScheduleCalendarInteractorImpl
import com.example.svetlogorskchpp.domain.usecases.CalendarAddShiftUseCases
import com.example.svetlogorskchpp.domain.usecases.FilterUseCases
import com.example.svetlogorskchpp.domain.usecases.GenerateDaysFullCalendarUseCases
import com.example.svetlogorskchpp.domain.usecases.JobTitleUseCases
import com.example.svetlogorskchpp.domain.usecases.NetworkAvailableUseCase
import com.example.svetlogorskchpp.domain.usecases.ShiftUseCases
import com.example.svetlogorskchpp.domain.usecases.calendarDate.CalendarDateUseCases
import com.example.svetlogorskchpp.domain.usecases.calendarDate.CalendarDateUseCasesImpl
import com.example.svetlogorskchpp.domain.usecases.calendarNote.CalendarNoteUseCases
import com.example.svetlogorskchpp.domain.usecases.calendarNote.CalendarNoteUseCasesImpl
import com.example.svetlogorskchpp.domain.usecases.calendarNoteTag.CalendarNoteTagUseCases
import com.example.svetlogorskchpp.domain.usecases.calendarNoteTag.CalendarNoteTagUseCasesImpl
import com.example.svetlogorskchpp.domain.usecases.inspectionSchedule.InspectionUsesCases
import com.example.svetlogorskchpp.domain.usecases.inspectionSchedule.InspectionUsesCasesImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ServiceScoped
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {

    @Provides
    @Named("ViewModel")
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
        filterUseCases: FilterUseCases,
        networkAvailableUseCase: NetworkAvailableUseCase,
    ): ShiftScheduleShiftPersonalInteractor {
        return ShiftScheduleShiftPersonalInteractorImpl(
            shiftPersonalRepository,
            shiftUseCases,
            jobTitleUseCases,
            filterUseCases,
            networkAvailableUseCase
        )
    }

    @Provides
    @ViewModelScoped
    fun provideCalendarNotesUseCases(
        noteRepository: NoteRepository,
        calendarDateUseCases: CalendarDateUseCases
    ): CalendarNoteUseCases {
        return CalendarNoteUseCasesImpl(noteRepository,calendarDateUseCases)
    }

    @Provides
    @ViewModelScoped
    fun provideCalendarDateUseCases(): CalendarDateUseCases {
        return CalendarDateUseCasesImpl()
    }

    @Provides
    @ViewModelScoped
    fun provideCalendarNoteTagUseCases(
        calendarNoteTagRepository: CalendarNoteTagRepository,
        calendarDateUseCases: CalendarDateUseCases,
    ): CalendarNoteTagUseCases {
        return CalendarNoteTagUseCasesImpl(calendarNoteTagRepository, calendarDateUseCases)
    }

   // @Provides
   // @ViewModelScoped
   // fun provideInspectionUseCases(
   //     inspectionRepository: InspectionRepository
   // ): InspectionUsesCases {
  //      return InspectionUsesCasesImpl(inspectionRepository)
   // }




}