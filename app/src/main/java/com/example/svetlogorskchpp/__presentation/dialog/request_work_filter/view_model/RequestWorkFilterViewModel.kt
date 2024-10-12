package com.example.svetlogorskchpp.__presentation.dialog.request_work_filter.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.svetlogorskchpp.__domain.en.RequestWorkFilter
import com.example.svetlogorskchpp.__domain.interactor.shift_schedule.note_list.ShiftScheduleNoteListInteractor
import com.example.svetlogorskchpp.__presentation.dialog.request_work_filter.model.RequestWorkFilterUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RequestWorkFilterViewModel @Inject constructor(
    private val shiftScheduleNoteListInteractor: ShiftScheduleNoteListInteractor,
) : ViewModel() {

    private val _requestWorkFilterStateUI = MutableStateFlow(RequestWorkFilterUI())
    val requestWorkFilterStateUI: StateFlow<RequestWorkFilterUI> = _requestWorkFilterStateUI

    private val filterFlagState = shiftScheduleNoteListInteractor.getFilterFlag()
        .map { it.toList() }
        .stateIn(
            scope = CoroutineScope(Dispatchers.Default),
            started = SharingStarted.Lazily,
            initialValue = emptyList()
        )

    init {
        viewModelScope.launch {
            filterFlagState.collect { flagsFilter ->
                if (flagsFilter.isNotEmpty() && (RequestWorkFilter.ALL !in flagsFilter)) {
                    _requestWorkFilterStateUI.update {
                        RequestWorkFilterUI(
                            isDispatcher = RequestWorkFilter.DISPATCHER in flagsFilter,
                            isChiefEngineer = RequestWorkFilter.CHIEF_ENGINEER in flagsFilter,
                            isOther = RequestWorkFilter.OTHER in flagsFilter,
                        )
                    }
                } else if (RequestWorkFilter.ALL in flagsFilter) {
                    _requestWorkFilterStateUI.update {
                        RequestWorkFilterUI(
                            isDispatcher = true,
                            isChiefEngineer = true,
                            isOther = true
                        )
                    }
                }
            }
        }
    }

    fun selectFilter(requestWorkFilter: RequestWorkFilter) {
        _requestWorkFilterStateUI.update { old ->
            when (requestWorkFilter) {
                RequestWorkFilter.OTHER -> old.copy(isOther = !old.isOther)
                RequestWorkFilter.DISPATCHER -> old.copy(isDispatcher = !old.isDispatcher)
                RequestWorkFilter.CHIEF_ENGINEER -> old.copy(isChiefEngineer = !old.isChiefEngineer)
                RequestWorkFilter.ALL ->
                    old.copy(
                        isChiefEngineer = true,
                        isDispatcher = true,
                        isOther = true
                    )
                }
            }
    }


    suspend fun saveFilterFlag() {
        val requestWorkFilterStateUI = requestWorkFilterStateUI.value
        val flagsFilter = mutableListOf<RequestWorkFilter>().apply {
            if (requestWorkFilterStateUI.isOther && requestWorkFilterStateUI.isDispatcher && requestWorkFilterStateUI.isChiefEngineer) add(
                RequestWorkFilter.ALL
            )
            else {
                if (requestWorkFilterStateUI.isOther)  add(RequestWorkFilter.OTHER)
                if (requestWorkFilterStateUI.isDispatcher) add(RequestWorkFilter.DISPATCHER)
                if (requestWorkFilterStateUI.isChiefEngineer) add(RequestWorkFilter.CHIEF_ENGINEER)
            }
        }


            shiftScheduleNoteListInteractor.setFilterFlag(flagsFilter)

    }
}