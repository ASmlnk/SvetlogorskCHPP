package com.example.svetlogorskchpp.__presentation.shift_schedule_requests_work.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.svetlogorskchpp.__domain.OperationResult
import com.example.svetlogorskchpp.__domain.SuccessResult
import com.example.svetlogorskchpp.__domain.en.PermissionRequestWork
import com.example.svetlogorskchpp.__domain.usecases.calendarDateUseCases.CalendarDateUseCases
import com.example.svetlogorskchpp.__domain.usecases.calendarNote.CalendarNoteUseCases
import com.example.svetlogorskchpp.__presentation.shift_schedule_requests_work.factory.ShiftScheduleRequestWorkViewModelFactory
import com.example.svetlogorskchpp.__presentation.shift_schedule_requests_work.mapper.NoteRequestWorkUiToDomainMapper
import com.example.svetlogorskchpp.__presentation.shift_schedule_requests_work.model.DateTimeUI
import com.example.svetlogorskchpp.__presentation.shift_schedule_requests_work.model.ExtendRequestWorkUI
import com.example.svetlogorskchpp.__presentation.shift_schedule_requests_work.model.NoteRequestWorkStateUI
import com.example.svetlogorskchpp.__presentation.shift_schedule_requests_work.model.NoteRequestWorkUI
import com.example.svetlogorskchpp.__presentation.shift_schedule_requests_work.model.ResetRequestWorkUI
import com.example.svetlogorskchpp.__presentation.shift_schedule_requests_work.model.Toast
import com.google.gson.Gson
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar

class ShiftScheduleRequestWorkViewModel @AssistedInject constructor(
    private val noteRequestWorkUiToDomainMapper: NoteRequestWorkUiToDomainMapper,
    private val calendarNoteUseCases: CalendarNoteUseCases,
    private val calendarDateUseCases: CalendarDateUseCases,
    @Assisted private val noteRequestWork: String,
) : ViewModel() {

    private val calendar = Calendar.getInstance()

    private val _noteRequestWorkExtendStateUI: MutableStateFlow<ExtendRequestWorkUI> = MutableStateFlow(
        ExtendRequestWorkUI()
    )

    private val _noteRequestWorkStateUi: MutableStateFlow<NoteRequestWorkStateUI> =
        MutableStateFlow(
            NoteRequestWorkStateUI(
                noteRequestWorkUI = initNoteRequestWork(noteRequestWork),
                calendarInstance = calendar
            )
        )

    val noteRequestWorkStateUI: StateFlow<NoteRequestWorkStateUI> = _noteRequestWorkStateUi

    fun insertNoteRequestWork(
        textAccession: String,
        textReason: String,
        textAdditionally: String,
        textNumber: String,
    ) {
        val noteRequestWorkUI = _noteRequestWorkStateUi.value.noteRequestWorkUI.copy(
            numberRequestWork = textNumber,
            accession = textAccession,
            reason = textReason,
            additionally = textAdditionally
        )
        val toastCheckFilling: Toast? = toastCheckFillingUI(noteRequestWorkUI)

        if (toastCheckFilling == null) {
            viewModelScope.launch {
                val result = calendarNoteUseCases.insertNote(
                    noteRequestWorkUiToDomainMapper.map(noteRequestWorkUI)
                )
                operationResult(result)
            }
        } else {
            viewModelScope.launch {
                updateToast(toastCheckFilling)
                delay(100)
                updateToast(null)
            }
        }
    }

    fun isExtendView (isExtend: Boolean) {
        resetExtend()
        _noteRequestWorkStateUi.update { oldState ->
            oldState.copy(isExtend = isExtend)
        }
    }

    fun chipPermission(permissionRequestWork: PermissionRequestWork) {
        val noteRequestWorkUI = _noteRequestWorkStateUi.value.noteRequestWorkUI.copy(
            permission = permissionRequestWork
        )

        _noteRequestWorkStateUi.update { oldState ->
            oldState.copy(noteRequestWorkUI = noteRequestWorkUI)
        }
    }

    private suspend fun resetUI() {
        _noteRequestWorkStateUi.update { oldState ->
            oldState.copy(
                resetRequestWorkUI = ResetRequestWorkUI.RESET,
                noteRequestWorkUI = NoteRequestWorkUI(),
                textDateOpen = null,
                textDateClose = null
            )
        }
        delay(100)
        _noteRequestWorkStateUi.update { oldState ->
            oldState.copy( resetRequestWorkUI = null)
        }
    }

    fun resetExtend() {
        _noteRequestWorkExtendStateUI.update { ExtendRequestWorkUI() }
        _noteRequestWorkStateUi.update { oldState ->
            oldState.copy(
                textDateOpenExtend = null,
                textDateCloseExtend = null
            )
        }
    }

    private fun toastCheckFillingUI(noteRequestWorkUI: NoteRequestWorkUI): Toast? {

        return if (noteRequestWorkUI.numberRequestWork.isEmpty()) {
            Toast.NUMBER
        } else if (noteRequestWorkUI.dateOpen == null || noteRequestWorkUI.dateClose == null) {
            Toast.DATE
        } else if (noteRequestWorkUI.accession.isEmpty()) {
            Toast.ACCESSION
        } else if (noteRequestWorkUI.reason.isEmpty()) {
            Toast.REASON
        } else {
            null
        }
    }

    fun dateOpen(calendarUI: Calendar, dateTimeUI: DateTimeUI) {

        when (dateTimeUI) {
            DateTimeUI.OPEN_TIME -> {
                val noteRequestWorkUI = _noteRequestWorkStateUi.value.noteRequestWorkUI.copy(
                    tagDateOpen = calendarDateUseCases.calendarToDateYMD(calendarUI),
                    tagMonthOpen = calendarDateUseCases.calendarToDateYM(calendarUI),
                    dateOpen = calendarUI
                )
                _noteRequestWorkStateUi.update { oldState ->
                    oldState.copy(
                        noteRequestWorkUI = noteRequestWorkUI,
                        textDateOpen = calendarDateUseCases.calendarToStringFormatDDMMMMYYYYHHmm(
                            calendarUI
                        )
                    )
                }
            }

            DateTimeUI.CLOSE_TIME -> {
                val noteRequestWorkUI = _noteRequestWorkStateUi.value.noteRequestWorkUI.copy(
                    tagDateClose = calendarDateUseCases.calendarToDateYMD(calendarUI),
                    tagMonthClose = calendarDateUseCases.calendarToDateYM(calendarUI),
                    dateClose = calendarUI
                )
                _noteRequestWorkStateUi.update { oldState ->
                    oldState.copy(
                        noteRequestWorkUI = noteRequestWorkUI,
                        textDateClose = calendarDateUseCases.calendarToStringFormatDDMMMMYYYYHHmm(
                            calendarUI
                        )
                    )
                }
            }
            DateTimeUI.OPEN_EXTEND_TIME -> {
                val extendRequestWorkUI = _noteRequestWorkExtendStateUI.value.copy(
                    tagDateOpen = calendarDateUseCases.calendarToDateYMD(calendarUI),
                    tagMonthOpen = calendarDateUseCases.calendarToDateYM(calendarUI),
                    dateOpen = calendarUI
                )
                _noteRequestWorkExtendStateUI.update { extendRequestWorkUI }
                _noteRequestWorkStateUi.update { oldState ->
                    oldState.copy(textDateOpenExtend = calendarDateUseCases.calendarToStringFormatDDMMMMYYYYHHmm(
                        calendarUI
                    ))
                }
            }
            DateTimeUI.CLOSE_EXTEND_TIME -> {
                val extendRequestWorkUI = _noteRequestWorkExtendStateUI.value.copy(
                    tagDateClose = calendarDateUseCases.calendarToDateYMD(calendarUI),
                    tagMonthClose = calendarDateUseCases.calendarToDateYM(calendarUI),
                    dateClose = calendarUI
                )
                _noteRequestWorkExtendStateUI.update { extendRequestWorkUI }
                _noteRequestWorkStateUi.update { oldState ->
                    oldState.copy(textDateCloseExtend = calendarDateUseCases.calendarToStringFormatDDMMMMYYYYHHmm(
                        calendarUI
                    ))
                }
            }
        }
    }

    fun calendarInstanceUI(): Calendar {
        return _noteRequestWorkStateUi.value.calendarInstance
    }

    private fun initNoteRequestWork(noteRequestWork: String): NoteRequestWorkUI {
        return if (noteRequestWork.isNotEmpty()) {
            Gson().fromJson(noteRequestWork, NoteRequestWorkUI::class.java)
        } else {
            NoteRequestWorkUI()
        }
    }

    private suspend fun operationResult(
        operationResult: OperationResult<SuccessResult>,
    ) {

        when (operationResult) {
            is OperationResult.Success<SuccessResult> -> {
                when (operationResult.data) {
                    SuccessResult.INSERT_REQUEST_WORK -> {
                        resetUI()
                        delay(200)
                        updateToast(Toast.INSERT_REQUEST_WORK)
                    }
                    SuccessResult.INSERT_NOTE -> updateToast(Toast.INSERT_NOTE)
                    SuccessResult.DELETE_REQUEST_WORK -> {
                        resetUI()
                        delay(200)
                        updateToast(Toast.DELETE_REQUEST_WORK)
                    }
                }
            }

            is OperationResult.Error -> {
                updateToast(Toast.ERROR_REQUEST_WORK)
            }
        }
        delay(100)
        updateToast(null)
    }

    private fun updateToast(toast: Toast?) {
        _noteRequestWorkStateUi.update { oldState ->
            oldState.copy(
                toastText = toast
            )
        }
    }

    @Suppress("UNCHECKED_CAST")
    companion object {
        fun providesFactory(
            assistedFactory: ShiftScheduleRequestWorkViewModelFactory,
            noteRequestWork: String,
        ): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return assistedFactory.create(noteRequestWork) as T
                }
            }
        }
    }
}