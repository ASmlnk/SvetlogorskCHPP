package com.example.svetlogorskchpp.__data.model

import java.util.Calendar
import java.util.Date
import java.util.UUID

data class NoteRequestWorkEntity(
    val id: String ,
    val tagDateOpen: Long,
    val tagDateClose: Long,
    val numberRequestWork: String,
    val dateOpen: Long,
    val dateClose: Long,
    val accession: String,
    val reason: String,
    val additionally: String,
    val isExtend: Boolean,
    val contentExtend: String,
)
