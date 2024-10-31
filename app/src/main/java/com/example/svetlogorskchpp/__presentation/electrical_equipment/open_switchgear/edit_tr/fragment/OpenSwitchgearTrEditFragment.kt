package com.example.svetlogorskchpp.__presentation.electrical_equipment.open_switchgear.edit_tr.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.svetlogorskchpp.BaseFragment
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.KeyOry
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.Voltage
import com.example.svetlogorskchpp.__presentation.electrical_equipment.open_switchgear.adapter.ProtectionEditAdapter
import com.example.svetlogorskchpp.__presentation.electrical_equipment.open_switchgear.edit_tr.view_model.OpenSwitchgearTrEditViewModel
import com.example.svetlogorskchpp.__presentation.electrical_equipment.open_switchgear.edit_vl.adapter.CustomSpinnerAdapter
import com.example.svetlogorskchpp.__presentation.electrical_equipment.open_switchgear.edit_vl.adapter.CustomSpinnerVoltageAdapter
import com.example.svetlogorskchpp.__presentation.electrical_equipment.open_switchgear.factory.OpenSwitchgearTrEditViewModelFactory
import com.example.svetlogorskchpp.__presentation.electrical_equipment.open_switchgear.model.SpinnerOryParameter
import com.example.svetlogorskchpp.__presentation.electrical_equipment.open_switchgear.model.VoltageSide
import com.example.svetlogorskchpp.databinding.ContentLayoutEditOryNameBinding
import com.example.svetlogorskchpp.databinding.ContentLayoutEditOryParameterBinding
import com.example.svetlogorskchpp.databinding.ContentLayoutEditOryRzaBinding
import com.example.svetlogorskchpp.databinding.ContentLayoutEditTypeTrBinding
import com.example.svetlogorskchpp.databinding.FragmentOpenSwitchgearTrEditBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.getValue

@AndroidEntryPoint
class OpenSwitchgearTrEditFragment : BaseFragment<FragmentOpenSwitchgearTrEditBinding>() {

    private val args: OpenSwitchgearTrEditFragmentArgs by navArgs()

    private lateinit var phaseProtectionAdapter: ProtectionEditAdapter
    private lateinit var earthProtectionAdapter: ProtectionEditAdapter

    private val listKeys =
        listOf(KeyOry.KEY_0, KeyOry.KEY_1, KeyOry.KEY_2, KeyOry.KEY_3, KeyOry.KEY_4)
    private val listVoltage =
        listOf(Voltage.KV, Voltage.KV_220, Voltage.KV_110, Voltage.KV_35)

    @Inject
    lateinit var viewModelFactory: OpenSwitchgearTrEditViewModelFactory
    private val viewModel: OpenSwitchgearTrEditViewModel by viewModels {
        OpenSwitchgearTrEditViewModel.providesFactory(
            openSwitchgearTrEditViewModelFactory = viewModelFactory,
            idTr = args.id
        )
    }

    private var _includeOryVnParameterBinding: ContentLayoutEditOryParameterBinding? = null
    private val includeOryVnParameterBinding get() = _includeOryVnParameterBinding!!

    private var _includeOrySnParameterBinding: ContentLayoutEditOryParameterBinding? = null
    private val includeOrySnParameterBinding get() = _includeOrySnParameterBinding!!

    private var _includeOryNameBinding: ContentLayoutEditOryNameBinding? = null
    private val includeOryNameBinding get() = _includeOryNameBinding!!

    private var _includeOryRzaBinding: ContentLayoutEditOryRzaBinding? = null
    private val includeOryRzaBinding get() = _includeOryRzaBinding!!

    private var _includeOryTypeTr: ContentLayoutEditTypeTrBinding? = null
    private val includeOryTypeTr get() = _includeOryTypeTr!!

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentOpenSwitchgearTrEditBinding {
        return FragmentOpenSwitchgearTrEditBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _includeOryTypeTr = ContentLayoutEditTypeTrBinding.bind(binding.contentTypeTr.root)
        _includeOryNameBinding = ContentLayoutEditOryNameBinding.bind(binding.contentOryName.root)
        _includeOryVnParameterBinding = ContentLayoutEditOryParameterBinding.bind(binding.contentOryParameterVn.root)
        _includeOrySnParameterBinding = ContentLayoutEditOryParameterBinding.bind(binding.contentOryParameterSrn.root)
        _includeOryRzaBinding = ContentLayoutEditOryRzaBinding.bind(binding.contentRza.root)

        setupSpinner(includeOryVnParameterBinding, VoltageSide.VN)
        setupSpinner(includeOrySnParameterBinding, VoltageSide.SN)
        setupProtectionView()
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

    private fun setupSpinner(binding: ContentLayoutEditOryParameterBinding, voltageSide: VoltageSide) {

        val adapterSpinner = CustomSpinnerAdapter(requireContext(), listKeys)
        val voltageAdapter = CustomSpinnerVoltageAdapter(requireContext(), listVoltage)

        binding.apply {
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
                        selectSpinner = selectedItem,
                        voltageSide = voltageSide
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
                        selectSpinner = selectedItem,
                        voltageSide = voltageSide
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
                        selectSpinner = selectedItem,
                        voltageSide = voltageSide
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
                        selectSpinner = selectedItem,
                        voltageSide = voltageSide
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
                    val selectedItem = parent.getItemAtPosition(position) as Voltage
                    viewModel.spinnerSaveState(
                        spinnerOryParameter = SpinnerOryParameter.VOLTAGE,
                        selectSpinner = selectedItem,
                        voltageSide = voltageSide
                    )
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
        }
    }
}
