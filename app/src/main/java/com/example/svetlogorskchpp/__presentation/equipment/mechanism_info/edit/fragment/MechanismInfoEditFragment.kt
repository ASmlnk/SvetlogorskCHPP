package com.example.svetlogorskchpp.__presentation.equipment.mechanism_info.edit.fragment

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.example.svetlogorskchpp.BaseFragment
import com.example.svetlogorskchpp.R
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.ElGeneralCategory
import com.example.svetlogorskchpp.__presentation.electrical_equipment.adapter.CustomSpinnerElGeneralCategoryAdapter
import com.example.svetlogorskchpp.__presentation.equipment.mechanism_info.edit.view_model.MechanismInfoEditViewModel
import com.example.svetlogorskchpp.__presentation.equipment.mechanism_info.factory.MechanismEditViewModelFactory
import com.example.svetlogorskchpp.__presentation.equipment.mechanism_info.model.MechanismInfoUIState
import com.example.svetlogorskchpp.databinding.FragmentMechanismInfoEditBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.getValue

@AndroidEntryPoint
class MechanismInfoEditFragment : BaseFragment<FragmentMechanismInfoEditBinding>() {

    private val args: MechanismInfoEditFragmentArgs by navArgs()

    @Inject
    lateinit var viewModelFactory: MechanismEditViewModelFactory
    private val viewModel: MechanismInfoEditViewModel by viewModels {
        MechanismInfoEditViewModel.Companion.providesFactory(
            factory = viewModelFactory,
            id = args.id
        )
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentMechanismInfoEditBinding {
        return FragmentMechanismInfoEditBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {

            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.etUIState.collect {
                    setupUI(it)
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
        setupSpinner()
        binding.apply {
            bSave.setOnClickListener {
                val selectState = saveEditText()
                viewModel.saveParameterItem(selectState)
            }
            ivUpdate.setOnClickListener {
                viewModel.newItem()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        val state = saveEditText()
        viewModel.saveState(state)
    }

    private fun setupUI(state: MechanismInfoUIState) {
        binding.apply {
            etName.setText(state.name)
            etInfo.setText(state.info)
            etAdditionally.setText(state.additionally)
            spinnerGeneralCategory.setSelection(viewModel.listGenCategory.indexOf(state.category))
        }
    }

    private fun saveEditText(): MechanismInfoUIState =
        viewModel.etUIState.value.copy(
            name = binding.etName.text.toString(),
            info = binding.etInfo.text.toString(),
            additionally = binding.etAdditionally.text.toString()
        )


    private fun setupSpinner() {

        val elGeneralCategoryAdapter =
            CustomSpinnerElGeneralCategoryAdapter(requireContext(), viewModel.listGenCategory)

        binding.apply {

            spinnerGeneralCategory.adapter = elGeneralCategoryAdapter

            spinnerGeneralCategory.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View?,
                        position: Int,
                        id: Long,
                    ) {
                        val selectedItem = parent.getItemAtPosition(position) as ElGeneralCategory
                        val selectState = saveEditText().copy(category = selectedItem)
                        viewModel.saveState(selectState)
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }
                }
        }
    }

    private fun showCustomSnackbar(view: View, text: String) {

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