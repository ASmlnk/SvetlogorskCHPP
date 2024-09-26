package com.example.svetlogorskchpp.__presentation.shift_schedule_requests_work.mapper

import com.example.svetlogorskchpp.__domain.model.Note
import com.example.svetlogorskchpp.__presentation.shift_schedule_requests_work.model.NoteRequestWorkUI
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

class NoteRequestWorkUiToDomainMapper @Inject constructor() {

    fun map (noteRequestWorkUI: NoteRequestWorkUI): Note.NoteRequestWork {
        return with(noteRequestWorkUI) {
            Note.NoteRequestWork(
                id = id,
                tagDateClose = tagDateClose ?: Date(),
                tagDateOpen = tagDateOpen ?: Date(),
                numberRequestWork = numberRequestWork,
                dateOpen = dateOpen ?: CALENDAR_INSTANCE,
                dateClose = dateClose ?: CALENDAR_INSTANCE,
                accession = accession,
                reason = reason,
                additionally = additionally,
                isExtend = isExtend,
                contentExtend = contentExtend
            )
        }
    }

    companion object {
        private val CALENDAR_INSTANCE = Calendar.getInstance()
    }
}