package com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.power_supply_selection.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.svetlogorskchpp.__domain.usecases.equipments.all_equipment.EquipmentAllUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class PowerSupplySelectionViewModel @Inject constructor(
    private val useCases: EquipmentAllUseCases,
): ViewModel() {

    val equipments = useCases.getEquipmentsFlow().stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        emptyList()
    )

}