package com.example.svetlogorskchpp.__domain.task_schedule

interface TaskSchedulerNotificationWorker {
    fun scheduleDailyTaskAtSixAM()
    fun cancelScheduleTask()
}