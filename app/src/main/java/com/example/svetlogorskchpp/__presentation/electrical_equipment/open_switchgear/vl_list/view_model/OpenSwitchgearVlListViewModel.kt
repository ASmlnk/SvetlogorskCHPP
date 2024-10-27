package com.example.svetlogorskchpp.__presentation.electrical_equipment.open_switchgear.vl_list.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.svetlogorskchpp.__domain.usecases.electrical_equipment.electircal_equipments.ElectricalEquipmentsUseCases
import com.example.svetlogorskchpp.__presentation.electrical_equipment.model.ElectricalEquipment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class OpenSwitchgearVlListViewModel @Inject constructor(
    private val useCases: ElectricalEquipmentsUseCases<ElectricalEquipment.Vl>
): ViewModel() {

    val electricalEquipmentStateFlow = useCases.getElectricalEquipments().stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        emptyList()
    )

}