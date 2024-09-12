package com.example.svetlogorskchpp.__domain.task_schedule

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.svetlogorskchpp.__frameworks.CalendarNotesWorker
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Calendar
import java.util.concurrent.TimeUnit
import javax.inject.Inject

private const val CALENDAR_NOTES_WORKER = "CalendarNotesWorker"

class TaskSchedulerNotificationWorkerImpl @Inject constructor(
    @ApplicationContext private val context: Context
): TaskSchedulerNotificationWorker {

   override fun scheduleDailyTaskAtSixAM() {
       val workRequest = PeriodicWorkRequestBuilder<CalendarNotesWorker>(1, TimeUnit.DAYS)
           .setInitialDelay(calculateInitialDelay(), TimeUnit.MILLISECONDS)
           .build()

       WorkManager.getInstance(context).enqueueUniquePeriodicWork(
           CALENDAR_NOTES_WORKER,
           ExistingPeriodicWorkPolicy.REPLACE,
           workRequest
       )
   }

    override fun cancelScheduleTask() {
        WorkManager.getInstance(context).cancelUniqueWork(CALENDAR_NOTES_WORKER)
    }



    private fun calculateInitialDelay(): Long {
        val currentTimeMillis = System.currentTimeMillis()
        val calendar = Calendar.getInstance().apply {
            timeInMillis = currentTimeMillis
            set(Calendar.HOUR_OF_DAY, 6) // Установите на 6:00 утра
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)

            if (timeInMillis <= currentTimeMillis) {
                add(Calendar.DAY_OF_MONTH, 1)
            }
        }
        return calendar.timeInMillis - currentTimeMillis
    }
}