package com.example.svetlogorskchpp.__domain.usecases.equipments.all_equipment.delete

import com.example.svetlogorskchpp.__data.model.SuccessResultFirebase
import com.example.svetlogorskchpp.__data.repository.equipment.EquipmentItemDeleteRepository
import com.example.svetlogorskchpp.__di.ElMotorDel
import com.example.svetlogorskchpp.__di.LightingAndOther
import com.example.svetlogorskchpp.__di.MechanInfo
import com.example.svetlogorskchpp.__di.Switchgear
import com.example.svetlogorskchpp.__domain.OperationResult
import com.example.svetlogorskchpp.__domain.usecases.NetworkAvailableUseCase
import com.example.svetlogorskchpp.__presentation.electrical_equipment.model.ElectricalEquipment
import javax.inject.Inject

class EquipmentsItemDeleteUseCasesImpl @Inject constructor(
    @ElMotorDel private val elMotorRepository: EquipmentItemDeleteRepository,
    @Switchgear private val switchgearRepository: EquipmentItemDeleteRepository,
    @LightingAndOther private val lightingAndOtherRepository: EquipmentItemDeleteRepository,
    @MechanInfo private val miRepository: EquipmentItemDeleteRepository,
    private val networkUseCases: NetworkAvailableUseCase
): EquipmentsItemDeleteUseCases {

    override suspend fun deleteItem(item: ElectricalEquipment): OperationResult<String> {

        return if (networkUseCases.isNetworkAvailable()) {
            val resultRepository = when (item) {
                is ElectricalEquipment.ElMotor -> elMotorRepository.deleteItem(item.id)
                is ElectricalEquipment.LightOther -> lightingAndOtherRepository.deleteItem(item.id)
                is ElectricalEquipment.Switchgear -> switchgearRepository.deleteItem(item.id)
                is ElectricalEquipment.Tg -> SuccessResultFirebase.UPDATE_ERROR
                is ElectricalEquipment.Tr -> SuccessResultFirebase.UPDATE_ERROR
                is ElectricalEquipment.Tsn -> SuccessResultFirebase.UPDATE_ERROR
                is ElectricalEquipment.Vl -> SuccessResultFirebase.UPDATE_ERROR
                is ElectricalEquipment.MechanismInfo -> miRepository.deleteItem(item.id)
            }

            when (resultRepository) {
                SuccessResultFirebase.UPDATE_OK -> OperationResult.Success("Данные удалены!")
                SuccessResultFirebase.UPDATE_ERROR -> OperationResult.Error(SuccessResultFirebase.UPDATE_ERROR.result)
            }
        } else {
            OperationResult.Error(SuccessResultFirebase.UPDATE_ERROR.result)
        }
    }
}