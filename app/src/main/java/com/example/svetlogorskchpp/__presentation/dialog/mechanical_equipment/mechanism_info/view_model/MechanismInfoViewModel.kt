package com.example.svetlogorskchpp.__presentation.dialog.mechanical_equipment.mechanism_info.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.svetlogorskchpp.__domain.model.equipment.MechanismInfo
import com.example.svetlogorskchpp.__domain.usecases.equipments.EquipmentsUseCases
import com.example.svetlogorskchpp.__domain.usecases.equipments.all_equipment.mechanical.EquipmentMechanismAllUseCases
import com.example.svetlogorskchpp.__domain.usecases.equipments.edit_access.EditAccessUseCases
import com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.BaseEquipmentDialogViewModel
import com.example.svetlogorskchpp.__presentation.dialog.mechanical_equipment.factory.MechanismViewModelFactory
import com.example.svetlogorskchpp.__presentation.dialog.mechanical_equipment.model.MechanismInfoDialogUiState
import com.example.svetlogorskchpp.__presentation.electrical_equipment.model.ElectricalEquipment
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MechanismInfoViewModel @AssistedInject constructor(
    @Assisted private val id: String,
    private val useCases: EquipmentsUseCases<MechanismInfo>,
    private val accessUseCases: EditAccessUseCases,
    private val mechanismUseCases: EquipmentMechanismAllUseCases
) : BaseEquipmentDialogViewModel(accessUseCases) {

    private val _uiState = MutableStateFlow(MechanismInfoDialogUiState())
    val uiState = _uiState.asStateFlow()

    private val _mechanismSubState = MutableStateFlow<List<ElectricalEquipment>>(emptyList())
    val mechanismSubState = _mechanismSubState.asStateFlow()

    init {
        viewModelScope.launch {
            useCases.getItemEquipment(id).collect { mechanism ->
                mechanism?.let {
                    _uiState.update { oldState ->
                        with(mechanism) {
                            oldState.copy(
                                name = name,
                                info = info,
                                category = category,
                                additionally = additionally
                            )
                        }
                    }
                    updatemechanismSub(mechanism.id)
                }
            }
        }
        viewModelScope.launch {
            isAccess.collect {
                _uiState.update { oldState ->
                    oldState.copy(isAccessEdit = it)
                }
            }
        }
    }

    private fun updatemechanismSub(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            mechanismUseCases.getSubMechanismFlow(id).collect {
                _mechanismSubState.value = it
            }
        }
    }

    fun isEditAccess(): Boolean {
        return uiState.value.isAccessEdit
    }

    companion object {
        fun provideFactory(
            factory: MechanismViewModelFactory,
            id: String,
        ): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return factory.onCreateDialogMechanismInfo(id) as T
                }
            }
        }
    }
}