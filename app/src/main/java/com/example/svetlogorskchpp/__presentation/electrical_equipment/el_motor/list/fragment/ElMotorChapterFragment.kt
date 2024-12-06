package com.example.svetlogorskchpp.__presentation.electrical_equipment.el_motor.list.fragment

import android.net.Uri
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
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.ElCategory
import com.example.svetlogorskchpp.__presentation.electrical_equipment.adapter.ElectricalEquipmentAdapter
import com.example.svetlogorskchpp.__presentation.electrical_equipment.el_motor.factory.ElMotorChapterViewModelFactory
import com.example.svetlogorskchpp.__presentation.electrical_equipment.el_motor.list.view_model.ElMotorChapterViewModel
import com.example.svetlogorskchpp.__presentation.electrical_equipment.model.ElMotorChapter
import com.example.svetlogorskchpp.databinding.FragmentElMotorListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ElMotorChapterFragment : BaseFragment<FragmentElMotorListBinding>() {

    private val args: ElMotorChapterFragmentArgs by navArgs()

    @Inject
    lateinit var viewModelFactory: ElMotorChapterViewModelFactory
    private val viewModel: ElMotorChapterViewModel by viewModels {
        ElMotorChapterViewModel.providesFactory(
            factory = viewModelFactory,
            elMotorChapter = args.chapter
        )
    }

    private val adapter = ElectricalEquipmentAdapter { dl, id ->
        val deepLink = Uri.parse(dl.link + id)
        findNavController().navigate(deepLink)
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentElMotorListBinding {
        return FragmentElMotorListBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    adapter.submitList(it.sortedBy { it.name.lowercase() })
                }
            }
        }

        binding.apply {
            rv.adapter = adapter
            tvNameChapter.text = args.chapter.str

            when (args.chapter) {
                ElMotorChapter.OTHER -> layoutChip.isGone = true
                ElMotorChapter.HOV -> chipGroupHov.isGone = false
                ElMotorChapter.KTC_TO -> chipGroupKtcTo.isGone = false
                ElMotorChapter.KTC_KO -> chipGroupKtcKo.isGone = false
                ElMotorChapter.TY -> layoutChip.isGone = true
                ElMotorChapter.EC -> layoutChip.isGone = true
                ElMotorChapter.KA -> chipGroupKa.isGone = false
                ElMotorChapter.RG -> chipGroupTg.isGone = false
                ElMotorChapter.REP -> layoutChip.isGone = true
            }

            chipTg1.setOnClickListener { applyFilter() }
            chipTg3.setOnClickListener { applyFilter() }
            chipTg4.setOnClickListener { applyFilter() }
            chipTg5.setOnClickListener { applyFilter() }
            chipTg6.setOnClickListener { applyFilter() }
            chipKa1.setOnClickListener { applyFilter() }
            chipKa6.setOnClickListener { applyFilter() }
            chipKa7.setOnClickListener { applyFilter() }
            chipKa8.setOnClickListener { applyFilter() }
            chipKa9.setOnClickListener { applyFilter() }
            chipPen.setOnClickListener { applyFilter() }
            chipPn.setOnClickListener { applyFilter() }
            chipSn.setOnClickListener { applyFilter() }
            chipBnt.setOnClickListener { applyFilter() }
            chipCoastal.setOnClickListener { applyFilter() }
            chipCoolingTower.setOnClickListener { applyFilter() }
            chipBug.setOnClickListener { applyFilter() }
            chipHvoSo.setOnClickListener { applyFilter() }
            chipHvoOther.setOnClickListener { applyFilter() }
            chipHvoDesalting.setOnClickListener { applyFilter() }
            chipHvoPreCleaning.setOnClickListener { applyFilter() }
        }
    }

    private fun FragmentElMotorListBinding.applyFilter() {
        val activeFilters = mutableListOf<ElCategory>()
        if (chipTg1.isChecked) activeFilters.add(ElCategory.TG_1)
        if (chipTg3.isChecked) activeFilters.add(ElCategory.TG_3)
        if (chipTg4.isChecked) activeFilters.add(ElCategory.TG_4)
        if (chipTg5.isChecked) activeFilters.add(ElCategory.TG_5)
        if (chipTg6.isChecked) activeFilters.add(ElCategory.TG_6)
        if (chipKa1.isChecked) activeFilters.add(ElCategory.KA_1)
        if (chipKa6.isChecked) activeFilters.add(ElCategory.KA_6)
        if (chipKa7.isChecked) activeFilters.add(ElCategory.KA_7)
        if (chipKa8.isChecked) activeFilters.add(ElCategory.KA_8)
        if (chipKa9.isChecked) activeFilters.add(ElCategory.KA_9)
        if (chipPen.isChecked) activeFilters.add(ElCategory.PEN)
        if (chipPn.isChecked) activeFilters.add(ElCategory.PN)
        if (chipSn.isChecked) activeFilters.add(ElCategory.SN)
        if (chipBnt.isChecked) activeFilters.add(ElCategory.BNT)
        if (chipCoastal.isChecked) activeFilters.add(ElCategory.NDV)
        if (chipCoolingTower.isChecked) activeFilters.add(ElCategory.CN)
        if (chipBug.isChecked) activeFilters.add(ElCategory.Bagernaya)
        if (chipHvoSo.isChecked) activeFilters.add(ElCategory.TREATMENT_FACILITIES)
        if (chipHvoOther.isChecked) activeFilters.addAll(
            listOf(
                ElCategory.AMMONIA,
                ElCategory.GIDROZIYNOE,
                ElCategory.ACIDIC,
                ElCategory.COAGULANT,
                ElCategory.N_TS,
                ElCategory.SOVEVOE,
                ElCategory.LIMESTONE,
                ElCategory.PHOSPHATE,
                ElCategory.NVK,
                ElCategory.ALKALINE,
                ElCategory.OTHER
            )
        )
        if (chipHvoDesalting.isChecked) activeFilters.add(ElCategory.DESALTING)
        if (chipHvoPreCleaning.isChecked) activeFilters.add(ElCategory.PRETREATMENT)
        viewModel.filterCategory(activeFilters)
    }
}