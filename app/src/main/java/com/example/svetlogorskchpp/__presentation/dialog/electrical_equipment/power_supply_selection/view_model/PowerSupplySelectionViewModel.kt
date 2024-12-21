package com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.power_supply_selection.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.NameDepartment
import com.example.svetlogorskchpp.__domain.usecases.equipments.all_equipment.electrical.EquipmentAllUseCases
import com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.model.PSFilter
import com.example.svetlogorskchpp.__presentation.electrical_equipment.model.ElectricalEquipment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PowerSupplySelectionViewModel @Inject constructor(
    private val useCases: EquipmentAllUseCases,
) : ViewModel() {

    private val _dataState = MutableStateFlow<List<ElectricalEquipment>>(emptyList())
    val dataState: StateFlow<List<ElectricalEquipment>> = _dataState

    val equipments = useCases.getEquipmentsAllPowerSupplyFlow().stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        emptyList()
    )

    init {
        viewModelScope.launch {
            equipments.collect {
                _dataState.value = it
            }
        }
    }

    fun filterData(filters: List<PSFilter>) {
        if (filters.isEmpty()) {
            _dataState.value = equipments.value
        } else {
            val filterList = mutableListOf<ElectricalEquipment>()
            val data = equipments.value
            filters.forEach { psFilter ->
                when (psFilter) {
                    PSFilter.TR -> {
                        filterList.addAll(data.filter { it is ElectricalEquipment.Tr })
                        filterList.addAll(data.filter { it is ElectricalEquipment.Tsn })
                    }

                    PSFilter.S_04 -> {
                        filterList.addAll(data.filter { it is ElectricalEquipment.Switchgear }
                            .filter { (it as ElectricalEquipment.Switchgear).nameDepartment == NameDepartment.RY })
                    }

                    PSFilter.S_3_6 -> {
                        filterList.addAll(data.filter { it is ElectricalEquipment.Switchgear }
                            .filter { (it as ElectricalEquipment.Switchgear).nameDepartment == NameDepartment.KRY })
                    }

                    PSFilter.TO -> {
                        filterList.addAll(data.filter { it is ElectricalEquipment.Switchgear }
                            .filter { (it as ElectricalEquipment.Switchgear).nameDepartment == NameDepartment.KTC_TO })
                    }

                    PSFilter.KO -> {
                        filterList.addAll(data.filter { it is ElectricalEquipment.Switchgear }
                            .filter { (it as ElectricalEquipment.Switchgear).nameDepartment == NameDepartment.KTC_KO })
                    }

                    PSFilter.HC -> {
                        filterList.addAll(data.filter { it is ElectricalEquipment.Switchgear }
                            .filter { (it as ElectricalEquipment.Switchgear).nameDepartment == NameDepartment.HC })
                    }

                    PSFilter.OTHER -> {
                        filterList.addAll(data.filter { it is ElectricalEquipment.Switchgear }
                            .filter { (it as ElectricalEquipment.Switchgear).nameDepartment == NameDepartment.OTHER })
                        filterList.addAll(data.filter { it is ElectricalEquipment.Switchgear }
                            .filter { (it as ElectricalEquipment.Switchgear).nameDepartment == NameDepartment.BNS })
                        filterList.addAll(data.filter { it is ElectricalEquipment.Switchgear }
                            .filter { (it as ElectricalEquipment.Switchgear).nameDepartment == NameDepartment.COOLING_TOWER })
                    }

                    PSFilter.POST_TOK -> {
                        filterList.addAll(data.filter { it is ElectricalEquipment.Switchgear }
                            .filter { (it as ElectricalEquipment.Switchgear).nameDepartment == NameDepartment.POST_TOK })
                    }

                    PSFilter.SHIELD_BLOCK -> {
                        filterList.addAll(data.filter { it is ElectricalEquipment.Switchgear }
                            .filter { (it as ElectricalEquipment.Switchgear).nameDepartment == NameDepartment.SHIELD_BLOCK })
                    }

                    PSFilter.TY -> {
                        filterList.addAll(data.filter { it is ElectricalEquipment.Switchgear }
                            .filter { (it as ElectricalEquipment.Switchgear).nameDepartment == NameDepartment.KTC_TY })
                    }
                }
            }
            _dataState.update { filterList }

        }
    }

}