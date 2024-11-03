package com.example.svetlogorskchpp.__di

import com.example.svetlogorskchpp.__data.database.electrical_equipment.OpenSwitchgearTr.OpenSwitchgearTrEntity
import com.example.svetlogorskchpp.__data.database.electrical_equipment.OpenSwitchgearVl.OpenSwitchgearVlEntity
import com.example.svetlogorskchpp.__data.repository.equipment.EquipmentRepository
import com.example.svetlogorskchpp.__data.repository.shift_schedule.noteRequestWork.NoteRequestWorkRepository
import com.example.svetlogorskchpp.__domain.mapper.electrical_equipment.ElectricalEquipmentMapper
import com.example.svetlogorskchpp.__domain.mapper.electrical_equipment.open_switchgear.OpenSwitchgearTrMapper
import com.example.svetlogorskchpp.__domain.mapper.electrical_equipment.open_switchgear.OpenSwitchgearVlMapper
import com.example.svetlogorskchpp.__domain.model.electrical_equipment.OpenSwitchgearTr
import com.example.svetlogorskchpp.__domain.model.electrical_equipment.OpenSwitchgearVl
import com.example.svetlogorskchpp.__domain.usecases.NetworkAvailableUseCase
import com.example.svetlogorskchpp.__domain.usecases.equipments.EquipmentsListUseCases
import com.example.svetlogorskchpp.__domain.usecases.equipments.EquipmentsUseCases
import com.example.svetlogorskchpp.__domain.usecases.equipments.item.electrical.EquipmentsOpenSwitchgearTrUseCasesImpl
import com.example.svetlogorskchpp.__domain.usecases.equipments.item.electrical.EquipmentsOpenSwitchgearVLUseCasesImpl
import com.example.svetlogorskchpp.__domain.usecases.equipments.list.electrical.EquipmentOpenSwitchgearVlListUseCasesImpl
import com.example.svetlogorskchpp.__domain.usecases.update_locale_base.UpdateLocaleBaseUseCases
import com.example.svetlogorskchpp.__domain.usecases.update_locale_base.UpdateLocaleBaseUseCasesImpl
import com.example.svetlogorskchpp.__presentation.electrical_equipment.model.ElectricalEquipment
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class UseCaseEquipmentModule {

    @Provides
    @ViewModelScoped
    fun provideOpenSwitchgearVlUseCases(
        openSwitchgearVlRepository: EquipmentRepository<OpenSwitchgearVlEntity>,
        openSwitchgearVlMapper: OpenSwitchgearVlMapper,
        networkAvailableUseCase: NetworkAvailableUseCase
    ): EquipmentsUseCases<OpenSwitchgearVl> {
        return EquipmentsOpenSwitchgearVLUseCasesImpl(
            openSwitchgearVlRepository,
            openSwitchgearVlMapper,
            networkAvailableUseCase
        )
    }

    @Provides
    @ViewModelScoped
    fun provideOpenSwitchgearTrUseCases(
        openSwitchgearTrRepository: EquipmentRepository<OpenSwitchgearTrEntity>,
        openSwitchgearTrMapper: OpenSwitchgearTrMapper,
        networkAvailableUseCase: NetworkAvailableUseCase
    ): EquipmentsUseCases<OpenSwitchgearTr> {
        return EquipmentsOpenSwitchgearTrUseCasesImpl(
            openSwitchgearTrRepository,
            openSwitchgearTrMapper,
            networkAvailableUseCase
        )
    }

    @Provides
    @ViewModelScoped
    fun provideElectricalEquipmentVlUseCases(
        openSwitchgearVlRepository: EquipmentRepository<OpenSwitchgearVlEntity>,
        electricalEquipmentMapper: ElectricalEquipmentMapper
    ): EquipmentsListUseCases<ElectricalEquipment.Vl> {
        return EquipmentOpenSwitchgearVlListUseCasesImpl(openSwitchgearVlRepository,electricalEquipmentMapper)
    }

    @Provides
    @ViewModelScoped
    fun provideUpdateLocaleUseCases(
        repositoryOpenSwitchgearVlRepository: EquipmentRepository<OpenSwitchgearVlEntity>,
        noteRequestWorkRepository: NoteRequestWorkRepository,
    ): UpdateLocaleBaseUseCases {
        return UpdateLocaleBaseUseCasesImpl(repositoryOpenSwitchgearVlRepository, noteRequestWorkRepository)
    }
}