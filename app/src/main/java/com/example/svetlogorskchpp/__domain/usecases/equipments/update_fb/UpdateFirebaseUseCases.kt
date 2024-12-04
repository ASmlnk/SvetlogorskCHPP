package com.example.svetlogorskchpp.__domain.usecases.equipments.update_fb

interface UpdateFirebaseUseCases {
    suspend fun loaderElMotorInFb()
    suspend fun loaderSwitchgearInFb()
    suspend fun loaderLightingAndOtherInFb()
    suspend fun reservationFirebase()
}