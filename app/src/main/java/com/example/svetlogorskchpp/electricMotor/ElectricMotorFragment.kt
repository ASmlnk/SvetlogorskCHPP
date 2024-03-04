package com.example.svetlogorskchpp.electricMotor

import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.svetlogorskchpp.R
import com.example.svetlogorskchpp.SharedPreferencesManager
import com.example.svetlogorskchpp.databinding.FragmentElectricMotorBinding
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ElectricMotorFragment : Fragment() {

    private var _binding: FragmentElectricMotorBinding? = null
    private val binding get() = _binding!!
    private val adapter = ElectricMotorAdapter()
    private val viewModel: ElectricMotorViewModel by viewModels()

    private val preferencesListener =
        SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
            if (key == "date_electric_motor1") {
                val date = sharedPreferences.getString(key, "0")!!.toLong()
                binding.textDate.text = viewModel.dateFormat(date)
            }
        }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentElectricMotorBinding.inflate(inflater, container, false)
        val view = binding.root

        SharedPreferencesManager.sharedPreferences.registerOnSharedPreferenceChangeListener(
            preferencesListener
        )
        // (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)

        binding.apply {

            chipAll.setOnCheckedChangeListener { _, b ->
                chipFilter(filter = "all", chipChecked = b, chip = chipAll)
            }
            materialCardViewVoltageAll.setOnClickListener {
                activatedChip(listOf(binding.chipAll))
            }

            chipVoltage.setOnCheckedChangeListener { _, b ->
                openChipGroup(chipGroup = binding.chipGroupU, view = view, chipChecked = b)
            }
            chipVoltage04.setOnCheckedChangeListener { _, b ->
                chipFilter(filter = "380", chipChecked = b, chip = chipVoltage04)
            }
            materialCardView04.setOnClickListener {
                activatedChip(listOf(binding.chipVoltage, binding.chipVoltage04))
            }
            chipVoltage315.setOnCheckedChangeListener { _, b ->
                chipFilter(filter = "3000", chipChecked = b, chip = chipVoltage315)
            }
            materialCardView315.setOnClickListener {
                activatedChip(listOf(binding.chipVoltage, binding.chipVoltage315))
            }
            chipVoltage63.setOnCheckedChangeListener { _, b ->
                chipFilter(filter = "6000", chipChecked = b, chip = chipVoltage63)
            }
            materialCardView63.setOnClickListener {
                activatedChip(listOf(binding.chipVoltage, binding.chipVoltage63))
            }

            chipTg.setOnCheckedChangeListener { _, b ->
                openChipGroup(chipGroup = binding.chipGroupTg, view = view, chipChecked = b)
            }
            chipTg1.setOnCheckedChangeListener { _, b ->
                chipFilter(filter = "ТГ-1", chipChecked = b, chip = chipTg1)
            }
            materialCardViewTg1.setOnClickListener {
                activatedChip(listOf(chipTg, chipTg1))
            }
            chipTg3.setOnCheckedChangeListener { _, b ->
                chipFilter(filter = "ТГ-3", chipChecked = b, chip = chipTg3)
            }
            materialCardViewTg3.setOnClickListener {
                activatedChip(listOf(chipTg, chipTg3))
            }
            chipTg4.setOnCheckedChangeListener { _, b ->
                chipFilter(filter = "ТГ-4", chipChecked = b, chip = chipTg4)
            }
            materialCardViewTg4.setOnClickListener {
                activatedChip(listOf(chipTg, chipTg4))
            }
            chipTg5.setOnCheckedChangeListener { _, b ->
                chipFilter(filter = "ТГ-5", chipChecked = b, chip = chipTg5)
            }
            materialCardViewTg5.setOnClickListener {
                activatedChip(listOf(chipTg, chipTg5))
            }
            chipTg6.setOnCheckedChangeListener { _, b ->
                chipFilter(filter = "ТГ-6", chipChecked = b, chip = chipTg6)
            }
            materialCardViewTg6.setOnClickListener {
                activatedChip(listOf(chipTg, chipTg6))
            }
            chipAllTg.setOnCheckedChangeListener { _, b ->
                chipFilter(filter = "ТГ", chipChecked = b, chip = chipAllTg)
            }
            materialCardViewTgAll.setOnClickListener {
                activatedChip(listOf(chipTg, chipAllTg))
            }

            chipKa.setOnCheckedChangeListener { _, b ->
                openChipGroup(chipGroup = binding.chipGroupKa, view = view, chipChecked = b)
            }
            chipKa1.setOnCheckedChangeListener { _, b ->
                chipFilter(filter = "К/А-1", chipChecked = b, chip = chipKa1)
            }
            materialCardViewKa1.setOnClickListener {
                activatedChip(listOf(chipKa, chipKa1))
            }
            chipKa6.setOnCheckedChangeListener { _, b ->
                chipFilter(filter = "К/А-6", chipChecked = b, chip = chipKa6)
            }
            materialCardViewKa6.setOnClickListener {
                activatedChip(listOf(chipKa, chipKa6))
            }
            chipKa7.setOnCheckedChangeListener { _, b ->
                chipFilter(filter = "К/А-7", chipChecked = b, chip = chipKa7)
            }
            materialCardViewKa7.setOnClickListener {
                activatedChip(listOf(chipKa, chipKa7))
            }
            chipKa8.setOnCheckedChangeListener { _, b ->
                chipFilter(filter = "К/А-8", chipChecked = b, chip = chipKa8)
            }
            materialCardViewKa8.setOnClickListener {
                activatedChip(listOf(chipKa, chipKa8))
            }
            chipKa9.setOnCheckedChangeListener { _, b ->
                chipFilter(filter = "К/А-9", chipChecked = b, chip = chipKa9)
            }
            materialCardViewKa9.setOnClickListener {
                activatedChip(listOf(chipKa, chipKa9))
            }
            chipAllKa.setOnCheckedChangeListener { _, b ->
                chipFilter(filter = "К/А", chipChecked = b, chip = chipAllKa)
            }
            materialCardViewKaAll.setOnClickListener {
                activatedChip(listOf(chipKa, chipAllKa))
            }

            chipRep.setOnCheckedChangeListener { _, b ->
                chipFilter(filter = "РЭП", chipChecked = b, chip = chipRep)
            }

            chipKtcTo.setOnCheckedChangeListener { _, b ->
                openChipGroup(chipGroup = binding.chipGroupKtcTo, view = view, chipChecked = b)
            }
            chipPen.setOnCheckedChangeListener { _, b ->
                chipFilter(filter = "ПЭН", chipChecked = b, chip = chipPen)
            }
            materialCardPen.setOnClickListener {
                activatedChip(listOf(chipKtcTo, chipPen))
            }
            chipPn.setOnCheckedChangeListener { _, b ->
                chipFilter(filter = "ПН", chipChecked = b, chip = chipPn)
            }
            materialCardViewPn.setOnClickListener {
                activatedChip(listOf(chipKtcTo, chipPn))
            }
            chipSn.setOnCheckedChangeListener { _, b ->
                chipFilter(filter = "СН", chipChecked = b, chip = chipSn)
            }
            materialCardViewSn.setOnClickListener {
                activatedChip(listOf(chipKtcTo, chipSn))
            }
            chipBnt.setOnCheckedChangeListener { _, b ->
                chipFilter(filter = "БНТ", chipChecked = b, chip = chipBnt)
            }
            materialCardViewBnt.setOnClickListener {
                activatedChip(listOf(chipKtcTo, chipBnt))
            }
            chipCoastal.setOnCheckedChangeListener { _, b ->
                chipFilter(filter = "НДВ", chipChecked = b, chip = chipCoastal)
            }
            materialCardViewCoastal.setOnClickListener {
                activatedChip(listOf(chipKtcTo, chipCoastal))
            }
            chipCoolingTower.setOnCheckedChangeListener { _, b ->
                chipFilter(filter = "ЦН", chipChecked = b, chip = chipCoolingTower)
            }
            materialCardViewCoolingTower.setOnClickListener {
                activatedChip(listOf(chipKtcTo, chipCoolingTower))
            }
            chipAllKtcTo.setOnCheckedChangeListener { _, b ->
                chipFilter(filter = "КТЦ т/о", chipChecked = b, chip = chipAllKtcTo)
            }
            materialCardViewAllKtcTo.setOnClickListener {
                activatedChip(listOf(chipKtcTo, chipAllKtcTo))
            }

            chipKtcKo.setOnCheckedChangeListener { _, b ->
                openChipGroup(chipGroup = binding.chipGroupKtcKo, view = view, chipChecked = b)
            }
            chipBug.setOnCheckedChangeListener { _, b ->
                chipFilter(filter = "Багерная", chipChecked = b, chip = chipBug)
            }
            materialCardBug.setOnClickListener {
                activatedChip(listOf(chipKtcKo, chipBug))
            }
            chipAllKtcKo.setOnCheckedChangeListener { _, b ->
                chipFilter(filter = "КТЦ к/о", chipChecked = b, chip = chipAllKtcKo)
            }
            materialCardAllKtcKo.setOnClickListener {
                activatedChip(listOf(chipKtcKo, chipAllKtcKo))
            }

            chipKtcTy.setOnCheckedChangeListener { _, b ->
                chipFilter(filter = "ТУ", chipChecked = b, chip = chipKtcTy)
            }

            chipEc.setOnCheckedChangeListener { _, b ->
                chipFilter(filter = "ЭЦ", chipChecked = b, chip = chipEc)
            }

            chipHvo.setOnCheckedChangeListener { _, b ->
                openChipGroup(chipGroup = binding.chipGroupHvo, view = view, chipChecked = b)
                //chipFilter(filter = "ХОВ", chipChecked = b, chip = chipHvo)
            }
            chipHvoDesalting.setOnCheckedChangeListener { _, b ->
                chipFilter(filter = "ОБ", chipChecked = b, chip = chipHvoDesalting)
            }
            materialCardHvoDesalting.setOnClickListener {
                activatedChip(listOf(chipHvo, chipHvoDesalting))
            }
            chipHvoPreCleaning.setOnCheckedChangeListener { _, b ->
                chipFilter(filter = "ПР", chipChecked = b, chip = chipHvoPreCleaning)
            }
            materialCardViewHvoPreCleaning.setOnClickListener {
                activatedChip(listOf(chipHvo, chipHvoPreCleaning))
            }
            chipHvoSo.setOnCheckedChangeListener { _, b ->
                chipFilter(filter = "ОС", chipChecked = b, chip = chipHvoSo)
            }
            materialCardViewHvoOS.setOnClickListener {
                activatedChip(listOf(chipHvo, chipHvoSo))
            }
            chipHvoOther.setOnCheckedChangeListener { _, b ->
                chipFilter(filter = "ОСТ", chipChecked = b, chip = chipHvoOther)
            }
            materialCardViewHvoOther.setOnClickListener {
                activatedChip(listOf(chipHvo, chipHvoOther))
            }
            chipHvoAll.setOnCheckedChangeListener { _, b ->
                chipFilter(filter = "ХОВ", chipChecked = b, chip = chipHvoAll)
            }
            materialCardViewHvoAll.setOnClickListener {
                activatedChip(listOf(chipHvo, chipHvoAll))
            }

            chipKry315.setOnCheckedChangeListener { _, b ->
                openChipGroup(chipGroup = binding.chipGroupKry315, view = view, chipChecked = b)
            }
            chipKry3151.setOnCheckedChangeListener { _, b ->
                chipFilter(filter = "К3-1", chipChecked = b, chip = chipKry3151)
            }
            materialCardKry315s1.setOnClickListener {
                activatedChip(listOf(chipKry315, chipKry3151))
            }
            chipKry3153.setOnCheckedChangeListener { _, b ->
                chipFilter(filter = "К3-3", chipChecked = b, chip = chipKry3153)
            }
            materialCardViewKry315s3.setOnClickListener {
                activatedChip(listOf(chipKry315, chipKry3153))
            }
            chipKry3154.setOnCheckedChangeListener { _, b ->
                chipFilter(filter = "К3-4", chipChecked = b, chip = chipKry3154)
            }
            materialCardViewKry315s4.setOnClickListener {
                activatedChip(listOf(chipKry315, chipKry3154))
            }
            chipKry3155.setOnCheckedChangeListener { _, b ->
                chipFilter(filter = "К3-5", chipChecked = b, chip = chipKry3155)
            }
            materialCardViewKry315s5.setOnClickListener {
                activatedChip(listOf(chipKry315, chipKry3155))
            }
            chipKry3156.setOnCheckedChangeListener { _, b ->
                chipFilter(filter = "К3-6", chipChecked = b, chip = chipKry3156)
            }
            materialCardViewKry315s6.setOnClickListener {
                activatedChip(listOf(chipKry315, chipKry3156))
            }
            chipKry3157.setOnCheckedChangeListener { _, b ->
                chipFilter(filter = "К3-7", chipChecked = b, chip = chipKry3157)
            }
            materialCardViewKry315s7.setOnClickListener {
                activatedChip(listOf(chipKry315, chipKry3157))
            }
            chipKry3158.setOnCheckedChangeListener { _, b ->
                chipFilter(filter = "К3-8", chipChecked = b, chip = chipKry3158)
            }
            materialCardViewKry315s8.setOnClickListener {
                activatedChip(listOf(chipKry315, chipKry3158))
            }
            chipKry3159.setOnCheckedChangeListener { _, b ->
                chipFilter(filter = "К3-9", chipChecked = b, chip = chipKry3159)
            }
            materialCardViewKry315s9.setOnClickListener {
                activatedChip(listOf(chipKry315, chipKry3159))
            }

            chipKry63.setOnCheckedChangeListener { _, b ->
                openChipGroup(chipGroup = binding.chipGroupKry63, view = view, chipChecked = b)
            }
            chipKry631.setOnCheckedChangeListener { _, b ->
                chipFilter(filter = "К6-1", chipChecked = b, chip = chipKry631)
            }
            chipKry632.setOnCheckedChangeListener { _, b ->
                chipFilter(filter = "К6-2", chipChecked = b, chip = chipKry632)
            }
            chipKry633.setOnCheckedChangeListener { _, b ->
                chipFilter(filter = "К6-3", chipChecked = b, chip = chipKry633)
            }

            chipRusn.setOnCheckedChangeListener { _, b ->
                openChipGroup(chipGroup = binding.chipGroupRusn, view = view, chipChecked = b)
            }
            chipRusn1.setOnCheckedChangeListener { _, b ->
                chipFilter(filter = "Р-1", chipChecked = b, chip = chipRusn1)
            }
            materialCardRusnS1.setOnClickListener {
                activatedChip(listOf(chipRusn, chipRusn1))
            }
            chipRusn2.setOnCheckedChangeListener { _, b ->
                chipFilter(filter = "Р-2", chipChecked = b, chip = chipRusn2)
            }
            materialCardViewRusnS2.setOnClickListener {
                activatedChip(listOf(chipRusn, chipRusn2))
            }
            chipRusn3.setOnCheckedChangeListener { _, b ->
                chipFilter(filter = "Р-3", chipChecked = b, chip = chipRusn3)
            }
            materialCardViewRusnS3.setOnClickListener {
                activatedChip(listOf(chipRusn, chipRusn3))
            }
            chipRusn4.setOnCheckedChangeListener { _, b ->
                chipFilter(filter = "Р-4", chipChecked = b, chip = chipRusn4)
            }
            materialCardViewRusnS4.setOnClickListener {
                activatedChip(listOf(chipRusn, chipRusn4))
            }
            chipRusn5.setOnCheckedChangeListener { _, b ->
                chipFilter(filter = "Р-5", chipChecked = b, chip = chipRusn5)
            }
            materialCardViewRusnS5.setOnClickListener {
                activatedChip(listOf(chipRusn, chipRusn5))
            }
            chipRusn6.setOnCheckedChangeListener { _, b ->
                chipFilter(filter = "Р-6", chipChecked = b, chip = chipRusn6)
            }
            materialCardViewRusnS6.setOnClickListener {
                activatedChip(listOf(chipRusn, chipRusn6))
            }
            chipRusn7.setOnCheckedChangeListener { _, b ->
                chipFilter(filter = "Р-7", chipChecked = b, chip = chipRusn7)
            }
            materialCardViewRusnS7.setOnClickListener {
                activatedChip(listOf(chipRusn, chipRusn7))
            }

            buttonClose.apply {
                setOnClickListener {
                    viewModel.getFilterList("")
                    closeChip(chipGroup = binding.chipGroupCategory, view = view)
                    viewElectric.smoothScrollTo(0, 0)
                    horizontalScrollView.scrollTo(0, 0)
                    horizontalScrollView2.scrollTo(0, 0)
                    //recycleElectricMotor.isGone = true
                }
                imageTintList = context?.getColorStateList(R.color.white)

            }
            buttonUp.apply {
                imageTintList = context?.getColorStateList(R.color.white)
                setOnClickListener {
                    //viewElectric.smoothScrollTo(0, 0)
                    recycleElectricMotor.smoothScrollToPosition(0)
                }
            }

            imageView5.setOnLongClickListener {
              // findNavController().navigate(R.id.action_electricMotorFragment_to_blankFragment)
                return@setOnLongClickListener true
            }

            chipFilterMenu.isGone = true
            cardViewGroupVoltage.isGone = true
            textDate.text = viewModel.getDataPref()
            // recycleElectricMotor.setHasFixedSize(false)
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recycleElectricMotor.adapter = adapter
        val display = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(display)
        val h = display.heightPixels
        binding.recycleElectricMotor.apply {
            layoutParams.height = h * 85 / 100
            isGone = true
        }

        viewModel.listFilterLiveData.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            binding.apply {
                cardViewGroupVoltage.isVisible = it.isEmpty()
                chipGroupCategory.isSelectionRequired = it.isNotEmpty()
                textFilter.isVisible = it.isNotEmpty()
                recycleElectricMotor.isVisible = it.isNotEmpty()
            }
            binding.viewElectric.smoothScrollTo(0, 0)
            if (it.isEmpty()) {
                binding.apply {
                    recycleElectricMotor.isGone = true
                    buttonClose.isGone = true
                    buttonUp.isGone = true
                }
            }
        }

        binding.recycleElectricMotor.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy > 0) {
                    if (!binding.recycleElectricMotor.isGone) {
                        binding.apply {
                            buttonClose.hide()
                            buttonUp.hide()
                        }
                    }
                    // binding.buttonClose.isGone = true
                    // binding.buttonUp.isGone = true
                } else {
                    if (!binding.recycleElectricMotor.isGone) {
                        binding.apply {
                            buttonClose.show()
                            buttonUp.show()
                        }
                    }
                    //  binding.buttonClose.isGone = false
                    //  binding.buttonUp.isGone = false
                }
            }
        })

        viewModel.dataElectricMotor.observe(viewLifecycleOwner) {
            binding.apply {
                chipFilterMenu.isVisible = true
                cardViewGroupVoltage.isVisible = true
                progress.isGone = true
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (!binding.recycleElectricMotor.isGone) binding.cardViewGroupVoltage.isGone = true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        SharedPreferencesManager.sharedPreferences.unregisterOnSharedPreferenceChangeListener(
            preferencesListener
        )
    }

    private fun chipFilter(filter: String, chipChecked: Boolean, chip: Chip) {
        if (chipChecked) {
            viewModel.getFilterList(filter)
            binding.textFilter.text = requireContext().getString(R.string.filter, chip.text)
        }
    }

    private fun openChipGroup(chipGroup: ChipGroup, view: View, chipChecked: Boolean) {
        chipGroup.isVisible = chipChecked
        if (!chipChecked) closeChip(chipGroup = chipGroup, view = view)
    }

    private fun closeChip(chipGroup: ChipGroup, view: View) {
        val chick = chipGroup.checkedChipId
        if (chick > 0) {
            val chip = view.findViewById<Chip>(chick)
            chip.isChecked = false
        }
    }

    private fun activatedChip(list: List<Chip>) {
        list.forEach { it.isChecked = true }
        lifecycleScope.launch(Dispatchers.Main) {
           delay(50)
            val chip1 = list[0]
            val dx = chip1.x
            binding.horizontalScrollView.scrollTo(dx.toInt(), 0)
            if (list.getOrNull(1) != null) {
                val chip2 = list.getOrNull(1)
                val dx2 = chip2!!.x
                binding.horizontalScrollView2.smoothScrollTo(dx2.toInt(), 0)
            }
        }
    }
}