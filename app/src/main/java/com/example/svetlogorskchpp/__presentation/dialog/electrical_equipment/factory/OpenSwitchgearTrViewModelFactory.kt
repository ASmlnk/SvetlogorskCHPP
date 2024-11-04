package com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.factory

import com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.open_switchgear_tr.view_model.OpenSwitchgearTrViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory

@AssistedFactory
interface OpenSwitchgearTrViewModelFactory  {
    fun create(@Assisted idTr: String): OpenSwitchgearTrViewModel
}