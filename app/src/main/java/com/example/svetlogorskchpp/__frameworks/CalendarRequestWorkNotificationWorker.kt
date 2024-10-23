package com.example.svetlogorskchpp.__frameworks

import android.annotation.SuppressLint
import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.text.Html
import android.text.Spanned
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.hilt.work.HiltWorker
import androidx.navigation.NavDeepLinkBuilder
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.svetlogorskchpp.CHANNEL_ID
import com.example.svetlogorskchpp.R
import com.example.svetlogorskchpp.__domain.model.shift_schedule.RequestWorkNotification
import com.example.svetlogorskchpp.__domain.usecases.shift_schedule.calendarNoteNotificationUseCases.CalendarNoteNotificationUseCases
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.util.Calendar

@HiltWorker
class CalendarRequestWorkNotificationWorker @AssistedInject constructor(
    @Assisted val context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val calendarNoteNotificationUseCases: CalendarNoteNotificationUseCases,
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        val calendarRequestWork = calendarNoteNotificationUseCases.calendarRequestWorkNotification(
            Calendar.getInstance()
        )
        notifyUser(calendarRequestWork)

        return Result.success()
    }

    @SuppressLint("MissingPermission")
    private fun notifyUser(calendarRequestWorkNotification: RequestWorkNotification) {
        val pendingIntent = NavDeepLinkBuilder(context)
            .setGraph(R.navigation.nav_graph)
            .setDestination(R.id.shiftScheduleFragment)
            .createPendingIntent()

        val resources = context.resources

        if (calendarRequestWorkNotification.isOpenRequestWork) {
            val contentText = resources.getString(
                R.string.notification_request_work_open,
                calendarRequestWorkNotification.date, calendarRequestWorkNotification.eventOpen
            )
            val notification = notificationRequestWork(contentText, pendingIntent)

            NotificationManagerCompat.from(context).notify(4, notification)
        }

        if (calendarRequestWorkNotification.isCloseRequestWork) {
            val contentText = resources.getString(
                R.string.notification_request_work_close,
                calendarRequestWorkNotification.date, calendarRequestWorkNotification.eventClose
            )
            val notification = notificationRequestWork(contentText, pendingIntent)

            NotificationManagerCompat.from(context).notify(5, notification)
        }

    }

    private fun notificationRequestWork(
        contentText: String,
        pendingIntent: PendingIntent
    ): Notification {
        return NotificationCompat
            .Builder(context, CHANNEL_ID)
            .setTicker(context.resources.getString(R.string.channel_name_description_calendar))
            .setSmallIcon(R.drawable.klipartz_com3)
            .setContentTitle(context.resources.getString(R.string.notification_title_request_work))
            .setContentText(contentText.fromHtmlCompat())
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(contentText.fromHtmlCompat()
                    )
            )
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
    }

    private fun String.fromHtmlCompat(): Spanned = Html.fromHtml(this, Html.FROM_HTML_MODE_COMPACT)

}