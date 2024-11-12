package com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.factory

import com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.tg.view_model.TurbogeneratorViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory

@AssistedFactory
interface TurbogeneratorViewModelFactory {
    fun create(@Assisted id: String): TurbogeneratorViewModel
}