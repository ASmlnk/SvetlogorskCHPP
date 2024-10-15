package com.example.svetlogorskchpp.__domain.task_schedule.update_request_work

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.svetlogorskchpp.__frameworks.RequestWorkUpdateBaseWorker
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject

private const val REQUEST_WORK_UPDATE = "requestWorkUpdate"

class TaskSchedulerUpdateRequestWorkBaseWorkerImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : TaskSchedulerUpdateRequestWorkBaseWorker {

    override fun scheduleDailyTask12hour() {
        val workRequest = PeriodicWorkRequestBuilder<RequestWorkUpdateBaseWorker>(
            12,
            TimeUnit.HOURS
        ).build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            REQUEST_WORK_UPDATE,
            ExistingPeriodicWorkPolicy.REPLACE,
            workRequest
        )
    }

    override fun cancelScheduleTask12hour() {
        WorkManager.getInstance(context).cancelUniqueWork(REQUEST_WORK_UPDATE)
    }
}