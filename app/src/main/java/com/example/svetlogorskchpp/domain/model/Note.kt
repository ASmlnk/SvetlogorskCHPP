package com.example.svetlogorskchpp.domain.model

import java.util.Date

data class Note (
    val id: Long = 0,
    val tagDate: Date,		//авязь с CalendarTag
    val dateNotes: Date,	 	//"YYYY-MM-DD HH:mm"
    val isTimeNotes: Boolean = false,
    val content: String)