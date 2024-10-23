package com.example.svetlogorskchpp.__presentation.dialog.shift_schedule.notes_notification.fragment

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
import com.example.svetlogorskchpp.__presentation.dialog.BaseBottomSheetDialog
import com.example.svetlogorskchpp.__presentation.dialog.shift_schedule.notes_notification.model.CalendarNotificationUI
import com.example.svetlogorskchpp.__presentation.dialog.shift_schedule.notes_notification.view_model.NotesNotificationViewModel
import com.example.svetlogorskchpp.databinding.DialogNotesNotificationBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NotesNotificationDialog: BaseBottomSheetDialog<DialogNotesNotificationBinding>() {

    private val viewModel: NotesNotificationViewModel by viewModels()

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): DialogNotesNotificationBinding {
        return DialogNotesNotificationBinding.bind(inflater.inflate(R.layout.dialog_notes_notification, container))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            tvMyNotesNotification.setOnClickListener {
               viewModel.clickNotesNotification()
            }
            tvRequestWorkNotification.setOnClickListener {
                viewModel.clickRequestWorkNotification()
            }
            tvViewRequestWork.setOnClickListener {
               viewModel.clickRequestWorkViewCalendar()
            }
            ivExit.setOnClickListener {
                dismiss()
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.calendarNotesNotificationUIState.collect { stateUI ->
                   viewSelectNotification(stateUI)
                }
            }
        }
    }

    private fun viewSelectNotification( calendarNotificationUI: CalendarNotificationUI) {
        binding.apply {
            ivMyNotesNotification.isGone = !calendarNotificationUI.isNotesNotification
            ivRequestWorkNotification.isGone = !calendarNotificationUI.isRequestWorkNotification
            ivViewRequestWork.isGone = !calendarNotificationUI.isRequestWorkViewCalendar
        }
    }
}