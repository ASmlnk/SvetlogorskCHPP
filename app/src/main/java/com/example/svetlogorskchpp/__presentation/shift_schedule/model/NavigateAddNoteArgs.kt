package com.example.svetlogorskchpp.__presentation.shift_schedule.model

import android.os.Parcelable
import com.example.svetlogorskchpp.__domain.en.Shift
import kotlinx.parcelize.Parcelize

@Parcelize
data class NavigateAddNoteArgs(
    val date: Long,
    val prevNightShift: Shift,
    val dayShift: Shift,
    val nextNightShift: Shift,
    val isTechnical: Boolean
): Parcelable
