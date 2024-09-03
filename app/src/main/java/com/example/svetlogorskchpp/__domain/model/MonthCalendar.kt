package com.example.svetlogorskchpp.__domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class MonthCalendar : Parcelable {
    ACTUAL_MONTH,
    PREV_MONTH,
    NEXT_MONTH
}