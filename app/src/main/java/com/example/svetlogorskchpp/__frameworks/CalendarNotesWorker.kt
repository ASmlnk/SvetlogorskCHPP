package com.example.svetlogorskchpp.__frameworks

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.svetlogorskchpp.__di.Widget
import com.example.svetlogorskchpp.__domain.interactor.shift_schedule.calendar.ShiftScheduleCalendarInteractor
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class CalendarNotesWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    @Widget private val shiftScheduleCalendarInteractor: ShiftScheduleCalendarInteractor,

): CoroutineWorker(context,workerParameters) {
    override suspend fun doWork(): Result {




        return Result.success()
    }
}