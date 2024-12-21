package com.example.svetlogorskchpp.__presentation.home_page.view_model

import androidx.lifecycle.ViewModel
import com.example.svetlogorskchpp.__domain.usecases.update_locale_base.UpdateLocaleBaseUseCases
import com.example.svetlogorskchpp.__presentation.electrical_equipment.model.ElMotorChapter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomePageViewModel @Inject constructor(
    private val updateLocaleBaseUseCases: UpdateLocaleBaseUseCases,
) : ViewModel() {

    private val _uiState = MutableStateFlow(false)
    val uiState: StateFlow<Boolean> = _uiState

    suspend fun updateLocaleBase() {
        _uiState.update { true }
        updateLocaleBaseUseCases.apply {
            updateOpenSwitchgearVl()
            updateOpenSwitchgearTr()
            updateTsn()
            updateTg()
            updateSwitchgear()
            updateElMotor()
            updateLightingAndOther()
            updateMechanismInfo()
        }
        _uiState.update { false }
    }

    fun elMotorChapter(): List<ElMotorChapter> {
        return listOf(
            ElMotorChapter.REP,
            ElMotorChapter.RG,
            ElMotorChapter.KA,
            ElMotorChapter.KTC_TO,
            ElMotorChapter.KTC_KO,
            ElMotorChapter.TY,
            ElMotorChapter.HOV,
            ElMotorChapter.EC,
            ElMotorChapter.OTHER,
        )
    }
}