package com.example.svetlogorskchpp.electricalAssembly

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.svetlogorskchpp.R
import com.example.svetlogorskchpp.databinding.DialogFragmentAssemblyBinding
import com.example.svetlogorskchpp.databinding.FragmentBlankBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DialogFragmentAssembly: BottomSheetDialogFragment() {

    private var _binding: DialogFragmentAssemblyBinding? = null
    private val binding get() = _binding!!

    override fun getTheme() = R.style.AppBottomSheetDialogTheme

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = DialogFragmentAssemblyBinding.bind(inflater.inflate(R.layout.dialog_fragment_assembly, container))
        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}