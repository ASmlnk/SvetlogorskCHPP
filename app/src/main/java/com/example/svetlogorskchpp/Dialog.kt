package com.example.svetlogorskchpp

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.svetlogorskchpp.databinding.DialogBinding

class Dialog : DialogFragment() {

    private var _binding: DialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogBinding.inflate(LayoutInflater.from(context))

        val dialog = AlertDialog.Builder(requireActivity()).create()
        dialog.apply {
            setView(binding.root)
        }
        return dialog
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}