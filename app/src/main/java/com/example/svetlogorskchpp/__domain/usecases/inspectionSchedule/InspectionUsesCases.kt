package com.example.svetlogorskchpp.__domain.usecases.inspectionSchedule

interface InspectionUsesCases {

    suspend fun getAllInspection(date: String)
}