package com.example.svetlogorskchpp.__domain.usecases.equipments.update_fb

import com.example.svetlogorskchpp.__data.repository.equipment.electrical.EquipmentUpdateFirebaseRepository
import com.example.svetlogorskchpp.__di.ElMotor
import com.example.svetlogorskchpp.__di.LightingAndOther
import com.example.svetlogorskchpp.__di.Switchgear
import com.example.svetlogorskchpp.__di.Tg
import com.example.svetlogorskchpp.__di.Tr
import com.example.svetlogorskchpp.__di.Tsn
import com.example.svetlogorskchpp.__di.Vl
import javax.inject.Inject

class UpdateFirebaseUseCasesImpl @Inject constructor(
    @ElMotor private val elMotorRepository: EquipmentUpdateFirebaseRepository,
    @Switchgear private val switchgearRepository: EquipmentUpdateFirebaseRepository,
    @LightingAndOther private val lightingAndOtherRepository: EquipmentUpdateFirebaseRepository,
    @Vl private val vlRepository: EquipmentUpdateFirebaseRepository,
    @Tr private val trRepository: EquipmentUpdateFirebaseRepository,
    @Tsn private val tsnRepository: EquipmentUpdateFirebaseRepository,
    @Tg private val tgRepository: EquipmentUpdateFirebaseRepository,
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

    override suspend fun reservationFirebase() {
        elMotorRepository.reservationFirebase()
        switchgearRepository.reservationFirebase()
        lightingAndOtherRepository.reservationFirebase()
        vlRepository.reservationFirebase()
        trRepository.reservationFirebase()
        tsnRepository.reservationFirebase()
        tgRepository.reservationFirebase()
    }
}