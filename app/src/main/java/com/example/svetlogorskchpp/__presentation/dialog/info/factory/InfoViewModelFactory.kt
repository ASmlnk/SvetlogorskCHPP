package com.example.svetlogorskchpp.__presentation.dialog.info.factory

import com.example.svetlogorskchpp.__domain.en.HardData
import com.example.svetlogorskchpp.__presentation.dialog.info.view_model.InfoViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory

@AssistedFactory
interface InfoViewModelFactory {
    fun create(@Assisted info: HardData): InfoViewModel
}