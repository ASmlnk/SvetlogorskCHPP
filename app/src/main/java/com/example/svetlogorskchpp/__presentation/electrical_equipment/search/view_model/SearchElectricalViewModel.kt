package com.example.svetlogorskchpp.__presentation.electrical_equipment.search.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.svetlogorskchpp.__domain.usecases.equipments.all_equipment.electrical.EquipmentAllUseCases
import com.example.svetlogorskchpp.__presentation.electrical_equipment.model.ElectricalEquipment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchElectricalViewModel @Inject constructor(
    private val useCases: EquipmentAllUseCases
): ViewModel() {

    private val _data = MutableStateFlow(emptyList<ElectricalEquipment>())
    val data: StateFlow<List<ElectricalEquipment>> = _data
    private var searchJob: Job? = null

    fun search(newText: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch(Dispatchers.IO) {
            delay(300)
            if (newText.isEmpty()) _data.value = emptyList()
            else if (newText == "fpl") {
                useCases.getEquipmentConsumersFlow("").collect { list ->
                    _data.value = list.filter { it.fplFilter() }
                }
            } else {
                useCases.getSearchElectricalEquipment(
                    "%${newText.lowercase()}%",
                    "${newText.lowercase()}%"
                ).collect {
                    _data.value = it
                }
            }

        }

    }
}