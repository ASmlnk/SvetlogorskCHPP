package com.example.svetlogorskchpp.__data.model

data class NoteRequestWorkEntity(
    val id: String ,
    val tagDateOpen: Long,
    val tagDateClose: Long,
    val tagMonthOpen: Long,
    val tagMonthClose: Long,
    val numberRequestWork: String,
    val dateOpen: Long,
    val dateClose: Long,
    val accession: String,
    val reason: String,
    val additionally: String,
    val isExtend: Boolean,
    val contentExtend: String,
)
