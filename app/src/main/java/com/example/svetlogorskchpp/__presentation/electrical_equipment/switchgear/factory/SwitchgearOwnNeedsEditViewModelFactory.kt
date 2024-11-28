package com.example.svetlogorskchpp.__presentation.electrical_equipment.switchgear.factory

import com.example.svetlogorskchpp.__presentation.electrical_equipment.switchgear.edit.view_model.SwitchgearOwnNeedsEditViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory

@AssistedFactory
interface SwitchgearOwnNeedsEditViewModelFactory {
    fun create(@Assisted id: String): SwitchgearOwnNeedsEditViewModel
}