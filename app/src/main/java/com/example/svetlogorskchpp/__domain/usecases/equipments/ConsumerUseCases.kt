package com.example.svetlogorskchpp.__domain.usecases.equipments

import kotlinx.coroutines.flow.Flow

interface ConsumerUseCases <out T>{
    fun getItemEquipmentConsumerFlow (id: String): Flow<T?>
}