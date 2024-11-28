package com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.factory

import com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.switchear.view_model.SwitchgearOwnNeedsInfoViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory

@AssistedFactory
interface SwitchgearOwnNeedsInfoViewModelFactory {
    fun create (@Assisted id: String): SwitchgearOwnNeedsInfoViewModel
}