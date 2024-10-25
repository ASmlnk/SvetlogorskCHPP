package com.example.svetlogorskchpp.__presentation.electrical_equipment.open_switchgear.vl_list.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.svetlogorskchpp.BaseFragment
import com.example.svetlogorskchpp.__presentation.electrical_equipment.adapter.ElectricalEquipmentAdapter
import com.example.svetlogorskchpp.__presentation.electrical_equipment.open_switchgear.vl_list.view_model.OpenSwitchgearVlViewModel
import com.example.svetlogorskchpp.databinding.FragmentOpenSwitchgearVlBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OpenSwitchgearVlFragment : BaseFragment<FragmentOpenSwitchgearVlBinding>() {

    private val viewModel: OpenSwitchgearVlViewModel by viewModels()

    private val adapter = ElectricalEquipmentAdapter { id ->
        val action =
            OpenSwitchgearVlFragmentDirections.actionOpenSwitchgearVlFragmentToOpenSwitchgearVlEditFragment(
                id
            )
        findNavController().navigate(action)
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentOpenSwitchgearVlBinding {
        return FragmentOpenSwitchgearVlBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.electricalEquipmentStateFlow.collect {
                    adapter.submitList(it)
                }
            }
        }

        binding.apply {
            rv.adapter = adapter
        }
    }
}