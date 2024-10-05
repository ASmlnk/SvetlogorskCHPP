package com.example.svetlogorskchpp.__data.database.requestWork

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.svetlogorskchpp.__domain.en.PermissionRequestWork
import com.example.svetlogorskchpp.__domain.model.Note
import java.util.Calendar
import java.util.Date

@Entity(tableName = "request_work")
data class NoteRequestWorkEntity(
    @PrimaryKey
    val id: String,
    val tagDateOpen: Long,
    val tagDateClose: Long,
    val tagMonthOpen: Long,
    val tagMonthClose: Long,
    val numberRequestWork: String,
    val dateOpen: Long,
    val dateClose: Long,
    val accession: String,
    val reason: String,
    val additionally: String,
    val isExtend: Boolean,
    val contentExtend: String,
    val permission: String
) {
    fun toNote(): Note {
        return Note.NoteRequestWork (
            id = this.id,
            tagDateOpen = longToDate(this.tagDateOpen),
            tagDateClose = longToDate(this.tagDateClose),
            tagMonthOpen = longToDate(this.tagMonthOpen),
            tagMonthClose = longToDate(this.tagMonthClose),
            numberRequestWork = this.numberRequestWork,
            dateOpen = dateToCalendar(longToDate(this.dateOpen)),
            dateClose = dateToCalendar(longToDate(this.dateClose)),
            accession = this.accession,
            reason = this.reason,
            additionally = this.additionally,
            isExtend = this.isExtend,
            contentExtend = this.contentExtend,
            permission = toPermissionRequestWork(this.permission)

        )
    }

    private fun longToDate(date: Long): Date = Date(date)

    private fun dateToCalendar(date: Date): Calendar {
        val calendar = Calendar.getInstance()
        calendar.time =date
        return calendar
    }

    private fun toPermissionRequestWork(permission: String): PermissionRequestWork {
        return when (permission) {
            "Диспетчер" -> PermissionRequestWork.DISPATCHER
            "Гл. инженер" -> PermissionRequestWork.CHIEF_ENGINEER
            else -> PermissionRequestWork.OTHER
        }
    }
}
