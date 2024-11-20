package com.example.svetlogorskchpp.__di

import com.example.svetlogorskchpp.__data.database.electrical_equipment.ElMotor.ElMotorEntity
import com.example.svetlogorskchpp.__data.database.electrical_equipment.LightingAndOther.LightingAndOtherEntity
import com.example.svetlogorskchpp.__data.database.electrical_equipment.OpenSwitchgearTr.OpenSwitchgearTrEntity
import com.example.svetlogorskchpp.__data.database.electrical_equipment.OpenSwitchgearVl.OpenSwitchgearVlEntity
import com.example.svetlogorskchpp.__data.database.electrical_equipment.Switchgear.SwitchgearEntity
import com.example.svetlogorskchpp.__data.database.electrical_equipment.transformerOwnNeeds.TransformerOwnNeedsEntity
import com.example.svetlogorskchpp.__data.database.electrical_equipment.turbogenerator.TurboGeneratorEntity
import com.example.svetlogorskchpp.__data.repository.equipment.EquipmentConsumerRepository
import com.example.svetlogorskchpp.__data.repository.equipment.EquipmentRepository
import com.example.svetlogorskchpp.__data.repository.equipment.electrical.ElMotorRepositoryImpl
import com.example.svetlogorskchpp.__data.repository.equipment.electrical.LightingAndOtherRepositoryImpl
import com.example.svetlogorskchpp.__data.repository.equipment.electrical.SwitchgearRepositoryImpl
import com.example.svetlogorskchpp.__data.repository.shift_schedule.noteRequestWork.NoteRequestWorkRepository
import com.example.svetlogorskchpp.__domain.TempUseCases
import com.example.svetlogorskchpp.__domain.mapper.electrical_equipment.ElMotorMapper
import com.example.svetlogorskchpp.__domain.mapper.electrical_equipment.ElectricalEquipmentListMapper
import com.example.svetlogorskchpp.__domain.mapper.electrical_equipment.LightingAndOtherMapper
import com.example.svetlogorskchpp.__domain.mapper.electrical_equipment.SwitchgearMapper
import com.example.svetlogorskchpp.__domain.mapper.electrical_equipment.TransformerOwnNeedsMapper
import com.example.svetlogorskchpp.__domain.mapper.electrical_equipment.TurbogeneratorMapper
import com.example.svetlogorskchpp.__domain.mapper.electrical_equipment.open_switchgear.OpenSwitchgearTrMapper
import com.example.svetlogorskchpp.__domain.mapper.electrical_equipment.open_switchgear.OpenSwitchgearVlMapper
import com.example.svetlogorskchpp.__domain.model.electrical_equipment.ElMotor
import com.example.svetlogorskchpp.__domain.model.electrical_equipment.LightingAndOther
import com.example.svetlogorskchpp.__domain.model.electrical_equipment.OpenSwitchgearTr
import com.example.svetlogorskchpp.__domain.model.electrical_equipment.OpenSwitchgearVl
import com.example.svetlogorskchpp.__domain.model.electrical_equipment.Switchgear
import com.example.svetlogorskchpp.__domain.model.electrical_equipment.TransformerOwnNeeds
import com.example.svetlogorskchpp.__domain.model.electrical_equipment.TurboGenerator
import com.example.svetlogorskchpp.__domain.usecases.NetworkAvailableUseCase
import com.example.svetlogorskchpp.__domain.usecases.equipments.ConsumerUseCases
import com.example.svetlogorskchpp.__domain.usecases.equipments.EquipmentsListUseCases
import com.example.svetlogorskchpp.__domain.usecases.equipments.EquipmentsUseCases
import com.example.svetlogorskchpp.__domain.usecases.equipments.all_equipment.EquipmentAllUseCases
import com.example.svetlogorskchpp.__domain.usecases.equipments.all_equipment.EquipmentAllUseCasesImpl
import com.example.svetlogorskchpp.__domain.usecases.equipments.all_equipment.EquipmentConsumerUseCases
import com.example.svetlogorskchpp.__domain.usecases.equipments.all_equipment.EquipmentPowerSupplyUseCases
import com.example.svetlogorskchpp.__domain.usecases.equipments.item.electrical.EquipmentElMotorUseCasesImpl
import com.example.svetlogorskchpp.__domain.usecases.equipments.item.electrical.EquipmentLightingAndOtherUseCasesImpl
import com.example.svetlogorskchpp.__domain.usecases.equipments.item.electrical.EquipmentSwitchgearUseCasesImpl
import com.example.svetlogorskchpp.__domain.usecases.equipments.item.electrical.consumer.ConsumerTrUseCasesImpl
import com.example.svetlogorskchpp.__domain.usecases.equipments.item.electrical.EquipmentTransformerOwnNeedsUseCasesImpl
import com.example.svetlogorskchpp.__domain.usecases.equipments.item.electrical.EquipmentTurboGeneratorUseCasesImpl
import com.example.svetlogorskchpp.__domain.usecases.equipments.item.electrical.EquipmentsOpenSwitchgearTrUseCasesImpl
import com.example.svetlogorskchpp.__domain.usecases.equipments.item.electrical.EquipmentsOpenSwitchgearVLUseCasesImpl
import com.example.svetlogorskchpp.__domain.usecases.equipments.list.electrical.EquipmentElMotorListUseCasesImpl
import com.example.svetlogorskchpp.__domain.usecases.equipments.list.electrical.EquipmentLightingAndOtherListUseCasesImpl
import com.example.svetlogorskchpp.__domain.usecases.equipments.list.electrical.EquipmentOpenSwitchgearTrListUseCasesImpl
import com.example.svetlogorskchpp.__domain.usecases.equipments.list.electrical.EquipmentOpenSwitchgearVlListUseCasesImpl
import com.example.svetlogorskchpp.__domain.usecases.equipments.list.electrical.EquipmentSwitchgearListUseCasesImpl
import com.example.svetlogorskchpp.__domain.usecases.equipments.list.electrical.EquipmentTransformerOwnNeedsListUseCasesImpl
import com.example.svetlogorskchpp.__domain.usecases.equipments.list.electrical.EquipmentTurboGeneratorListUseCasesImpl
import com.example.svetlogorskchpp.__domain.usecases.update_locale_base.UpdateLocaleBaseUseCases
import com.example.svetlogorskchpp.__domain.usecases.update_locale_base.UpdateLocaleBaseUseCasesImpl
import com.example.svetlogorskchpp.__presentation.electrical_equipment.model.ElectricalEquipment
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
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
    fun provideEquipmentTurbogeneratorUseCases(
        repository: EquipmentRepository<TurboGeneratorEntity>,
        mapper: TurbogeneratorMapper,
        networkAvailableUseCase: NetworkAvailableUseCase
    ): EquipmentsUseCases<TurboGenerator> {
        return EquipmentTurboGeneratorUseCasesImpl(
            repository,
            mapper,
            networkAvailableUseCase
        )
    }

    @Provides
    @ViewModelScoped
    fun provideEquipmentElMotorUseCases(
        repository: EquipmentRepository<ElMotorEntity>,
        mapper: ElMotorMapper,
        networkAvailableUseCase: NetworkAvailableUseCase
    ): EquipmentsUseCases<ElMotor> {
        return EquipmentElMotorUseCasesImpl(
            repository,
            mapper,
            networkAvailableUseCase
        )
    }

    @Provides
    @ViewModelScoped
    fun provideEquipmentSwitchgearUseCases(
        repository: EquipmentRepository<SwitchgearEntity>,
        mapper: SwitchgearMapper,
        networkAvailableUseCase: NetworkAvailableUseCase
    ): EquipmentsUseCases<Switchgear> {
        return EquipmentSwitchgearUseCasesImpl(
            repository,
            mapper,
            networkAvailableUseCase
        )
    }

    @Provides
    @ViewModelScoped
    fun provideEquipmentLightingAndOtherUseCases(
        repository: EquipmentRepository<LightingAndOtherEntity>,
        mapper: LightingAndOtherMapper,
        networkAvailableUseCase: NetworkAvailableUseCase
    ): EquipmentsUseCases<LightingAndOther> {
        return EquipmentLightingAndOtherUseCasesImpl(
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
        return EquipmentOpenSwitchgearVlListUseCasesImpl(
            openSwitchgearVlRepository,
            electricalEquipmentListMapper
        )
    }

    @Provides
    @ViewModelScoped
    fun provideElectricalEquipmentTrUseCases(
        openSwitchgearTrRepository: EquipmentRepository<OpenSwitchgearTrEntity>,
        electricalEquipmentListMapper: ElectricalEquipmentListMapper
    ): EquipmentsListUseCases<ElectricalEquipment.Tr> {
        return EquipmentOpenSwitchgearTrListUseCasesImpl(
            openSwitchgearTrRepository,
            electricalEquipmentListMapper
        )
    }

    @Provides
    @ViewModelScoped
    fun provideEquipmentTransformerOwnNeedsListUseCases(
        repository: EquipmentRepository<TransformerOwnNeedsEntity>,
        electricalEquipmentListMapper: ElectricalEquipmentListMapper
    ): EquipmentsListUseCases<ElectricalEquipment.Tsn> {
        return EquipmentTransformerOwnNeedsListUseCasesImpl(
            repository,
            electricalEquipmentListMapper
        )
    }

    @Provides
    @ViewModelScoped
    fun provideEquipmentTurbogeneratorListUseCases(
        repository: EquipmentRepository<TurboGeneratorEntity>,
        electricalEquipmentListMapper: ElectricalEquipmentListMapper
    ): EquipmentsListUseCases<ElectricalEquipment.Tg> {
        return EquipmentTurboGeneratorListUseCasesImpl(repository, electricalEquipmentListMapper)
    }

    @Provides
    @ViewModelScoped
    fun provideEquipmentLightingAndOtherListUseCases(
        repository: EquipmentRepository<LightingAndOtherEntity>,
        electricalEquipmentListMapper: ElectricalEquipmentListMapper
    ): EquipmentsListUseCases<ElectricalEquipment.LightOther> {
        return EquipmentLightingAndOtherListUseCasesImpl(repository, electricalEquipmentListMapper)
    }

    @Provides
    @ViewModelScoped
    fun provideEquipmentSwitchgearListUseCases(
        repository: EquipmentRepository<SwitchgearEntity>,
        electricalEquipmentListMapper: ElectricalEquipmentListMapper
    ): EquipmentsListUseCases<ElectricalEquipment.Switchgear> {
        return EquipmentSwitchgearListUseCasesImpl(repository, electricalEquipmentListMapper)
    }

    @Provides
    @ViewModelScoped
    fun provideEquipmentElMotorListUseCases(
        repository: EquipmentRepository<ElMotorEntity>,
        electricalEquipmentListMapper: ElectricalEquipmentListMapper
    ): EquipmentsListUseCases<ElectricalEquipment.ElMotor> {
        return EquipmentElMotorListUseCasesImpl(repository, electricalEquipmentListMapper)
    }

    @Provides
    @ViewModelScoped
    fun provideEquipmentConsumerTrUseCases(
        openSwitchgearTrRepository: EquipmentRepository<OpenSwitchgearTrEntity>,
        electricalEquipmentListMapper: ElectricalEquipmentListMapper
    ): ConsumerUseCases<ElectricalEquipment.Tr> {
        return ConsumerTrUseCasesImpl(openSwitchgearTrRepository, electricalEquipmentListMapper)
    }

    @Provides
    @ViewModelScoped
    fun provideUpdateLocaleUseCases(
        repositoryOpenSwitchgearVlRepository: EquipmentRepository<OpenSwitchgearVlEntity>,
        repositoryOpenSwitchgearTrRepository: EquipmentRepository<OpenSwitchgearTrEntity>,
        repositoryTsn: EquipmentRepository<TransformerOwnNeedsEntity>,
        repositoryTg: EquipmentRepository<TurboGeneratorEntity>,
        repositoryElMotor: EquipmentRepository<ElMotorEntity>,
        repositorySwitchgear: EquipmentRepository<SwitchgearEntity>,
        repositoryLightingAndOther: EquipmentRepository<LightingAndOtherEntity>,
        noteRequestWorkRepository: NoteRequestWorkRepository,
    ): UpdateLocaleBaseUseCases {
        return UpdateLocaleBaseUseCasesImpl(
            repositoryOpenSwitchgearVlRepository,
            repositoryOpenSwitchgearTrRepository,
            repositoryTsn,
            repositoryTg,
            repositoryElMotor,
            repositorySwitchgear,
            repositoryLightingAndOther,
            noteRequestWorkRepository
        )
    }

    @Provides
    @ViewModelScoped
    fun provideUpdateEquipmentAllUseCases(
        vlUseCases: EquipmentOpenSwitchgearVlListUseCasesImpl,
        elMotorUseCases: EquipmentElMotorListUseCasesImpl,
        lightingAndOtherUseCases: EquipmentLightingAndOtherListUseCasesImpl,
        switchgearUseCases: EquipmentSwitchgearListUseCasesImpl,
        trUseCases: EquipmentOpenSwitchgearTrListUseCasesImpl,
        tsnUseCases: EquipmentTransformerOwnNeedsListUseCasesImpl,
        powerSupplyUseCases: EquipmentPowerSupplyUseCases,
        consumerUseCases: EquipmentConsumerUseCases,
    ): EquipmentAllUseCases {
        return EquipmentAllUseCasesImpl(
            vlUseCases,
            elMotorUseCases,
            lightingAndOtherUseCases,
            switchgearUseCases,
            trUseCases,
            tsnUseCases,
            powerSupplyUseCases,
            consumerUseCases
        )
    }

    @Provides
    @ViewModelScoped
    fun provideEquipmentPowerSupplyUseCases(
        mapper: ElectricalEquipmentListMapper,
        repositoryTr: EquipmentRepository<OpenSwitchgearTrEntity>,
        repositoryTsn: EquipmentRepository<TransformerOwnNeedsEntity>,
        repositorySwitchgear: EquipmentRepository<SwitchgearEntity>
    ): EquipmentPowerSupplyUseCases {
        return EquipmentPowerSupplyUseCases(
            mapper,
            repositoryTr,
            repositoryTsn,
            repositorySwitchgear
        )
    }

    @Provides
    @ViewModelScoped
    fun provideEuipmentConsumerUseCases(
        mapper: ElectricalEquipmentListMapper,
        repositoryTg: EquipmentConsumerRepository<TurboGeneratorEntity>,
        repositoryTsn: EquipmentConsumerRepository<TransformerOwnNeedsEntity>,
        repositorySwitchgear: EquipmentConsumerRepository<SwitchgearEntity>,
        repositoryElMotor: EquipmentConsumerRepository<ElMotorEntity>,
        repositoryLighting: EquipmentConsumerRepository<LightingAndOtherEntity>
    ): EquipmentConsumerUseCases {
        return EquipmentConsumerUseCases(
            mapper,
            repositoryTg,
            repositoryTsn,
            repositorySwitchgear,
            repositoryElMotor,
            repositoryLighting
        )
    }

    @Provides
    @ViewModelScoped
    fun provideTempUseCases(
        elRepository: ElMotorRepositoryImpl,
        swRepository: SwitchgearRepositoryImpl,
        liRepository: LightingAndOtherRepositoryImpl,
        firebase: FirebaseFirestore,
        gson: Gson
    ): TempUseCases {
        return TempUseCases(elRepository, swRepository, liRepository, firebase, gson)
    }

}