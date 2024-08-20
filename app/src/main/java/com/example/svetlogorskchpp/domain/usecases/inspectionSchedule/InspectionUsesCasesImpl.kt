package com.example.svetlogorskchpp.domain.usecases.inspectionSchedule

import com.example.svetlogorskchpp.data.repository.inspection.InspectionRepository
import javax.inject.Inject

class InspectionUsesCasesImpl @Inject constructor(
    private val inspectionRepository: InspectionRepository
): InspectionUsesCases {

    override suspend fun getAllInspection(date: String) {
        inspectionRepository.getAllInspection(date)
    }

}