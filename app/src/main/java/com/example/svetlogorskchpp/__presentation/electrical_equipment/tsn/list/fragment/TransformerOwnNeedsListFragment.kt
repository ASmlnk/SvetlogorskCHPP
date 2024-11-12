package com.example.svetlogorskchpp.__presentation.electrical_equipment.tsn.list.fragment

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
import com.example.svetlogorskchpp.__presentation.electrical_equipment.tsn.list.view_model.TransformerOwnNeedsListViewModel
import com.example.svetlogorskchpp.databinding.FragmentTsnListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TransformerOwnNeedsListFragment: BaseFragment<FragmentTsnListBinding>() {

    val args: TransformerOwnNeedsListFragmentArgs by navArgs()
    private val viewModel: TransformerOwnNeedsListViewModel by viewModels()

    private val adapter = ElectricalEquipmentAdapter { id ->
        val action =
            TransformerOwnNeedsListFragmentDirections.actionTransformerOwnNeedsListFragmentToEquipmentTsnDialog(
                id
            )
        findNavController().navigate(action)
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentTsnListBinding {
        return FragmentTsnListBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.dataFlow.collect { list ->
                    adapter.submitList(list)
                }
            }
        }

        binding.apply {
            rv.adapter = adapter
            tvName.text = resources.getString(R.string.tsn_small_title, args.voltage.str)
        }

    }
}