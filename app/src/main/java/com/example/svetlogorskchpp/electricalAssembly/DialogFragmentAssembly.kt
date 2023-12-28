package com.example.svetlogorskchpp.electricalAssembly

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.svetlogorskchpp.R
import com.example.svetlogorskchpp.databinding.DialogFragmentAssemblyBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DialogFragmentAssembly : BottomSheetDialogFragment() {

    private var _binding: DialogFragmentAssemblyBinding? = null
    private val binding get() = _binding!!
    private val adapter = ItemElectricalAssemblyAdapter()

    override fun getTheme() = R.style.AppBottomSheetDialogTheme

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = DialogFragmentAssemblyBinding.bind(inflater.inflate(R.layout.dialog_fragment_assembly, container))


        val idElectricalAssembly =
            DialogFragmentAssemblyArgs.fromBundle(requireArguments()).idElectricalAssembly


        val viewModelFactory = DialogAssemblyViewModelFactory(idElectricalAssembly)
        val viewModel =
            ViewModelProvider(this, viewModelFactory)[DialogFragmentAssemblyViewModel::class.java]

        viewModel.electricalAssembly.observe(viewLifecycleOwner) { electricalAssembly ->
            adapter.submitList(electricalAssembly.listItemAssembly.sortedBy { it.name })
            binding.textNameElectricalAssembly.text = electricalAssembly.nameAssembly
            binding.textInputAssembly.text = electricalAssembly.inputAssembly.replace(
                "\\n",
                System.getProperty("line.separator")
            )
            binding.imageView6.setImageResource(
                when (electricalAssembly.nameDepartment) {
                    "ХЦ" -> R.drawable.chemistry_class_6837437
                    "КТЦ т/о" -> R.drawable.factory_1643683
                    "КТЦ к/о" -> R.drawable.water_boiler
                    "БНС" -> R.drawable.water_tank_9614228
                    "Градирня" -> R.drawable.power_plant_4491274
                    "Щит. блок" -> R.drawable.high_voltage_8107242
                    "КРУ" -> R.drawable.power_supply_283291
                    "РУ" -> R.drawable.electrical_panel_1
                    else -> R.drawable.el_panel
                }
            )
        }


        binding.recycleItemElectricAssembly.adapter = adapter
        val view = binding.root

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}