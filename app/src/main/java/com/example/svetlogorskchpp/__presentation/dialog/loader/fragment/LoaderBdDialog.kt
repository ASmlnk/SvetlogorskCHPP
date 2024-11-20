package com.example.svetlogorskchpp.__presentation.dialog.loader.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.svetlogorskchpp.__presentation.dialog.BaseBottomSheetDialog
import com.example.svetlogorskchpp.R
import com.example.svetlogorskchpp.__presentation.dialog.loader.view_model.LoaderBdViewModel
import com.example.svetlogorskchpp.databinding.DialogLoaderLocaleFbBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoaderBdDialog: BaseBottomSheetDialog<DialogLoaderLocaleFbBinding>() {

    val viewModel: LoaderBdViewModel by viewModels()

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): DialogLoaderLocaleFbBinding {
        return DialogLoaderLocaleFbBinding.bind(
            inflater.inflate(R.layout.dialog_loader_locale_fb, container)
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            bElMotor.setOnClickListener {
                viewModel.loaderElMotorInFb()
            }
            bLighting.setOnClickListener{
                viewModel.loaderLightingAndOtherInFb()
            }
            bSwitchgear.setOnClickListener{
                viewModel.loaderSwitchgearInFb()
            }
        }
    }
}