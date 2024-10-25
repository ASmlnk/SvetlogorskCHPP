package com.example.svetlogorskchpp.__frameworks

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.svetlogorskchpp.__data.repository.shift_schedule.noteRequestWork.NoteRequestWorkRepository
import com.example.svetlogorskchpp.__domain.usecases.shift_schedule.calendarNoteNotificationUseCases.CalendarNoteNotificationUseCases
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.util.Calendar

@HiltWorker
class RequestWorkUpdateBaseWorker @AssistedInject constructor(
    @Assisted val context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val noteRequestWorkRepository: NoteRequestWorkRepository
) : CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result {

        noteRequestWorkRepository.getRequestWorkFirebase()

        return Result.success()
    }
}