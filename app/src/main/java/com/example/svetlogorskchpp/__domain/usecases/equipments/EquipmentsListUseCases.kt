package com.example.svetlogorskchpp.__domain.usecases.equipments

import kotlinx.coroutines.flow.Flow

interface EquipmentsListUseCases <out E> {
    fun getElectricalEquipments(): Flow<List<E>>
    fun getSearchElectricalEquipment(searchQuery: String, prefixQuery: String): Flow<List<E>>
}