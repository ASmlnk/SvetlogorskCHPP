package com.example.svetlogorskchpp.openSwitchgear

import android.os.Bundle
import android.util.DisplayMetrics
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.svetlogorskchpp.R
import com.example.svetlogorskchpp.databinding.FragmentOpenSwitchgearBinding
import com.example.svetlogorskchpp.model.powerLines.OverheadPowerLines

class OpenSwitchgear : Fragment() {

    private var _binding: FragmentOpenSwitchgearBinding? = null
    private val binding get() = _binding!!

    private val viewModel: OpenSwitchgearViewModel by viewModels()
    val adapter = OpenSwitchgearAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOpenSwitchgearBinding.inflate(inflater, container, false)

      //  binding.recycleLep.layoutManager = LinearLayoutManager(context)

        val display = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(display)
        val h = display.heightPixels
        binding.recycleLep.layoutParams.height = h //* 80 / 100
        binding.recycleLep.adapter = adapter
        binding.apply {
            chipOry.setOnCheckedChangeListener { _, b ->
                chipFilter(filter = "ОРУ", chipChecked = b)
            }
            chipOry110.setOnCheckedChangeListener { _, b ->
                chipFilter(filter = "ОРУ-110", chipChecked = b)
            }
            chipOry220.setOnCheckedChangeListener { _, b ->
                chipFilter(filter = "ОРУ-220", chipChecked = b)
            }
            imageView5.setOnLongClickListener {
               // findNavController().navigate(R.id.action_openSwitchgear_to_homePageFragment)
                return@setOnLongClickListener true
            }
        }

        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.liveData.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
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
}