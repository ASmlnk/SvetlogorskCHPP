package com.example.svetlogorskchpp.__data.repository.inspection

interface InspectionRepository {
    suspend fun getAllInspection(date: String)
}