package com.example.svetlogorskchpp.__presentation.shift_schedule_requests_work.model

import java.util.Calendar
import java.util.Date

data class ExtendRequestWorkUI(
    val tagDateOpen: Date? = null,
    val tagDateClose: Date? = null,
    val tagMonthOpen: Date? = null,
    val tagMonthClose: Date? = null,
    val dateOpen: Calendar? = null,
    val dateClose: Calendar? = null,
    val numberRequestWork: String = ""
)