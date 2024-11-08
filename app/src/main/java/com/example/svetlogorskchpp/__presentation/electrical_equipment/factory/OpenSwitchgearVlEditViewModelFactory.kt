package com.example.svetlogorskchpp.__presentation.electrical_equipment.factory

import com.example.svetlogorskchpp.__presentation.electrical_equipment.open_switchgear.edit_vl.view_model.OpenSwitchgearVlEditViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory

@AssistedFactory
interface OpenSwitchgearVlEditViewModelFactory {
    fun create(@Assisted idVl: String): OpenSwitchgearVlEditViewModel
}