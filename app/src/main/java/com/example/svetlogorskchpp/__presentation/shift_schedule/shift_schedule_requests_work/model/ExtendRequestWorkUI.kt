package com.example.svetlogorskchpp.__presentation.shift_schedule.shift_schedule_requests_work.model

import java.util.Calendar
import java.util.Date
import java.util.UUID

data class ExtendRequestWorkUI(
    val id: String = UUID.randomUUID().toString(),
    val tagDateOpen: Date? = null,
    val tagDateClose: Date? = null,
    val tagMonthOpen: Date? = null,
    val tagMonthClose: Date? = null,
    val dateOpen: Calendar? = null,
    val dateClose: Calendar? = null,
    val numberRequestWork: String = ""
)