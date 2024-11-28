package com.example.svetlogorskchpp.__presentation.electrical_equipment.switchgear.list.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.svetlogorskchpp.BaseFragment
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.ElAssembly
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.NameDepartment
import com.example.svetlogorskchpp.__presentation.electrical_equipment.adapter.ElectricalEquipmentAdapter
import com.example.svetlogorskchpp.__presentation.electrical_equipment.switchgear.list.view_model.SwitchgearOwnNeedsListViewModel
import com.example.svetlogorskchpp.databinding.FragmentSwitchgearOwnNeedsListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SwitchgearOwnNeedsListFragment: BaseFragment<FragmentSwitchgearOwnNeedsListBinding>() {

    private val viewModel: SwitchgearOwnNeedsListViewModel by viewModels()
    val args: SwitchgearOwnNeedsListFragmentArgs by navArgs()

    private val adapter = ElectricalEquipmentAdapter { dl,id ->
        val action =
            SwitchgearOwnNeedsListFragmentDirections.actionSwitchgearOwnNeedsListFragmentToSwitchgearOwnNeedsFragment(id)
        findNavController().navigate(action)
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSwitchgearOwnNeedsListBinding {
        return FragmentSwitchgearOwnNeedsListBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            rv.adapter = adapter
            chipRtzo.setOnClickListener {applyFilter()}
            chipShpt1.setOnClickListener {applyFilter()}
            chipShpt2.setOnClickListener {applyFilter()}
            chipLighting.setOnClickListener {applyFilter()}
            chipAssembly.setOnClickListener {applyFilter()}

            tvNameDepartment.text = args.nameDepartment.str

            chipVisible()


            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.dataFlow.collect {
                        adapter.submitList(
                            it.filter { it.nameDepartment == args.nameDepartment }
                                .sortedBy { it.name }
                        )
                    }
                }
            }
        }

    }

    private fun FragmentSwitchgearOwnNeedsListBinding.applyFilter() {
        val activeFilters = mutableListOf<ElAssembly>()
        if(chipRtzo.isChecked) activeFilters.add(ElAssembly.RTZO)
        if(chipShpt1.isChecked) activeFilters.add(ElAssembly.SHPT_1)
        if(chipShpt2.isChecked) activeFilters.add(ElAssembly.SHPT_2)
        if(chipLighting.isChecked) activeFilters.add(ElAssembly.LIGHTING)
        if(chipAssembly.isChecked) activeFilters.add(ElAssembly.ASSEMBLY)
        viewModel.filterCategory(activeFilters)
    }

    private fun FragmentSwitchgearOwnNeedsListBinding.chipVisible() {
        when (args.nameDepartment) {
            NameDepartment.BNS -> {
                chipShpt1.isGone = true
                chipShpt2.isGone = true
            }

            NameDepartment.COOLING_TOWER -> {
                chipShpt1.isGone = true
                chipShpt2.isGone = true
            }

            NameDepartment.KRY -> {
                chipGroup.visibility = View.INVISIBLE

            }

            NameDepartment.KTC_KO -> {
                chipShpt1.isGone = true
                chipShpt2.isGone = true
            }

            NameDepartment.KTC_TO -> {
                chipShpt1.isGone = true
                chipShpt2.isGone = true
            }

            NameDepartment.RY -> {
                chipGroup.visibility = View.INVISIBLE
            }

            NameDepartment.HC -> {
                chipShpt1.isGone = true
                chipShpt2.isGone = true
            }

            NameDepartment.SHIELD_BLOCK -> {
                chipGroup.visibility = View.INVISIBLE
            }

            NameDepartment.OTHER -> {
                chipShpt1.isGone = true
                chipShpt2.isGone = true
            }

            NameDepartment.POST_TOK -> {
                chipLighting.isGone = true
                chipRtzo.isGone = true
            }
        }
    }
}