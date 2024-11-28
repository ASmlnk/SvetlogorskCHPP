package com.example.svetlogorskchpp.__presentation.electrical_equipment.switchgear.item.fragment

import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.svetlogorskchpp.BaseFragment
import com.example.svetlogorskchpp.R
import com.example.svetlogorskchpp.__presentation.electrical_equipment.adapter.ElectricalEquipmentAdapter
import com.example.svetlogorskchpp.__presentation.electrical_equipment.switchgear.factory.SwitchgearOwnNeedsViewModelFactory
import com.example.svetlogorskchpp.__presentation.electrical_equipment.switchgear.item.view_model.SwitchgearOwnNeedsViewModel
import com.example.svetlogorskchpp.__presentation.electrical_equipment.switchgear.model.FilterSwitchgear
import com.example.svetlogorskchpp.__presentation.item_decorator.BottomSpaceItemDecorator
import com.example.svetlogorskchpp.databinding.FragmentSwitchgearOwnNeedsBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.getValue

@AndroidEntryPoint
class SwitchgearOwnNeedsFragment : BaseFragment<FragmentSwitchgearOwnNeedsBinding>() {

    private val args: SwitchgearOwnNeedsFragmentArgs by navArgs()

    @Inject
    lateinit var viewModelFactory: SwitchgearOwnNeedsViewModelFactory
    private val viewModel: SwitchgearOwnNeedsViewModel by viewModels {
        SwitchgearOwnNeedsViewModel.providesFactory(
            factory = viewModelFactory,
            id = args.id
        )
    }

    private val adapter = ElectricalEquipmentAdapter { dl, id ->
      //    val action =
      //     SwitchgearOwnNeedsFragmentDirections.actionSwitchgearOwnNeedsFragmentToSwitchgearOwnNeedsDialog(id)
      //  findNavController().navigate(action)
        val deepLink = Uri.parse(dl.link + id)
        findNavController().navigate(deepLink)
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentSwitchgearOwnNeedsBinding {
        return FragmentSwitchgearOwnNeedsBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter.setOnItemClickListener {
            viewModel.deleteItem(it)
        }

        binding.apply {
            rv.adapter = adapter
            rv.addItemDecoration(BottomSpaceItemDecorator(200))

            fbInfoDialog.setOnClickListener {
                val action = SwitchgearOwnNeedsFragmentDirections.actionSwitchgearOwnNeedsFragmentToSwitchgearOwnNeedsDialog(args.id)
                findNavController().navigate(action)
            }

            chipElMotor.setOnClickListener { applyFilter() }
            chipLighting.setOnClickListener { applyFilter() }
            chipAssembly.setOnClickListener { applyFilter() }

            ivActiveDelete.setOnLongClickListener {
                viewModel.activeDelete()
                return@setOnLongClickListener true
            }

        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    binding.apply {
                        tvName.text = it.name
                        tvNameDepartment.text = it.nameDepartment
                        adapter.submitList(it.listSwitchgear.sortedBy { it.name() })
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.toastResultFlow.collect { toast ->
                    showCustomSnackbar(binding.root, toast)
                }
            }
        }
    }

    private fun FragmentSwitchgearOwnNeedsBinding.applyFilter() {
        val activeFilters = mutableListOf<FilterSwitchgear>()
        if (chipElMotor.isChecked) activeFilters.add(FilterSwitchgear.EL_MOTOR)
        if (chipLighting.isChecked) activeFilters.add(FilterSwitchgear.LIGTH)
        if (chipAssembly.isChecked) activeFilters.add(FilterSwitchgear.SWITCHGEAR)

        viewModel.filterCategory(activeFilters)
    }

    fun showCustomSnackbar(view: View, text: String) {

        val snackbar = Snackbar.make(view, text, Snackbar.LENGTH_LONG)

        val snackbarView = snackbar.view
        val background: Drawable? = ContextCompat.getDrawable(
            requireContext(),
            R.drawable.background_snakbar
        )
        snackbarView.background = background

        val params = snackbarView.layoutParams as FrameLayout.LayoutParams
        params.setMargins(0, 100, 0, 0)
        params.gravity = Gravity.TOP
        snackbarView.layoutParams = params

        val textView =
            snackbarView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        textView.apply {
            setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.floatingActionButton
                )
            )  // Цвет текста
            textSize = 18f
        }
        snackbar.show()
    }
}