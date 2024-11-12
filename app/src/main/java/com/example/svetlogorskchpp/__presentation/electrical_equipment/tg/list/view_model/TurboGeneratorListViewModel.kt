package com.example.svetlogorskchpp.__presentation.electrical_equipment.tg.list.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.svetlogorskchpp.__domain.usecases.equipments.EquipmentsListUseCases
import com.example.svetlogorskchpp.__presentation.electrical_equipment.model.ElectricalEquipment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class TurboGeneratorListViewModel @Inject constructor(
    private val useCases: EquipmentsListUseCases<ElectricalEquipment.Tg>
): ViewModel() {

    val dataFlow = useCases.getElectricalEquipments().stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        emptyList()
    )

}