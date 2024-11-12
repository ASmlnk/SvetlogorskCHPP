package com.example.svetlogorskchpp.__di

import com.example.svetlogorskchpp.__domain.interactor.shift_schedule.calendar.ShiftScheduleCalendarInteractor
import com.example.svetlogorskchpp.__domain.interactor.shift_schedule.calendar.ShiftScheduleCalendarInteractorImpl
import com.example.svetlogorskchpp.__domain.model.electrical_equipment.OpenSwitchgearTr
import com.example.svetlogorskchpp.__domain.model.electrical_equipment.OpenSwitchgearVl
import com.example.svetlogorskchpp.__domain.model.electrical_equipment.TransformerOwnNeeds
import com.example.svetlogorskchpp.__domain.model.electrical_equipment.TurboGenerator
import com.example.svetlogorskchpp.__domain.task_schedule.notification.TaskSchedulerNotificationWorker
import com.example.svetlogorskchpp.__domain.task_schedule.notification.TaskSchedulerNotificationWorkerImpl
import com.example.svetlogorskchpp.__domain.task_schedule.update_request_work.TaskSchedulerUpdateRequestWorkBaseWorker
import com.example.svetlogorskchpp.__domain.task_schedule.update_request_work.TaskSchedulerUpdateRequestWorkBaseWorkerImpl
import com.example.svetlogorskchpp.__domain.usecases.shift_schedule.calendarTagUseCases.CalendarTagUseCases
import com.example.svetlogorskchpp.__domain.usecases.shift_schedule.calendarDateUseCases.CalendarDateUseCases
import com.example.svetlogorskchpp.__domain.usecases.shift_schedule.calendarDateUseCases.CalendarDateUseCasesImpl
import com.example.svetlogorskchpp.__domain.usecases.shift_schedule.calendarNote.CalendarNoteUseCases
import com.example.svetlogorskchpp.__domain.usecases.shift_schedule.calendarNote.CalendarNoteUseCasesImpl
import com.example.svetlogorskchpp.__domain.usecases.shift_schedule.calendarNoteTag.CalendarNoteTagUseCases
import com.example.svetlogorskchpp.__domain.usecases.shift_schedule.calendarNoteTag.CalendarNoteTagUseCasesImpl
import com.example.svetlogorskchpp.__domain.usecases.calendarPreferencesNotificationUseCases.CalendarPreferencesNotificationUseCases
import com.example.svetlogorskchpp.__domain.usecases.calendarPreferencesNotificationUseCases.CalendarPreferencesNotificationUseCasesImpl
import com.example.svetlogorskchpp.__domain.usecases.equipments.EquipmentsUseCases
import com.example.svetlogorskchpp.__domain.usecases.equipments.all_equipment.EquipmentAllUseCases
import com.example.svetlogorskchpp.__domain.usecases.equipments.all_equipment.EquipmentAllUseCasesImpl
import com.example.svetlogorskchpp.__domain.usecases.equipments.item.electrical.EquipmentTransformerOwnNeedsUseCasesImpl
import com.example.svetlogorskchpp.__domain.usecases.equipments.item.electrical.EquipmentTurboGeneratorUseCasesImpl
import com.example.svetlogorskchpp.__domain.usecases.equipments.item.electrical.EquipmentsOpenSwitchgearTrUseCasesImpl
import com.example.svetlogorskchpp.__domain.usecases.equipments.item.electrical.EquipmentsOpenSwitchgearVLUseCasesImpl
import com.example.svetlogorskchpp.__domain.usecases.shift_schedule.calendarTagUseCases.CalendarTagUseCasesImpl
import com.example.svetlogorskchpp.__domain.usecases.hardData.HardDataUseCases
import com.example.svetlogorskchpp.__domain.usecases.hardData.RequestWorkHardDataUseCasesImpl
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class ViewModelModule {

    @Binds
    abstract fun provideCalendarDate(calendarDateUseCasesImpl: CalendarDateUseCasesImpl): CalendarDateUseCases

    @Binds
    abstract fun provideCalendarNoteTagUseCases(calendarNoteTagCasesImpl: CalendarNoteTagUseCasesImpl): CalendarNoteTagUseCases

    @Binds
    abstract fun provideCalendarNoteUseCases(calendarNoteCasesImpl: CalendarNoteUseCasesImpl): CalendarNoteUseCases

    @Binds
    abstract fun provideFirestoryRepository(remoteDB: FirebaseFirestore): FirebaseFirestore

    @Binds
    abstract fun provideCalendarTagUseCases(calendarTagUseCasesImpl: CalendarTagUseCasesImpl): CalendarTagUseCases

    @Binds
    abstract fun provideShiftScheduleCalendarInteractor(shiftScheduleCalendarInteractorImpl: ShiftScheduleCalendarInteractorImpl): ShiftScheduleCalendarInteractor

    @Binds
    abstract fun provideTaskSchedulerNotificationWorker(taskSchedulerNotificationWorkerImpl: TaskSchedulerNotificationWorkerImpl): TaskSchedulerNotificationWorker

    @Binds
    abstract fun provideHardDataUseCases(requestWorkHardDataUseCasesImpl: RequestWorkHardDataUseCasesImpl): HardDataUseCases<String>

    @Binds
    abstract fun provideCalendarPreferencesNotificationUseCases(
        calendarPreferencesNotificationUseCases: CalendarPreferencesNotificationUseCasesImpl,
    ): CalendarPreferencesNotificationUseCases

    @Binds
    abstract fun provideTaskSchedulerRequestWorkUpdateWorker(
        taskSchedulerUpdateRequestWorkBaseWorkerImpl: TaskSchedulerUpdateRequestWorkBaseWorkerImpl,
    ): TaskSchedulerUpdateRequestWorkBaseWorker

    @Binds
    abstract fun provideOpenSwitchgearVlUseCases(openSwitchgearVLUseCasesImpl: EquipmentsOpenSwitchgearVLUseCasesImpl): EquipmentsUseCases<OpenSwitchgearVl>

    @Binds
    abstract fun provideOpenSwitchgearTrUseCases(openSwitchgearTrUseCasesImpl: EquipmentsOpenSwitchgearTrUseCasesImpl): EquipmentsUseCases<OpenSwitchgearTr>

    @Binds
    abstract fun provideTransformerOwnNeedsUseCases(equipmentsOpenSwitchgearTrUseCasesImpl: EquipmentTransformerOwnNeedsUseCasesImpl): EquipmentsUseCases<TransformerOwnNeeds>

    @Binds
    abstract fun provideTurboGeneratorUseCases(equipmentTurboGeneratorUseCasesImpl: EquipmentTurboGeneratorUseCasesImpl): EquipmentsUseCases<TurboGenerator>

    @Binds
    abstract fun provideEquipmentAllUseCases (equipmentAllUseCasesImpl: EquipmentAllUseCasesImpl): EquipmentAllUseCases


}