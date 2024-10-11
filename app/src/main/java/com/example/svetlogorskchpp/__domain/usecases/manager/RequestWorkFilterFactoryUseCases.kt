package com.example.svetlogorskchpp.__domain.usecases.manager

import com.example.svetlogorskchpp.__domain.en.PermissionRequestWork
import com.example.svetlogorskchpp.__domain.en.RequestWorkFilter
import com.example.svetlogorskchpp.__domain.model.Note
import javax.inject.Inject

class RequestWorkFilterFactoryUseCases @Inject constructor() {

    fun getFilters(flags: List<RequestWorkFilter>): List<RequestFilter> {
        val filters = mutableListOf<RequestFilter>()
        if (RequestWorkFilter.DISPATCHER in flags) filters.add(RequestFilter.DispatcherFilter)
        if (RequestWorkFilter.CHIEF_ENGINEER in flags) filters.add(RequestFilter.ChiefEngineerFilter)
        if (RequestWorkFilter.OTHER in flags) filters.add(RequestFilter.OtherFilter)
        if (RequestWorkFilter.ALL in flags) filters.clear()
        return filters
    }
}

sealed class RequestFilter {
    abstract fun filter(requests: List<Note.NoteRequestWork>): List<Note.NoteRequestWork>

    data object DispatcherFilter : RequestFilter() {
        override fun filter(requests: List<Note.NoteRequestWork>): List<Note.NoteRequestWork> {
            return requests.filter { it.permission == PermissionRequestWork.DISPATCHER }
        }
    }

    data object ChiefEngineerFilter : RequestFilter() {
        override fun filter(requests: List<Note.NoteRequestWork>): List<Note.NoteRequestWork> {
            return requests.filter { it.permission == PermissionRequestWork.CHIEF_ENGINEER }
        }
    }

    data object OtherFilter : RequestFilter() {
        override fun filter(requests: List<Note.NoteRequestWork>): List<Note.NoteRequestWork> {
            return requests.filter { it.permission == PermissionRequestWork.OTHER }
        }
    }
}