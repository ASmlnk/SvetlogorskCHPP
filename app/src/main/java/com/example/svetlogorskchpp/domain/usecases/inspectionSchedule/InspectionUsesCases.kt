package com.example.svetlogorskchpp.domain.usecases.inspectionSchedule

import com.example.svetlogorskchpp.model.inspectionSchedule.Inspection
import kotlinx.coroutines.flow.StateFlow

interface InspectionUsesCases {

    suspend fun getAllInspection(date: String)
}