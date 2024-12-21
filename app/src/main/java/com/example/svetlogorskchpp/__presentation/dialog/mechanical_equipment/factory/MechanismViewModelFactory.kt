package com.example.svetlogorskchpp.__presentation.dialog.mechanical_equipment.factory

import com.example.svetlogorskchpp.__presentation.dialog.mechanical_equipment.mechanism_info.view_model.MechanismInfoViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory

@AssistedFactory
interface MechanismViewModelFactory {
    fun onCreateDialogMechanismInfo(@Assisted id: String): MechanismInfoViewModel
}