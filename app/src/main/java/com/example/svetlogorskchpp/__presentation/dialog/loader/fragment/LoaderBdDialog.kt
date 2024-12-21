package com.example.svetlogorskchpp.__presentation.dialog.loader.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
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
            bMechanismInfo.setOnClickListener {
                viewModel.loaderMechanismInfo()
            }
            bReserved.setOnClickListener{
                viewModel.reservationFirebase()
            }
            bAddTsn.setOnClickListener {
                findNavController().navigate(R.id.action_loaderBdDialog_to_transformerOwnNeedsEditFragment)
            }
            bAddTg.setOnClickListener {
                findNavController().navigate(R.id.action_loaderBdDialog_to_turbogeneratorEditFragment)
            }
            bAddTr.setOnClickListener {
                findNavController().navigate(R.id.action_loaderBdDialog_to_openSwitchgearTrEditFragment)
            }
            bAddVl.setOnClickListener {
                findNavController().navigate(R.id.action_loaderBdDialog_to_openSwitchgearVlEditFragment)
            }
            bAddElMotor.setOnClickListener {
                findNavController().navigate(R.id.action_loaderBdDialog_to_elMotorEditFragment)
            }
            bAddLighting.setOnClickListener {
                findNavController().navigate(R.id.action_loaderBdDialog_to_lightingAndOtherEditFragment)
            }
            bAddSwitchgear.setOnClickListener {
                findNavController().navigate(R.id.action_loaderBdDialog_to_switchgearOwnNeedsEditFragment)
            }
            bAddMechanismInfo.setOnClickListener {
                findNavController().navigate(R.id.action_loaderBdDialog_to_mechanismInfoFragment)
            }
        }
    }
}