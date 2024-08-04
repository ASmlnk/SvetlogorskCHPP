package com.example.svetlogorskchpp.electricMotor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.svetlogorskchpp.databinding.FragmentElectricMotorSearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ElectricMotorSearchFragment : Fragment() {

    private var _binding: FragmentElectricMotorSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ElectricMotorSearchViewModel by viewModels()
    private val adapter = ElectricMotorAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentElectricMotorSearchBinding.inflate(inflater, container, false)

        binding.apply {
            recycleElectricMotor.adapter = adapter

            recycleElectricMotor.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    if (dy != 0) binding.searchView.clearFocus()
                }
            })

            searchView.setOnCloseListener {
                binding.apply {
                    constrainImage.isGone = false
                    recycleElectricMotor.isGone = true
                    layoutSearch.isGone = true
                }
                false
            }

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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.listSearchLiveData.observe(viewLifecycleOwner) {
            adapter.submitList(it) {
                binding.recycleElectricMotor.scrollToPosition(0)
            }
            if (it.isNotEmpty()) {
                binding.apply {
                    recycleElectricMotor.isGone = false
                    constrainImage.isGone = true
                    layoutSearch.isGone = false
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}