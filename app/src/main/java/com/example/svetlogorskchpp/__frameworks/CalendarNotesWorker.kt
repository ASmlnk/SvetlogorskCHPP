package com.example.svetlogorskchpp.__frameworks

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.svetlogorskchpp.CHANNEL_ID
import com.example.svetlogorskchpp.MainActivity
import com.example.svetlogorskchpp.R
import com.example.svetlogorskchpp.__di.Widget
import com.example.svetlogorskchpp.__domain.interactor.shift_schedule.calendar.ShiftScheduleCalendarInteractor
import com.example.svetlogorskchpp.__domain.model.NoteTechnicalNotification
import com.example.svetlogorskchpp.__domain.usecases.calendarNoteNotificationUseCases.CalendarNoteNotificationUseCases
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.util.Calendar

@HiltWorker
class CalendarNotesWorker @AssistedInject constructor(
    @Assisted val context: Context,
    @Assisted val workerParameters: WorkerParameters,
    private val calendarNoteNotificationUseCases: CalendarNoteNotificationUseCases,


    ) : CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result {
        val calendarNoteTechnicalNotification =
            calendarNoteNotificationUseCases.calendarNoteTechnicalNotification(
                Calendar.getInstance()
            )
        notifyUser(calendarNoteTechnicalNotification)

        return Result.success()
    }

    @SuppressLint("MissingPermission")
    private fun notifyUser(calendarNoteTechnicalNotification: NoteTechnicalNotification) {
        val intent = MainActivity.newIntent(context)
        val pendingIntent =
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val resources = context.resources

        val notification = NotificationCompat
            .Builder(context, CHANNEL_ID)
            .setTicker("1")
            .setSmallIcon(R.drawable.klipartz_com3)
            .setContentTitle("2")
            .setContentText("3")
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat.from(context).notify(0, notification)

    }
}