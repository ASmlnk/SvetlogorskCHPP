package com.example.svetlogorskchpp.__domain.usecases.equipments.edit_access

import com.example.svetlogorskchpp.__data.model.FirebaseKey
import com.example.svetlogorskchpp.__data.repository.firebase.FirebaseDataRepository
import com.example.svetlogorskchpp.__data.repository.preferences.EditAccessPreferencesRepository
import com.example.svetlogorskchpp.__domain.OperationResult
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.EditAccessResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class EditAccessUseCasesImpl @Inject constructor(
    private val preferencesRepository: EditAccessPreferencesRepository,
    private val firebaseRepository: FirebaseDataRepository,
): EditAccessUseCases {
    override suspend fun equalsEditAccessPrefFb() = withContext(Dispatchers.IO) {
        val kdFirebase = firebaseRepository.getDocumentInfo(
            FirebaseKey.COLLECTION_ELECTRICAL_EQUIPMENT,
            FirebaseKey.DOCUMENT_EDIT
        )
        if (kdFirebase == null) return@withContext
        preferencesRepository.editInfoKd.collect { kdPref ->
            if (kdPref == kdFirebase) {
                preferencesRepository.setIsEditInfoAccess(true)
            } else {
                preferencesRepository.setIsEditInfoAccess(false)
            }
        }
    }

    override fun getIsEditAccess(): Flow<Boolean> = preferencesRepository.isEditInfoAccess

    override suspend fun setKdEditAccess(kd: String): OperationResult<EditAccessResult> = withContext(Dispatchers.IO) {
        val kdFirebase = firebaseRepository.getDocumentInfo(
            FirebaseKey.COLLECTION_ELECTRICAL_EQUIPMENT,
            FirebaseKey.DOCUMENT_EDIT
        )
        if (kdFirebase == null) return@withContext OperationResult.Error(EditAccessResult.ERROR_NETWORK.str)
        if (kd == kdFirebase) {
            preferencesRepository.setIsEditInfoAccess(true)
            preferencesRepository.setEditInfoKd(kd)
            return@withContext OperationResult.Success(EditAccessResult.OK)
        } else {
            return@withContext OperationResult.Error(EditAccessResult.ERROR_KD.str)
        }
    }
}