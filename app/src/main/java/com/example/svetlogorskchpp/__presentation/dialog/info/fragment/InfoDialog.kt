package com.example.svetlogorskchpp.__presentation.dialog.info.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.example.svetlogorskchpp.R
import com.example.svetlogorskchpp.__presentation.dialog.BaseBottomSheetDialog
import com.example.svetlogorskchpp.__presentation.dialog.info.adapter.InfoAdapter
import com.example.svetlogorskchpp.__presentation.dialog.info.factory.InfoViewModelFactory
import com.example.svetlogorskchpp.__presentation.dialog.info.view_model.InfoViewModel
import com.example.svetlogorskchpp.databinding.DialogInfoBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class InfoDialog: BaseBottomSheetDialog<DialogInfoBinding>() {

    private val args: InfoDialogArgs by navArgs()

    private val adapter = InfoAdapter()

    @Inject
    lateinit var viewModelFactory: InfoViewModelFactory
    private val viewModel: InfoViewModel by viewModels {
        InfoViewModel.providesFactory(
            assistedFactory = viewModelFactory,
            info = args.info
        )
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): DialogInfoBinding {
        return DialogInfoBinding.bind(
            inflater.inflate(R.layout.dialog_info, container)
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            rv.adapter = adapter
            tvName.text = args.info.str
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.contentState.collect {
                    adapter.submitList(it)
                }
            }
        }

    }
}