package com.example.svetlogorskchpp.__presentation.dialog.request_work_filter.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.svetlogorskchpp.R
import com.example.svetlogorskchpp.__domain.en.RequestWorkFilter
import com.example.svetlogorskchpp.__presentation.dialog.BaseBottomSheetDialog
import com.example.svetlogorskchpp.__presentation.dialog.request_work_filter.model.RequestWorkFilterUI
import com.example.svetlogorskchpp.__presentation.dialog.request_work_filter.view_model.RequestWorkFilterViewModel
import com.example.svetlogorskchpp.databinding.DialogRequestWorkFilterBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RequestWorkFilterDialog : BaseBottomSheetDialog<DialogRequestWorkFilterBinding>() {

    private val viewModel: RequestWorkFilterViewModel by viewModels()

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): DialogRequestWorkFilterBinding {
        return DialogRequestWorkFilterBinding.bind(
            inflater.inflate(
                R.layout.dialog_request_work_filter,
                container
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            ivExit.setOnClickListener {
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.saveFilterFlag()
                    delay(100)
                    dismiss()
                }
            }
            tvFilterOther.setOnClickListener {
                viewModel.selectFilter(RequestWorkFilter.OTHER)
            }
            tvFilterDispatcher.setOnClickListener {
                viewModel.selectFilter(RequestWorkFilter.DISPATCHER)
            }
            tvFilterChiefEngineer.setOnClickListener {
                viewModel.selectFilter(RequestWorkFilter.CHIEF_ENGINEER)
            }
            tvFilterAll.setOnClickListener {
                viewModel.selectFilter(RequestWorkFilter.ALL)
            }
            tvFilterClosed.setOnClickListener {
                viewModel.selectFilter(RequestWorkFilter.CLOSED)
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.requestWorkFilterStateUI.collect { stateFilterUI ->
                    viewFilterFlagUI(stateFilterUI)
                }
            }
        }
    }


    override fun onStart() {
        super.onStart()
        val dialog = dialog as BottomSheetDialog
        val bottomSheet = dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)

        // Получаем поведение для нижнего листа
        val behavior = BottomSheetBehavior.from(bottomSheet!!)

        // Запрещаем закрытие с помощью свайпа вниз
        behavior.isDraggable = false

        // Запрещаем закрытие при нажатии на свободное пространство
        dialog.setCancelable(false)
    }


    private fun viewFilterFlagUI(stateFilterUI: RequestWorkFilterUI) {
        binding.apply {
            ivSelectOther.isGone = !stateFilterUI.isOther
            ivSelectDispatcher.isGone = !stateFilterUI.isDispatcher
            ivSelectChiefEngineer.isGone = !stateFilterUI.isChiefEngineer
            ivSelectClosed.isGone = !stateFilterUI.isClosed
        }
    }
}