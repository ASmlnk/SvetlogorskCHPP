package com.example.svetlogorskchpp.__domain.usecases.hardData

import com.example.svetlogorskchpp.__data.hard.HardDataRepository
import com.example.svetlogorskchpp.__di.RequestWorkAccession
import com.example.svetlogorskchpp.__di.RequestWorkReason
import com.example.svetlogorskchpp.__domain.en.HardData
import javax.inject.Inject

class RequestWorkHardDataUseCasesImpl @Inject constructor(
    @RequestWorkReason private val reasonHardData: HardDataRepository<String>,
    @RequestWorkAccession private val accessionHardData: HardDataRepository<String>
): HardDataUseCases<String> {
    override fun data(hardData: HardData): List<String> {
        return when(hardData) {
            HardData.REQUEST_WORK_ACCESSION -> accessionHardData.data()
            HardData.REQUEST_WORK_REASON ->reasonHardData.data()
        }
    }
}