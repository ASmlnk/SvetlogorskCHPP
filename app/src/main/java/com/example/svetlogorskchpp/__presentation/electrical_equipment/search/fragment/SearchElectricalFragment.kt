package com.example.svetlogorskchpp.__presentation.electrical_equipment.search.fragment

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isGone
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.svetlogorskchpp.BaseFragment
import com.example.svetlogorskchpp.__presentation.electrical_equipment.adapter.ElectricalEquipmentAdapter
import com.example.svetlogorskchpp.__presentation.electrical_equipment.search.view_model.SearchElectricalViewModel
import com.example.svetlogorskchpp.databinding.FragmentSearchElectricalBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchElectricalFragment: BaseFragment<FragmentSearchElectricalBinding>() {

    private val adapter = ElectricalEquipmentAdapter { dl, id ->
       val deepLink = Uri.parse(dl.link + id)
        findNavController().navigate(deepLink)
    }

    private val viewModel: SearchElectricalViewModel by viewModels()

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentSearchElectricalBinding {
        return FragmentSearchElectricalBinding.inflate(inflater, container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.data.collect {
                    adapter.submitList(it)
                    binding.line.isGone = it.isEmpty()
                }
            }
        }

        binding.apply {
            rv.adapter = adapter

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    viewModel.search(newText.toString())
                    return false
                }
            })
        }
    }
}