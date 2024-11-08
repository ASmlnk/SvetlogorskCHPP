package com.example.svetlogorskchpp.__di

import com.example.svetlogorskchpp.__data.database.electrical_equipment.OpenSwitchgearTr.OpenSwitchgearTrEntity
import com.example.svetlogorskchpp.__data.database.electrical_equipment.OpenSwitchgearVl.OpenSwitchgearVlEntity
import com.example.svetlogorskchpp.__data.database.electrical_equipment.transformerOwnNeeds.TransformerOwnNeedsEntity
import com.example.svetlogorskchpp.__data.repository.equipment.EquipmentRepository
import com.example.svetlogorskchpp.__data.repository.shift_schedule.noteRequestWork.NoteRequestWorkRepository
import com.example.svetlogorskchpp.__domain.mapper.electrical_equipment.ElectricalEquipmentListMapper
import com.example.svetlogorskchpp.__domain.mapper.electrical_equipment.TransformerOwnNeedsMapper
import com.example.svetlogorskchpp.__domain.mapper.electrical_equipment.open_switchgear.OpenSwitchgearTrMapper
import com.example.svetlogorskchpp.__domain.mapper.electrical_equipment.open_switchgear.OpenSwitchgearVlMapper
import com.example.svetlogorskchpp.__domain.model.electrical_equipment.OpenSwitchgearTr
import com.example.svetlogorskchpp.__domain.model.electrical_equipment.OpenSwitchgearVl
import com.example.svetlogorskchpp.__domain.model.electrical_equipment.TransformerOwnNeeds
import com.example.svetlogorskchpp.__domain.usecases.NetworkAvailableUseCase
import com.example.svetlogorskchpp.__domain.usecases.equipments.EquipmentConsumerUseCases
import com.example.svetlogorskchpp.__domain.usecases.equipments.EquipmentsListUseCases
import com.example.svetlogorskchpp.__domain.usecases.equipments.EquipmentsUseCases
import com.example.svetlogorskchpp.__domain.usecases.equipments.all_equipment.EquipmentAllUseCases
import com.example.svetlogorskchpp.__domain.usecases.equipments.all_equipment.EquipmentAllUseCasesImpl
import com.example.svetlogorskchpp.__domain.usecases.equipments.item.electrical.EquipmentConsumerTrUseCasesImpl
import com.example.svetlogorskchpp.__domain.usecases.equipments.item.electrical.EquipmentTransformerOwnNeedsUseCasesImpl
import com.example.svetlogorskchpp.__domain.usecases.equipments.item.electrical.EquipmentsOpenSwitchgearTrUseCasesImpl
import com.example.svetlogorskchpp.__domain.usecases.equipments.item.electrical.EquipmentsOpenSwitchgearVLUseCasesImpl
import com.example.svetlogorskchpp.__domain.usecases.equipments.list.electrical.EquipmentOpenSwitchgearTrListUseCasesImpl
import com.example.svetlogorskchpp.__domain.usecases.equipments.list.electrical.EquipmentOpenSwitchgearVlListUseCasesImpl
import com.example.svetlogorskchpp.__domain.usecases.equipments.list.electrical.EquipmentTransformerOwnNeedsListUseCasesImpl
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
    fun provideEquipmentTransformerOwnNeedsUseCases(
        repository: EquipmentRepository<TransformerOwnNeedsEntity>,
        mapper: TransformerOwnNeedsMapper,
        networkAvailableUseCase: NetworkAvailableUseCase
    ): EquipmentsUseCases<TransformerOwnNeeds> {
        return EquipmentTransformerOwnNeedsUseCasesImpl(
            repository,
            mapper,
            networkAvailableUseCase
        )
    }

    @Provides
    @ViewModelScoped
    fun provideElectricalEquipmentVlUseCases(
        openSwitchgearVlRepository: EquipmentRepository<OpenSwitchgearVlEntity>,
        electricalEquipmentListMapper: ElectricalEquipmentListMapper
    ): EquipmentsListUseCases<ElectricalEquipment.Vl> {
        return EquipmentOpenSwitchgearVlListUseCasesImpl(openSwitchgearVlRepository,electricalEquipmentListMapper)
    }

    @Provides
    @ViewModelScoped
    fun provideElectricalEquipmentTrUseCases(
        openSwitchgearTrRepository: EquipmentRepository<OpenSwitchgearTrEntity>,
        electricalEquipmentListMapper: ElectricalEquipmentListMapper
    ): EquipmentsListUseCases<ElectricalEquipment.Tr> {
        return EquipmentOpenSwitchgearTrListUseCasesImpl(openSwitchgearTrRepository,electricalEquipmentListMapper)
    }

    @Provides
    @ViewModelScoped
    fun provideEquipmentTransformerOwnNeedsListUseCases(
        repository: EquipmentRepository<TransformerOwnNeedsEntity>,
        electricalEquipmentListMapper: ElectricalEquipmentListMapper
    ): EquipmentsListUseCases<ElectricalEquipment.Tsn> {
        return EquipmentTransformerOwnNeedsListUseCasesImpl(repository, electricalEquipmentListMapper)
    }



    @Provides
    @ViewModelScoped
    fun provideEquipmentConsumerTrUseCases(
        openSwitchgearTrRepository: EquipmentRepository<OpenSwitchgearTrEntity>,
        electricalEquipmentListMapper: ElectricalEquipmentListMapper
    ): EquipmentConsumerUseCases<ElectricalEquipment.Tr> {
        return EquipmentConsumerTrUseCasesImpl(openSwitchgearTrRepository,electricalEquipmentListMapper)
    }

    @Provides
    @ViewModelScoped
    fun provideUpdateLocaleUseCases(
        repositoryOpenSwitchgearVlRepository: EquipmentRepository<OpenSwitchgearVlEntity>,
        repositoryOpenSwitchgearTrRepository: EquipmentRepository<OpenSwitchgearTrEntity>,
        noteRequestWorkRepository: NoteRequestWorkRepository,
    ): UpdateLocaleBaseUseCases {
        return UpdateLocaleBaseUseCasesImpl(repositoryOpenSwitchgearVlRepository,repositoryOpenSwitchgearTrRepository, noteRequestWorkRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideUpdateEquipmentAllUseCases (
        trUseCases: EquipmentOpenSwitchgearTrListUseCasesImpl,
        trConsumerUseCases: EquipmentConsumerTrUseCasesImpl
    ) : EquipmentAllUseCases {
        return EquipmentAllUseCasesImpl(trUseCases,trConsumerUseCases)
    }
 }