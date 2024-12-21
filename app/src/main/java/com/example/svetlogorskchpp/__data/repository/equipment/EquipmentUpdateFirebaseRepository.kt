package com.example.svetlogorskchpp.__data.repository.equipment

interface EquipmentUpdateFirebaseRepository {
    suspend fun loadingLocaleInFirebase()
    suspend fun reservationFirebase()
}