package com.example.svetlogorskchpp.__data.mapper

import com.example.svetlogorskchpp.__data.model.NoteRequestWorkEntity
import com.example.svetlogorskchpp.__domain.model.Note
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

class NoteRequestWorkDomainToEntityMapper @Inject constructor() {

    fun map(noteRequestWork: Note.NoteRequestWork): NoteRequestWorkEntity {
        return with(noteRequestWork) {
            NoteRequestWorkEntity(
                id = id,
                tagDateClose = tagDateClose.time,
                tagDateOpen = tagDateOpen.time,
                numberRequestWork = numberRequestWork,
                dateOpen = dateOpen.time.time,
                dateClose = dateClose.time.time,
                accession = accession,
                reason = reason,
                additionally = additionally,
                isExtend = isExtend,
                contentExtend = contentExtend
            )
        }
    }
}
