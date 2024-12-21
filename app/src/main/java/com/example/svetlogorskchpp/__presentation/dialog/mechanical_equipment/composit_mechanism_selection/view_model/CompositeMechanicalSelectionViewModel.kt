package com.example.svetlogorskchpp.__presentation.dialog.mechanical_equipment.composit_mechanism_selection.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.ElGeneralCategory
import com.example.svetlogorskchpp.__domain.usecases.equipments.all_equipment.mechanical.EquipmentMechanismAllUseCases
import com.example.svetlogorskchpp.__presentation.electrical_equipment.model.ElectricalEquipment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.forEach

@HiltViewModel
class CompositeMechanicalSelectionViewModel @Inject constructor(
    private val useCases: EquipmentMechanismAllUseCases
): ViewModel() {

    private val _dataState = MutableStateFlow<List<ElectricalEquipment>>(emptyList())
    val dataState: StateFlow<List<ElectricalEquipment>> = _dataState

    val equipments = useCases.getAllCompositeMechanismFlow().stateIn(
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

    fun filterData(filters: List<ElGeneralCategory>) {
        if (filters.isEmpty()) {
            _dataState.value = equipments.value
        } else {
            val filterList = mutableListOf<ElectricalEquipment>()
            val data = equipments.value
            filters.forEach { filter ->
                when (filter) {
                    ElGeneralCategory.OTHER -> {
                        filterList.addAll(data.filter { it is ElectricalEquipment.MechanismInfo }
                            .filter { (it as ElectricalEquipment.MechanismInfo).category ==
                                ElGeneralCategory.OTHER })
                    }
                    ElGeneralCategory.HOV -> {
                        filterList.addAll(data.filter { it is ElectricalEquipment.MechanismInfo }
                            .filter { (it as ElectricalEquipment.MechanismInfo).category ==
                                ElGeneralCategory.HOV })
                    }
                    ElGeneralCategory.KTC_TO -> {
                        filterList.addAll(data.filter { it is ElectricalEquipment.MechanismInfo }
                            .filter { (it as ElectricalEquipment.MechanismInfo).category ==
                                ElGeneralCategory.KTC_TO })
                    }
                    ElGeneralCategory.KTC_KO -> {
                        filterList.addAll(data.filter { it is ElectricalEquipment.MechanismInfo }
                            .filter { (it as ElectricalEquipment.MechanismInfo).category ==
                                ElGeneralCategory.KTC_KO })
                    }
                    ElGeneralCategory.TY -> {
                        filterList.addAll(data.filter { it is ElectricalEquipment.MechanismInfo }
                            .filter { (it as ElectricalEquipment.MechanismInfo).category ==
                                ElGeneralCategory.TY })
                    }
                    ElGeneralCategory.EC -> {
                        filterList.addAll(data.filter { it is ElectricalEquipment.MechanismInfo }
                            .filter { (it as ElectricalEquipment.MechanismInfo).category ==
                                ElGeneralCategory.EC })
                    }
                    ElGeneralCategory.KA -> {
                        filterList.addAll(data.filter { it is ElectricalEquipment.MechanismInfo }
                            .filter { (it as ElectricalEquipment.MechanismInfo).category ==
                                ElGeneralCategory.KA })
                    }
                    ElGeneralCategory.RG -> {
                        filterList.addAll(data.filter { it is ElectricalEquipment.MechanismInfo }
                            .filter { (it as ElectricalEquipment.MechanismInfo).category ==
                                ElGeneralCategory.RG })
                    }
                }
            }
            _dataState.update { filterList }
        }
    }
}