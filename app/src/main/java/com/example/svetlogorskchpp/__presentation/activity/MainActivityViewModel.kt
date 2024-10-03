package com.example.svetlogorskchpp.__presentation.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.svetlogorskchpp.__data.repository.noteRequestWork.NoteRequestWorkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val noteRequestWorkRepository: NoteRequestWorkRepository
):ViewModel() {

    suspend fun getRequestWorkFirebase() {
        noteRequestWorkRepository.getRequestWorkFirebase()
    }

}