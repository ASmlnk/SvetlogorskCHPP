package com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.svetlogorskchpp.__domain.OperationResult
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.EditAccessResult
import com.example.svetlogorskchpp.__domain.usecases.equipments.edit_access.EditAccessUseCases
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

open class BaseEquipmentDialogViewModel (
    private val accessUseCases: EditAccessUseCases,
): ViewModel() {


    protected val isAccess = accessUseCases.getIsEditAccess()

    protected val _toastResultFlow = MutableSharedFlow<OperationResult<EditAccessResult>>()
    val toastResultFlow: SharedFlow<OperationResult<EditAccessResult>> = _toastResultFlow



    fun equalsPassword(password: String) {
        viewModelScope.launch {
            val result = accessUseCases.setKdEditAccess(password)
            _toastResultFlow.emit(result)
        }
    }
}