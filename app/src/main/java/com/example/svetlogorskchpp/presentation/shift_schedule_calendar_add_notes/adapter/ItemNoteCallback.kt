package com.example.svetlogorskchpp.presentation.shift_schedule_calendar_add_notes.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.svetlogorskchpp.domain.model.Note

class ItemNoteCallback: DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem == newItem
    }
}