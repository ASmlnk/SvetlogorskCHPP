package com.example.svetlogorskchpp.__di

import com.example.svetlogorskchpp.__data.database.electrical_equipment.ElMotor.ElMotorEntity
import com.example.svetlogorskchpp.__data.database.equipment.mechanism_info.MechanismInfoEntity
import com.example.svetlogorskchpp.__data.repository.equipment.EquipmentConsumerRepository
import com.example.svetlogorskchpp.__data.repository.equipment.EquipmentRepository
import com.example.svetlogorskchpp.__data.repository.equipment.EquipmentSubMechanismRepository
import com.example.svetlogorskchpp.__domain.mapper.electrical_equipment.ElectricalEquipmentListMapper
import com.example.svetlogorskchpp.__domain.mapper.equipment.MechanismInfoMapper
import com.example.svetlogorskchpp.__domain.model.equipment.MechanismInfo
import com.example.svetlogorskchpp.__domain.usecases.NetworkAvailableUseCase
import com.example.svetlogorskchpp.__domain.usecases.equipments.EquipmentsListUseCases
import com.example.svetlogorskchpp.__domain.usecases.equipments.EquipmentsUseCases
import com.example.svetlogorskchpp.__domain.usecases.equipments.all_equipment.mechanical.EquipmentMechanismAllUseCasesImpl
import com.example.svetlogorskchpp.__domain.usecases.equipments.all_equipment.mechanical.EquipmentMechanismAllUseCases
import com.example.svetlogorskchpp.__domain.usecases.equipments.item.mechanical.EquipmentMechanismInfoUseCasesImpl
import com.example.svetlogorskchpp.__domain.usecases.equipments.list.mechanical.EquipmentMechanismInfoListUseCasesImpl
import com.example.svetlogorskchpp.__presentation.electrical_equipment.model.ElectricalEquipment
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class UseCasesEquipmentMechanicalModule {

    @Provides
    @ViewModelScoped
    fun provideEquipmentMechanismInfoListUseCases(
        repository: EquipmentRepository<MechanismInfoEntity>,
        electricalEquipmentListMapper: ElectricalEquipmentListMapper
    ): EquipmentsListUseCases<ElectricalEquipment.MechanismInfo> {
        return EquipmentMechanismInfoListUseCasesImpl (repository, electricalEquipmentListMapper)
    }


    @Provides
    @ViewModelScoped
    fun provideEquipmentMechanismInfoUseCases(
        repository: EquipmentRepository<MechanismInfoEntity>,
        mapper: MechanismInfoMapper,
        networkUseCases: NetworkAvailableUseCase
    ): EquipmentsUseCases<MechanismInfo> {
        return EquipmentMechanismInfoUseCasesImpl (repository, mapper, networkUseCases)
    }

    @Provides
    @ViewModelScoped
    fun provideEquipmentMechanicalAllUseCases(
        mapper: ElectricalEquipmentListMapper,
        repositoryElMotor: EquipmentSubMechanismRepository<ElMotorEntity>,
        repositorySubMechanism: EquipmentSubMechanismRepository<MechanismInfoEntity>,
        repositoryMechanismInfo: EquipmentRepository<MechanismInfoEntity>
    ): EquipmentMechanismAllUseCases {
        return EquipmentMechanismAllUseCasesImpl(mapper, repositoryElMotor, repositorySubMechanism, repositoryMechanismInfo)
    }
}