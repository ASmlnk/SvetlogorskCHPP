package com.example.svetlogorskchpp.__data.repository.equipment.electrical

interface EquipmentUpdateFirebaseRepository {
    suspend fun loadingLocaleInFirebase()
    suspend fun reservationFirebase()
}