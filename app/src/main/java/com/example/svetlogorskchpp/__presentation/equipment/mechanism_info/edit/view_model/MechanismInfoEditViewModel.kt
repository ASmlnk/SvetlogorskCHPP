package com.example.svetlogorskchpp.__presentation.equipment.mechanism_info.edit.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.svetlogorskchpp.__domain.OperationResult
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.ElGeneralCategory
import com.example.svetlogorskchpp.__domain.model.equipment.MechanismInfo
import com.example.svetlogorskchpp.__domain.usecases.equipments.EquipmentsUseCases
import com.example.svetlogorskchpp.__presentation.equipment.mechanism_info.factory.MechanismEditViewModelFactory
import com.example.svetlogorskchpp.__presentation.equipment.mechanism_info.model.MechanismInfoUIState
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MechanismInfoEditViewModel @AssistedInject constructor(
    @Assisted private val id: String,
    private val useCases: EquipmentsUseCases<MechanismInfo>,
) : ViewModel() {

    private val _etUIState = MutableStateFlow(MechanismInfoUIState())
    val etUIState: StateFlow<MechanismInfoUIState> = _etUIState

    private val _toastResultFlow = MutableSharedFlow<String>()
    val toastResultFlow: SharedFlow<String> = _toastResultFlow

    val listGenCategory = listGeneralCategory()

    init {
        viewModelScope.launch {
            useCases.getItemEquipment(id).collect { mechanism ->
                mechanism?.let { item ->
                    with(item) {
                        _etUIState.update { oldState ->
                            oldState.copy(
                                id = id,
                                name = name,
                                info = info,
                                category = category,
                                additionally = additionally
                            )
                        }
                    }
                }
            }
        }
    }

    fun saveParameterItem (mechanismInfoUiState: MechanismInfoUIState) {
        val mechanismInfo = with(mechanismInfoUiState) {
            MechanismInfo(
                id = id,
                info = info,
                name = name,
                additionally = additionally,
                category = category,
                compositeMechanismId = "",
                compositeMechanismName = ""
            )
        }
        viewModelScope.launch {
            val resultToast = useCases.saveItemEquipment(mechanismInfo)
            when (resultToast) {
                is OperationResult.Error -> _toastResultFlow.emit(resultToast.massage)
                is OperationResult.Success -> _toastResultFlow.emit(resultToast.data)
            }
        }
    }

    fun listGeneralCategory(): List<ElGeneralCategory> {
        return listOf(
            ElGeneralCategory.OTHER,
            ElGeneralCategory.HOV,
            ElGeneralCategory.KTC_TO,
            ElGeneralCategory.KTC_KO,
            ElGeneralCategory.TY,
            ElGeneralCategory.EC,
            ElGeneralCategory.KA,
            ElGeneralCategory.RG,
        )
    }

    fun newItem() {
        val newMechanismInfoUiState = MechanismInfoUIState()
        _etUIState.value = newMechanismInfoUiState

    }

    fun saveState(state: MechanismInfoUIState) {
        _etUIState.update { state }
    }

    companion object {
        fun providesFactory(
            factory: MechanismEditViewModelFactory,
            id: String,
        ): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return factory.createMechanismInfoEditVM(id) as T
                }
            }
        }
    }

}