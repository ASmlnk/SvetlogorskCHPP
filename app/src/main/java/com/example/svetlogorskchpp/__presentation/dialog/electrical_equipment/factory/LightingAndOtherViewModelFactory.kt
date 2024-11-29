package com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.factory

import com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.lighting_and_other.view_model.LightingAndOtherViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory

@AssistedFactory
interface LightingAndOtherViewModelFactory {
    fun create(@Assisted id: String): LightingAndOtherViewModel
}