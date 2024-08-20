package com.example.svetlogorskchpp.data.repository.inspection

import com.example.svetlogorskchpp.model.inspectionSchedule.Inspection
import kotlinx.coroutines.flow.StateFlow

interface InspectionRepository {
    suspend fun getAllInspection(date: String)
}