package com.example.svetlogorskchpp.__domain.usecases.shift_schedule

import com.example.svetlogorskchpp.__domain.en.shift_schedule.RequestWorkSorted
import com.example.svetlogorskchpp.__domain.model.shift_schedule.Note
import javax.inject.Inject

class RequestWorkSortedUseCases @Inject constructor() {

    fun applySorted(requestWorks: List<Note.NoteRequestWork>, flag: RequestWorkSorted) : List<Note.NoteRequestWork> {
        return when(flag) {
            RequestWorkSorted.DATE_OPEN -> requestWorks.sortedByDescending { it.dateOpen.timeInMillis }
            RequestWorkSorted.DATE_CLOSE -> requestWorks.sortedByDescending { it.dateClose.timeInMillis }
            RequestWorkSorted.NUMBER -> requestWorks.sortedByDescending { numberStringToInt(it.numberRequestWork) }
        }
    }

    private fun numberStringToInt(number: String) :Int {
        val digits = number.filter { it.isDigit() }
        val numberInt: Int = if(digits.isNotEmpty()) {
            digits.toInt()
        } else {
            null
        } ?: 0
        return numberInt
    }
}