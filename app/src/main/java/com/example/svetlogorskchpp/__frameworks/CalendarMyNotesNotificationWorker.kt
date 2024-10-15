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
import com.example.svetlogorskchpp.__domain.model.NoteTechnicalNotification
import com.example.svetlogorskchpp.__domain.usecases.calendarNoteNotificationUseCases.CalendarNoteNotificationUseCases
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.util.Calendar

@HiltWorker
class CalendarMyNotesNotificationWorker @AssistedInject constructor(
    @Assisted val context: Context,
    @Assisted workerParameters: WorkerParameters,
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
        val pendingIntent = NavDeepLinkBuilder(context)
            .setGraph(R.navigation.nav_graph)
            .setDestination(R.id.shiftScheduleFragment)
            .createPendingIntent()

        val resources = context.resources

        if (calendarNoteTechnicalNotification.isTechnicalToday) {
            val contentText = resources.getString(
                R.string.notification_technical_today,
                "<i>${calendarNoteTechnicalNotification.dateToday}</i>"
            )
            val notification = notificationTechnical(contentText, pendingIntent)

            NotificationManagerCompat.from(context).notify(0, notification)
        }

        if (calendarNoteTechnicalNotification.isTechnicalTomorrow) {
            val contentText = resources.getString(
                R.string.notification_technical_tomorrow,
                "<i>${calendarNoteTechnicalNotification.dateTomorrow}</i>"
            )
            val notification = notificationTechnical(contentText, pendingIntent)

            NotificationManagerCompat.from(context).notify(1, notification)
        }

        if (calendarNoteTechnicalNotification.isNoteToday) {
            val contentText = calendarNoteTechnicalNotification.eventToday
            val notification = notificationNotes(
                context.getString(R.string.notification_note_today, "<i>${calendarNoteTechnicalNotification.dateToday}</i>", contentText),
                pendingIntent
            )

            NotificationManagerCompat.from(context).notify(2, notification)
        }
        if (calendarNoteTechnicalNotification.isNoteTomorrow) {
            val contentText = calendarNoteTechnicalNotification.eventTomorrow
            val notification = notificationNotes(
                context.getString(R.string.notification_note_tomorrow, "<i>${calendarNoteTechnicalNotification.dateTomorrow}</i>", contentText),
                pendingIntent
            )

            NotificationManagerCompat.from(context).notify(3, notification)
        }
    }

    private fun String.fromHtmlCompat(): Spanned = Html.fromHtml(this, Html.FROM_HTML_MODE_COMPACT)

    private fun notificationTechnical(
        contentText: String,
        pendingIntent: PendingIntent,
    ): Notification {
        return NotificationCompat
            .Builder(context, CHANNEL_ID)
            .setTicker(context.resources.getString(R.string.channel_name_description_calendar))
            .setSmallIcon(R.drawable.klipartz_com3)
            .setContentTitle(context.resources.getString(R.string.notification_title_technical))
            .setContentText(contentText.fromHtmlCompat())
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
    }

    private fun notificationNotes(
        contentText: String,
        pendingIntent: PendingIntent
    ): Notification {
        return NotificationCompat
            .Builder(context, CHANNEL_ID)
            .setTicker(context.resources.getString(R.string.channel_name_description_calendar))
            .setSmallIcon(R.drawable.klipartz_com3)
            .setContentTitle(context.resources.getString(R.string.notification_title_notes))
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
}