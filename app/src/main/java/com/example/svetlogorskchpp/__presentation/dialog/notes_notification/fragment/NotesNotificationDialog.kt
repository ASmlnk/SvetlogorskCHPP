package com.example.svetlogorskchpp.__presentation.dialog.notes_notification.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.svetlogorskchpp.R
import com.example.svetlogorskchpp.__presentation.dialog.BaseBottomSheetDialog
import com.example.svetlogorskchpp.databinding.DialogNotesNotificationBinding

class NotesNotificationDialog: BaseBottomSheetDialog<DialogNotesNotificationBinding>() {
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): DialogNotesNotificationBinding {
        return DialogNotesNotificationBinding.bind(inflater.inflate(R.layout.dialog_notes_notification, container))
    }
}