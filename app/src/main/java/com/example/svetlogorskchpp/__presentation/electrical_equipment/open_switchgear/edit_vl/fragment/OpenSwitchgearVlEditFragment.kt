package com.example.svetlogorskchpp.__presentation.electrical_equipment.open_switchgear.edit_vl.fragment

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.example.svetlogorskchpp.BaseFragment
import com.example.svetlogorskchpp.R
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.KeyOry
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.VoltageOry
import com.example.svetlogorskchpp.__presentation.electrical_equipment.open_switchgear.adapter.ProtectionEditAdapter
import com.example.svetlogorskchpp.__presentation.electrical_equipment.open_switchgear.edit_vl.adapter.CustomSpinnerAdapter
import com.example.svetlogorskchpp.__presentation.electrical_equipment.open_switchgear.edit_vl.adapter.CustomSpinnerVoltageAdapter
import com.example.svetlogorskchpp.__presentation.electrical_equipment.open_switchgear.edit_vl.view_model.OpenSwitchgearVlEditViewModel
import com.example.svetlogorskchpp.__presentation.electrical_equipment.open_switchgear.factory.OpenSwitchgearVlEditViewModelFactory
import com.example.svetlogorskchpp.__presentation.electrical_equipment.open_switchgear.model.OpSwVlEditUIState
import com.example.svetlogorskchpp.__presentation.electrical_equipment.open_switchgear.model.SpinnerOryParameter
import com.example.svetlogorskchpp.__presentation.shift_schedule.shift_schedule_requests_work.model.Toast
import com.example.svetlogorskchpp.databinding.ContentLayoutEditOryNameBinding
import com.example.svetlogorskchpp.databinding.ContentLayoutEditOryParameterBinding
import com.example.svetlogorskchpp.databinding.ContentLayoutEditOryRzaBinding
import com.example.svetlogorskchpp.databinding.FragmentOpenSwitchgearVlEditBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class OpenSwitchgearVlEditFragment : BaseFragment<FragmentOpenSwitchgearVlEditBinding>() {

    private val args: OpenSwitchgearVlEditFragmentArgs by navArgs()

    @Inject
    lateinit var viewModelFactory: OpenSwitchgearVlEditViewModelFactory
    private val viewModel: OpenSwitchgearVlEditViewModel by viewModels {
        OpenSwitchgearVlEditViewModel.providesFactory(
            openSwitchgearVlEditViewModelFactory = viewModelFactory,
            idVl = args.id
        )
    }

    private lateinit var phaseProtectionAdapter: ProtectionEditAdapter
    private lateinit var earthProtectionAdapter: ProtectionEditAdapter

    private val listKeys =
        listOf(KeyOry.KEY_0, KeyOry.KEY_1, KeyOry.KEY_2, KeyOry.KEY_3, KeyOry.KEY_4)
    private val listVoltage =
        listOf(VoltageOry.KV, VoltageOry.KV_220, VoltageOry.KV_110)


    private var _includeOryParameterBinding: ContentLayoutEditOryParameterBinding? = null
    private val includeOryParameterBinding get() = _includeOryParameterBinding!!

    private var _includeOryNameBinding: ContentLayoutEditOryNameBinding? = null
    private val includeOryNameBinding get() = _includeOryNameBinding!!

    private var _includeOryRzaBinding: ContentLayoutEditOryRzaBinding? = null
    private val includeOryRzaBinding get() = _includeOryRzaBinding!!

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentOpenSwitchgearVlEditBinding {
        return FragmentOpenSwitchgearVlEditBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.opSwVlEditUIState.collect { state ->
                    updateEditTextUI(state)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.spinnerUIState.collect { state ->

                    includeOryParameterBinding.apply {
                        spinner1shr.setSelection(listKeys.indexOf(state.keyShr1))
                        spinner2shr.setSelection(listKeys.indexOf(state.keyShr2))
                        spinnerLr.setSelection(listKeys.indexOf(state.keyLr))
                        spinnerOr.setSelection(listKeys.indexOf(state.keyOr))
                        spinnerVoltage.setSelection(listVoltage.indexOf(state.voltageOry))
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.protectionUIState.collect { state ->
                    phaseProtectionAdapter.submitList(state.phaseProtection)
                    earthProtectionAdapter.submitList(state.earthProtection)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.toastResultFlow.collect {
                    showCustomSnackbar(binding.root, it)
                }
            }
        }

        _includeOryParameterBinding =
            ContentLayoutEditOryParameterBinding.bind(binding.contentOryParameter.root)
        _includeOryNameBinding = ContentLayoutEditOryNameBinding.bind(binding.contentOryName.root)
        _includeOryRzaBinding = ContentLayoutEditOryRzaBinding.bind(binding.contentRza.root)

        setupSpinner()
        setupProtectionView()

        binding.apply {
            chipTransit.setOnCheckedChangeListener { _, isChecked ->
                chipTransitSelected(isChecked)
            }
            chipVl.setOnCheckedChangeListener { _, isChecked ->
                chipVlSelected(isChecked)
            }
            bSave.setOnClickListener {
                saveParameterVl()
            }
        }
    }

    private fun updateEditTextUI(opSwVlEditUIState: OpSwVlEditUIState) {
        includeOryRzaBinding.apply {
            if (opSwVlEditUIState.automation.isNotEmpty()) etAutomation.setText(opSwVlEditUIState.automation)
            if (opSwVlEditUIState.apv.isNotEmpty()) etApv.setText(opSwVlEditUIState.apv)
        }
        includeOryNameBinding.apply {
            if (opSwVlEditUIState.name.isNotEmpty()) etName.setText(opSwVlEditUIState.name)
            if (opSwVlEditUIState.panelMcp.isNotEmpty()) panelMcp.setText(opSwVlEditUIState.panelMcp)
        }
        includeOryParameterBinding.apply {
            if (opSwVlEditUIState.bysSystem.isNotEmpty()) etTireNumber.setText(opSwVlEditUIState.bysSystem)
            if (opSwVlEditUIState.cell.isNotEmpty()) etCellNumber.setText(opSwVlEditUIState.cell)
            if (opSwVlEditUIState.typeSwitch.isNotEmpty()) etSwitch.setText(opSwVlEditUIState.typeSwitch)
            if (opSwVlEditUIState.typeInsTr.isNotEmpty()) etInstrumentTransformer.setText(
                opSwVlEditUIState.typeInsTr
            )
        }

        binding.apply {
            chipTransit.isChecked = opSwVlEditUIState.isTransit
            chipVl.isChecked = opSwVlEditUIState.isVl
        }
    }

    private fun chipTransitSelected(isTransit: Boolean) {
        val opSwVlEditUIState = viewModel.opSwVlEditUIState.value.copy(
            name = includeOryNameBinding.etName.text.toString(),
            panelMcp = includeOryNameBinding.panelMcp.text.toString(),
            bysSystem = includeOryParameterBinding.etTireNumber.text.toString(),
            cell = includeOryParameterBinding.etCellNumber.text.toString(),
            isTransit = isTransit,
            typeSwitch = includeOryParameterBinding.etSwitch.text.toString(),
            typeInsTr = includeOryParameterBinding.etInstrumentTransformer.text.toString(),
            automation = includeOryRzaBinding.etAutomation.text.toString(),
            apv = includeOryRzaBinding.etApv.text.toString()
        )
        viewModel.chipSelected(opSwVlEditUIState)
    }

    private fun chipVlSelected(isVl: Boolean) {
        val opSwVlEditUIState = viewModel.opSwVlEditUIState.value.copy(
            name = includeOryNameBinding.etName.text.toString(),
            panelMcp = includeOryNameBinding.panelMcp.text.toString(),
            bysSystem = includeOryParameterBinding.etTireNumber.text.toString(),
            cell = includeOryParameterBinding.etCellNumber.text.toString(),
            isVl = isVl,
            typeSwitch = includeOryParameterBinding.etSwitch.text.toString(),
            typeInsTr = includeOryParameterBinding.etInstrumentTransformer.text.toString(),
            automation = includeOryRzaBinding.etAutomation.text.toString(),
            apv = includeOryRzaBinding.etApv.text.toString()
        )
        viewModel.chipSelected(opSwVlEditUIState)
    }

    private fun saveParameterVl() {
        val opSwVlEditUIState = viewModel.opSwVlEditUIState.value.copy(
            name = includeOryNameBinding.etName.text.toString(),
            panelMcp = includeOryNameBinding.panelMcp.text.toString(),
            bysSystem = includeOryParameterBinding.etTireNumber.text.toString(),
            cell = includeOryParameterBinding.etCellNumber.text.toString(),
            typeSwitch = includeOryParameterBinding.etSwitch.text.toString(),
            typeInsTr = includeOryParameterBinding.etInstrumentTransformer.text.toString(),
            automation = includeOryRzaBinding.etAutomation.text.toString(),
            apv = includeOryRzaBinding.etApv.text.toString()
        )
        viewModel.saveParameterVl(opSwVlEditUIState)
    }

    private fun setupProtectionView() {
        phaseProtectionAdapter = ProtectionEditAdapter {protection ->
            viewModel.deletePhaseProtection(protection)
        }
        earthProtectionAdapter= ProtectionEditAdapter {protection ->
            viewModel.deleteEarthProtection(protection)
        }
        includeOryRzaBinding.apply {
            rvPhaseProtection.adapter = phaseProtectionAdapter
            ivPhaseProtection.setOnClickListener {
                val textProtection = etPhaseProtection.text.toString()
                if(textProtection.isNotEmpty()) viewModel.addPhaseProtection(textProtection)
                etPhaseProtection.setText("")
            }
            rvEarthProtection.adapter = earthProtectionAdapter
                ivEarthProtection.setOnClickListener {
                val textProtection = etEarthProtection.text.toString()
                if(textProtection.isNotEmpty()) viewModel.addEarthProtection(textProtection)
                    etEarthProtection.setText("")
            }
        }
    }

    private fun setupSpinner() {

        val adapterSpinner = CustomSpinnerAdapter(requireContext(), listKeys)
        val voltageAdapter = CustomSpinnerVoltageAdapter(requireContext(), listVoltage)

        includeOryParameterBinding.apply {
            spinner1shr.adapter = adapterSpinner
            spinner2shr.adapter = adapterSpinner
            spinnerLr.adapter = adapterSpinner
            spinnerOr.adapter = adapterSpinner
            spinnerVoltage.adapter = voltageAdapter

            spinner1shr.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long,
                ) {
                    val selectedItem = parent.getItemAtPosition(position) as KeyOry
                    viewModel.spinnerSaveState(
                        spinnerOryParameter = SpinnerOryParameter.SH_R_1,
                        selectSpinner = selectedItem
                    )
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }

            spinner2shr.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long,
                ) {
                    val selectedItem = parent.getItemAtPosition(position) as KeyOry
                    viewModel.spinnerSaveState(
                        spinnerOryParameter = SpinnerOryParameter.SH_R_2,
                        selectSpinner = selectedItem
                    )
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
            spinnerLr.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long,
                ) {
                    val selectedItem = parent.getItemAtPosition(position) as KeyOry
                    viewModel.spinnerSaveState(
                        spinnerOryParameter = SpinnerOryParameter.LR,
                        selectSpinner = selectedItem
                    )
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
            spinnerOr.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long,
                ) {
                    val selectedItem = parent.getItemAtPosition(position) as KeyOry
                    viewModel.spinnerSaveState(
                        spinnerOryParameter = SpinnerOryParameter.OR,
                        selectSpinner = selectedItem
                    )
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
            spinnerVoltage.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long,
                ) {
                    val selectedItem = parent.getItemAtPosition(position) as VoltageOry
                    viewModel.spinnerSaveState(
                        spinnerOryParameter = SpinnerOryParameter.VOLTAGE,
                        selectSpinner = selectedItem
                    )
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
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