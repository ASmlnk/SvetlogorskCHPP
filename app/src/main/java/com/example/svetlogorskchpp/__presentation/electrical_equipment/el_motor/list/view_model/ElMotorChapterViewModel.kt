package com.example.svetlogorskchpp.__presentation.electrical_equipment.el_motor.list.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.ElAssembly
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.ElCategory
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.ElGeneralCategory
import com.example.svetlogorskchpp.__domain.usecases.equipments.EquipmentElMotorChapterUseCases
import com.example.svetlogorskchpp.__presentation.electrical_equipment.el_motor.factory.ElMotorChapterViewModelFactory
import com.example.svetlogorskchpp.__presentation.electrical_equipment.model.ElMotorChapter
import com.example.svetlogorskchpp.__presentation.electrical_equipment.model.ElectricalEquipment
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class ElMotorChapterViewModel @AssistedInject constructor(
    @Assisted private val elMotorChapter: ElMotorChapter,
    private val useCases: EquipmentElMotorChapterUseCases
): ViewModel() {

    private val _uiState = MutableStateFlow<List<ElectricalEquipment.ElMotor>>(emptyList())
    val uiState: StateFlow<List<ElectricalEquipment.ElMotor>> = _uiState

    private val dataState = when(elMotorChapter) {
        ElMotorChapter.OTHER -> useCases.getElMotorGenCategory(ElGeneralCategory.OTHER)
        ElMotorChapter.HOV -> useCases.getElMotorGenCategory(ElGeneralCategory.HOV)
        ElMotorChapter.KTC_TO -> useCases.getElMotorGenCategory(ElGeneralCategory.KTC_TO)
        ElMotorChapter.KTC_KO -> useCases.getElMotorGenCategory(ElGeneralCategory.KTC_KO)
        ElMotorChapter.TY -> useCases.getElMotorGenCategory(ElGeneralCategory.TY)
        ElMotorChapter.EC -> useCases.getElMotorGenCategory(ElGeneralCategory.EC)
        ElMotorChapter.KA -> useCases.getElMotorGenCategory(ElGeneralCategory.KA)
        ElMotorChapter.RG -> useCases.getElMotorGenCategory(ElGeneralCategory.RG)
        ElMotorChapter.REP -> useCases.getIsRep()
    }.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        emptyList()
    )

    init {
        viewModelScope.launch(Dispatchers.IO) {
            dataState.collect {
                _uiState.value = it
            }
        }
    }

    fun filterCategory(activeFilters: List<ElCategory>) {

        if (activeFilters.isEmpty()) {
            _uiState.value = dataState.value
        } else {
            _uiState.value = dataState.value.filter { item ->
                activeFilters.any { filter ->
                    item.category == filter
                }
            }
        }
    }


    companion object {
        fun providesFactory(
            factory: ElMotorChapterViewModelFactory,
            elMotorChapter: ElMotorChapter
        ): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return factory.create(elMotorChapter) as T
                }
            }
        }
    }
}