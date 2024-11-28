package com.example.svetlogorskchpp.__di

import android.content.Context
import com.example.svetlogorskchpp.__data.hard.HardDataListRepository
import com.example.svetlogorskchpp.__data.mapper.NoteRequestWorkDomainToEntityMapper
import com.example.svetlogorskchpp.__data.mapper.NoteRequestWorkEntityToDomainMapper
import com.example.svetlogorskchpp.__data.repository.equipment.EquipmentItemDeleteRepository
import com.example.svetlogorskchpp.__data.repository.equipment.electrical.EquipmentUpdateFirebaseRepository
import com.example.svetlogorskchpp.__data.repository.shift_schedule.calendarNoteTag.CalendarNoteTagRepository
import com.example.svetlogorskchpp.__data.repository.shift_schedule.calendarRequestWorkTag.CalendarRequestWorkTagRepository
import com.example.svetlogorskchpp.__data.repository.shift_schedule.note.NoteRepository
import com.example.svetlogorskchpp.__data.repository.shift_schedule.noteRequestWork.NoteRequestWorkRepository
import com.example.svetlogorskchpp.__data.repository.preferences.NotesNotificationPreferencesRepository
import com.example.svetlogorskchpp.__data.repository.preferences.PreferencesRepository
import com.example.svetlogorskchpp.__data.repository.preferences.RequestWorkPreferencesRepository
import com.example.svetlogorskchpp.__data.repository.shift_schedule.shiftPersonnel.ShiftPersonalRepository
import com.example.svetlogorskchpp.__domain.interactor.shift_schedule.ShiftPersonal.ShiftScheduleShiftPersonalInteractor
import com.example.svetlogorskchpp.__domain.interactor.shift_schedule.ShiftPersonal.ShiftScheduleShiftPersonalInteractorImpl
import com.example.svetlogorskchpp.__domain.interactor.shift_schedule.calendar.ShiftScheduleCalendarInteractor
import com.example.svetlogorskchpp.__domain.interactor.shift_schedule.calendar.ShiftScheduleCalendarInteractorImpl
import com.example.svetlogorskchpp.__domain.interactor.shift_schedule.note_list.ShiftScheduleNoteListInteractor
import com.example.svetlogorskchpp.__domain.interactor.shift_schedule.note_list.ShiftScheduleNoteListInteractorImpl
import com.example.svetlogorskchpp.__domain.task_schedule.notification.TaskSchedulerNotificationWorker
import com.example.svetlogorskchpp.__domain.task_schedule.notification.TaskSchedulerNotificationWorkerImpl
import com.example.svetlogorskchpp.__domain.task_schedule.update_request_work.TaskSchedulerUpdateRequestWorkBaseWorker
import com.example.svetlogorskchpp.__domain.task_schedule.update_request_work.TaskSchedulerUpdateRequestWorkBaseWorkerImpl
import com.example.svetlogorskchpp.__domain.usecases.shift_schedule.CalendarAddShiftUseCases
import com.example.svetlogorskchpp.__domain.usecases.shift_schedule.FilterUseCases
import com.example.svetlogorskchpp.__domain.usecases.shift_schedule.GenerateDaysFullCalendarUseCases
import com.example.svetlogorskchpp.__domain.usecases.shift_schedule.JobTitleUseCases
import com.example.svetlogorskchpp.__domain.usecases.NetworkAvailableUseCase
import com.example.svetlogorskchpp.__domain.usecases.shift_schedule.RequestWorkSortedUseCases
import com.example.svetlogorskchpp.__domain.usecases.shift_schedule.ShiftUseCases
import com.example.svetlogorskchpp.__domain.usecases.shift_schedule.calendarDateUseCases.CalendarDateUseCases
import com.example.svetlogorskchpp.__domain.usecases.shift_schedule.calendarDateUseCases.CalendarDateUseCasesImpl
import com.example.svetlogorskchpp.__domain.usecases.shift_schedule.calendarNote.CalendarNoteUseCases
import com.example.svetlogorskchpp.__domain.usecases.shift_schedule.calendarNote.CalendarNoteUseCasesImpl
import com.example.svetlogorskchpp.__domain.usecases.shift_schedule.calendarNoteTag.CalendarNoteTagUseCases
import com.example.svetlogorskchpp.__domain.usecases.shift_schedule.calendarNoteTag.CalendarNoteTagUseCasesImpl
import com.example.svetlogorskchpp.__domain.usecases.shift_schedule.calendarTagUseCases.CalendarTagUseCases
import com.example.svetlogorskchpp.__domain.usecases.shift_schedule.calendarTagUseCases.CalendarTagUseCasesImpl
import com.example.svetlogorskchpp.__domain.usecases.hardData.HardDataUseCases
import com.example.svetlogorskchpp.__domain.usecases.hardData.HardDataUseCasesImpl
import com.example.svetlogorskchpp.__domain.usecases.shift_schedule.RequestWorkFilterFactoryUseCases
import com.example.svetlogorskchpp.__domain.usecases.calendarPreferencesNotificationUseCases.CalendarPreferencesNotificationUseCases
import com.example.svetlogorskchpp.__domain.usecases.calendarPreferencesNotificationUseCases.CalendarPreferencesNotificationUseCasesImpl
import com.example.svetlogorskchpp.__domain.usecases.equipments.all_equipment.delete.EquipmentsItemDeleteUseCases
import com.example.svetlogorskchpp.__domain.usecases.equipments.all_equipment.delete.EquipmentsItemDeleteUseCasesImpl
import com.example.svetlogorskchpp.__domain.usecases.equipments.update_fb.UpdateFirebaseUseCases
import com.example.svetlogorskchpp.__domain.usecases.equipments.update_fb.UpdateFirebaseUseCasesImpl
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
        notesNotificationPreferencesRepository: NotesNotificationPreferencesRepository,
    ): CalendarNoteUseCases {
        return CalendarNoteUseCasesImpl(
            noteRepository,
            calendarDateUseCases,
            noteRequestWorkRepository,
            noteRequestWorkDomainToEntityMapper,
            notesNotificationPreferencesRepository,
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
        preferencesNotificationUseCases: CalendarPreferencesNotificationUseCases,
    ): CalendarNoteTagUseCases {
        return CalendarNoteTagUseCasesImpl(
            calendarNoteTagRepository,
            calendarRequestWorkTagRepository,
            calendarDateUseCases,
            preferencesNotificationUseCases
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
    fun provideTaskSchedulerRequestWorkUpdateWorker(
        context: Context,
    ): TaskSchedulerUpdateRequestWorkBaseWorker {
        return TaskSchedulerUpdateRequestWorkBaseWorkerImpl(context)
    }

    @Provides
    @ViewModelScoped
    fun provideCalendarPreferencesNotification(
        preferencesRepository: NotesNotificationPreferencesRepository,
    ): CalendarPreferencesNotificationUseCases {
        return CalendarPreferencesNotificationUseCasesImpl(preferencesRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideRequestWorkHardData(
        @RequestWorkReason reasonHardData: HardDataListRepository<String>,
        @RequestWorkAccession accessionHardData: HardDataListRepository<String>,
        @InfoORY infoOry: HardDataListRepository<String>,
        @InfoTSN infoTsn: HardDataListRepository<String>,
    ): HardDataUseCases<String> {
        return HardDataUseCasesImpl(reasonHardData, accessionHardData, infoOry, infoTsn)
    }

    @Provides
    @ViewModelScoped
    fun provideShiftScheduleNoteList(
        preferencesRepository: RequestWorkPreferencesRepository,
        noteRequestWorkRepository: NoteRequestWorkRepository,
        sortedUseCases: RequestWorkSortedUseCases,
        filterUseCases: RequestWorkFilterFactoryUseCases,
    ): ShiftScheduleNoteListInteractor {
        return ShiftScheduleNoteListInteractorImpl(
            preferencesRepository,
            noteRequestWorkRepository,
            sortedUseCases,
            filterUseCases
        )
    }

    @Provides
    @ViewModelScoped
    fun provideUpdeteFirebaseUseCases(
        @ElMotor elMotorRepository: EquipmentUpdateFirebaseRepository,
        @Switchgear switchgearRepository: EquipmentUpdateFirebaseRepository,
        @LightingAndOther lightingAndOtherRepository: EquipmentUpdateFirebaseRepository,
    ): UpdateFirebaseUseCases {
        return UpdateFirebaseUseCasesImpl(
            elMotorRepository,
            switchgearRepository,
            lightingAndOtherRepository
        )
    }

    @Provides
    @ViewModelScoped
    fun provideItemDeleteUseCases(
        @ElMotorDel elMotorRepository: EquipmentItemDeleteRepository,
        @Switchgear switchgearRepository: EquipmentItemDeleteRepository,
        @LightingAndOther lightingAndOtherRepository: EquipmentItemDeleteRepository,
        networkUseCases: NetworkAvailableUseCase,
    ): EquipmentsItemDeleteUseCases {
        return EquipmentsItemDeleteUseCasesImpl(
            elMotorRepository,
            switchgearRepository,
            lightingAndOtherRepository,
            networkUseCases
        )
    }
}



