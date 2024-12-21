package com.example.svetlogorskchpp.__presentation.dialog.mechanical_equipment.mechanism_info.fragment

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isGone
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.BaseEquipmentBottomSheetDialog
import com.example.svetlogorskchpp.R
import com.example.svetlogorskchpp.__domain.OperationResult
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.ElGeneralCategory
import com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.adapter.PowerSupplySelectionAdapter
import com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.el_motor.fragment.ElMotorDialogDirections
import com.example.svetlogorskchpp.__presentation.dialog.mechanical_equipment.factory.MechanismViewModelFactory
import com.example.svetlogorskchpp.__presentation.dialog.mechanical_equipment.model.MechanismInfoDialogUiState
import com.example.svetlogorskchpp.__presentation.dialog.mechanical_equipment.mechanism_info.view_model.MechanismInfoViewModel
import com.example.svetlogorskchpp.databinding.DialogEquipmentMechanismInfoBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MechanismInfoDialog: BaseEquipmentBottomSheetDialog<DialogEquipmentMechanismInfoBinding>() {

    private val args: MechanismInfoDialogArgs by navArgs()

    private val adapterMechanismSub = PowerSupplySelectionAdapter { id, name, dl ->
        val deepLink = Uri.parse(dl.link + id)
        findNavController().navigate(deepLink)
    }

    @Inject
    lateinit var viewModelFactory: MechanismViewModelFactory
    private val viewModel: MechanismInfoViewModel by viewModels {
        MechanismInfoViewModel.provideFactory(
            factory = viewModelFactory,
            id = args.id
        )
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): DialogEquipmentMechanismInfoBinding {
        return DialogEquipmentMechanismInfoBinding.bind(
            inflater.inflate(R.layout.dialog_equipment_mechanism_info, container)
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    setupUI(it)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.mechanismSubState.collect {
                    adapterMechanismSub.submitList(it)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.toastResultFlow.collect { toast ->
                if (toast is OperationResult.Success) {
                    val action =
                        ElMotorDialogDirections.Companion.actionElMotorDialogToElMotorEditFragment(
                            args.id
                        )
                    findNavController().navigate(action)
                } else {
                    Toast.makeText(
                        context,
                        (toast as OperationResult.Error).massage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        binding.apply {
            rvSubMechanism.adapter = adapterMechanismSub

            ivEditContent.setOnClickListener {
                if (viewModel.isEditAccess()) {
                    val action =
                        MechanismInfoDialogDirections.Companion.actionMechanismInfoDialogToMechanismInfoFragment(
                            args.id
                        )
                    findNavController().navigate(action)
                } else {
                    showPasswordDialog(requireContext()) {
                        viewModel.equalsPassword(it)
                    }
                }
            }
        }
    }

    private fun setupUI(state: MechanismInfoDialogUiState) = binding.apply {

        with(state) {
            tvName.apply {
                text = name
                isGone = name.isEmpty()
            }
            tvInfo.apply {
                text = info
                isGone = info.isEmpty()
            }
            tvAdditionally.apply {
                text = additionally
                isGone = additionally.isEmpty()
            }

            when(category) {
                ElGeneralCategory.OTHER -> ivCategory.setImageResource(R.drawable.flash_4049918)
                ElGeneralCategory.HOV -> ivCategory.setImageResource(R.drawable.chemistry_class_6837437)
                ElGeneralCategory.KTC_TO -> ivCategory.setImageResource(R.drawable.factory_1643683)
                ElGeneralCategory.KTC_KO -> ivCategory.setImageResource(R.drawable.free_icon_water_ko)
                ElGeneralCategory.TY -> ivCategory.setImageResource(R.drawable.free_icon_oil_tank)
                ElGeneralCategory.EC -> ivCategory.setImageResource(R.drawable.flash_4049918)
                ElGeneralCategory.KA -> ivCategory.setImageResource(R.drawable.water_boiler)
                ElGeneralCategory.RG -> ivCategory.setImageResource(R.drawable.generator_1)
            }
        }
    }
}