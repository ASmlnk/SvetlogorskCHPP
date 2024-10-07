package com.example.svetlogorskchpp.__presentation.dialog

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.svetlogorskchpp.R
import com.example.svetlogorskchpp.databinding.DialogRequestWorkSortedBinding

class RequestWorkSortedDialog: BaseBottomSheetDialog<DialogRequestWorkSortedBinding>() {

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): DialogRequestWorkSortedBinding {
        return DialogRequestWorkSortedBinding.bind(inflater.inflate(R.layout.dialog_request_work_sorted, container))
    }
}