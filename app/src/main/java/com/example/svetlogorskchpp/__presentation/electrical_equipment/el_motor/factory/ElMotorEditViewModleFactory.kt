package com.example.svetlogorskchpp.__presentation.electrical_equipment.el_motor.factory

import com.example.svetlogorskchpp.__presentation.electrical_equipment.el_motor.edit.view_model.ElMotorEditViewModel
import com.example.svetlogorskchpp.__presentation.electrical_equipment.switchgear.edit.view_model.SwitchgearOwnNeedsEditViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory

@AssistedFactory
interface ElMotorEditViewModleFactory {
        fun create(@Assisted id: String): ElMotorEditViewModel
}