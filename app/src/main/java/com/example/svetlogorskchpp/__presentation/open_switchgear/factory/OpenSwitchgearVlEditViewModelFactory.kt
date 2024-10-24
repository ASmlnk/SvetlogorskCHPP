package com.example.svetlogorskchpp.__presentation.open_switchgear.factory

import com.example.svetlogorskchpp.__presentation.open_switchgear.edit_vl.view_model.OpenSwitchgearVlEditViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory

@AssistedFactory
interface OpenSwitchgearVlEditViewModelFactory {
    fun create(@Assisted idVl: String):OpenSwitchgearVlEditViewModel
}