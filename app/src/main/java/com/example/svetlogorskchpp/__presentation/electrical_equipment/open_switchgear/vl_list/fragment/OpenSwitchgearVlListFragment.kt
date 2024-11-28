package com.example.svetlogorskchpp.__presentation.electrical_equipment.open_switchgear.vl_list.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.svetlogorskchpp.BaseFragment
import com.example.svetlogorskchpp.R
import com.example.svetlogorskchpp.__presentation.electrical_equipment.adapter.ElectricalEquipmentAdapter
import com.example.svetlogorskchpp.__presentation.electrical_equipment.open_switchgear.vl_list.view_model.OpenSwitchgearVlListViewModel
import com.example.svetlogorskchpp.__presentation.home_page.EquipmentFilter
import com.example.svetlogorskchpp.databinding.FragmentOpenSwitchgearVlBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OpenSwitchgearVlListFragment : BaseFragment<FragmentOpenSwitchgearVlBinding>() {

    val args: OpenSwitchgearVlListFragmentArgs by navArgs()

    private val viewModel: OpenSwitchgearVlListViewModel by viewModels()

    private val adapter = ElectricalEquipmentAdapter { dl,id ->
        val action =
            OpenSwitchgearVlListFragmentDirections.actionOpenSwitchgearVlFragmentToOpenSwitchgearVlDialog(
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
                viewModel.electricalEquipmentStateFlow.collect { listEquipment ->
                    val listFilter =
                        if (args.filter == EquipmentFilter.ORY_VL) listEquipment.filter { it.isVl }
                        else listEquipment.filterNot { it.isVl }
                    adapter.submitList(listFilter)
                }
            }
        }

        binding.apply {
            rv.adapter = adapter
            tvName.text = if (args.filter == EquipmentFilter.ORY_VL) resources.getString(R.string.vl_name) else resources.getString(R.string.shv_ov_tn)
        }
    }
}