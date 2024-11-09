package com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.factory

import com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.tsn.view_model.EquipmentTsnViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory

@AssistedFactory
interface EquipmentTsnViewModelFactory  {
    fun create(@Assisted id: String): EquipmentTsnViewModel
}