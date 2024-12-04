package com.example.svetlogorskchpp.__data.repository.equipment

import android.content.Context
import com.example.svetlogorskchpp.__data.model.FirebaseKey
import com.google.gson.Gson
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class ReservationSaveFileRepository @Inject constructor(
    private val gson: Gson,
    private val context: Context
) {
    fun <E> saveFile (
        document: FirebaseKey,
        listEquipment: List<E>
    ) {
        val dd = gson.toJson(listEquipment)
        val file = File(context.filesDir, document.name)

        FileOutputStream(file).use { outputStream ->
            outputStream.write(dd.toByteArray())
        }
    }
}