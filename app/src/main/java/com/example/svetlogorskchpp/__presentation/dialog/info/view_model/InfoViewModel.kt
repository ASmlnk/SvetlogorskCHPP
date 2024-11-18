package com.example.svetlogorskchpp.__presentation.dialog.info.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.svetlogorskchpp.__domain.en.HardData
import com.example.svetlogorskchpp.__domain.usecases.hardData.HardDataUseCases
import com.example.svetlogorskchpp.__presentation.dialog.info.factory.InfoViewModelFactory
import com.example.svetlogorskchpp.__presentation.shift_schedule.shift_schedule_requests_work.factory.ShiftScheduleRequestWorkViewModelFactory
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class InfoViewModel @AssistedInject constructor(
    private val useCases: HardDataUseCases<String>,
    @Assisted private val info: HardData
): ViewModel() {

    private val _contentState: MutableStateFlow<List<String>> = MutableStateFlow(contentInfo())
    val contentState: StateFlow<List<String>> = _contentState

    fun contentInfo(): List<String> {
        return useCases.data(info)
    }

    companion object {
        fun providesFactory(
            assistedFactory: InfoViewModelFactory,
            info: HardData,
        ): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return assistedFactory.create(info) as T
                }
            }
        }
    }
}