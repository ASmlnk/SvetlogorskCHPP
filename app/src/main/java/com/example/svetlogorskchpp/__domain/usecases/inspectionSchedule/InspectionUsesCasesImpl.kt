package com.example.svetlogorskchpp.__domain.usecases.inspectionSchedule

import com.example.svetlogorskchpp.__data.repository.inspection.InspectionRepository
import javax.inject.Inject

class InspectionUsesCasesImpl @Inject constructor(
    private val inspectionRepository: InspectionRepository
): InspectionUsesCases {

    override suspend fun getAllInspection(date: String) {
        inspectionRepository.getAllInspection(date)
    }

}