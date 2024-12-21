package com.example.svetlogorskchpp.__presentation.electrical_equipment.switchgear.item.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.svetlogorskchpp.__domain.OperationResult
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.EditAccessResult
import com.example.svetlogorskchpp.__domain.model.electrical_equipment.Switchgear
import com.example.svetlogorskchpp.__domain.usecases.equipments.EquipmentsUseCases
import com.example.svetlogorskchpp.__domain.usecases.equipments.all_equipment.electrical.EquipmentAllUseCases
import com.example.svetlogorskchpp.__domain.usecases.equipments.all_equipment.delete.EquipmentsItemDeleteUseCases
import com.example.svetlogorskchpp.__domain.usecases.equipments.edit_access.EditAccessUseCases
import com.example.svetlogorskchpp.__presentation.electrical_equipment.model.ElectricalEquipment
import com.example.svetlogorskchpp.__presentation.electrical_equipment.switchgear.factory.SwitchgearOwnNeedsViewModelFactory
import com.example.svetlogorskchpp.__presentation.electrical_equipment.switchgear.model.FilterSwitchgear
import com.example.svetlogorskchpp.__presentation.electrical_equipment.switchgear.model.SwitchgearUIState
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SwitchgearOwnNeedsViewModel @AssistedInject constructor(
    @Assisted private val id: String,
    private val useCasesAll: EquipmentAllUseCases,
    private val useCasesData: EquipmentsUseCases<Switchgear>,
    private val useCasesDeleteData: EquipmentsItemDeleteUseCases,
    private val accessUseCases: EditAccessUseCases,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SwitchgearUIState())
    val uiState: StateFlow<SwitchgearUIState> = _uiState

    private val _toastResultFlow = MutableSharedFlow<String>()
    val toastResultFlow: SharedFlow<String> = _toastResultFlow

    private val isAccess = accessUseCases.getIsEditAccess()

    private val _toastResultAccessFlow = MutableSharedFlow<OperationResult<EditAccessResult>>()
    val toastResultAccessFlow: SharedFlow<OperationResult<EditAccessResult>> =
        _toastResultAccessFlow

    private val dataConsumerStateFlow = useCasesAll.getEquipmentConsumersFlow(id).stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        emptyList()
    )

    init {
        viewModelScope.launch {
            useCasesData.getItemEquipment(id).collect { dataSwitchgear ->
                dataSwitchgear?.let {
                    _uiState.update {
                        it.copy(
                            name = dataSwitchgear.name,
                            id = dataSwitchgear.id,
                            nameDepartment = dataSwitchgear.nameDepartment.str
                        )
                    }
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

        viewModelScope.launch {
            dataConsumerStateFlow.collect { dataConsumer ->
                _uiState.update { it.copy(listSwitchgear = dataConsumer) }
            }
        }
    }

    fun isEditAccess(): Boolean {
        return uiState.value.isAccessEdit
    }

    fun equalsPassword(password: String) {
        viewModelScope.launch {
            val result = accessUseCases.setKdEditAccess(password)
            _toastResultAccessFlow.emit(result)
        }
    }

    fun deleteItem(item: ElectricalEquipment) {
        viewModelScope.launch {
            val resultToast = useCasesDeleteData.deleteItem(item)
            when (resultToast) {
                is OperationResult.Error -> _toastResultFlow.emit(resultToast.massage)
                is OperationResult.Success -> _toastResultFlow.emit(resultToast.data)
            }
        }
    }

    fun filterCategory(activeFilters: List<FilterSwitchgear>) {
        if (activeFilters.isEmpty()) {
            _uiState.update { oldState ->
                oldState.copy(listSwitchgear = dataConsumerStateFlow.value)
            }
        } else {
            val data = dataConsumerStateFlow.value
            val filterList = mutableListOf<ElectricalEquipment>()

            activeFilters.forEach { filter ->
                when (filter) {
                    FilterSwitchgear.EL_MOTOR -> filterList.addAll(data.filter { it is ElectricalEquipment.ElMotor })
                    FilterSwitchgear.SWITCHGEAR -> filterList.addAll(data.filter { it is ElectricalEquipment.Switchgear })
                    FilterSwitchgear.LIGTH -> {
                        filterList.addAll(data.filter { it is ElectricalEquipment.LightOther })
                        filterList.addAll(data.filter { it is ElectricalEquipment.Tsn })
                    }
                }
            }

            _uiState.update { oldState ->
                oldState.copy(listSwitchgear = filterList)
            }
        }
    }

    fun sorted() {
        val isSorted = !_uiState.value.isSortedCell
        _uiState.update { oldState ->
            oldState.copy(isSortedCell = isSorted)
        }
        viewModelScope.launch {
            _toastResultFlow.emit(
                if (isSorted) "Сортировка по номеру ячейки!" else "Сортировка по имени"
            )
        }
    }

    fun activeDelete() {
        _uiState.update { oldState ->
            val equipments = oldState.listSwitchgear.map { item ->
                when (item) {
                    is ElectricalEquipment.ElMotor -> item.copy(isDelete = !item.isDelete)
                    is ElectricalEquipment.LightOther -> item.copy(isDelete = !item.isDelete)
                    is ElectricalEquipment.Switchgear -> item.copy(isDelete = !item.isDelete)
                    is ElectricalEquipment.Tg -> item.copy(isDelete = item.isDelete)
                    is ElectricalEquipment.Tr -> item.copy(isDelete = item.isDelete)
                    is ElectricalEquipment.Tsn -> item.copy(isDelete = item.isDelete)
                    is ElectricalEquipment.Vl -> item.copy(isDelete = item.isDelete)
                    is ElectricalEquipment.MechanismInfo -> item.copy(isDelete = item.isDelete)
                }
            }
            oldState.copy(listSwitchgear = equipments)
        }
    }

    companion object {
        fun providesFactory(
            factory: SwitchgearOwnNeedsViewModelFactory,
            id: String,
        ): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return factory.create(id) as T
                }
            }
        }
    }
}