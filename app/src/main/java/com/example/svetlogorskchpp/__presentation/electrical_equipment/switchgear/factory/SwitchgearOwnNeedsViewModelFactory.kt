package com.example.svetlogorskchpp.__presentation.electrical_equipment.switchgear.factory

import com.example.svetlogorskchpp.__presentation.electrical_equipment.switchgear.item.view_model.SwitchgearOwnNeedsViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory

@AssistedFactory
interface SwitchgearOwnNeedsViewModelFactory {
    fun create(@Assisted id: String): SwitchgearOwnNeedsViewModel
}