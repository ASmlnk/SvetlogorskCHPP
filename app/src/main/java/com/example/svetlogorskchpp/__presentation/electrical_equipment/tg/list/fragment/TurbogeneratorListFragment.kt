package com.example.svetlogorskchpp.__presentation.electrical_equipment.tg.list.fragment

import android.os.Bundle
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
import com.example.svetlogorskchpp.__presentation.electrical_equipment.tg.list.view_model.TurboGeneratorListViewModel
import com.example.svetlogorskchpp.databinding.FragmentTgListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.getValue

@AndroidEntryPoint
class TurbogeneratorListFragment: BaseFragment<FragmentTgListBinding>() {

    private val viewModel: TurboGeneratorListViewModel by viewModels()

    private val adapter = ElectricalEquipmentAdapter { dl,id ->
        val action =
            TurbogeneratorListFragmentDirections.actionTurbogeneratorListFragmentToTurbogeneratorDialog(
                id
            )
        findNavController().navigate(action)
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentTgListBinding {
        return FragmentTgListBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.dataFlow.collect { list ->
                    adapter.submitList(list)
                }
            }
        }

        binding.apply {
            rv.adapter = adapter
        }
    }
}