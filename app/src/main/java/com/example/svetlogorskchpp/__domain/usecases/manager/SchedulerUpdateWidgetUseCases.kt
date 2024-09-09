package com.example.svetlogorskchpp.__domain.usecases.manager

import android.content.Intent

interface SchedulerUpdateWidgetUseCases {
    fun updateWidgetLastAction(intent: Intent, timeUpdateMinute: Int)
    fun updateWidgetMidnight(intent: Intent)
}