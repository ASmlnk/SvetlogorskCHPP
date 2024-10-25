package com.example.svetlogorskchpp.__domain.usecases.electrical_equipment.electircal_equipments

import kotlinx.coroutines.flow.Flow

interface ElectricalEquipmentsUseCases <out T> {
    fun getElectricalEquipments(): Flow<List<T>>
}