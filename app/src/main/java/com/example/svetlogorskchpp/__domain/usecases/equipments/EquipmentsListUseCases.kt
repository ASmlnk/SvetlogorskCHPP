package com.example.svetlogorskchpp.__domain.usecases.equipments

import kotlinx.coroutines.flow.Flow

interface EquipmentsListUseCases <out T> {
    fun getElectricalEquipments(): Flow<List<T>>
}