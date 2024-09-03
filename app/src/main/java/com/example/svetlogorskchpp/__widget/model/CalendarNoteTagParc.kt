package com.example.svetlogorskchpp.__widget.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CalendarNoteTagParc(
val date: Long,    //"YYYY-MM-DD"
val month: Long,    //"YYYY-MM"
val isTechnical: Boolean,
val isNotes: Boolean = false,

) : Parcelable
