package com.example.svetlogorskchpp.__presentation.shift_schedule.shift_schedule_requests_work.mapper

import com.example.svetlogorskchpp.__domain.en.shift_schedule.PermissionRequestWork
import com.example.svetlogorskchpp.__domain.model.shift_schedule.Note
import com.example.svetlogorskchpp.__presentation.shift_schedule.shift_schedule_requests_work.model.NoteRequestWorkUI
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
                dateOpen = dateOpen ?: com.example.svetlogorskchpp.__presentation.shift_schedule.shift_schedule_requests_work.mapper.NoteRequestWorkUiToDomainMapper.CALENDAR_INSTANCE,
                dateClose = dateClose ?: com.example.svetlogorskchpp.__presentation.shift_schedule.shift_schedule_requests_work.mapper.NoteRequestWorkUiToDomainMapper.CALENDAR_INSTANCE,
                accession = accession,
                reason = reason,
                additionally = additionally,
                isExtend = isExtend,
                contentExtend = contentExtend,
                tagMonthOpen = tagMonthOpen ?: Date(),
                tagMonthClose = tagMonthClose ?: Date(),
                permission = permission ?: PermissionRequestWork.OTHER
            )
        }
    }

    companion object {
        private val CALENDAR_INSTANCE = Calendar.getInstance()
    }
}