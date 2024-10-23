package com.example.svetlogorskchpp.__domain.usecases.shift_schedule

import android.icu.util.Calendar
import com.example.svetlogorskchpp.__domain.en.shift_schedule.PermissionRequestWork
import com.example.svetlogorskchpp.__domain.en.shift_schedule.RequestWorkFilter
import com.example.svetlogorskchpp.__domain.model.shift_schedule.Note
import javax.inject.Inject

class RequestWorkFilterFactoryUseCases @Inject constructor() {

    fun getFilters(flags: List<RequestWorkFilter>): List<RequestFilter> {
        val filters = mutableListOf<RequestFilter>()
        if (RequestWorkFilter.DISPATCHER in flags) filters.add(RequestFilter.DispatcherFilter)
        if (RequestWorkFilter.CHIEF_ENGINEER in flags) filters.add(RequestFilter.ChiefEngineerFilter)
        if (RequestWorkFilter.OTHER in flags) filters.add(RequestFilter.OtherFilter)
        if (RequestWorkFilter.CLOSED in flags) filters.add(RequestFilter.ClosedFilter)
        if (RequestWorkFilter.ALL in flags) {
            filters.clear()
            filters.apply {
                add(RequestFilter.OtherFilter)
                add(RequestFilter.DispatcherFilter)
                add(RequestFilter.ChiefEngineerFilter)
                add(RequestFilter.ClosedFilter)
            }
        }
        return filters
    }
}

sealed class RequestFilter {
    abstract fun filter(requests: List<Note.NoteRequestWork>): List<Note.NoteRequestWork>

    data object DispatcherFilter : RequestFilter() {
        override fun filter(requests: List<Note.NoteRequestWork>): List<Note.NoteRequestWork> {
            return requests.filter { it.permission == PermissionRequestWork.DISPATCHER }.filterNot { Calendar.getInstance().timeInMillis > it.dateClose.timeInMillis }
        }
    }

    data object ChiefEngineerFilter : RequestFilter() {
        override fun filter(requests: List<Note.NoteRequestWork>): List<Note.NoteRequestWork> {
            return requests.filter { it.permission == PermissionRequestWork.CHIEF_ENGINEER }.filterNot { Calendar.getInstance().timeInMillis > it.dateClose.timeInMillis }
        }
    }

    data object OtherFilter : RequestFilter() {
        override fun filter(requests: List<Note.NoteRequestWork>): List<Note.NoteRequestWork> {
            return requests.filter { it.permission == PermissionRequestWork.OTHER }.filterNot { Calendar.getInstance().timeInMillis > it.dateClose.timeInMillis }
        }
    }

    data object ClosedFilter : RequestFilter() {
        override fun filter(requests: List<Note.NoteRequestWork>): List<Note.NoteRequestWork> {
            return requests.filter { Calendar.getInstance().timeInMillis > it.dateClose.timeInMillis }
        }
    }
}