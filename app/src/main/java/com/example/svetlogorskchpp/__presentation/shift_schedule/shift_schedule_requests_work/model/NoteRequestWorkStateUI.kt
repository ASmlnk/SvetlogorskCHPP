package com.example.svetlogorskchpp.__presentation.shift_schedule.shift_schedule_requests_work.model

import com.example.svetlogorskchpp.__domain.en.shift_schedule.PermissionRequestWork
import java.util.Calendar

data class NoteRequestWorkStateUI(
    val noteRequestWorkUI: NoteRequestWorkUI,
    val isExtend: Boolean = false,
    val calendarInstance: Calendar,
    val textDateOpen: String? = null,
    val textDateClose: String? = null,
    val textDateOpenExtend: String? = null,
    val textDateCloseExtend: String? = null,
    val toastText: Toast? = null,
    val resetRequestWorkUI: ResetRequestWorkUI? = null,
    val chipInit: PermissionRequestWork? = null
) {
}