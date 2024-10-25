package com.example.svetlogorskchpp.__di

import android.content.Context
import com.example.svetlogorskchpp.__data.database.electrical_equipment.OpenSwitchgearVl.OpenSwitchgearVlEntity
import com.example.svetlogorskchpp.__data.hard.HardDataRepository
import com.example.svetlogorskchpp.__data.mapper.NoteRequestWorkDomainToEntityMapper
import com.example.svetlogorskchpp.__data.mapper.NoteRequestWorkEntityToDomainMapper
import com.example.svetlogorskchpp.__data.repository.electrical_equipment.open_switchgear.OpenSwitchgearRepository
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
import com.example.svetlogorskchpp.__domain.mapper.electrical_equipment.ElectricalEquipmentMapper
import com.example.svetlogorskchpp.__domain.mapper.electrical_equipment.open_switchgear.OpenSwitchgearVlMapper
import com.example.svetlogorskchpp.__domain.model.electrical_equipment.OpenSwitchgearVl
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
import com.example.svetlogorskchpp.__domain.usecases.hardData.RequestWorkHardDataUseCasesImpl
import com.example.svetlogorskchpp.__domain.usecases.shift_schedule.RequestWorkFilterFactoryUseCases
import com.example.svetlogorskchpp.__domain.usecases.calendarPreferencesNotificationUseCases.CalendarPreferencesNotificationUseCases
import com.example.svetlogorskchpp.__domain.usecases.calendarPreferencesNotificationUseCases.CalendarPreferencesNotificationUseCasesImpl
import com.example.svetlogorskchpp.__domain.usecases.electrical_equipment.electircal_equipments.ElectricalEquipmentVlUseCasesImpl
import com.example.svetlogorskchpp.__domain.usecases.electrical_equipment.electircal_equipments.ElectricalEquipmentsUseCases
import com.example.svetlogorskchpp.__domain.usecases.electrical_equipment.open_switchgear.OpenSwitchgearUseCases
import com.example.svetlogorskchpp.__domain.usecases.electrical_equipment.open_switchgear.OpenSwitchgearVLUseCasesImpl
import com.example.svetlogorskchpp.__presentation.electrical_equipment.model.ElectricalEquipment
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
    fun provideOpenSwitchgearVlUseCases(
        openSwitchgearVlRepository: OpenSwitchgearRepository<OpenSwitchgearVlEntity>,
        openSwitchgearVlMapper: OpenSwitchgearVlMapper
    ): OpenSwitchgearUseCases<OpenSwitchgearVl> {
        return OpenSwitchgearVLUseCasesImpl(
            openSwitchgearVlRepository,
            openSwitchgearVlMapper
        )
    }

    @Provides
    @ViewModelScoped
    fun provideElectricalEquipmentVlUseCases(
        openSwitchgearVlRepository: OpenSwitchgearRepository<OpenSwitchgearVlEntity>,
        electricalEquipmentMapper: ElectricalEquipmentMapper
    ): ElectricalEquipmentsUseCases<ElectricalEquipment.Vl> {
        return ElectricalEquipmentVlUseCasesImpl(openSwitchgearVlRepository,electricalEquipmentMapper)
    }
}



