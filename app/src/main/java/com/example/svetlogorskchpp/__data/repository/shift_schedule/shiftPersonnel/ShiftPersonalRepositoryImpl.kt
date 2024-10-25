package com.example.svetlogorskchpp.__data.repository.shift_schedule.shiftPersonnel


import com.example.svetlogorskchpp.__data.model.ShiftPersonalDto
import com.example.svetlogorskchpp.__data.repository.shift_schedule.RepositorySt
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class ShiftPersonalRepositoryImpl @Inject constructor(
    private val dataFirebase: FirebaseFirestore
): ShiftPersonalRepository {

    override fun getShiftPersonalStream(networkAvailable: Boolean): Flow<List<ShiftPersonalDto>> = flow {
        if(networkAvailable) dataFirebase.enableNetwork() else dataFirebase.disableNetwork()
        val snapshot = dataFirebase
            .collection(RepositorySt.SHIFT_PERSONAL_COLLECTION_NAME.get)
            .document(RepositorySt.SHIFT_PERSONAL_DOCUMENT_NAME.get).get().await()

        val snapshotData = snapshot.data as Map<String, Map<String, String>>

        val shiftPersonalDto = toShiftPersonalApi(snapshotData)
        emit(shiftPersonalDto)

    }.catch {
        emit(emptyList())

    }.flowOn(Dispatchers.IO)

   override suspend fun setShiftPersonalBD(list: List<ShiftPersonalDto>) {
        dataFirebase.enableNetwork()
        val newData = list.groupBy { it.shift }.mapValues { entry ->
            entry.value.associate { it.jobTitle to it.namePersonal }
        }

        try {
            dataFirebase.collection(RepositorySt.SHIFT_PERSONAL_COLLECTION_NAME.get)
                .document(RepositorySt.SHIFT_PERSONAL_DOCUMENT_NAME.get).set(newData).await()
            println("Document successfully updated!")
        } catch (e: Exception) {
            e.printStackTrace()
            println("Error updating document: ${e.message}")
        }
    }


    private fun toShiftPersonalApi(users: Map<String, Map<String, String>>): List<ShiftPersonalDto> {
        val listShiftPersonalApi = mutableListOf<ShiftPersonalDto>()
        users.forEach { (key, valueMap) ->
            valueMap.forEach { (jobTitleK, namePersonalV) ->
                val shiftPersonalApi = ShiftPersonalDto(
                    shift = key,
                    jobTitle = jobTitleK,
                    namePersonal = namePersonalV
                )
                listShiftPersonalApi.add(shiftPersonalApi)
            }
        }
        return listShiftPersonalApi
    }
}


/*
private suspend fun getShiftPersonalFirebase(): List<ShiftPersonalDto> = withContext(Dispatchers.IO) {
        dataFirebase.enableNetwork()
        val snapshot = dataFirebase
            .collection(RepositorySt.SHIFT_PERSONAL_COLLECTION_NAME.get)
            .document(RepositorySt.SHIFT_PERSONAL_DOCUMENT_NAME.get).get().await()

        val snapshotData = snapshot.data as Map<String, Map<String, String>>

        val shiftPersonalDto = toShiftPersonalApi(snapshotData)
        shiftPersonalDto
    }
*/

/*suspend fun getShiftPersonalStream(): Flow<List<ShiftPersonalEntity>> = withContext(Dispatchers.IO){
    val data = getShiftPersonalFirebase().map { it.toShitPersonalEntity() }

    val countShiftPersonalBD = shiftPersonalDao.countShiftPersonalEntities()
    if (countShiftPersonalBD == 0 || countShiftPersonalBD == data.size) {
        shiftPersonalDao.insertAll(data)
    } else {
        shiftPersonalDao.clearAll()
        shiftPersonalDao.insertAll(data)
    }

    shiftPersonalDao.getAll()
}*/
