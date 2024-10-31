package com.example.svetlogorskchpp.__presentation.home_page.view_model

import androidx.lifecycle.ViewModel
import com.example.svetlogorskchpp.__domain.usecases.update_locale_base.UpdateLocaleBaseUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomePageViewModel @Inject constructor(
    private val updateLocaleBaseUseCases: UpdateLocaleBaseUseCases
): ViewModel() {

    private val _uiState = MutableStateFlow(false)
    val uiState: StateFlow<Boolean> = _uiState


    suspend fun updateLocaleBase() {
        _uiState.update { true }
        updateLocaleBaseUseCases.updateOpenSwitchgearVl()
        _uiState.update { false }
    }
}