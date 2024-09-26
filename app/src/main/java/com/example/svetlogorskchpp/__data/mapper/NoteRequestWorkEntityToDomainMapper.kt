package com.example.svetlogorskchpp.__data.mapper

import com.example.svetlogorskchpp.__data.model.NoteRequestWorkEntity
import com.example.svetlogorskchpp.__domain.model.Note
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
                contentExtend = contentExtend
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
}
