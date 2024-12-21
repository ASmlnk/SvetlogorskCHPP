package com.example.svetlogorskchpp.__presentation.equipment.mechanism_info.list.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.ElGeneralCategory
import com.example.svetlogorskchpp.__domain.usecases.equipments.EquipmentsListUseCases
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
class MechanismInfoListViewModel @Inject constructor(
    private val useCases: EquipmentsListUseCases<ElectricalEquipment.MechanismInfo>,
) : ViewModel() {

    private val _dataFlow = MutableStateFlow<List<ElectricalEquipment.MechanismInfo>>(emptyList())
    val dataFlow: StateFlow<List<ElectricalEquipment.MechanismInfo>> = _dataFlow

    private val dataUseCases = useCases.getElectricalEquipments().stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        emptyList()
    )

    init {
        viewModelScope.launch {
            dataUseCases.collect {
                _dataFlow.value = it
            }
        }
    }

    fun filterData(filters: List<ElGeneralCategory>) {
        if (filters.isEmpty()) {
            _dataFlow.value = dataUseCases.value
        } else {
            val filterList = mutableListOf<ElectricalEquipment.MechanismInfo>()
            val data = dataUseCases.value
            filters.forEach { filter ->
                when (filter) {
                    ElGeneralCategory.OTHER -> {
                        filterList.addAll(data.filter { it.category == ElGeneralCategory.OTHER })
                    }

                    ElGeneralCategory.HOV -> {
                        filterList.addAll(data.filter { it.category == ElGeneralCategory.HOV })
                    }

                    ElGeneralCategory.KTC_TO -> {
                        filterList.addAll(data.filter { it.category == ElGeneralCategory.KTC_TO })
                    }

                    ElGeneralCategory.KTC_KO -> {
                        filterList.addAll(data.filter { it.category == ElGeneralCategory.KTC_KO })
                    }

                    ElGeneralCategory.TY -> {
                        filterList.addAll(data.filter { it.category == ElGeneralCategory.TY })
                    }

                    ElGeneralCategory.EC -> {
                        filterList.addAll(data.filter { it.category == ElGeneralCategory.EC })
                    }

                    ElGeneralCategory.KA -> {
                        filterList.addAll(data.filter { it.category == ElGeneralCategory.KA })
                    }

                    ElGeneralCategory.RG -> {
                        filterList.addAll(data.filter { it.category == ElGeneralCategory.RG })
                    }
                }
            }
            _dataFlow.update { filterList }
        }
    }
}