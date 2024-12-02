package com.example.svetlogorskchpp.__presentation.electrical_equipment.factory

import com.example.svetlogorskchpp.__presentation.electrical_equipment.lighting_and_other.edit.view_model.LightingAndOtherEditViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory

@AssistedFactory
interface LightingAndOtherEditViewModelFactory {
    fun create (@Assisted id: String): LightingAndOtherEditViewModel
}