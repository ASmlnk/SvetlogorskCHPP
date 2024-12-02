package com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.viewbinding.ViewBinding
import com.example.svetlogorskchpp.R
import com.example.svetlogorskchpp.__presentation.dialog.BaseBottomSheetDialog

abstract class BaseEquipmentBottomSheetDialog<T: ViewBinding>: BaseBottomSheetDialog<T>() {

    fun showPasswordDialog(context: Context, onPasswordEntered: (String) -> Unit) {
        val dialogView: View = LayoutInflater.from(context).inflate(R.layout.dialog_password, null)
        val passwordInput: EditText = dialogView.findViewById(R.id.password_input)
        val btnOk: Button = dialogView.findViewById(R.id.btn_ok)
        val btnCancel: Button = dialogView.findViewById(R.id.btn_cancel)

        // Создание диалогового окна
        val dialog = AlertDialog.Builder(context)
            .setView(dialogView)
            .setCancelable(false) // Установите false, если хотите запретить закрытие диалога по нажатию вне его
            .create()

        btnOk.setOnClickListener {
            val password = passwordInput.text.toString()
            onPasswordEntered(password)
            dialog.dismiss()
        }

        btnCancel.setOnClickListener {
            dialog.cancel()
        }

        dialog.show()
    }
}