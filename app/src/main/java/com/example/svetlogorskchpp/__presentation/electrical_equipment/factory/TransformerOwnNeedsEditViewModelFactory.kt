package com.example.svetlogorskchpp.__presentation.electrical_equipment.factory

import com.example.svetlogorskchpp.__presentation.electrical_equipment.tsn.edit.view_model.TransformerOwnNeedsEditViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory

@AssistedFactory
interface TransformerOwnNeedsEditViewModelFactory {
    fun create(@Assisted id: String): TransformerOwnNeedsEditViewModel
}