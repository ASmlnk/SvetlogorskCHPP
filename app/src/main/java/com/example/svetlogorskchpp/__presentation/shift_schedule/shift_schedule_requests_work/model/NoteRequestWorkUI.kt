package com.example.svetlogorskchpp.__presentation.shift_schedule.shift_schedule_requests_work.model

import com.example.svetlogorskchpp.__domain.en.shift_schedule.PermissionRequestWork
import java.util.Calendar
import java.util.Date
import java.util.UUID

data class NoteRequestWorkUI(
    val id: String = UUID.randomUUID().toString(),
    val tagDateOpen: Date? = null,
    val tagDateClose: Date? = null,
    val tagMonthOpen: Date? = null,
    val tagMonthClose: Date? = null,
    val numberRequestWork: String = "",
    val dateOpen: Calendar? = null,
    val dateClose: Calendar? = null,
    val accession: String = "",
    val reason: String = "",
    val additionally: String = "",
    val isExtend: Boolean = false,
    val contentExtend: String = "",
    val permission: PermissionRequestWork? = null
) {
}