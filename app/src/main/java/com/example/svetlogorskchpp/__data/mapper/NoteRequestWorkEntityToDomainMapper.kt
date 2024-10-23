package com.example.svetlogorskchpp.__data.mapper

import com.example.svetlogorskchpp.__data.database.requestWork.NoteRequestWorkEntity
import com.example.svetlogorskchpp.__domain.en.shift_schedule.PermissionRequestWork
import com.example.svetlogorskchpp.__domain.model.shift_schedule.Note
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

class NoteRequestWorkEntityToDomainMapper @Inject constructor() {

    fun map(noteRequestWorkEntity: NoteRequestWorkEntity): Note.NoteRequestWork {
        return with(noteRequestWorkEntity) {
            Note.NoteRequestWork(
                id = id,
                tagDateClose = dateToDate(tagDateClose),
                tagDateOpen = dateToDate(tagDateOpen),
                tagMonthOpen = dateToDate(tagMonthOpen),
                tagMonthClose = dateToDate(tagMonthClose),
                numberRequestWork = numberRequestWork,
                dateOpen = dateToCalendar(dateOpen),
                dateClose = dateToCalendar(dateClose),
                accession = accession,
                reason = reason,
                additionally = additionally,
                isExtend = isExtend,
                contentExtend = contentExtend,
                permission = toPermissionRequestWork(permission)
            )
        }
    }

    private fun dateToCalendar(dateMils: Long): Calendar {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = dateMils
        return calendar
    }

    private fun dateToDate(dateMils: Long): Date {
        val date = Date()
        date.time = dateMils
        return date
    }

    private fun toPermissionRequestWork(permission: String): PermissionRequestWork {
        return when (permission) {
            "диспетчер" -> PermissionRequestWork.DISPATCHER
            "главный инженер" -> PermissionRequestWork.CHIEF_ENGINEER
            else -> PermissionRequestWork.OTHER
        }
    }

}
