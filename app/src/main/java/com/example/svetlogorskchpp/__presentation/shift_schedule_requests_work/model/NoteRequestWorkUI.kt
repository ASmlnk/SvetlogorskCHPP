package com.example.svetlogorskchpp.__presentation.shift_schedule_requests_work.model

import java.util.Calendar
import java.util.Date
import java.util.UUID

data class NoteRequestWorkUI(
    val id: String = UUID.randomUUID().toString(),
    val tagDateOpen: Date? = null,
    val tagDateClose: Date? = null,
    val numberRequestWork: String = "",
    val dateOpen: Calendar? = null,
    val dateClose: Calendar? = null,
    val accession: String = "",
    val reason: String = "",
    val additionally: String = "",
    val isExtend: Boolean = false,
    val contentExtend: String = "",
) {
}