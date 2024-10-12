package com.example.svetlogorskchpp.__presentation.dialog.request_work_filter.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.svetlogorskchpp.R
import com.example.svetlogorskchpp.__presentation.dialog.BaseBottomSheetDialog
import com.example.svetlogorskchpp.databinding.DialogRequestWorkFilterBinding

class RequestWorkFilterDialog: BaseBottomSheetDialog<DialogRequestWorkFilterBinding>() {

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): DialogRequestWorkFilterBinding {
        return DialogRequestWorkFilterBinding.bind(inflater.inflate(R.layout.dialog_request_work_filter, container))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            ivExit.setOnClickListener {
                dismiss()
            }

        }
    }

}