package com.example.svetlogorskchpp.__presentation.electrical_equipment.switchgear.list.view_model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.ElAssembly
import com.example.svetlogorskchpp.R
import com.example.svetlogorskchpp.__domain.usecases.equipments.EquipmentsListUseCases
import com.example.svetlogorskchpp.__presentation.electrical_equipment.model.ElectricalEquipment
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SwitchgearOwnNeedsListViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val useCases: EquipmentsListUseCases<ElectricalEquipment.Switchgear>,
) : ViewModel() {

    private val _dataFlow = MutableStateFlow<List<ElectricalEquipment.Switchgear>>(emptyList())
    val dataFlow: StateFlow<List<ElectricalEquipment.Switchgear>> = _dataFlow

    init {
        viewModelScope.launch {
            dataUseCases.collect {
                _dataFlow.value = it
            }
        }
    }

    private val dataUseCases = useCases.getElectricalEquipments().stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        emptyList()
    )

    fun filterCategory(activeFilters: List<ElAssembly>) {

        if (activeFilters.isEmpty()) {
            _dataFlow.value = dataUseCases.value
        } else {
            _dataFlow.value = dataUseCases.value.filter { item ->
                activeFilters.any { filter ->
                    item.category == filter
                }
            }
        }
    }
}