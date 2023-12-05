package com.example.svetlogorskchpp.openSwitchgear

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.svetlogorskchpp.databinding.FragmentOpenSwitchgearBinding
import com.example.svetlogorskchpp.model.powerLines.OverheadPowerLines

class OpenSwitchgear : Fragment() {

    private var _binding: FragmentOpenSwitchgearBinding? = null
    private val binding get() = _binding!!

    private val viewModel: OpenSwitchgearViewModel by viewModels()

    private val data = OverheadPowerLines()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOpenSwitchgearBinding.inflate(inflater, container, false)

        val list = data.getAllPowerLines()
        val adapter = OpenSwitchgearAdapter(list)

        binding.recycleLep.apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = adapter }


        return  binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}