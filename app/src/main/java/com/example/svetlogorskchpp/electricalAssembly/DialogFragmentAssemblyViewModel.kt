package com.example.svetlogorskchpp.electricalAssembly

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.svetlogorskchpp.model.ElectricalAssembly
import com.example.svetlogorskchpp.model.firebase.FirestoreRepository
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DialogFragmentAssemblyViewModel(
    private val idElectricalAssembly: String,
    val data:FirestoreRepository
) : ViewModel() {



    private val _electricalAssemblyLiveData = MutableLiveData<ElectricalAssembly>()
    val electricalAssembly: LiveData<ElectricalAssembly>
        get() = _electricalAssemblyLiveData


    init {
        Log.e("123456", "s3")
        viewModelScope.launch(Dispatchers.IO) {
            val electricalAssembly = data.getIdElectricalAssembly(idElectricalAssembly)
            withContext(Dispatchers.Main) {
                _electricalAssemblyLiveData.value = electricalAssembly
            }
        }
    }
}