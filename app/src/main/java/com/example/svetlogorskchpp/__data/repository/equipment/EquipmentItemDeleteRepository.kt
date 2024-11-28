package com.example.svetlogorskchpp.__data.repository.equipment

import com.example.svetlogorskchpp.__data.model.SuccessResultFirebase

interface EquipmentItemDeleteRepository {
    suspend fun deleteItem(id: String) : SuccessResultFirebase
}