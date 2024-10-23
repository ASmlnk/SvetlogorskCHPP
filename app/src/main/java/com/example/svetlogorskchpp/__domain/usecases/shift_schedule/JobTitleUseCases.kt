package com.example.svetlogorskchpp.__domain.usecases.shift_schedule

import com.example.svetlogorskchpp.__domain.en.shift_schedule.JobTitle
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