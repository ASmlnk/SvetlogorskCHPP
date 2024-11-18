package com.example.svetlogorskchpp.__domain.usecases.hardData

import com.example.svetlogorskchpp.__data.hard.HardDataListRepository
import com.example.svetlogorskchpp.__di.InfoORY
import com.example.svetlogorskchpp.__di.InfoTSN
import com.example.svetlogorskchpp.__di.RequestWorkAccession
import com.example.svetlogorskchpp.__di.RequestWorkReason
import com.example.svetlogorskchpp.__domain.en.HardData
import javax.inject.Inject

class HardDataUseCasesImpl @Inject constructor(
    @RequestWorkReason private val reasonHardData: HardDataListRepository<String>,
    @RequestWorkAccession private val accessionHardData: HardDataListRepository<String>,
    @InfoORY private val infoORY: HardDataListRepository<String>,
    @InfoTSN private val infoTSN: HardDataListRepository<String>,
): HardDataUseCases<String> {
    override fun data(hardData: HardData): List<String> {
        return when(hardData) {
            HardData.REQUEST_WORK_ACCESSION -> accessionHardData.data()
            HardData.REQUEST_WORK_REASON ->reasonHardData.data()
            HardData.INFO_ORY -> infoORY.data()
            HardData.INFO_TSN -> infoTSN.data()
        }
    }
}