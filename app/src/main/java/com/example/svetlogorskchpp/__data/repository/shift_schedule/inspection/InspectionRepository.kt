package com.example.svetlogorskchpp.__data.repository.shift_schedule.inspection

interface InspectionRepository {
    suspend fun getAllInspection(date: String)
}