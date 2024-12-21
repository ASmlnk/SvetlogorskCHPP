package com.example.svetlogorskchpp.__presentation.equipment.mechanism_info.list.fragment

import android.net.Uri
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
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.ElGeneralCategory
import com.example.svetlogorskchpp.__presentation.electrical_equipment.adapter.ElectricalEquipmentAdapter
import com.example.svetlogorskchpp.__presentation.equipment.mechanism_info.list.view_model.MechanismInfoListViewModel
import com.example.svetlogorskchpp.databinding.FragmentMechanismInfoListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MechanismInfoListFragment: BaseFragment<FragmentMechanismInfoListBinding>() {

    private val adapter = ElectricalEquipmentAdapter { dl, id ->
        val deepLink = Uri.parse(dl.link + id)
        findNavController().navigate(deepLink)
    }

    private val viewModel: MechanismInfoListViewModel by viewModels()

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentMechanismInfoListBinding {
        return FragmentMechanismInfoListBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.dataFlow.collect {
                    adapter.submitList(it.sortedBy { it.name() })
                }
            }
        }

        binding.apply {
            rv.adapter = adapter

            chipHc.setOnClickListener {applyFilter()}
            chipOther.setOnClickListener {applyFilter()}
            chipKa.setOnClickListener {applyFilter()}
            chipTg.setOnClickListener {applyFilter()}
            chipKtcTy.setOnClickListener {applyFilter()}
            chipKtcKo.setOnClickListener {applyFilter()}
            chipKtcTo.setOnClickListener {applyFilter()}
        }

    }

    private fun FragmentMechanismInfoListBinding.applyFilter() {
        val activeFilters = mutableListOf<ElGeneralCategory>()
        if  (chipHc.isChecked) activeFilters.add(ElGeneralCategory.HOV)
        if  (chipOther.isChecked) activeFilters.add(ElGeneralCategory.OTHER)
        if  (chipKa.isChecked) activeFilters.add(ElGeneralCategory.KA)
        if  (chipTg.isChecked) activeFilters.add(ElGeneralCategory.RG)
        if  (chipKtcTy.isChecked) activeFilters.add(ElGeneralCategory.TY)
        if  (chipKtcKo.isChecked) activeFilters.add(ElGeneralCategory.KTC_KO)
        if  (chipKtcTo.isChecked) activeFilters.add(ElGeneralCategory.KTC_TO)

        viewModel.filterData(activeFilters)
    }
}