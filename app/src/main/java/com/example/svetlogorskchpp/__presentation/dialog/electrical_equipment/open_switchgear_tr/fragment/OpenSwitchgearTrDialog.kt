package com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.open_switchgear_tr.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.svetlogorskchpp.R
import com.example.svetlogorskchpp.__presentation.dialog.BaseBottomSheetDialog
import com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.factory.OpenSwitchgearTrViewModelFactory
import com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.open_switchgear_tr.view_model.OpenSwitchgearTrViewModel
import com.example.svetlogorskchpp.databinding.DialogOpenSwitchgearTrBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OpenSwitchgearTrDialog: BaseBottomSheetDialog<DialogOpenSwitchgearTrBinding>() {

    private val args: OpenSwitchgearTrDialogArgs by navArgs()

    @Inject
    lateinit var viewModelFactory: OpenSwitchgearTrViewModelFactory
    private val viewModel: OpenSwitchgearTrViewModel by viewModels {
        OpenSwitchgearTrViewModel.providesFactory(
            openSwitchgearTrViewModelFactory = viewModelFactory,
            idTr = args.id
        )
    }


    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): DialogOpenSwitchgearTrBinding {
        return DialogOpenSwitchgearTrBinding.bind(
            inflater.inflate(
                R.layout.dialog_open_switchgear_tr,
                container
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivEditContent.setOnClickListener {
            val action =
                OpenSwitchgearTrDialogDirections.Companion.actionOpenSwitchgearTrDialogToOpenSwitchgearTrEditFragment (
                    args.id
                )
            findNavController().navigate(action)
        }
    }
}