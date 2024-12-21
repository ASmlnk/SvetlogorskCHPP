package com.example.svetlogorskchpp.__presentation.equipment.mechanism_info.factory

import com.example.svetlogorskchpp.__presentation.equipment.mechanism_info.edit.view_model.MechanismInfoEditViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory

@AssistedFactory
interface MechanismEditViewModelFactory {
    fun createMechanismInfoEditVM(@Assisted id: String) : MechanismInfoEditViewModel
}