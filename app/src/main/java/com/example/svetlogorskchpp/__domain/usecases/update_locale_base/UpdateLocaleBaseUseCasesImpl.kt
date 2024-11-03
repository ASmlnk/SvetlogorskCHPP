package com.example.svetlogorskchpp.__domain.usecases.update_locale_base

import com.example.svetlogorskchpp.__data.database.electrical_equipment.OpenSwitchgearVl.OpenSwitchgearVlEntity
import com.example.svetlogorskchpp.__data.repository.equipment.EquipmentRepository
import com.example.svetlogorskchpp.__data.repository.shift_schedule.noteRequestWork.NoteRequestWorkRepository
import javax.inject.Inject

class UpdateLocaleBaseUseCasesImpl @Inject constructor(
    private val repositoryOpenSwitchgearVlRepository: EquipmentRepository<OpenSwitchgearVlEntity>,
    private val noteRequestWorkRepository: NoteRequestWorkRepository,
): UpdateLocaleBaseUseCases {
    override suspend fun updateOpenSwitchgearVl() {
        repositoryOpenSwitchgearVlRepository.updateLocaleData()
    }

    override suspend fun updateRequestWork() {
        noteRequestWorkRepository.getRequestWorkFirebase()
    }
}