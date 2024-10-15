package com.example.svetlogorskchpp.__domain.task_schedule.notification

interface TaskSchedulerNotificationWorker {
    fun scheduleDailyTaskMyNotesAtSixAM()
    fun cancelScheduleTaskMyNotes()
    fun cancelScheduleTaskRequestWork()
    fun scheduleDailyTaskRequestWorkAtSixAM()
}