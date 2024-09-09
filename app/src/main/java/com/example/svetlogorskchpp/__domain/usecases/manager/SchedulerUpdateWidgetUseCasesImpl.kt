package com.example.svetlogorskchpp.__domain.usecases.manager

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Calendar
import javax.inject.Inject

class SchedulerUpdateWidgetUseCasesImpl @Inject constructor(@ApplicationContext val appContext: Context) :
    SchedulerUpdateWidgetUseCases {
   private val alarmManager = appContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    override fun updateWidgetLastAction(intent: Intent, timeUpdateMinute: Int) {

        val pendingIntent = PendingIntent.getBroadcast(
            appContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.set(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis() + timeUpdateMinute * 60 * 1000, //30 * 60 * 1000, // 30 минут
            pendingIntent
        )
    }

    override fun updateWidgetMidnight(intent: Intent) {
        val pendingIntent = PendingIntent.getBroadcast(
            appContext, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Установите время для первого срабатывания в полночь
        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)

            // Если текущее время уже после полуночи, планируем на следующий день
            if (timeInMillis <= System.currentTimeMillis()) {
                add(Calendar.DAY_OF_MONTH, 1)
            }
        }

        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }
}