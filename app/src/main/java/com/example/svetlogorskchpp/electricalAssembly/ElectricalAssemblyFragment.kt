package com.example.svetlogorskchpp.electricalAssembly

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.svetlogorskchpp.R
import com.example.svetlogorskchpp.databinding.FragmentElectricMotorBinding
import com.example.svetlogorskchpp.databinding.FragmentElectricalAssemblyBinding

class ElectricalAssemblyFragment : Fragment() {

    private var _binding: FragmentElectricalAssemblyBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentElectricalAssemblyBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.dialogButton.setOnClickListener {
            findNavController().navigate(R.id.action_electricalAssemblyFragment_to_dialogFragmentAssembly)
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}