package com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.factory

import com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.el_motor.view_model.ElMotorViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory

@AssistedFactory
interface ElMotorViewModelFactory {
    fun create(@Assisted id: String): ElMotorViewModel
}