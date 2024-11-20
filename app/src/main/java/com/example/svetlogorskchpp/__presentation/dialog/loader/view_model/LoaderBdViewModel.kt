package com.example.svetlogorskchpp.__presentation.dialog.loader.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.svetlogorskchpp.__domain.usecases.equipments.update_fb.UpdateFirebaseUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoaderBdViewModel @Inject constructor(
    private val updateFirebaseUseCases: UpdateFirebaseUseCases,
) : ViewModel() {

    fun loaderElMotorInFb() {
        viewModelScope.launch(Dispatchers.IO) {
            updateFirebaseUseCases.loaderElMotorInFb()
        }
    }

    fun loaderSwitchgearInFb() {
        viewModelScope.launch(Dispatchers.IO) {
            updateFirebaseUseCases.loaderSwitchgearInFb()
        }
    }

    fun loaderLightingAndOtherInFb() {
        viewModelScope.launch(Dispatchers.IO) {
            updateFirebaseUseCases.loaderLightingAndOtherInFb()
        }
    }
}