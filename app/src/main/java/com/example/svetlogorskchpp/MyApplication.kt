package com.example.svetlogorskchpp

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.example.svetlogorskchpp.__domain.usecases.calendarNoteNotificationUseCases.CalendarNoteNotificationUseCases
import com.example.svetlogorskchpp.model.firebase.FirestoreRepository
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import java.util.Calendar
import javax.inject.Inject

const val CHANNEL_ID = "calendar_notes"

@HiltAndroidApp
class MyApplication : Application() {

    @Inject
    lateinit var notificationManager: NotificationManager

    override fun onCreate() {
        super.onCreate()
        SharedPreferencesManager.init(this)

        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                getString(R.string.channel_name_calendar_notes),
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = getString(R.string.channel_name_description_calendar)
            }
            notificationManager.createNotificationChannel(channel)
        }
    }
}