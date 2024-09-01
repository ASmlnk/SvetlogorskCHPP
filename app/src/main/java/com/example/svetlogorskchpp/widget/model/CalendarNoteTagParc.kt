package com.example.svetlogorskchpp.widget.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class CalendarNoteTagParc(
val date: Long,    //"YYYY-MM-DD"
val month: Long,    //"YYYY-MM"
val isTechnical: Boolean,
val isNotes: Boolean = false,

) : Parcelable
