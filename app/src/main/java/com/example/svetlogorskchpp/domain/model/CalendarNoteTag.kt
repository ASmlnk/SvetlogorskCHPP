package com.example.svetlogorskchpp.domain.model

import java.util.Date

data class CalendarNoteTag (

    val id: Long = 0,
    val date: Date, 	//"YYYY-MM-DD"
    val month: Date, 	//"YYYY-MM"
    val isTechnical: Boolean,
    val isNotes: Boolean = false)