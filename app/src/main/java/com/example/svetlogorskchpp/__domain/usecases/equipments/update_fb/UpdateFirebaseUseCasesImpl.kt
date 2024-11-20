package com.example.svetlogorskchpp.__domain.usecases.equipments.update_fb

import com.example.svetlogorskchpp.__data.repository.equipment.electrical.EquipmentUpdateFirebaseRepository
import com.example.svetlogorskchpp.__di.ElMotor
import com.example.svetlogorskchpp.__di.LightingAndOther
import com.example.svetlogorskchpp.__di.Switchgear
import javax.inject.Inject

class UpdateFirebaseUseCasesImpl @Inject constructor(
    @ElMotor private val elMotorRepository: EquipmentUpdateFirebaseRepository,
    @Switchgear private val switchgearRepository: EquipmentUpdateFirebaseRepository,
    @LightingAndOther private val lightingAndOtherRepository: EquipmentUpdateFirebaseRepository
): UpdateFirebaseUseCases {
    override suspend fun loaderElMotorInFb() {
        elMotorRepository.loadingLocaleInFirebase()
    }

    override suspend fun loaderSwitchgearInFb() {
        switchgearRepository.loadingLocaleInFirebase()
    }

    override suspend fun loaderLightingAndOtherInFb() {
        lightingAndOtherRepository.loadingLocaleInFirebase()
    }
}