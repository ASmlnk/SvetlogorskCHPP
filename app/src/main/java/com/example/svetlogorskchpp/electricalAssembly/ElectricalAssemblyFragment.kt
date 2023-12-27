package com.example.svetlogorskchpp.electricalAssembly

import android.os.Bundle
import android.util.DisplayMetrics
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.svetlogorskchpp.R
import com.example.svetlogorskchpp.databinding.FragmentElectricalAssemblyBinding
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ElectricalAssemblyFragment : Fragment() {

    private var _binding: FragmentElectricalAssemblyBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ElectricalAssemblyViewModel by viewModels()
    private val adapter = ElectricalAssemblyAdapter {
        viewModel.onClickedDialogAssembly(it)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentElectricalAssemblyBinding.inflate(inflater, container, false)
        val view = binding.root
        val display = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(display)
        val h = display.heightPixels
        binding.apply {
            recyclerLayout.apply {
                layoutParams.height = h * 88 / 100
            }
            constrainMaterialCard.layoutParams.height = h * 88 / 100
            recycleElectricalAssembly.adapter = adapter

            chipHc.setOnCheckedChangeListener { _, b ->
                chipFilter(filter = "ХЦ", chipChecked = b)
            }
            materialCardViewHc.setOnClickListener { activatedChip(chipHc) }

            chipAll.setOnCheckedChangeListener { _, b ->
                chipFilter(filter = "Все", chipChecked = b)
            }
            materialCardViewAll.setOnClickListener { activatedChip(chipAll) }

            chipKtcTo.setOnCheckedChangeListener { _, b ->
                chipFilter(filter = "КТЦ т/о", chipChecked = b)
            }
            materialCardViewKtcTo.setOnClickListener { activatedChip(chipKtcTo) }

            chipKtcKo.setOnCheckedChangeListener { _, b ->
                chipFilter(filter = "КТЦ к/о", chipChecked = b)
            }
            materialCardViewKtcKo.setOnClickListener { activatedChip(chipKtcKo) }

            chipRy.setOnCheckedChangeListener { _, b ->
                chipFilter(filter = "РУ", chipChecked = b)
            }
            materialCardViewRy.setOnClickListener { activatedChip(chipRy) }

            chipBns.setOnCheckedChangeListener { _, b ->
                chipFilter(filter = "БНС", chipChecked = b)
            }
            materialCardViewBns.setOnClickListener { activatedChip(chipBns) }

            chipCoolingTower.setOnCheckedChangeListener { _, b ->
                chipFilter(filter = "Градирня", chipChecked = b)
            }
            materialCardViewCn.setOnClickListener { activatedChip(chipCoolingTower) }

            chipKry.setOnCheckedChangeListener { _, b ->
                chipFilter(filter = "КРУ", chipChecked = b)
            }
            materialCardViewKry.setOnClickListener { activatedChip(chipKry) }

            chipPanelBlock.setOnCheckedChangeListener { _, b ->
                chipFilter(filter = "Щит. блок", chipChecked = b)
            }
            materialCardViewPanelBlock.setOnClickListener { activatedChip(chipPanelBlock) }

            buttonClose.apply {
                setOnClickListener {
                    viewModel.getFilterList("")
                    closeChip(chipGroup = binding.chipGroup, view = view)
                   // viewElectric.smoothScrollTo(0, 0)
                    horizontalScrollView.scrollTo(0, 0)
                    //horizontalScrollView2.scrollTo(0, 0)
                    //recycleElectricMotor.isGone = true
                }
                imageTintList = context?.getColorStateList(R.color.white)
            }
            constrainMaterialCard.isGone = true
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.liveDataAssembly.observe(viewLifecycleOwner) {
          binding.apply {
              constrainMaterialCard.isVisible = true
              progress.isGone = true
          }
        }

        viewModel.listFilterAssembly.observe(viewLifecycleOwner) {
            binding.apply {
                adapter.submitList(it)
                lifecycleScope.launch(Dispatchers.Main) {
                    delay(50)
                    recyclerLayout.isGone = it.isEmpty()
                    buttonClose.isGone = it.isEmpty()
                    constrainMaterialCard.isGone = it.isNotEmpty()
                }
            }
        }

        viewModel.navigateToDialogAssembly.observe(viewLifecycleOwner) {
            it?.let {
            val action = ElectricalAssemblyFragmentDirections.actionElectricalAssemblyFragmentToDialogFragmentAssembly(it)
            this.findNavController().navigate(action)
            viewModel.onDialogNavigation()
            }
        }

        binding.recycleElectricalAssembly.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy > 0) {
                    if (!binding.recyclerLayout.isGone) {
                        binding.apply {
                            buttonClose.hide()
                            //buttonUp.hide()
                        }
                    }
                } else {
                    if (!binding.recyclerLayout.isGone) {
                        binding.apply {
                            buttonClose.show()
                           // buttonUp.show()
                        }
                    }
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun chipFilter(filter: String, chipChecked: Boolean) {
        if (chipChecked) {
            viewModel.getFilterList(filter)
        }
    }

    private fun activatedChip(chip: Chip) {
        lifecycleScope.launch ( Dispatchers.Main ) {
            delay(50)
        chip.isChecked = true
        val dx = chip.x
        binding.horizontalScrollView.scrollTo(dx.toInt(), 0)
        }
    }

    private fun closeChip(chipGroup: ChipGroup, view: View) {
        val chick = chipGroup.checkedChipId
        if (chick > 0) {
            val chip = view.findViewById<Chip>(chick)
            chip.isChecked = false
        }
    }

}