package com.example.svetlogorskchpp.presentation.shift_schedule.model

import android.os.Parcelable
import com.example.svetlogorskchpp.domain.en.Shift
import com.example.svetlogorskchpp.domain.model.CalendarNoteTag
import com.example.svetlogorskchpp.domain.model.MonthCalendar
import kotlinx.parcelize.Parcelize
import java.util.Calendar

@Parcelize
data class NavigateAddNoteArgs(
    val date: Long,
    val prevNightShift: Shift,
    val dayShift: Shift,
    val nextNightShift: Shift,
    val idNoteTag: Long
): Parcelable
