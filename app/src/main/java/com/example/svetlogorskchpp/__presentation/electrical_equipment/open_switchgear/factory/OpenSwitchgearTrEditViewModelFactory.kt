package com.example.svetlogorskchpp.__presentation.electrical_equipment.open_switchgear.factory

import com.example.svetlogorskchpp.__presentation.electrical_equipment.open_switchgear.edit_tr.view_model.OpenSwitchgearTrEditViewModel
import com.example.svetlogorskchpp.__presentation.electrical_equipment.open_switchgear.edit_vl.view_model.OpenSwitchgearVlEditViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory

@AssistedFactory
interface OpenSwitchgearTrEditViewModelFactory {
    fun create(@Assisted idTr: String): OpenSwitchgearTrEditViewModel
}