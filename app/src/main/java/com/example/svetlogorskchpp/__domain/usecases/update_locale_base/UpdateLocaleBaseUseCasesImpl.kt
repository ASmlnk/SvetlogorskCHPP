package com.example.svetlogorskchpp.__domain.usecases.update_locale_base

import com.example.svetlogorskchpp.__data.database.electrical_equipment.ElMotor.ElMotorEntity
import com.example.svetlogorskchpp.__data.database.electrical_equipment.LightingAndOther.LightingAndOtherEntity
import com.example.svetlogorskchpp.__data.database.electrical_equipment.OpenSwitchgearTr.OpenSwitchgearTrEntity
import com.example.svetlogorskchpp.__data.database.electrical_equipment.OpenSwitchgearVl.OpenSwitchgearVlEntity
import com.example.svetlogorskchpp.__data.database.electrical_equipment.Switchgear.SwitchgearEntity
import com.example.svetlogorskchpp.__data.database.electrical_equipment.transformerOwnNeeds.TransformerOwnNeedsEntity
import com.example.svetlogorskchpp.__data.database.electrical_equipment.turbogenerator.TurboGeneratorEntity
import com.example.svetlogorskchpp.__data.repository.equipment.EquipmentRepository
import com.example.svetlogorskchpp.__data.repository.shift_schedule.noteRequestWork.NoteRequestWorkRepository
import javax.inject.Inject

class UpdateLocaleBaseUseCasesImpl @Inject constructor(
    private val repositoryOpenSwitchgearVlRepository: EquipmentRepository<OpenSwitchgearVlEntity>,
    private val repositoryOpenSwitchgearTrRepository: EquipmentRepository<OpenSwitchgearTrEntity>,
    private val repositoryTsn: EquipmentRepository<TransformerOwnNeedsEntity>,
    private val repositoryTg: EquipmentRepository<TurboGeneratorEntity>,
    private val repositoryElMotor: EquipmentRepository<ElMotorEntity>,
    private val repositorySwitchgear: EquipmentRepository<SwitchgearEntity>,
    private val repositoryLightingAndOther: EquipmentRepository<LightingAndOtherEntity>,
    private val noteRequestWorkRepository: NoteRequestWorkRepository,
): UpdateLocaleBaseUseCases {
    override suspend fun updateOpenSwitchgearVl() {
        repositoryOpenSwitchgearVlRepository.updateLocaleData()
    }

    override suspend fun updateRequestWork() {
        noteRequestWorkRepository.getRequestWorkFirebase()
    }

    override suspend fun updateOpenSwitchgearTr() {
        repositoryOpenSwitchgearTrRepository.updateLocaleData()
    }

    override suspend fun updateTsn() {
        repositoryTsn.updateLocaleData()
    }

    override suspend fun updateTg() {
        repositoryTg.updateLocaleData()
    }

    override suspend fun updateElMotor() {
        repositoryElMotor.updateLocaleData()
    }

    override suspend fun updateSwitchgear() {
        repositorySwitchgear.updateLocaleData()
    }

    override suspend fun updateLightingAndOther() {
        repositoryLightingAndOther.updateLocaleData()
    }
}