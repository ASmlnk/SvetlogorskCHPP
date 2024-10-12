package com.example.svetlogorskchpp.__di

import android.content.Context
import com.example.svetlogorskchpp.__data.hard.HardDataRepository
import com.example.svetlogorskchpp.__data.mapper.NoteRequestWorkDomainToEntityMapper
import com.example.svetlogorskchpp.__data.mapper.NoteRequestWorkEntityToDomainMapper
import com.example.svetlogorskchpp.__data.repository.calendarNoteTag.CalendarNoteTagRepository
import com.example.svetlogorskchpp.__data.repository.calendarRequestWorkTag.CalendarRequestWorkTagRepository
import com.example.svetlogorskchpp.__data.repository.note.NoteRepository
import com.example.svetlogorskchpp.__data.repository.noteRequestWork.NoteRequestWorkRepository
import com.example.svetlogorskchpp.__data.repository.preferences.PreferencesRepository
import com.example.svetlogorskchpp.__data.repository.preferences.RequestWorkPreferencesRepository
import com.example.svetlogorskchpp.__data.repository.shiftPersonnel.ShiftPersonalRepository
import com.example.svetlogorskchpp.__domain.interactor.shift_schedule.ShiftPersonal.ShiftScheduleShiftPersonalInteractor
import com.example.svetlogorskchpp.__domain.interactor.shift_schedule.ShiftPersonal.ShiftScheduleShiftPersonalInteractorImpl
import com.example.svetlogorskchpp.__domain.interactor.shift_schedule.calendar.ShiftScheduleCalendarInteractor
import com.example.svetlogorskchpp.__domain.interactor.shift_schedule.calendar.ShiftScheduleCalendarInteractorImpl
import com.example.svetlogorskchpp.__domain.interactor.shift_schedule.note_list.ShiftScheduleNoteListInteractor
import com.example.svetlogorskchpp.__domain.interactor.shift_schedule.note_list.ShiftScheduleNoteListInteractorImpl
import com.example.svetlogorskchpp.__domain.task_schedule.TaskSchedulerNotificationWorker
import com.example.svetlogorskchpp.__domain.task_schedule.TaskSchedulerNotificationWorkerImpl
import com.example.svetlogorskchpp.__domain.usecases.CalendarAddShiftUseCases
import com.example.svetlogorskchpp.__domain.usecases.FilterUseCases
import com.example.svetlogorskchpp.__domain.usecases.GenerateDaysFullCalendarUseCases
import com.example.svetlogorskchpp.__domain.usecases.JobTitleUseCases
import com.example.svetlogorskchpp.__domain.usecases.NetworkAvailableUseCase
import com.example.svetlogorskchpp.__domain.usecases.RequestWorkSortedUseCases
import com.example.svetlogorskchpp.__domain.usecases.ShiftUseCases
import com.example.svetlogorskchpp.__domain.usecases.calendarDateUseCases.CalendarDateUseCases
import com.example.svetlogorskchpp.__domain.usecases.calendarDateUseCases.CalendarDateUseCasesImpl
import com.example.svetlogorskchpp.__domain.usecases.calendarNote.CalendarNoteUseCases
import com.example.svetlogorskchpp.__domain.usecases.calendarNote.CalendarNoteUseCasesImpl
import com.example.svetlogorskchpp.__domain.usecases.calendarNoteTag.CalendarNoteTagUseCases
import com.example.svetlogorskchpp.__domain.usecases.calendarNoteTag.CalendarNoteTagUseCasesImpl
import com.example.svetlogorskchpp.__domain.usecases.calendarTagUseCases.CalendarTagUseCases
import com.example.svetlogorskchpp.__domain.usecases.calendarTagUseCases.CalendarTagUseCasesImpl
import com.example.svetlogorskchpp.__domain.usecases.hardData.HardDataUseCases
import com.example.svetlogorskchpp.__domain.usecases.hardData.RequestWorkHardDataUseCasesImpl
import com.example.svetlogorskchpp.__domain.usecases.RequestWorkFilterFactoryUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {

    @Provides
    @App
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
        calendarDateUseCases: CalendarDateUseCases,
        noteRequestWorkDomainToEntityMapper: NoteRequestWorkDomainToEntityMapper,
        noteRequestWorkEntityToDomainMapper: NoteRequestWorkEntityToDomainMapper,
        noteRequestWorkRepository: NoteRequestWorkRepository,
    ): CalendarNoteUseCases {
        return CalendarNoteUseCasesImpl(
            noteRepository,
            calendarDateUseCases,
            noteRequestWorkRepository,
            noteRequestWorkDomainToEntityMapper,
            noteRequestWorkEntityToDomainMapper
        )
    }

    @Provides
    @ViewModelScoped
    fun provideCalendarDateUseCases(): CalendarDateUseCases {
        return CalendarDateUseCasesImpl()
    }

    @Provides
    @ViewModelScoped
    fun provideCalendarTagUseCases(): CalendarTagUseCases {
        return CalendarTagUseCasesImpl()
    }

    @Provides
    @ViewModelScoped
    fun provideCalendarNoteTagUseCases(
        calendarNoteTagRepository: CalendarNoteTagRepository,
        calendarDateUseCases: CalendarDateUseCases,
        calendarRequestWorkTagRepository: CalendarRequestWorkTagRepository,
    ): CalendarNoteTagUseCases {
        return CalendarNoteTagUseCasesImpl(
            calendarNoteTagRepository,
            calendarRequestWorkTagRepository,
            calendarDateUseCases
        )
    }

    @Provides
    @ViewModelScoped
    fun provideTaskSchedulerNotificationWorker(
        context: Context,
    ): TaskSchedulerNotificationWorker {
        return TaskSchedulerNotificationWorkerImpl(context)
    }

    @Provides
    @ViewModelScoped
    fun provideRequestWorkHardData(
        @RequestWorkReason reasonHardData: HardDataRepository<String>,
        @RequestWorkAccession accessionHardData: HardDataRepository<String>,
    ): HardDataUseCases<String> {
        return RequestWorkHardDataUseCasesImpl(reasonHardData, accessionHardData)
    }

    @Provides
    @ViewModelScoped
    fun provideShiftScheduleNoteList(
        preferencesRepository: RequestWorkPreferencesRepository,
        noteRequestWorkRepository: NoteRequestWorkRepository,
        sortedUseCases: RequestWorkSortedUseCases,
        filterUseCases: RequestWorkFilterFactoryUseCases,
    ) : ShiftScheduleNoteListInteractor {
        return ShiftScheduleNoteListInteractorImpl(preferencesRepository, noteRequestWorkRepository, sortedUseCases, filterUseCases)
    }

}



