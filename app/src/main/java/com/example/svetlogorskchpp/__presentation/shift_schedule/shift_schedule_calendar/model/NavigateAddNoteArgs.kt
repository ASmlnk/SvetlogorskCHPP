package com.example.svetlogorskchpp.__presentation.shift_schedule.shift_schedule_calendar.model

import android.os.Parcelable
import com.example.svetlogorskchpp.__domain.en.shift_schedule.Shift
import kotlinx.parcelize.Parcelize

@Parcelize
data class NavigateAddNoteArgs(
    val date: Long,
    val prevNightShift: Shift,
    val dayShift: Shift,
    val nextNightShift: Shift,
    val isTechnical: Boolean
): Parcelable
