package com.example.svetlogorskchpp.__presentation.electrical_equipment.lighting_and_other.edit.fragment

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.svetlogorskchpp.BaseFragment
import com.example.svetlogorskchpp.R
import com.example.svetlogorskchpp.__presentation.electrical_equipment.factory.LightingAndOtherEditViewModelFactory
import com.example.svetlogorskchpp.__presentation.electrical_equipment.lighting_and_other.edit.model.LightingAndOtherEditUIState
import com.example.svetlogorskchpp.__presentation.electrical_equipment.lighting_and_other.edit.view_model.LightingAndOtherEditViewModel
import com.example.svetlogorskchpp.databinding.FragmentLightingAndOtherEditBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LightingAndOtherEditFragment : BaseFragment<FragmentLightingAndOtherEditBinding>() {

    private val args: LightingAndOtherEditFragmentArgs by navArgs()

    @Inject
    lateinit var viewModelFactory: LightingAndOtherEditViewModelFactory
    private val viewModel: LightingAndOtherEditViewModel by viewModels {
        LightingAndOtherEditViewModel.providesFactory(
            factory = viewModelFactory,
            id = args.id
        )
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentLightingAndOtherEditBinding {
        return FragmentLightingAndOtherEditBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.etUIState.collect {
                    setupUI(it)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.toastResultFlow.collect { toast ->
                    showCustomSnackbar(binding.root, toast)
                }
            }
        }

        binding.apply {
            chipLighting.setOnCheckedChangeListener { _, isChecked ->
                val selectState = saveEditText().copy(isLighting = isChecked)
                viewModel.saveState(selectState)
            }

            bAddPowerSupply.setOnClickListener {
                findNavController().navigate(R.id.action_lightingAndOtherEditFragment_to_powerSupplySelectionDialog)
            }
            ivDeletePowerSupply.setOnClickListener {
                val state = saveEditText().copy(
                    powerSupplyId = "",
                    powerSupplyName = "",
                    powerSupplyCell = ""
                )
                viewModel.saveState(state)
            }
            bSave.setOnClickListener{
                val selectState = saveEditText()
                viewModel.saveParameterElMotor(selectState)
            }

            ivUpdate.setOnClickListener {
                viewModel.newItem()
            }
        }

        listenerPowerSupply()
    }

    override fun onStop() {
        super.onStop()
        val state = saveEditText()
        viewModel.saveState(state)
    }

    private fun setupUI(uiState: LightingAndOtherEditUIState) {

        with(uiState) {
            binding.apply {
                etName.setText(name)
                etPowerSupplyCell.setText(powerSupplyCell)
                etLocation.setText(location)
                etSwitch.setText(typeSwitch)
                etAdditionally.setText(additionally)

                tvPowerSupplyName.text = powerSupplyName
                chipLighting.isChecked = isLighting
            }
        }
    }

    private fun saveEditText(): LightingAndOtherEditUIState =
        viewModel.etUIState.value.copy(
            name = binding.etName.text.toString(),
            powerSupplyCell = binding.etPowerSupplyCell.text.toString(),
            typeSwitch = binding.etSwitch.text.toString(),
            additionally = binding.etAdditionally.text.toString(),
            location = binding.etLocation.text.toString(),
        )

    private fun listenerPowerSupply() {
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Pair<String, String>>(
            "selectedData"
        )?.observe(viewLifecycleOwner) { selectedData ->
            val (id, name) = selectedData
            val selectState = saveEditText().copy(
                powerSupplyId = id,
                powerSupplyName = name
            )
            viewModel.saveState(selectState)
        }
    }

    private fun showCustomSnackbar(view: View, text: String) {

        val snackbar = Snackbar.make(view, text, Snackbar.LENGTH_LONG)

        val snackbarView = snackbar.view
        val background: Drawable? = ContextCompat.getDrawable(
            requireContext(),
            R.drawable.background_snakbar
        )
        snackbarView.background = background

        val params = snackbarView.layoutParams as FrameLayout.LayoutParams
        params.setMargins(0, 100, 0, 0)
        params.gravity = Gravity.TOP
        snackbarView.layoutParams = params

        val textView =
            snackbarView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        textView.apply {
            setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.floatingActionButton
                )
            )  // Цвет текста
            textSize = 18f
        }
        snackbar.show()
    }
}