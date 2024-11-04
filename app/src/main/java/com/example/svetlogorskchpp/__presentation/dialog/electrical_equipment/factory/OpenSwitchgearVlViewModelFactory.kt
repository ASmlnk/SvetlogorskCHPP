package com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.factory

import com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.open_switchgear_vl.view_model.OpenSwitchgearVlViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory

@AssistedFactory
interface OpenSwitchgearVlViewModelFactory {
    fun create(@Assisted idVl: String): OpenSwitchgearVlViewModel
}