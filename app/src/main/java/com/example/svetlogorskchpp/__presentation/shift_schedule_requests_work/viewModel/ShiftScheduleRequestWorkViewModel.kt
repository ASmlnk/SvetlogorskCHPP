package com.example.svetlogorskchpp.__presentation.shift_schedule_requests_work.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.svetlogorskchpp.__domain.usecases.calendarDateUseCases.CalendarDateUseCases
import com.example.svetlogorskchpp.__domain.usecases.calendarNote.CalendarNoteUseCases
import com.example.svetlogorskchpp.__presentation.shift_schedule_requests_work.factory.ShiftScheduleRequestWorkViewModelFactory
import com.example.svetlogorskchpp.__presentation.shift_schedule_requests_work.mapper.NoteRequestWorkUiToDomainMapper
import com.example.svetlogorskchpp.__presentation.shift_schedule_requests_work.model.DateTimeUI
import com.example.svetlogorskchpp.__presentation.shift_schedule_requests_work.model.NoteRequestWorkStateUI
import com.example.svetlogorskchpp.__presentation.shift_schedule_requests_work.model.NoteRequestWorkUI
import com.example.svetlogorskchpp.__presentation.shift_schedule_requests_work.model.ToastRequestWork
import com.google.gson.Gson
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
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

    private val _noteRequestWorkStateUi: MutableStateFlow<NoteRequestWorkStateUI> =
        MutableStateFlow(
            NoteRequestWorkStateUI(
                noteRequestWorkUI = initNoteRequestWork(noteRequestWork),
                calendarInstance = calendar
            )
        )

    init {
        viewModelScope.launch {
            calendarNoteUseCases.noteRequestWorkFlow.collect {

            }
        }

    }
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
        val toastCheckFilling: ToastRequestWork? = toastCheckFillingUI(noteRequestWorkUI)

        if (toastCheckFilling == null) {
            viewModelScope.launch {
                calendarNoteUseCases.insertNote(noteRequestWorkUiToDomainMapper.map(noteRequestWorkUI))
            }
        } else {
            viewModelScope.launch {
                _noteRequestWorkStateUi.update { oldState ->
                    oldState.copy(
                        toastText = toastCheckFilling
                    )
                }
                delay(1000)
                _noteRequestWorkStateUi.update { oldState ->
                    oldState.copy(
                        toastText = null
                    )
                }
            }
        }
    }

    fun toastCheckFillingUI(noteRequestWorkUI: NoteRequestWorkUI): ToastRequestWork? {

        return if (noteRequestWorkUI.numberRequestWork.isEmpty()) {
            ToastRequestWork.NUMBER
        } else if (noteRequestWorkUI.dateOpen == null || noteRequestWorkUI.dateClose == null) {
            ToastRequestWork.DATE
        } else if (noteRequestWorkUI.accession.isEmpty()) {
            ToastRequestWork.ACCESSION
        } else if (noteRequestWorkUI.reason.isEmpty()) {
            ToastRequestWork.REASON
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