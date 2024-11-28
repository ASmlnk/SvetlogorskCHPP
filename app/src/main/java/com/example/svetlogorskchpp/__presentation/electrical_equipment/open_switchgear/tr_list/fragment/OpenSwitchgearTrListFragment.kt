package com.example.svetlogorskchpp.__presentation.electrical_equipment.open_switchgear.tr_list.fragment

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
import com.example.svetlogorskchpp.__presentation.electrical_equipment.open_switchgear.tr_list.view_model.OpenSwitchgearTrListViewModel
import com.example.svetlogorskchpp.databinding.FragmentOpenSwitchgearTrBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.getValue

@AndroidEntryPoint
class OpenSwitchgearTrListFragment: BaseFragment<FragmentOpenSwitchgearTrBinding>() {

    private val viewModel: OpenSwitchgearTrListViewModel by viewModels()

    private val adapter = ElectricalEquipmentAdapter { dl,id ->
        val action = OpenSwitchgearTrListFragmentDirections.actionOpenSwitchgearTrListFragmentToOpenSwitchgearTrDialog (id)
        findNavController().navigate(action)
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentOpenSwitchgearTrBinding {
        return FragmentOpenSwitchgearTrBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.electricalEquipmentStateFlow.collect { listTr ->
                    val sortedList = listTr.sortedBy { it.nameNumber }
                    adapter.submitList(sortedList)
                }
            }
        }

        binding.apply {
            rv.adapter = adapter
        }
    }
}