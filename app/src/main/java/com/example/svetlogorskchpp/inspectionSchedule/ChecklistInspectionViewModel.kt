package com.example.svetlogorskchpp.inspectionSchedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.svetlogorskchpp.model.firebase.FirestoreRepository
import kotlinx.coroutines.launch
import javax.inject.Inject


class ChecklistInspectionViewModel (val nameCategory: String, val data: FirestoreRepository): ViewModel() {

   // @Inject lateinit var data: FirestoreRepository
   // val data = FirestoreRepository.get()

    private val _navigateToDialogChecklist = MutableLiveData<String?>()
    val navigateToDialogChecklist: LiveData<String?>
        get() = _navigateToDialogChecklist
    init {
        viewModelScope.launch {
            data.getChecklistInspection(nameCategory)

        }
    }

    fun onClickedDialogChecklist(itemId: String) {
        _navigateToDialogChecklist.value = itemId
    }

    fun onDialogNavigation() {
        _navigateToDialogChecklist.value = null
    }

    override fun onCleared() {
        super.onCleared()
        data.clearListChecklistInspection()
    }
}