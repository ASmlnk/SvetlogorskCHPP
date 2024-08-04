package com.example.svetlogorskchpp.domain.usecases

import com.example.svetlogorskchpp.domain.en.JobTitle
import com.example.svetlogorskchpp.domain.en.Shift
import javax.inject.Inject

class JobTitleUseCases @Inject constructor() {

    fun stringToJobTitle(str: String): JobTitle {
        return when (str) {
            "NSS" -> JobTitle.NSS
            "NSE" -> JobTitle.NSE
            "DEM_5R" -> JobTitle.DEM_5R
            "DEM_6R" -> JobTitle.DEM_6R
            else -> JobTitle.TITLE_NO
        }
    }

    fun jobTitleToString(jobTitle: JobTitle): String {
        return when (jobTitle) {
            JobTitle.NSS -> "NSS"
            JobTitle.NSE -> "NSE"
            JobTitle.DEM_5R -> "DEM_5R"
            JobTitle.DEM_6R -> "DEM_6R"
            JobTitle.TITLE_NO -> ""
        }
    }



}